package com.emt.bpay.feign.controller;


import com.emt.common.utils.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController
{
    @RequestMapping(value = "/GetCurrentMonth")
    public String GetCurrentMonth(){
          return DateUtils.getMonth();
    }

    @RequestMapping(value = "/GetCurrentYear")
    public String GetCurrentYear(){
        return DateUtils.getYear();
    }
}
