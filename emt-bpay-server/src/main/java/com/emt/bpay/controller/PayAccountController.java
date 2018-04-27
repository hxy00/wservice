package com.emt.bpay.controller;


import com.emt.base.entity.ReturnObject;
import com.emt.bpay.BpayServerApplication;
import com.emt.bpay.dao.pojo.PayAccountVO;
import com.emt.bpay.dao.pojo.SetAccountStatusVO;
import com.emt.bpay.dao.pojo.SetDepositVO;
import com.emt.bpay.rapi.BPayRemoteAPI;
import com.emt.bpay.server.inter.IPayAccountSv;
import com.emt.common.utils.BindingResultConvert;
import com.emt.common.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/3/15.
 */

@RestController
@RequestMapping("/PayAccount")
@SpringApplicationConfiguration(classes = BpayServerApplication.class)
public class PayAccountController
{
    private static Logger logger = LoggerFactory
            .getLogger(PayAccountController.class);

    BPayRemoteAPI remoteAPI = new BPayRemoteAPI();

    @Autowired
    private IPayAccountSv payAccountSv;

  /*  @Value("${my.message:no}")
    private String message;*/

    @InitBinder("payAccountVO")
    public void InitBinderPayAccountVO(WebDataBinder binder)
    {
        binder.setFieldDefaultPrefix("payAccountVO.");
    }

    @InitBinder("setDepositVO")
    public void InitBinderSetDepositVO(WebDataBinder binder)
    {
        binder.setFieldDefaultPrefix("setDepositVO.");
    }

    @InitBinder("setAccountStatusVO")
    public void InitBinderSetAccountStatusVO(WebDataBinder binder)
    {
        binder.setFieldDefaultPrefix("setAccountStatusVO.");
    }

 /*   @RequestMapping("/hello")
    public String Hello()
    {
        return " bbbb ->128 Hello: " + message;
    }*/


    @RequestMapping("/GetAccountByPayeeId")
    public ReturnObject getAccountByPayeeId(String paramJson)
    {
        List<String> _msg = new ArrayList<String>();

        if (StringUtils.isEmpty(paramJson))
            _msg.add("参数不能为空");

        String _json = null;
        try
        {
            _json = URLDecoder.decode(paramJson,"UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            _msg.add("UnsupportedEncodingException: "+ e.getMessage());
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


        JSONObject jsonObject = new JSONObject(_json);
        List<String> msg = new ArrayList<String>();

        List<Map<String, Object>> list = payAccountSv.getAccountByPayeeId(""+jsonObject.get("company_code"),
                ""+jsonObject.get("payee_id"));

        if (null == list || 0 == list.size())
        {
            msg.add("没有该网点信息!");
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
        } else
        {
            return new ReturnObject(ReturnObject.SuccessEnum.success, "成功", list, list.size());
        }
    }

    @RequestMapping("/PageAccount")
    @Deprecated
    public PageInfo pageAccount(int pageIndex, int pageSize, PayAccountVO payAccountVO)
    {
        //过时了，不使用
        logger.info("帐户分页: pageIndex = {}, pageSize:{}, PayAccountVo:{}", pageIndex, pageSize, payAccountVO);
        return payAccountSv.pageAccount(pageIndex, pageSize, payAccountVO);
    }

    @RequestMapping("/AccountByPage")
    public PageInfo accountByPage(int pageIndex, int pageSize, String accountJson)
    {
        logger.info("帐户分页AccountByPage: pageIndex = {}, pageSize:{}, accountJson:{}", pageIndex, pageSize, accountJson);
        PayAccountVO vo = null;
        if (StrUtil.isNotEmpty(accountJson))
        {
            try
            {
                vo = JSONUtil.toBean(JSONUtil.parseObj(accountJson), PayAccountVO.class);
            } catch (Exception e)
            {
                logger.error("参数accountJson转PayAccountVO时出错，出错信息：{}", e);
            }
        }
        return payAccountSv.pageAccount(pageIndex, pageSize, vo);
    }

    @RequestMapping("/SetDeposit")
    public ReturnObject setDeposit(String depositJson)
    {
        logger.info("bpay-service: 设置保底金额");
        SetDepositVO vo = null;
        if (StrUtil.isNotEmpty(depositJson))
        {
            try
            {
                vo = JSONUtil.toBean(JSONUtil.parseObj(depositJson), SetDepositVO.class);
                vo.setCreate_oper("系统测试");
                vo.setCreate_ip("127.0.0.1");
                vo.setCreate_oper_id("sys_test");
            } catch (Exception e)
            {
                List<String> _list = new ArrayList<String>();
                _list.add(e.getMessage());
                logger.error("参数accountJson转PayAccountVO时出错，出错信息：{}", e.getMessage());
                return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
            }

            return payAccountSv.setDeposit(vo);
        } else
        {
            List<String> _list = new ArrayList<String>();
            _list.add("设置保底金额参数不能为NULL");
            logger.error("设置保底金额参数不能为NULL");
            return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
        }

     /*   logger.info("--------  设置保底金额  -------------");
        logger.info("设置保底金额, SetDepositVO = {} ", setDepositVO);
        List<String> _list = new ArrayList<String>();
        if (bindingResult.hasErrors())
        {
            _list = BindingResultConvert.BindingResultToList(bindingResult);
            logger.info("设置保底金额失败，参数校验不通过，原因：{} ", _list);
            return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, 0);
        }

        int _count = payAccountSv.setDeposit(setDepositVO);
        if (_count > 0)
        {
            _list.clear();
            _list.add(String.format("成功更新%s条记录", _count));
            logger.info("设置保底金额完成，消息：{} ", _list);
            return new ReturnObject(ReturnObject.SuccessEnum.success, _list, null, _count);
        } else
        {
            _list.clear();
            _list.add(String.format("更新%s条记录，更新失败", _count));
            logger.info("设置保底金额更新失败，消息：{} ", _list);
            return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, 0);
        }*/

    }


    @RequestMapping("/setAccountStatus")
    public ReturnObject setAccountStatus(@Valid SetAccountStatusVO setAccountStatusVO, BindingResult bindingResult)
    {
        logger.info("--------  设置帐户状态  -------------");
        List<String> _list = new ArrayList<String>();
        if (bindingResult.hasErrors())
        {
            _list = BindingResultConvert.BindingResultToList(bindingResult);
            logger.info("设置帐户状态失败，参数校验不通过，原因：{} ", _list);
            return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, 0);
        }
        int _count = payAccountSv.setAccountStatus(setAccountStatusVO);
        if (_count > 0)
        {
            _list.clear();
            _list.add(String.format("成功更新%s条记录", _count));
            logger.info("设置帐户状态完成，消息：{} ", _list);
            return new ReturnObject(ReturnObject.SuccessEnum.success, _list, null, _count);
        } else
        {
            _list.clear();
            _list.add(String.format("更新%s条记录，更新失败", _count));
            logger.info("设置帐户状态更新失败，消息：{} ", _list);
            return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, 0);
        }
    }


    /**
     * 根据网点编号获取网点总帐信息
     * @param company_code
     * @param payee_id
     * @param payee_id
     * @return
     */
    @RequestMapping("/LedgersByPayeeId")
    public ReturnObject LedgersByPayeeId(String company_code, String payee_id){
        return payAccountSv.LedgersByPayeeId(company_code, payee_id);
    }

    /***
     * 获取银行卡列表
     * @param token
     * @param company_code
     * @param payee_id
     * @return
     */
    @RequestMapping("/BankCardByPayeeId")
    public ReturnObject BankCardByPayeeId(String token, String company_code, String payee_id){
        return payAccountSv.BankCardByPayeeId(token, company_code,payee_id);
    }


    /***
     * 插入银行卡申请
     * @return
     */
    @RequestMapping("/InsertPayBankCardApply")
    public ReturnObject InsertPayBankCardApply(String accountJson, MultipartFile file){


        String _json = null;
        try {
            _json = URLDecoder.decode(accountJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> msg = new ArrayList<String>();
        if (StringUtils.isEmpty(_json))
            msg.add("参数不能为空");
        else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                Map<String, String> mapJson = objectMapper.readValue(_json, Map.class);
                logger.info("插入银行卡申请参数：{}", _json);
                if (StringUtils.isEmpty(mapJson.get("company_code"))) {
                    msg.add("套帐号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("payee_id"))) {
                    msg.add("网点编号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("bank_name"))) {
                    msg.add("所属银行不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("account_id"))) {
                    msg.add("银行卡号不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("account_name"))) {
                    msg.add("银行帐户名称不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("bank_city_name"))) {
                    msg.add("开户行所在城市不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("branch"))) {
                    msg.add("支行名称不能为空!");
                }
                if (StringUtils.isEmpty(mapJson.get("branch_code"))) {
                    msg.add("支行编码不能为空!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (msg != null && msg.size() > 0) {
            return  new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
        }
        return payAccountSv.InsertPayBankCardApply(_json, file);
    }

    /***
     * 插入银行卡申请
     * @return
     */
    @RequestMapping("/BankCardApplyByPage")
    public  PageInfo BankCardApplyByPage(int pageIndex, int pageSize, String paramJson){
        PageInfo pageInfo = payAccountSv.BankCardApplyByPage(pageIndex, pageSize, paramJson);
        return  pageInfo;
    }
}
