package com.waiyan.restaurantwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(scanBasePackages = "com.waiyan.restaurantwebapp")
@ComponentScan(basePackages = "com.waiyan.restaurantwebapp")
@EnableMongoRepositories(basePackages = "com.waiyan.restaurantwebapp.repository")
public class RestaurantWebAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantWebAppApplication.class, args);
	}

	@Override
  	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(RestaurantWebAppApplication.class);
  }

}
