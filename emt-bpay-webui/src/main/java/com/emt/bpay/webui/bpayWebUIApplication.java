package com.emt.bpay.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by dsj on 2017/3/17.
 */
@SpringBootApplication
@EnableEurekaClient
public class bpayWebUIApplication
{
    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(bpayWebUIApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
