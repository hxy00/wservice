package com.emt.bpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*
https://github.com/abel533/MyBatis-Spring-Boot
https://github.com/pagehelper/Mybatis-PageHelper
http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
http://git.oschina.net/free/Mybatis_PageHelper
*/

@EnableEurekaClient
@SpringBootApplication
public class BpayServerApplication
{
    public static void main(String[] args)  throws Exception
    {
            SpringApplication.run(BpayServerApplication.class, args);
    }
}
