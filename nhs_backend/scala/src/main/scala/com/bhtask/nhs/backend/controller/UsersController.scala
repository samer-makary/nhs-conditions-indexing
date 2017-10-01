package com.bhtask.nhs.backend.controller

import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

@RestController
@RequestMapping(path = Array("/users"))
class UsersController {

  @GetMapping(path = Array("/authorized"))
  def authorized(): Boolean = true
}
