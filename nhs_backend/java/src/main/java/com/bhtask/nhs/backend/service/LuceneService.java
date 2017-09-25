package com.bhtask.nhs.backend.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhtask.nhs.backend.BackendProperties;

@Service("lucene")
public class LuceneService implements ConditionsService {

    private static final Logger logger = LoggerFactory.getLogger(LuceneService.class);

    @Autowired
    private BackendProperties backendProps;

    @PostConstruct
    private void loadData() {
	final File dataFile = new File(backendProps.getDataPath());
	if (!dataFile.exists())
	    logger.error("Data file does not exist at path: {}.", backendProps.getDataPath());
	else
	    logger.info("Starting Lucene index building from data file...");

    }

    @Override
    public void search(String query) {
	// TODO Auto-generated method stub

    }

}
