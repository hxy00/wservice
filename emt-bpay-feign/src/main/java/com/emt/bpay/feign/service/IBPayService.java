package com.emt.bpay.feign.service;

import com.emt.bpay.feign.config.CommonConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/3/22.
 */
@FeignClient(value="EMT-BPAY-SERVICE", fallback = BpayServiceClientHystrix.class,configuration = CommonConfig.class)
public interface IBPayService
{
    @RequestMapping(value = "/PayAccount/PayAccountByPayeeId")
    String PayAccountByPayeeId(@RequestParam(value = "paramJson") String paramJson);

    @RequestMapping(value = "/PayAccount/AccountByPage")
    String accountByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "accountJson") String accountJson);

    @RequestMapping("/PayAccount/SetDeposit")
    String setDeposit(@RequestParam(value = "depositJson") String depositJson);

    @RequestMapping("/PayAccount/LedgersByPayeeId")
    String LedgersByPayeeId(@RequestParam(value = "company_code") String company_code, @RequestParam(value = "payee_id") String payee_id);

    @RequestMapping(value = "/PayAccount/BankCardByPayeeId")
    String BankCardByPayeeId(@RequestParam(value = "paramJson") String paramJson);

   // @RequestMapping(method = RequestMethod.POST,value = "/PayAccount/InsertPayBankCardApply", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   //String InsertPayBankCardApply(@RequestParam(value = "accountJson") String accountJson,  @RequestPart(value ="file") MultipartFile file);
   @RequestMapping(value = "/PayAccount/InsertPayBankCardApply")
    String InsertPayBankCardApply(@RequestParam(value = "accountJson") String accountJson);

    @RequestMapping(value = "/PayAccount/CancelApply")
    String CancelApply(@RequestParam(value = "paramJson") String paramJson);

    /***
     * 根据SN获取银行卡信息
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "/PayAccount/BankCardBySn")
    String BankCardBySn(@RequestParam(value = "paramJson")String paramJson);

    @RequestMapping("/PayCheck/GetUnWithdrawMoney")
    String GetUnWithdrawMoney();


    @RequestMapping("/PayCurrent/DocTypeGroupReport")
    String DocTypeGroupReport(@RequestParam(value = "paramJson") String paramJson);


    @RequestMapping(value = "/PayCurrent/CurrentByPage")
    String CurrentByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "accountJson") String accountJson);

    @RequestMapping(value = "/PayCurrent/ExportCurrent")
    List<Map<String, Object>> ExportCurrent(@RequestParam(value = "paramJson") String paramJson);

    @RequestMapping(value = "/PayAccount/BankCardApplyByPage")
    String BankCardApplyByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "paramJson") String paramJson);
}
