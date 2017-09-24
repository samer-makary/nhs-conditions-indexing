package com.bhtask.nhs.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class BackendApplication {

    @Bean
    public WebSecurityConfigurerAdapter setupSecurity() {
	return new WebSecurityConfigurerAdapter(true) {

	    @Override
	    public void configure(WebSecurity web) throws Exception {
		// allow everything, just for demo purposes.
		web.ignoring().anyRequest();
	    }
	};
    }

    public static void main(String[] args) {
	SpringApplication.run(BackendApplication.class, args);
    }
}
