package com.emt.bpay.feign.controller;

import com.emt.bpay.feign.service.IBPayService;
import com.emt.common.json.JSONObject;
import com.emt.common.utils.ExcelUtil;
import com.emt.common.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by dsj on 2017/3/22.
 */
@RestController
public class BPayServerController
{
    Logger logger = LoggerFactory.getLogger(BPayServerController.class);

    public static final String ftpIp = "10.8.2.215";  //"112.124.21.23";
    public static final int ftpPort = 2121;
    public static final String ftpUserName= "emt_ftp";
    public static final String ftpPassword="YS#^*!3681";


    @Autowired
    private IBPayService bPayService;

    @Autowired
    private LoadBalancerClient client;

    @RequestMapping(value = "/PayAccountByPayeeId")
    public String PayAccountByPayeeId(@RequestParam(value = "paramJson") String paramJson){
        String _return = bPayService.PayAccountByPayeeId(paramJson);
        logger.info("PayAccountByPayeeId，返回：{}", _return);
        return _return;
    }


    @RequestMapping(value = "/AccountByPage")
    public String accountByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "accountJson") String accountJson){
        String _json = null;
        try
        {
            _json = URLDecoder.decode(accountJson,"UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        logger.info("Feign Controller, 帐户信息分页，pageIndex = {}, pageSize = {}, accountJson = {}", pageIndex, pageSize, _json);
        return bPayService.accountByPage(pageIndex, pageSize, _json);
    }


    @RequestMapping(value = "/SetDeposit")
    public String setDeposit(@RequestParam(value = "depositJson") String depositJson){
        logger.info("Feign Controller, 设置保底金额, depositJson = {}", depositJson);
        return bPayService.setDeposit(depositJson);
    }

    @RequestMapping(value = "/LedgersByPayeeId")
    public String  LedgersByPayeeId(@RequestParam(value = "company_code") String company_code, @RequestParam(value = "payee_id") String payee_id){
        logger.info("Feign Controller, 获取总账信息, company_code = {}, payee_id ={}",company_code, payee_id);
        return bPayService.LedgersByPayeeId(company_code, payee_id);
    }

    @RequestMapping(value = "/BankCardByPayeeId")
    public String  BankCardByPayeeId(@RequestParam(value = "paramJson") String paramJson){
        logger.info("Feign Controller, 获取总账信息,参数{}",paramJson);
        return bPayService.BankCardByPayeeId(paramJson);
    }

    /***
     * 根据SN获取银行卡信息
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "/BankCardBySn")
    public String BankCardBySn(@RequestParam(value = "paramJson")String paramJson){
        String _reStr =  bPayService.BankCardBySn(paramJson);
        logger.info("根据SN获取银行卡信息，返回数据：{}", _reStr);
        return _reStr;
    }

    @RequestMapping(value = "/InsertPayBankCardApply")
    public String  InsertPayBankCardApply(@RequestParam(value = "accountJson") String accountJson,  @RequestPart("file") MultipartFile file){
        logger.info("Feign Controller, 插入银行卡申请,");
        logger.info("Feign Controller, 文件大小 {},", file.getSize());
       /* try {
            logger.info("文件：{}", convertStreamToString(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Date dt = new Date();
        JSONObject jobj = null;
        try {
            jobj = new JSONObject(URLDecoder.decode(accountJson, "UTF-8"));
            String _fileName = jobj.get("payee_id").toString()+"_"+ dt.getTime()+getExtensionName(file.getOriginalFilename());
            jobj.put("file_name", _fileName );
            FTPUtil.uploadFile(ftpIp, ftpPort, ftpUserName, ftpPassword, _fileName, file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String _ret = bPayService.InsertPayBankCardApply(jobj.toString());
        logger.info("Feign Controller, 插入银行卡申请,返回数据--->{}", _ret);
        return _ret;
    }


    @RequestMapping(value = "/CancelApply")
    public String  CancelApply(@RequestParam(value = "paramJson") String paramJson){

        String _ret = bPayService.CancelApply(paramJson);
        logger.info("Feign Controller, 取消银行卡申请,返回数据--->{}", _ret);
        return _ret;
    }

    @RequestMapping(value = "/GetUnWithdrawMoney")
    public String  GetUnWithdrawMoney(){
        logger.info("Feign Controller, 调用GetUnWithdrawMoney 获取提现未完成列表 ");
        return bPayService.GetUnWithdrawMoney();
    }

    @RequestMapping(value = "/DocTypeGroupReport")
    public String  DocTypeGroupReport(@RequestParam(value = "paramJson") String paramJson){
        logger.info("Feign Controller, 调用DocTypeGroupReport 按凭证类型进行分组统计 ");
        String _return = bPayService.DocTypeGroupReport(paramJson);
        logger.info("DocTypeGroupReport，返回：{}", _return);
        return _return;

    }

    @RequestMapping(value = "/CurrentByPage")
    public String  CurrentByPage(@RequestParam(value = "pageIndex") int pageIndex, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "accountJson") String accountJson){

        logger.info("Feign Controller, 往来帐分页，pageIndex = {}, pageSize = {}, accountJson = {}", pageIndex, pageSize, accountJson);
        String _return = bPayService.CurrentByPage(pageIndex, pageSize, accountJson);
        logger.info("Feign Controller, 往来帐分页返回结果字符串：{}", _return);
        return _return;
    }



    @RequestMapping(value = "/ExportCurrent")
    public String  ExportCurrent(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "company_code")  String company_code,@RequestParam(value = "payee_id")  String payee_id, @RequestParam(value = "year")  String year,@RequestParam(value = "month")  String month,@RequestParam(value = "user_id")  String user_id, @RequestParam(value = "token")  String token){

       // logger.info("Feign Controller, 往来帐下载，  accountJson = {}",  accountJson);
        String columnNames[] = { "凭证号", "摘要", "收入", "支出", "余额","记帐时间" };// 列名
        String keys[] = { "doc_code", "abstract", "debit", "credit", "banlance","reg_time"};// map中的key

        JSONObject json  = new JSONObject();
        try
        {
            json.put("company_code", company_code);
            json.put("payee_id", payee_id);
            json.put("year", year);
            json.put("month", month);
            json.put("user_id", user_id);
            json.put("token", token);

        }catch (Exception e){
            logger.error("出错：{}", e.getMessage());
        }


        List<Map<String, Object>> list =  bPayService.ExportCurrent(json.toString());
        if (list.size() > 0) {
            String fileName = String.format("%s_%s%s", payee_id, year, month);

            try
            {
                ExcelUtil.export(list, fileName, keys, columnNames, response);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return  list.size() + "条数据已导出";
        } else {
            return "没有数据可导出";
        }
    }


    @RequestMapping(value = "/BankCardApplyByPage")
    public String BankCardApplyByPage(@RequestParam(value = "pageIndex") int pageIndex,
                                      @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "paramJson") String paramJson)
    {
        String _json = null;
        try
        {
            _json = URLDecoder.decode(paramJson,"UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        String _return = bPayService.BankCardApplyByPage(pageIndex, pageSize, _json);
        return _return;
    }


    /*
* Java文件操作 获取文件扩展名
*
*  Created on: 2011-8-2
*      Author: blueeagle
*/
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return "."+filename.substring(dot + 1);
            }
        }
        return "."+filename;
    }
}
