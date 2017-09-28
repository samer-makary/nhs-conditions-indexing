package com.bhtask.nhs.backend

import java.text.SimpleDateFormat
import java.util.Date

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(Array(classOf[BackendProperties]))
class BackendApplication

object BackendApplicationMain extends App {
  val df = new SimpleDateFormat
  println("Starting NHS backend service at: " + (df format new Date))
  SpringApplication.run(classOf[BackendApplication])
}
