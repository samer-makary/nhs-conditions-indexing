# -*- coding: utf-8 -*-
import scrapy


class NhsconditionsSpider(scrapy.Spider):
    name = 'nhsConditions'
    allowed_domains = ['http://www.nhs.uk/Conditions/Pages/BodyMap.aspx']
    start_urls = ['http://http://www.nhs.uk/Conditions/Pages/BodyMap.aspx/']

    def parse(self, response):
        pass
