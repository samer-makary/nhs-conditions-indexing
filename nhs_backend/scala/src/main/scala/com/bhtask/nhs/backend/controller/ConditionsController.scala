package com.bhtask.nhs.backend.controller

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

@RestController
@RequestMapping(path = Array("/conditions"))
class ConditionsController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[ConditionsController])

  @GetMapping(path = Array("/alive"))
  def alive(): String = "Yes"

  @GetMapping(path = Array("/count"))
  def count(): Long = {
    logger debug "Received request for conditions count."
    -1
  }
}
