package com.emt.bpay.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by dsj on 2017/3/27.
 */
@SpringBootApplication
public class BPayWebApplication
{
    public static void main(String[] args)
    {
        new SpringApplicationBuilder(BPayWebApplication.class).web(true).run(args);
    }
}
