package com.bhtask.nhs.backend.dao

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.{Document, Field, FieldIndex, FieldType}

import scala.beans.BeanProperty

@Document(indexName = "nhs", `type` = "conditions")
class Condition {

  @Id
  @BeanProperty
  @JsonProperty("url")
  @Field(index = FieldIndex.no, `type` = FieldType.String)
  var url: String = null

  @BeanProperty
  @JsonProperty("title")
  @Field(index = FieldIndex.not_analyzed, `type` = FieldType.String)
  var title: String = null

  @BeanProperty
  @JsonProperty("content")
  @Field(index = FieldIndex.analyzed, analyzer = "english", `type` = FieldType.String)
  var content: String = null
}

object Condition {
  final val FIELD__URL: String = "url"
  final val FIELD__TITLE: String = "title"
  final val FIELD__CONTENT: String = "content"
}
