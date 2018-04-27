package com.emt.bpay.controller;

import com.emt.base.entity.ReturnObject;
import com.emt.bpay.BpayServerApplication;
import com.emt.bpay.server.inter.IPayCheckSv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/PayCheck")
@SpringApplicationConfiguration(classes = BpayServerApplication.class)
public class PayCheckController
{
    private static Logger logger = LoggerFactory
            .getLogger(PayCheckController.class);

   // @Resource(name = "payCheckSvImpl", type = PayCheckSvImpl.class)
   @Autowired
    private IPayCheckSv payCheckSv;

    @RequestMapping("/GetUnWithdrawMoney")
    public ReturnObject getUnWithdrawMoney()
    {
        logger.info("------------------------------------------------进入PayCheck  Controller  getUnWithdrawMoney ---------------------------------");
        List<String> msg = new ArrayList<String>();
        List<Map<String, Object>> list = payCheckSv.getUnWithdrawMoney();
        if (null == list || 0 == list.size())
        {
            msg.add("没有提现数据!");
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
        } else
        {
            return new ReturnObject(ReturnObject.SuccessEnum.success, "成功", list, list.size());
        }
    }



   @RequestMapping("/buy")
    public ReturnObject buy(String buy_time ){
       List<String> msg = new ArrayList<String>();
       List<Map<String, Object>> list = payCheckSv.buy(buy_time);
       if (null == list || 0 == list.size())
       {
           msg.add("没有数据!");
           return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
       } else
       {
           return new ReturnObject(ReturnObject.SuccessEnum.success, "成功", list, list.size());
       }
   }
}
