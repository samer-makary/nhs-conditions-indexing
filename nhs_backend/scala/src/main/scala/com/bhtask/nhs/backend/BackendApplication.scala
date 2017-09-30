package com.bhtask.nhs.backend

import java.text.SimpleDateFormat
import java.util.Date

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.{HttpSecurity, WebSecurity}
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@SpringBootApplication
@EnableConfigurationProperties(Array(classOf[BackendProperties]))
class BackendApplication {

  @Bean def setupSecurity = new WebSecurityConfigurerAdapter(true) {
    override def configure(web: WebSecurity) = {
      web.ignoring()
        .antMatchers("/", "/index.html", "/nhs/**", "/conditions/count", "/conditions/search", "/conditions/all")
    }
  }

}

object BackendApplicationMain extends App {
  val df = new SimpleDateFormat
  println("Starting NHS backend service at: " + (df format new Date))
  SpringApplication.run(classOf[BackendApplication])
}
