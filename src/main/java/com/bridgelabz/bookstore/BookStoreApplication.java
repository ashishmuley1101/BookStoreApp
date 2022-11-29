package com.bridgelabz.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class BookStoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(BookStoreApplication.class, args);
		log.info("Book Store App Started in {} Environment",context.getEnvironment().getProperty("environment"));
		log.info("Book Store DB user is {}",context.getEnvironment().getProperty("spring.datasource.username"));

	}

}
