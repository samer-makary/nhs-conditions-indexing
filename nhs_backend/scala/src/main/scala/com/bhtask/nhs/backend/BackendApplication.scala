package com.bhtask.nhs.backend

import java.text.SimpleDateFormat
import java.util.Date

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.{HttpSecurity, WebSecurity}
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService

@SpringBootApplication
@EnableConfigurationProperties(Array(classOf[BackendProperties]))
class BackendApplication(@Autowired val jdbcUserDetailsService: UserDetailsService) {

  @Bean def setupSecurity = new WebSecurityConfigurerAdapter(true) {
    override def configure(web: WebSecurity) = {
      web.ignoring()
        .antMatchers("/", "/index.html", "/nhs/**", "/conditions/count", "/conditions/search", "/conditions/all")
    }

    override def configure(http: HttpSecurity) = {
      http.authorizeRequests()
        .antMatchers("/conditions/loadIndex").hasAuthority("ADMIN")
        .antMatchers("/users/**").hasAnyAuthority("USER", "ADMIN")
        .anyRequest().fullyAuthenticated()
      http.httpBasic().and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .csrf().disable()
    }

    override def configure(auth: AuthenticationManagerBuilder) = {
      auth.userDetailsService(jdbcUserDetailsService)
    }
  }

}

object BackendApplicationMain extends App {
  val df = new SimpleDateFormat
  println("Starting NHS backend service at: " + (df format new Date))
  SpringApplication.run(classOf[BackendApplication])
}
