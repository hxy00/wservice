package com.emt.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;


@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@ImportResource(locations={"classpath:spring-task.xml"})
public class taskApplication
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("---------------------------begin   taks ---------------------------");
        SpringApplication.run(taskApplication.class, args);
    }


    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
