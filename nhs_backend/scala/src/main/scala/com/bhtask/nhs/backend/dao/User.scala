package com.bhtask.nhs.backend.dao

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.{Document, Field}

import scala.beans.BeanProperty

@Document(collection = "users")
class User {

  @Id
  @BeanProperty
  @JsonProperty
  var id: String = null

  @BeanProperty
  @JsonProperty
  @Field("username")
  var username: String = null

  @BeanProperty
  @JsonIgnore
  @Field("password")
  var password: String = null

  @BeanProperty
  @JsonProperty
  @Field("name")
  var name: String = null

  @BeanProperty
  @JsonProperty
  @Field("authorities")
  var authorities: Array[String] = null
}

object User {
  final val FIELD__USERNAME = "username"
}
