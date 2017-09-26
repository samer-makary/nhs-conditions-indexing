package com.bhtask.nhs.backend.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConditionsRepository extends ElasticsearchRepository<Condition, String> {

}
