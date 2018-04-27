package com.emt.bpay.webui.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by dsj on 2017/3/17.
 */
@RestController

public class bpayWebUIController
{
    @Autowired
    RestTemplate restTemplate;


    final String SERVICE_NAME = "EMT-BPAY-SERVICE";

    //@HystrixCommand(fallbackMethod = "hello")
    @RequestMapping(value = "/PayAccountByPayeeId")
    public String PayAccountByPayeeId(String company_code, String payee_id)
    {
        return restTemplate.getForObject("http://" + SERVICE_NAME + "/BPay/PayAccount/GetPayAccountByPayeeId?company_code=" + company_code + "&payee_id=" + payee_id, String.class);
    }

}
