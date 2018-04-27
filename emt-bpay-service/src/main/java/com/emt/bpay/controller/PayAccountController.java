package com.emt.bpay.controller;


import com.emt.base.entity.ReturnObject;
import com.emt.bpay.BPayServiceApplication;
import com.emt.bpay.rapi.BPayRemoteAPI;
import com.emt.bpay.server.inter.IPayAccountSv;
import com.emt.common.json.JSONException;
import com.emt.common.json.JSONObject;
import com.emt.common.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/PayAccount")
@SpringApplicationConfiguration(classes = BPayServiceApplication.class)
public class PayAccountController
{
    private static Logger logger = LoggerFactory
            .getLogger(PayAccountController.class);

    BPayRemoteAPI remoteAPI = new BPayRemoteAPI();

    @Autowired
    private IPayAccountSv payAccountSv;


    @RequestMapping("/PayAccountByPayeeId")
    public ReturnObject PayAccountByPayeeId(String paramJson)
    {
        List<String> _msg = new ArrayList<String>();

        if (StringUtils.isEmpty(paramJson))
            _msg.add("参数不能为空");

        String _json = null;
        try
        {
            _json = URLDecoder.decode(paramJson, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            _msg.add("UnsupportedEncodingException: " + e.getMessage());
            e.printStackTrace();
        }

        String _checkMsg = remoteAPI.CheckToken(_json);
        if (!StringUtils.isEmpty(_checkMsg) && _checkMsg.length() > 0)
        {
            _msg.add(_checkMsg);
        }
        if (_msg.size() > 0)
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", _msg, _msg.size());
        }
        ReturnObject obj = payAccountSv.PayAccountByPayeeId(_json);
        return obj;
    }

    /***
     * 获取银行卡列表

     * @return
     */
    @RequestMapping("/BankCardByPayeeId")
    public ReturnObject BankCardByPayeeId(String paramJson)
    {

        logger.info("------------获取银行卡列表 --------------------");
        List<String> _msg = new ArrayList<String>();

        if (StringUtils.isEmpty(paramJson))
            _msg.add("参数不能为空");

        String _json = null;
        try
        {
            _json = URLDecoder.decode(paramJson, "UTF-8");
            logger.info("BankCardByPayeeId传入参数：{}", _json);
        } catch (UnsupportedEncodingException e)
        {
            _msg.add("UnsupportedEncodingException: " + e.getMessage());
            e.printStackTrace();
        }

        String _checkMsg = remoteAPI.CheckToken(_json);
        if (!StringUtils.isEmpty(_checkMsg) && _checkMsg.length() > 0)
        {
            _msg.add(_checkMsg);
        }

        JSONObject jsonObject = null;
        String companyCode = "";
        String payeeId = "";
        try
        {
            jsonObject = new JSONObject(_json);
            companyCode = "" + jsonObject.get("company_code");
            payeeId = "" + jsonObject.get("payee_id");
        } catch (JSONException e)
        {
            _msg.add("参数非法:" + e.getMessage());
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(companyCode))
            _msg.add("套帐号不能为空");
        if (StringUtils.isEmpty(payeeId))
            _msg.add("网点编号不能为空");


        if (_msg.size() > 0)
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", _msg, _msg.size());
        }

        return payAccountSv.BankCardByPayeeId(_json);
    }


    /***
     * 获取银行卡列表

     * @return
     */
    @RequestMapping("/BankCardBySn")
    public ReturnObject BankCardBySn(String paramJson)
    {

        logger.info("------------获取银行卡 --------------------");
        List<String> _msg = new ArrayList<String>();

        if (StringUtils.isEmpty(paramJson))
            _msg.add("参数不能为空");

        String _json = null;
        try
        {
            _json = URLDecoder.decode(paramJson, "UTF-8");
            logger.info("BankCardBySn传入参数：{}", _json);
        } catch (UnsupportedEncodingException e)
        {
            _msg.add("UnsupportedEncodingException: " + e.getMessage());
            e.printStackTrace();
        }

        String _checkMsg = remoteAPI.CheckToken(_json);
        if (!StringUtils.isEmpty(_checkMsg) && _checkMsg.length() > 0)
        {
            _msg.add(_checkMsg);
        }

        JSONObject jsonObject = null;
        String sn = "";

        try
        {
            jsonObject = new JSONObject(_json);
            sn = "" + jsonObject.get("sn");

        } catch (JSONException e)
        {
            _msg.add("参数非法:" + e.getMessage());
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(sn))
            _msg.add("Sn不能为空");

        if (_msg.size() > 0)
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", _msg, _msg.size());
        }
        return payAccountSv.BankCardBySn(_json);
    }


    /***
     * 插入银行卡申请
     * @return
     */
    @RequestMapping("/InsertPayBankCardApply")
    public ReturnObject InsertPayBankCardApply(String accountJson)
    {
        String _json = accountJson;
        //try
       // {
        //    _json = URLDecoder.decode(accountJson, "UTF-8");
      //  } catch (UnsupportedEncodingException e)
      // {
       //     e.printStackTrace();
      //  }
        List<String> msg = new ArrayList<String>();
        if (StringUtils.isEmpty(_json))
            msg.add("参数不能为空");
        else
        {


            try
            {
                ObjectMapper objectMapper = new ObjectMapper();

                Map<String, String> mapJson = objectMapper.readValue(_json, Map.class);
                logger.info("插入银行卡申请参数：{}", _json);


                if (payAccountSv.UnVerifyApplyNum(mapJson.get("join_sn")) > 0)
                {
                    msg.add("该银行卡正在审核中，请不要重复提交!");
                }
                if (StringUtils.isEmpty(mapJson.get("company_code")))
                {
                    msg.add("套帐号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("payee_id")))
                {
                    msg.add("网点编号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("bank_name")))
                {
                    msg.add("所属银行不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("account_id")))
                {
                    msg.add("银行卡号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("account_name")))
                {
                    msg.add("银行帐户名称不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("bank_city_name")))
                {
                    msg.add("开户行所在城市不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("branch")))
                {
                    msg.add("支行名称不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("branch_code")))
                {
                    msg.add("支行编码不能为空!");
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if ((msg != null) && (msg.size() > 0))
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
        }
        return this.payAccountSv.InsertPayBankCardApply(_json, null);
    }


    /***
     * 取消银行卡申请
     * @return
     */
    @RequestMapping("/CancelApply")
    public ReturnObject CancelApply(String paramJson)
    {

        List<String> msg = new ArrayList<String>();
        if (StringUtils.isEmpty(paramJson))
            msg.add("参数不能为空");
        else
        {
            String _checkMsg = remoteAPI.CheckToken(paramJson);
            if (!StringUtils.isEmpty(_checkMsg) && _checkMsg.length() > 0)
            {
                logger.info("Token验证不通过， 原因:{}", _checkMsg);
                msg.add("Token验证不通过， 原因:"+ _checkMsg);
            }
            try
            {
                ObjectMapper objectMapper = new ObjectMapper();

                Map<String, String> mapJson = objectMapper.readValue(paramJson, Map.class);
                logger.info("插入银行卡申请参数：{}", paramJson);

                if (StringUtils.isEmpty(mapJson.get("company_code")))
                {
                    msg.add("套帐号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("payee_id")))
                {
                    msg.add("网点编号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("id")))
                {
                    msg.add("Id不能为空!");
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if ((msg != null) && (msg.size() > 0))
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
        }
        return this.payAccountSv.CancelApply(paramJson);
    }
    /***
     * 银行卡变更申请列表
     * @return
     */
    @RequestMapping("/BankCardApplyByPage")
    public PageInfo BankCardApplyByPage(int pageIndex, int pageSize, String paramJson)
    {
        String _json = null;
        try
        {
            _json = URLDecoder.decode(paramJson, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String _checkMsg = remoteAPI.CheckToken(_json);
        if (!StringUtils.isEmpty(_checkMsg) && _checkMsg.length() > 0)
        {
            logger.info("Token验证不通过， 原因:{}", _checkMsg);
            return null;
        }
        List<String> _msg = new ArrayList<String>();

        JSONObject jsonObject = null;
        String companyCode = "";
        String payeeId = "";
        try
        {
            jsonObject = new JSONObject(_json);
            companyCode = "" + jsonObject.get("company_code");
            payeeId = "" + jsonObject.get("payee_id");
        } catch (JSONException e)
        {
            _msg.add("参数非法:" + e.getMessage());
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(companyCode))
            _msg.add("套帐号不能为空");
        if (StringUtils.isEmpty(payeeId))
            _msg.add("网点编号不能为空");


        if (_msg.size() > 0)
        {
            logger.info("BankCardApplyByPage 出错了：{}", _msg);
            return null;
        }

        PageInfo pageInfo = payAccountSv.BankCardApplyByPage(pageIndex, pageSize, _json);

        logger.info("银行卡变更申请列表返回数据：{}", pageInfo);
        return pageInfo;
    }
}
