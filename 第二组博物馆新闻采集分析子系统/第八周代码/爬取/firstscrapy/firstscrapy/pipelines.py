# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
#import pymsql
#一个管道类对应一种存储方式
class FirstscrapyPipeline:
    conn=None
    #只在开始爬虫时调用
    def open_spider(self, spider):
        print("开始爬虫。。。。")


    # 每接收到一个item就被调用一次
    def process_item(self, item, spider):
        title = item['title']
        return item

    def close_spider(self, spider):
        print("结束爬虫！")
