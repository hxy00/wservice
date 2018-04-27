package com.emt.task.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="EMT-BPAY-SERVICE", fallback = BpayServiceClientHystrix.class)
public interface IBPayService
{
    @RequestMapping("/PayCheck/GetUnWithdrawMoney")
    String GetUnWithdrawMoney();


    @RequestMapping(value = "/PayCheck/buy")
    String buy(@RequestParam(value = "buy_time")  String buy_time);

}
