package com.calathea.navi;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {RedissonAutoConfiguration.class})
public class NaviApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaviApplication.class, args);
	}

}
