package com.bhtask.nhs.backend;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.bhtask.nhs.backend")
public class BackendProperties {

    private String dataPath;

    public String getDataPath() {
	return dataPath;
    }

    public void setDataPath(String dataPath) {
	this.dataPath = dataPath;
    }

}
