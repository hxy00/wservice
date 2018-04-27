package com.emt.task.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BpayServiceClientHystrix  implements IBPayService
{
    Logger logger = LoggerFactory
            .getLogger(BpayServiceClientHystrix.class);

    @Override
    public String GetUnWithdrawMoney()
    {
        logger.error("Feign: 调用GetUnWithdrawMoney 获取提现未完成列表  失败！");
        return "Feign: 调用GetUnWithdrawMoney  获取提现未完成列表 失败！";
        // List<String> _list = new ArrayList<String>();
        // _list.add("Feign: 调用LedgersByPayeeId失败!");
        //return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
    }


    @Override
    public String buy( String buy_time)
    {
        logger.error("Feign:购买  失败！");
        return "Feign: 购买 失败！";
        // List<String> _list = new ArrayList<String>();
        // _list.add("Feign: 调用LedgersByPayeeId失败!");
        //return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
    }
}
