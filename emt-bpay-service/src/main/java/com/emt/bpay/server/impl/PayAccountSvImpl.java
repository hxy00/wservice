package com.emt.bpay.server.impl;

import com.emt.base.entity.ReturnObject;
import com.emt.bpay.rapi.BPayRemoteAPI;
import com.emt.bpay.server.inter.IPayAccountSv;
import com.emt.common.json.JSONArray;
import com.emt.common.json.JSONHelper;
import com.emt.common.json.JSONObject;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("payAccountSvImpl")
public class PayAccountSvImpl implements IPayAccountSv{

    private static Logger logger = LoggerFactory
            .getLogger(PayAccountSvImpl.class);

    BPayRemoteAPI remoteAPI = new BPayRemoteAPI();

/*    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();*/

    /**
     * 网点银行帐户信息
     * @param paramJson
     * @return
     */
    @Override
    public ReturnObject  PayAccountByPayeeId(String paramJson){
         //return remoteAPI.PayAccountByPayeeId(paramJson);
        String _return = "";
        List<String> msg = new ArrayList<String>();
        try {
            _return = remoteAPI.PayAccountByPayeeId(paramJson);
            logger.info("BankCardByPayeeId 调用返回字符串:{}", _return);
            JSONObject json = new JSONObject(_return);
            if ("0".equals(json.get("retcode").toString())) {
                JSONObject _data = (JSONObject)json.get("data");
                if ("0".equals(_data.get("success").toString())){
                    JSONArray jsonArray = _data.optJSONArray("json_data");
                    JSONObject _data2 = jsonArray.getJSONObject(0);

                    List<Map<String, Object>> list =  new ArrayList<Map<String, Object>>();
                    Map map = new HashMap<String, Object>();
                    map.put("payee_name",  _data2.get("payee_name"));
                    map.put("agency_name",  _data2.get("agency_name"));
                    map.put("payee_id", _data2.get("payee_id"));
                    map.put("wait_verify", _data2.get("wait_verify"));
                    map.put("card_num", _data2.get("card_num"));
                    list.add(map);
                    return new ReturnObject(ReturnObject.SuccessEnum.success,"成功",list,list.size());
                }else
                {
                    msg.add("调用远程API内部业务出错，返回success:" + _data.get("success").toString());
                    return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
                }
            }else
            {
                msg.add("调用远程API出错，返回retcode:" + json.get("retcode").toString());
                return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
            }

        } catch (Exception e) {
            logger.info("BankCardByPayeeId 出错了:{}", e.getMessage());
            msg.add("调用远程API出错，出错了:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
    }

    /***
     * 获取银行卡列表
     * @return
     */
    @Override
    public  ReturnObject BankCardByPayeeId(String paramJson){
        String _return = "";
        List<String> msg = new ArrayList<String>();
        try {
            _return = remoteAPI.BankCardByPayeeId(paramJson);
            logger.info("BankCardByPayeeId 调用返回字符串:{}", _return);
            JSONObject json = new JSONObject(_return);
            if ("0".equals(json.get("retcode").toString())) {
                JSONObject _data = (JSONObject)json.get("data");
                if ("0".equals(_data.get("success").toString())){
                    return new ReturnObject(ReturnObject.SuccessEnum.success,"成功",
                            JSONHelper.JSONArrayToListMap(_data.optJSONArray("json_data")),_data.optJSONArray("json_data").length());
                }else
                {
                    msg.add("调用远程API内部业务出错，返回success:" + _data.get("success").toString());
                    return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
                }
            }else
            {
                msg.add("调用远程API出错，返回retcode:" + json.get("retcode").toString());
                return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
            }

        } catch (Exception e) {
            logger.info("BankCardByPayeeId 出错了:{}", e.getMessage());
            msg.add("调用远程API出错，出错了:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
    }


    /***
     * 根据SN获取银行卡信息
     * @param paramJson
     * @return
     */
    @Override
    public ReturnObject BankCardBySn(String paramJson)
    {
        String _return = "";
        List<String> msg = new ArrayList<String>();
        try {
            _return = remoteAPI.BankCardBySn(paramJson);
            logger.info("BankCardBySn 调用返回字符串:{}", _return);
            JSONObject json = new JSONObject(_return);
            if ("0".equals(json.get("retcode").toString())) {
                JSONObject _data = (JSONObject)json.get("data");
                if ("0".equals(_data.get("success").toString())){
                    return new ReturnObject(ReturnObject.SuccessEnum.success,"成功",
                            JSONHelper.JSONArrayToListMap(_data.optJSONArray("json_data")),_data.optJSONArray("json_data").length());
                }else
                {
                    msg.add("调用远程API内部业务出错，返回success:" + _data.get("success").toString());
                    return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
                }
            }else
            {
                msg.add("调用远程API出错，返回retcode:" + json.get("retcode").toString());
                return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
            }

        } catch (Exception e) {
            logger.info("BankCardBySn  出错了:{}", e.getMessage());
            msg.add("调用远程API出错，出错了:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
    }

    /**
     * 根据SN获取银行卡未审核数
     * @param sn
     * @return
     */
    public int  UnVerifyApplyNum(String sn){
        List<Map<String, Object>>   list =  remoteAPI.UnVerifyApplyNum(sn);
        if (list != null && list.size() > 0){
            return  Integer.parseInt(list.get(0).get("num").toString());
        }else
           return 0;
    }

    /**
     * 插入银行卡申请
     */
    @Override
    public  ReturnObject InsertPayBankCardApply(String bankCarkJson, MultipartFile file) {
        String _return = "";
        List msg = new ArrayList();
        try {
            _return = this.remoteAPI.InsertPayBankCardApply(bankCarkJson, file);
            logger.info("InsertPayBankCardApply 调用返回字符串:{}", _return);
            JSONObject json = new JSONObject(_return);
            if ("0".equals(json.get("retcode").toString())) {
                JSONObject _data = (JSONObject)json.get("data");
                if ("0".equals(_data.get("success").toString())) {
                    return new ReturnObject(ReturnObject.SuccessEnum.success, "成功",
                            JSONHelper.JSONArrayToListMap(_data
                                    .optJSONArray("json_data")),
                            _data.optJSONArray("json_data").length());
                }

                logger.info("message-->{}", _data.optJSONArray("message"));
                return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败",
                        JSONHelper.JSONArrayToList(_data
                                .optJSONArray("message")),
                        _data.optJSONArray("message").length());
            }

            msg.add("调用远程API出错，返回retcode:" + json.get("retcode").toString());
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
        }
        catch (Exception e)
        {
            logger.info("InsertPayBankCardApply 出错了:{}", e.getMessage());
            msg.add("调用远程API出错，出错了:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
    }

    /**
     * 取消银行卡申请
     * @param accountJson
     * @return
     */
    @Override
    public ReturnObject  CancelApply(String accountJson){
        String _return = "";
        List msg = new ArrayList();
        try {
            _return = remoteAPI.CancelApply(accountJson);
            logger.info("CancelApply  调用返回字符串:{}", _return);
            JSONObject json = new JSONObject(_return);
            if ("0".equals(json.get("retcode").toString())) {
                JSONObject _data = (JSONObject)json.get("data");
                if ("0".equals(_data.get("success").toString())) {
                    return new ReturnObject(ReturnObject.SuccessEnum.success, "成功",
                            JSONHelper.JSONArrayToListMap(_data
                                    .optJSONArray("json_data")),
                            _data.optJSONArray("json_data").length());
                }

                logger.info("message-->{}", _data.optJSONArray("message"));
                return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败",
                        JSONHelper.JSONArrayToList(_data
                                .optJSONArray("message")),
                        _data.optJSONArray("message").length());
            }

            msg.add("调用远程API出错，返回retcode:" + json.get("retcode").toString());
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
        }
        catch (Exception e)
        {
            logger.info("CancelApply 出错了:{}", e.getMessage());
            msg.add("调用远程API出错，出错了:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
    }

    /**
     * 查询银行卡变更申请记录
     * @param pageIndex
     * @param pageSize
     * @param paramJson
     * @return
     */
    @Override
    public   PageInfo  BankCardApplyByPage(int pageIndex, int pageSize, String paramJson){
        PageInfo page  = remoteAPI.BankCardApplyByPage(pageIndex, pageSize, paramJson);
        if (page.getList() == null){
            page.setList(new ArrayList());
        }
        return page;
    }
}



