package com.bhtask.nhs.backend.controller

import java.util

import com.bhtask.nhs.backend.dao.Condition
import com.bhtask.nhs.backend.service.ConditionsService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

import scala.collection.JavaConverters._

@RestController
@RequestMapping(path = Array("/conditions"))
class ConditionsController(@Autowired val conditionsService: ConditionsService) {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[ConditionsController])

  @GetMapping(path = Array("/alive"))
  def alive(): String = "Yes"

  @GetMapping(path = Array("/all"))
  def all(): util.Collection[String] = {
    logger debug "Received request for all conditions links."
    conditionsService.getAllLinks.asJavaCollection
  }

  @GetMapping(path = Array("/count"))
  def count(): Long = {
    logger debug "Received request for conditions count."
    conditionsService getCount
  }

  @GetMapping(path = Array("/search"))
  def search(@RequestParam query: String): util.Collection[Condition] = {
    logger debug "Received search query for conditions."
    conditionsService.search(query).asJavaCollection
  }

  @PostMapping(path = Array("/loadIndex"))
  def loadIndex(): String = {
    logger debug "Received request for loading data into search index."
    if (conditionsService loadIndex) {
      "Succeeded!"
    } else {
      "Failed to (re)load data into index."
    }
  }
}
