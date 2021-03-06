package com.emt.bpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BPayServiceApplication {
    public static void main(String[] args)  throws Exception
    {
        SpringApplication.run(BPayServiceApplication.class, args);
    }
}
