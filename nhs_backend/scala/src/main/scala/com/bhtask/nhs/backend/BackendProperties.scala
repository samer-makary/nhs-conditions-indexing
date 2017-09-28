package com.bhtask.nhs.backend

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties
class BackendProperties {

  @Value("${com.bhtask.nhs.backend.data-path}")
  var data: String = null

}
