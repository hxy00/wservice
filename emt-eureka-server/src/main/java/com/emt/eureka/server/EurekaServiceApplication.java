package com.emt.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by dsj on 2017/3/14.
 */
@EnableEurekaServer         //开启eureka服务
@SpringBootApplication
public class EurekaServiceApplication
{
    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);

    }
}
