package com.bhtask.nhs.backend.service

import java.lang

import com.bhtask.nhs.backend.BackendProperties
import com.bhtask.nhs.backend.dao.{Condition, ConditionsRepository}
import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders._
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._
import scala.io.Source

@Service
class ElasticsearchConditionsServiceImpl(@Autowired val conditionsDao: ConditionsRepository,
                                         @Autowired val backendProps: BackendProperties)
  extends ConditionsService {

  private val logger: Logger = LoggerFactory.getLogger(classOf[ElasticsearchConditionsServiceImpl])

  override def search(query: String): Iterable[Condition] = {
    def queryBuilder: QueryBuilder = new NativeSearchQueryBuilder()
      .withQuery(matchQuery(Condition.FIELD__CONTENT, query)
        .minimumShouldMatch("100%"))
      .build().getQuery

    conditionsDao.search(queryBuilder).asScala
  }

  override def getAllLinks: Iterable[String] = conditionsDao.findAll().asScala.map(c => c.getTitle)

  override def getCount: Long = conditionsDao.count()

  override def loadIndex(): Boolean = {
    val dataFile = Source.fromFile(backendProps.data)
    try {
      val parser = new ObjectMapper

      logger info "Loading data file line-by-line into ES index."
      val conditions: lang.Iterable[Condition] = dataFile.getLines()
        .map(line => parser.readValue(line, classOf[Condition]))
        .toIterable.asJava

      conditionsDao.save(conditions)
      logger info "Finished data loading into ES index successfully."
      return true
    } catch {
      case t: Throwable => logger.error("Failed to load data file into index.", t)
    } finally {
      dataFile close
    }
    false
  }
}
