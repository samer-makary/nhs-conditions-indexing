# -*- coding: utf-8 -*-
import scrapy
from functools import partial


class NhsconditionsSpider(scrapy.Spider):
	name = 'nhsConditions'
	start_urls = ['http://www.nhs.uk/Conditions/Pages/hub.aspx']
	exclude = [
		'starts-with(@href, "http://www.nhs.uk/ServiceDirectories/")',
		'starts-with(@href, "http://www.nhs.uk/servicedirectories/")',
		'starts-with(@href, "http://www.nhs.uk/Tools/")',
		'starts-with(@href, "http://www.nhs.uk/tools/")',
	]


	def parse(self, response):
		# scan the every entry in the index
		for index_entry in response.xpath('//div[@id="haz-mod1"]/ul/li'):
			entry_url = response.urljoin(index_entry.xpath('./a/@href').extract_first())
			yield scrapy.Request(entry_url, callback=self._parse_index_page)


	def _parse_index_page(self, response):
		# scan for index sections in the page
		links_selector = './/a[%s]' % (' and '.join(map(lambda c: 'not(%s)' % c, self.exclude)))
		for sec_conds in response.css('.index-section').xpath('./ul'):
			for condition in sec_conds.xpath(links_selector):
				cond_title = condition.xpath('./text()').extract_first()
				cond_url = condition.xpath('./@href').extract_first()
				full_uri = response.urljoin(cond_url)
				yield scrapy.Request(full_uri, callback=partial(self._parse_condition, cond_title, full_uri))


	def _parse_condition(self, title, url, response):
		content = response.css('.main-content').select('normalize-space(.)').extract_first()
		if not content:
			# beta NHS content
			content = response.css('article').select('normalize-space(.)').extract_first()
		if not content:
			# NHS live-well content
			content = response.css('.live-well').select('normalize-space(.)').extract_first()
		if not content:
			# NHS article
			content = response.css('.article').select('normalize-space(.)').extract_first()

		yield {
			"title": title,
			"url": url,
			"content": content.strip().encode('utf-8') if content else None
		}