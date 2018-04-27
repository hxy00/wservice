package com.emt.epay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
public class EpayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpayServerApplication.class, args);
	}
}
