package com.emt.bpay.controller;

import com.emt.base.entity.ReturnObject;
import com.emt.bpay.BpayServerApplication;
import com.emt.bpay.dao.pojo.PayCurrentVO;
import com.emt.bpay.rapi.BPayRemoteAPI;
import com.emt.bpay.server.inter.IPayCurrentSv;
import com.emt.common.json.JSONException;
import com.emt.common.json.JSONObject;
import com.emt.common.utils.DateUtils;
import com.emt.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.StrUtil;
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
@RequestMapping("/PayCurrent")
@SpringApplicationConfiguration(classes = BpayServerApplication.class)
public class PayCurrentController
{
    private static Logger logger = LoggerFactory
            .getLogger(PayCurrentController.class);

    BPayRemoteAPI remoteAPI = new BPayRemoteAPI();

   // @Resource(name = "payCurrentSvImpl", type = PayCurrentSvImpl.class)
   @Autowired
    private IPayCurrentSv payCurrentSv;


    @RequestMapping("/DocTypeGroupReport")
    public ReturnObject DocTypeGroupReport(String paramJson)
    {
        logger.info("------------------------------------------------进入PayCurrent  Controller  DocTypeGroupReport ---------------------------------");
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


        String year = "";
        String month ="";
        String companyCode = "";
        String payeeId = "";
        try
        {
            JSONObject jsonObject = new JSONObject(_json);
            year = ""+jsonObject.get("year");
            month = ""+jsonObject.get("month");
            companyCode = ""+jsonObject.get("companyCode");
            payeeId= ""+jsonObject.get("payeeId");
        } catch (JSONException e)
        {
            _msg.add("参数非法:"+e.getMessage());
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(year))
            _msg.add("年份不能为空");
        if (StringUtils.isEmpty(month))
            _msg.add("月份不能为空");
        if (StringUtils.isEmpty(companyCode))
            _msg.add("套帐号不能为空");
        if (StringUtils.isEmpty(payeeId))
            _msg.add("网点编号不能为空");


        if (_msg.size() > 0)
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", _msg, _msg.size());
        }

         String bDate  = DateUtils.getMinMonthDate(year+"-"+month+"-01") + " 0:0:0";
         String eDate =  DateUtils.getMaxMonthDate(year+"-"+month+"-01") + " 23:59:59";

        List<String> msg = new ArrayList<String>();
        List<Map<String, Object>> list = payCurrentSv.DocTypeGroupReport(companyCode, payeeId, bDate, eDate);
        if (null == list || 0 == list.size())
        {
            msg.add("没有数据!");
            return new ReturnObject(ReturnObject.SuccessEnum.fail, "失败", msg, msg.size());
        } else
        {
            return new ReturnObject(ReturnObject.SuccessEnum.success, "成功", list, list.size());
        }
    }

    /**
     * 往来账分页查询

     * @return
     */
    @RequestMapping("/CurrentByPage")
    public PageInfo PageCurrent(int pageIndex, int pageSize, String accountJson){
        String _json = null;
        try
        {
            _json =  URLDecoder.decode(accountJson,"UTF-8" );
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String _checkMsg = remoteAPI.CheckToken(_json);
        if (!StringUtils.isEmpty(_checkMsg) && _checkMsg.length() > 0)
        {
            logger.info("Token验证不通过， 原因:{}",_checkMsg);
             return null;
        }


        logger.info("帐户分页AccountByPage: pageIndex = {}, pageSize:{}, accountJson:{}", pageIndex, pageSize, _json);
        PayCurrentVO vo = null;
        if (StrUtil.isNotEmpty(accountJson))
        {
            try
            {
                vo = JSONUtil.toBean(JSONUtil.parseObj(_json), PayCurrentVO.class);
                if (StringUtils.isNotEmpty(vo.getBegin_reg_time())){
                    vo.setBegin_reg_time(vo.getBegin_reg_time()+" 0:0:0");
                }
                if (StringUtils.isNotEmpty(vo.getEnd_reg_time())){
                    vo.setEnd_reg_time(vo.getEnd_reg_time() + " 23:59:59");
                }
            } catch (Exception e)
            {
                logger.error("参数accountJson转PayCurrentVO时出错，出错信息：{}", e);
            }
        }
        return payCurrentSv.CurrentByPage(pageIndex, pageSize, vo);
    }

    @RequestMapping("/ExportCurrent")
    public   List<Map<String, Object>> ExportCurrent(String paramJson){

        logger.info("帐户往来账下载:  ");
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


        String year = "";
        String month ="";
        String companyCode = "";
        String payeeId = "";
        try
        {
            JSONObject jsonObject = new JSONObject(_json);
            year = ""+jsonObject.get("year");
            month = ""+jsonObject.get("month");
            companyCode = ""+jsonObject.get("company_code");
            payeeId= ""+jsonObject.get("payee_id");
        } catch (JSONException e)
        {
            _msg.add("参数非法:"+e.getMessage());
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(year))
            _msg.add("年份不能为空");
        if (StringUtils.isEmpty(month))
            _msg.add("月份不能为空");
        if (StringUtils.isEmpty(companyCode))
            _msg.add("套帐号不能为空");
        if (StringUtils.isEmpty(payeeId))
            _msg.add("网点编号不能为空");


        if (_msg.size() > 0)
        {
            logger.error("下载失败,失败原因：{}", _msg);
            return null;
        }



        try {
           // String columnNames[] = { "凭证号", "摘要", "收入", "支出", "余额","记帐时间" };// 列名
           // String keys[] = { "doc_code", "abstract", "debit", "credit", "banlance","reg_time"};// map中的key
            String bDate  = DateUtils.getMinMonthDate(year+"-"+month+"-01") + " 0:0:0";
            String eDate =  DateUtils.getMaxMonthDate(year+"-"+month+"-01") + " 23:59:59";


              PayCurrentVO vo  = new PayCurrentVO();
              vo.setCompany_code(companyCode);
              vo.setPayee_id(payeeId);
              vo.setBegin_reg_time(bDate);
              vo.setEnd_reg_time(eDate);
              return   payCurrentSv.CurrentByMap(vo);

/*
            if (list.size() > 0) {
             //   String fileName = UUID.randomUUID().toString();
            //    ExcelUtil.export(list, fileName, keys, columnNames, response);
                return new ReturnObject(ReturnObject.SuccessEnum.success, "成功", list, list.size());
            } else {
                return new ReturnObject(ReturnObject.SuccessEnum.success, "成功", "没有数据可导出", list.size());
            }*/
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }


    }
}
