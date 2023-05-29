package com.app.forecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LastWeekForecastApp {

	public static void main(String[] args) {
		SpringApplication.run(LastWeekForecastApp.class, args);
	}
}
