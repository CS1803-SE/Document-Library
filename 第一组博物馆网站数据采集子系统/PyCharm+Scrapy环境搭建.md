## PyCharm安装和配置

### 下载

官网下载专业版，可免费试用30天

### 安装

安装设置时有四个选项需要注意：

Create Desktop Shortcut（创建桌面快捷方式）：根据系统选择32位和64位

Update PATH variable（将PyCharm添加至环境变量）：可从cmd中启动PyCharm（好像没什么用？）

Update Context Menu（更新上下文菜单）：右键时多一个快捷选项“把当前文件夹以IDEA的项目打开”

Create Associations（创建关联）：所选后缀文件以PyCharm作为默认打开方式

### 创建第一个项目

![image-20210414222421560](https://gitee.com/zhu-an/picture-bed/raw/master/img/20210414222429.png)

默认环境为Virtualenv，即根据本地环境虚拟出的Python环境，该环境下install的package仅环境内可用，可防止项目共用同一个site-packages导致项目过于臃肿

若不想使用虚拟环境可勾选下面的Previously configured interpreter使用本地Python环境

## Scrapy安装和配置

### 安装

打开IDE配置文件File -> Settings，如下页面点击“+”添加新的package

![image-20210414223415802](https://gitee.com/zhu-an/picture-bed/raw/master/img/20210414223415.png)

搜索Scrapy进行下载即可，PyCharm会自动解决依赖问题，下载Scrapy及其依赖的Package

注意：默认下载源仓库为国外仓库，推荐更换下载源为国内仓库

三个国内靠谱的pip镜像源：

阿里: http://mirrors.aliyun.com/pypi/simple/

清华: https://pypi.tuna.tsinghua.edu.cn/simple

豆瓣: http://pypi.douban.com/simple/

![image-20210414223745255](https://gitee.com/zhu-an/picture-bed/raw/master/img/20210414223745.png)

![image-20210414224240287](https://gitee.com/zhu-an/picture-bed/raw/master/img/20210414224240.png)

### 测试

在PyCharm项目的Terminal中查看版本测试安装是否成功（这里使用本地cmd测试会提示no module）

![image-20210414224839646](https://gitee.com/zhu-an/picture-bed/raw/master/img/20210414224839.png)

### 创建第一个爬虫

在项目Terminal下创建爬虫项目mySpider

```bash
scrapy startproject mySpider
```

目录结构如下

![image-20210415095102186](C:\Users\10711\AppData\Roaming\Typora\typora-user-images\image-20210415095102186.png)

修改配置文件settings.py

```python
#####关闭robots.txt规则限制，否则只能爬robots.txt中配置好的网站#####
# Obey robots.txt rules
ROBOTSTXT_OBEY = False

#####取消以下注释#####
ITEM_PIPELINES = {
    'testSpider.pipelines.TestspiderPipeline': 300,
}

#####添加数据库连接配置#####
# 连接数据MySQL
# 数据库地址
MYSQL_HOST = '192.168.23.175'
# 数据库用户名:
MYSQL_USER = 'root'
# 数据库密码
MYSQL_PASSWORD = '@ZuBei502'
# 数据库端口
MYSQL_PORT = 3306
# 数据库名称
MYSQL_DBNAME = 'test'
# 数据库编码
MYSQL_CHARSET = 'utf8'
```

修改items.py

```python
# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy
from scrapy.loader import ItemLoader
from scrapy.loader.processors import TakeFirst

class SpiderItem(scrapy.Item):
    # define the fields for your item here like:
    name = scrapy.Field()
    title = scrapy.Field()

    pass

```

修改pipelines.py

```python
# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter

#这里如果提示没有pymysql包需要先下载
import pymysql
from twisted.enterprise import adbapi
# 异步更新操作
class TestspiderPipeline(object):
    def __init__(self, dbpool):
        self.dbpool = dbpool

    @classmethod
    def from_settings(cls, settings):  # 函数名固定，会被scrapy调用，直接可用settings的值
        """
        数据库建立连接
        :param settings: 配置参数
        :return: 实例化参数
        """
        adbparams = dict(
            host = settings['MYSQL_HOST'],
            db = settings['MYSQL_DBNAME'],
            user = settings['MYSQL_USER'],
            password = settings['MYSQL_PASSWORD'],
            cursorclass = pymysql.cursors.DictCursor  # 指定cursor类型
        )

        # 连接数据池ConnectionPool，使用pymysql或者Mysqldb连接
        dbpool = adbapi.ConnectionPool('pymysql', **adbparams)
        # 返回实例化参数
        return cls(dbpool)

    def process_item(self, item, spider):
        """
        使用twisted将MySQL插入变成异步执行。通过连接池执行具体的sql操作，返回一个对象
        """
        query = self.dbpool.runInteraction(self.do_insert, item)  # 指定操作方法和操作数据
        # 添加异常处理
        query.addCallback(self.handle_error)  # 处理异常

    def do_insert(self, cursor, item):
        # 对数据库进行插入操作，并不需要commit，twisted会自动commit
        insert_sql = """
        insert into teacher(name, title) VALUES (%s,%s)
        """
        cursor.execute(insert_sql, (item['name'], item['title']))

    def handle_error(self, failure):
        if failure:
            # 打印错误信息
            print(failure)



```

在spiders目录下创建第一个爬虫spiders
	
	
	import scrapy
	from ..items import SpiderItem
	#from items import SpiderItemLoader, SpiderItem
	# class collection75Item(scrapy.Item):
	# museumID = scrapy.Field()
	# collectionID = scrapy.Field()
	# collectionName = scrapy.Field()
	# collectionIntroduction = scrapy.Field()
	# collectionImage = scrapy.Field() # 图片链接
	class SpidersSpider(scrapy.Spider):
	    name = 'spiders' # 爬虫名
	    allowed_domains = ['itcast.cn'] # 允许爬虫的范围
	    start_urls = ['http://www.itcast.cn/channel/teacher.shtml'] # 最开始请求的url的地址
	
	    def parse(self, response):
	        li_list = response.xpath("//div[@class='tea_con']/div/ul/li")
	        print(li_list)
	        item = SpiderItem()
	
	        for li in li_list:
	            item['name'] = li.xpath(".//h3/text()").extract_first()
	            item['title'] = li.xpath(".//h4/text()").extract_first()
	            yield item
	

接着在项目Terminal下执行爬虫进行测试

```bash
scrapy crawl spiders
```

这里数据会爬取后会存储到配置好的数据库当中，注意开启Mysql用户的远程访问权限（本地Mysql无需开启）

```sql
--开启root用户任意ip远程访问的权限
use mysql;
update user set host = '%' where user = 'root';
```

![image-20210415100818470](C:\Users\10711\AppData\Roaming\Typora\typora-user-images\image-20210415100818470.png)

Success！