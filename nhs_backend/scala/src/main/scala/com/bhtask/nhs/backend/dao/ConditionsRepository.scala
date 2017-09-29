package com.bhtask.nhs.backend.dao

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

trait ConditionsRepository extends ElasticsearchRepository[Condition, String] {

}
