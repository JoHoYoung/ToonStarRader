package com.ntoon.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class AppApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger("com.ntoon");
  public static void main(String[] args) {
    SpringApplication.run(AppApplication.class, args);
    LOGGER.debug("TEST");
  }

}

