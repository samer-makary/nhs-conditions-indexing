package com.bhtask.nhs.backend.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.elasticsearch.index.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.bhtask.nhs.backend.BackendProperties;
import com.bhtask.nhs.backend.dao.Condition;
import com.bhtask.nhs.backend.dao.ConditionsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("elasticsearchConditionsServiceImpl")
public class ElasticsearchConditionsServiceImpl implements ConditionsService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConditionsServiceImpl.class);

    @Autowired
    private BackendProperties backendProps;

    @Autowired
    private ConditionsRepository conditionsDao;

    @PostConstruct
    private void loadData() {
	final File dataFile = new File(backendProps.getDataPath());
	if (!dataFile.exists()) {
	    logger.error("Data file does not exist at path: {}.", backendProps.getDataPath());
	    return;
	}

	logger.info("Starting Elasticsearch index building from data file...");

	try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
	    ObjectMapper parser = new ObjectMapper();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		Condition condition = parser.readValue(line, Condition.class);
		logger.debug("indexing condition with URL: {}.", condition.getUrl());
		conditionsDao.save(condition);
	    }
	    logger.info("Finished loading data and building index.");
	} catch (Exception e) {
	    logger.error("Failed to index data file into Elasticsearch.", e);
	}
    }

    @Override
    public List<Condition> search(String query) {
	// @formatter:off
	QueryBuilder queryBuilder = new NativeSearchQueryBuilder()
		.withQuery(matchQuery(Condition.FIELD__CONTENT, query)
			.minimumShouldMatch("100%"))
		.build().getQuery();
	// @formatter:on

	final List<Condition> links = new ArrayList<>();
	conditionsDao.search(queryBuilder).forEach(links::add);
	return links;
    }

    @Override
    public List<String> getAllLinks() {
	// @formatter:off
	QueryBuilder queryBuilder = new NativeSearchQueryBuilder()
		.withQuery(matchAllQuery())
		.build().getQuery();
	// @formatter:on

	final List<String> links = new ArrayList<>();
	conditionsDao.search(queryBuilder).forEach(c -> links.add(c.getUrl()));
	return links;
    }

    @Override
    public long getCount() {
	return conditionsDao.count();
    }

}
