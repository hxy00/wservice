package com.emt.bpay.feign.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017-04-10.
 */
@Component
public class BpayServiceClientHystrix implements IBPayService
{
    Logger logger = LoggerFactory
            .getLogger(BpayServiceClientHystrix.class);

    @Override
    public String PayAccountByPayeeId(@RequestParam(value = "paramJson") String paramJson)
    {
        logger.error("Feign 根据网点号获取网点信息！");
        return "根据网点号获取网点信息 失败";
    }

    @Override
    public String accountByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "accountJson") String accountJson)
    {
        logger.error("Feign 获取帐户信息（分页）失败！");
        return "Feign 获取帐户信息（分页）失败！";
        //return null;
    }


    @Override
    public String setDeposit(@RequestParam(value = "depositJson") String depositJson)
    {
        logger.error("Feign: 调用setDeposit失败！");
        return "Feign: 调用setDeposit失败！";
        //List<String> _list = new ArrayList<String>();
        //_list.add("Feign: 调用setDeposit失败!");
        //return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
    }

    @Override
    public String LedgersByPayeeId(@RequestParam(value = "company_code") String company_code, @RequestParam(value = "payee_id") String payee_id)
    {
        logger.error("Feign: 调用LedgersByPayeeId失败！");
        return "Feign: 调用LedgersByPayeeId失败！";
        // List<String> _list = new ArrayList<String>();
        // _list.add("Feign: 调用LedgersByPayeeId失败!");
        //return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
    }

    @Override
    public String BankCardByPayeeId(@RequestParam(value = "paramJson") String paramJson)
    {
        logger.error("Feign: 调用BankCardByPayeeId失败！");
        return "Feign: 调用BankCardByPayeeId失败！";

    }

    /***
     * 根据SN获取银行卡信息
     * @param paramJson
     * @return
     */
   @Override
    public String BankCardBySn(@RequestParam(value = "paramJson")String paramJson){
        logger.info("根据SN获取银行卡信息，参数：{}", paramJson);
        return "Feign:调用BankCardBySn 失败";
   }
/*
   @Override
    public String InsertPayBankCardApply(@RequestParam("accountJson") String accountJson, @RequestPart("file") MultipartFile file)
    {
        this.logger.error("Feign: 调用InsertPayBankCardApply失败！");
        return "Feign: 调用InsertPayBankCardApply失败！";
    }
*/
    @Override
    public String InsertPayBankCardApply(@RequestParam("accountJson") String accountJson)
    {
        this.logger.error("Feign: 调用InsertPayBankCardApply失败！");
        return "Feign: 调用InsertPayBankCardApply失败！";
    }

    @Override
    public String CancelApply(@RequestParam(value = "paramJson") String paramJson){
        this.logger.error("Feign: 调用CancelApply失败");
        return "Feign: 调用CancelApply失败";
    }

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
    public String DocTypeGroupReport(@RequestParam(value = "paramJson") String paramJson)
    {
        logger.error("Feign: 调用DocTypeGroupReport 按凭证类型进行分组统计 失败！");
        return "Feign: 调用DocTypeGroupReport  按凭证类型进行分组统计 失败！";
        // List<String> _list = new ArrayList<String>();
        // _list.add("Feign: 调用LedgersByPayeeId失败!");
        //return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
    }


    @Override
    public String CurrentByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "accountJson") String accountJson)
    {
        logger.error("Feign: 调用CurrentByPage 按凭证类型进行分组统计 失败！");
        return "Feign: 调用CurrentByPage  按凭证类型进行分组统计 失败！";
        // List<String> _list = new ArrayList<String>();
        // _list.add("Feign: 调用LedgersByPayeeId失败!");
        //return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
    }

    @Override
    public List<Map<String, Object>> ExportCurrent(@RequestParam(value = "paramJson") String paramJson)
    {
        logger.error("Feign: 调用CurrentByPage 按凭证类型进行分组统计 失败！");
        return null;
        // List<String> _list = new ArrayList<String>();
        // _list.add("Feign: 调用LedgersByPayeeId失败!");
        //return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
    }

    @Override
    public String BankCardApplyByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "paramJson") String paramJson)
    {
        logger.error("Feign: BankCardApplyByPage 获取申请列表 失败！");
        return "Feign:  BankCardApplyByPage 获取申请列表 失败！";
    }
}
