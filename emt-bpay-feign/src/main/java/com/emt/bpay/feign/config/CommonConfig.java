package com.emt.bpay.feign.config;

import feign.Request;
import feign.Retryer;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.servlet.MultipartConfigElement;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class CommonConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(1024000L * 1024000L);
        return factory.createMultipartConfig();
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }

    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }


    @Bean
    public Retryer feginRetryer(){
        Retryer retryer = new Retryer.Default(200, SECONDS.toMinutes(50), 1);
        return retryer;
    }

    @Bean
    Request.Options feignOptions() {

        return new Request.Options(600 * 1000000, 600 * 1000000);
    }

}
