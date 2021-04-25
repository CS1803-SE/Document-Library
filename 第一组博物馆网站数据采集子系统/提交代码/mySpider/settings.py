BOT_NAME = 'mySpider'

SPIDER_MODULES = ['mySpider.spiders']
NEWSPIDER_MODULE = 'mySpider.spiders'

LOG_LEVEL = "WARNING"

ROBOTSTXT_OBEY = False

ITEM_PIPELINES = {
    'mySpider.pipelines.MuseumPipeLine': 300,
    'mySpider.pipelines.CollectionPipeLine': 301,
    'mySpider.pipelines.ExhibitionPipeLine': 302,
}

FEED_EXPORT_ENCODING = 'utf-8-sig'

# 远程数据库
MYSQL_HOST = "46.17.172.103"
MYSQL_DBNAME = "u606804608_MuseumSpider"
MYSQL_USER = "u606804608_jerAx"
MYSQL_PASSWORD = "Password12345678"