package com.emt.bpay.rapi;

import com.emt.base.entity.RemoteApiParams;
import com.emt.bpay.pojo.PayCurrentVO;
import com.emt.common.json.JSONArray;
import com.emt.common.json.JSONException;
import com.emt.common.json.JSONObject;
import com.emt.common.rapi.RemoteAPI;
import com.emt.common.utils.JSONUtil;
import com.emt.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.emt.common.json.JSONHelper.JSONArrayToListMap;

public class BPayRemoteAPI {

    private static Logger logger = LoggerFactory
            .getLogger(BPayRemoteAPI.class);

/*
    @Value("${myProps.bpay-remote-api-url}")
    String url;
*/

    public String PayAccountByPayeeId(String paramJson){
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("PayAccountByPayeeId");
        params.setBusiId(150001L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("a67365a3c9344ca08c75acab47e195a5");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());
        params.setUrl(RemoteApiConfig.apiUrl);

        params.setJsonParams(paramJson);
        logger.info("远程调用获取银行卡接口,参数:{}", params);
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);

            logger.info("远程调用获取银行卡接口，返回字符串:{}", _re);

        } catch (Exception e) {
            logger.info("远程调用获取银行卡接口，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  _re;
    }

    /***
     * 获取银行卡列表
     * @return
     */
    public String BankCardByPayeeId(String paramJson) throws Exception {
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("SelectBankCardByPayeeId");
        params.setBusiId(150017L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("c364bc0eeb484641bcaa2b0533d399a3");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());
        params.setUrl(RemoteApiConfig.apiUrl);
        params.setJsonParams(paramJson);
        logger.info("远程调用获取银行卡列表接口,参数:{}", params);
        try {
          _re  = RemoteAPI.CallRemoteAPI(params);

          logger.info("远程调用获取银行卡列表接口，返回字符串:{}", _re);

        } catch (Exception e) {
            logger.info("远程调用获取银行卡接口，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  _re;
    }

    /***
     * 根据SN获取银行卡信息
     * @param paramJson
     * @return
     */
    public String BankCardBySn(String paramJson) throws Exception {
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("BankCardBySnApi");
        params.setBusiId(150029L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("fda89e8e450c4e34ad6237324cba599a");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());
        params.setUrl(RemoteApiConfig.apiUrl);



        params.setJsonParams(paramJson.toString());
        logger.info("远程调用 获取根据SN获取银行卡信息 接口,参数:{}", params);
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);

            logger.info("远程调用获取 根据SN获取银行卡信息 接口，返回字符串:{}", _re);

        } catch (Exception e) {
            logger.info("远程调用获取 根据SN获取银行卡信息 接口，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  _re;
    }

    /**
     * 插入银行卡申请
     * @return
     */
    public  String InsertPayBankCardApply(String accountJson, MultipartFile file){
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("InsertPayBankCardApplyApi");
        params.setBusiId(150024L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("7a6f6d3c9a4b474484885b6a0fc8ad07");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());

        params.setUrl(RemoteApiConfig.apiUrl);

        /*
        JSONObject jobj = null;
        try {
            jobj = new JSONObject(accountJson);
            String _fileName = jobj.get("payee_id").toString()+"_"+ dt.getTime()+getExtensionName(file.getOriginalFilename());
            jobj.put("file_name", _fileName );

            FTPUtil.uploadFile(RemoteApiConfig.ftpIp, RemoteApiConfig.ftpPort, RemoteApiConfig.ftpUserName,
                    RemoteApiConfig.ftpPassword, _fileName,
                    file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        params.setJsonParams(accountJson);
        logger.info("远程调用插入银行卡申请接口,参数:{}", params.toJson());
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);

            logger.info("远程调用插入银行卡申请接口，返回字符串:{}", _re);

        } catch (Exception e) {
            logger.info("远程调用插入银行卡申请接口，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  _re;
    }

    /**
     * 取消银行卡申请
     * @param accountJson
     * @return
     */
    public String  CancelApply(String accountJson){
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("AppleyInvalidApi");
        params.setBusiId(150031L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("75e319810e29411db276f03c2072f202");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());
        params.setUrl(RemoteApiConfig.apiUrl);
        //params.setUrl("http://localhost:8080/eMaotai/api/business/");
        params.setJsonParams(accountJson);
        logger.info("远程调用 取消银行卡申请 接口,参数:{}", params.toJson());
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);

            logger.info("远程调用 取消银行卡申请 接口，返回字符串:{}", _re);

        } catch (Exception e) {
            logger.info("远程调用 取消银行卡申请 接口，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  _re;
    }

    /**
     * 银行卡变更列表
     * @param pageIndex
     * @param pageSize
     * @param paramJson
     * @return
     */
    public PageInfo BankCardApplyByPage(int pageIndex, int pageSize, String paramJson){
        PageInfo pageInfo = new PageInfo();
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("BankCardApplyByPageApi");
        params.setBusiId(150025L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("ebc3f9b808464a51a0a7c1adcfd66276");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());

        params.setUrl(RemoteApiConfig.apiUrl);

        JSONObject jobj = null;
        try {
            jobj = new JSONObject(paramJson);
            jobj.put("page", pageIndex);
            jobj.put("pageSize", pageSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
/*        jobj.put("company_code", company_code);
        jobj.put("payee_id", payee_id);
        jobj.put("sys_id", "200001");
        jobj.put("user_id",   payee_id);
        jobj.put("token",  token);*/

        params.setJsonParams(jobj.toString());
        logger.info("远程调用 申请列表接口,参数:{}", params.toJson());
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);
            logger.info("远程调用 申请列表接口，返回字符串:{}", _re);
            JSONObject json = new JSONObject(_re);
            if ((""+json.get("retcode")).equals("0")){
                String _dataJson = ""+json.get("data");
                logger.info("_dataJson---> {}", _dataJson);
                JSONObject _json2 = new JSONObject(_dataJson);
                if ((""+_json2.get("success")).equals("0")){
                    pageInfo = this.JsonToPageInfo(_json2.get("json_data").toString());
                }else
                {
                    logger.error("远程调用 申请列表接口，返回 success {}, message:{}", _json2.get("success"),  _json2.get("message"));
                }
            }else
            {
                logger.error("远程调用 申请列表接口，返回 retcode {}, retmsg:{}", json.get("retcode"), json.get("retmsg"));
            }
        } catch (Exception e) {
            logger.info("远程调用插入银行卡申请接口，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  pageInfo;
    }


    /**
     * 凭证类型进行分组统计
     * @param paramJson
     * @return
     */
    public List<Map<String, Object>> DocTypeGroupReport(String paramJson){
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("DocTypeGroupReportApi");
        params.setBusiId(150026L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("4b2af0d76e114c91ac7ff8ce65927465");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());

        params.setUrl(RemoteApiConfig.apiUrl);

        params.setJsonParams(paramJson);
        logger.info("远程调用 凭证类型进行分组统计,参数:{}", params.toJson());
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);
            logger.info("远程调用 凭证类型进行分组统计，返回字符串:{}", _re);
            JSONObject json = new JSONObject(_re);
            if ((""+json.get("retcode")).equals("0")){
                String _dataJson = ""+json.get("data");
                logger.info("_dataJson---> {}", _dataJson);
                JSONObject _json2 = new JSONObject(_dataJson);
                if ((""+_json2.get("success")).equals("0")){
                     return   JSONArrayToListMap(_json2.optJSONArray("json_data"));
                }else
                {
                    logger.error("远程调用 凭证类型进行分组统计，返回 success {}, message:{}", _json2.get("success"),  _json2.get("message"));
                }
            }else
            {
                logger.error("远程调用 凭证类型进行分组统计，返回 retcode {}, retmsg:{}", json.get("retcode"), json.get("retmsg"));
            }
        } catch (Exception e) {
            logger.info("远程调用 凭证类型进行分组统计，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 根据SN获取银行卡未审核数
     * @param sn
     * @return
     */
    public List<Map<String, Object>>  UnVerifyApplyNum(String sn){
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("UnVerifyApplyNumApi");
        params.setBusiId(150030L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("a2647be77539435fb7e66d770a965f8e");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());

        params.setUrl(RemoteApiConfig.apiUrl);
        //params.setUrl("http://localhost:8080/eMaotai/api/business/");
        JSONObject jsons = new JSONObject();
        try
        {
            jsons.put("sn", sn);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        params.setJsonParams(jsons.toString());
        logger.info("远程调用 根据SN获取银行卡未审核数,参数:{}", params.toJson());
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);
            logger.info("远程调用 根据SN获取银行卡未审核数，返回字符串:{}", _re);
            JSONObject json = new JSONObject(_re);
            if ((""+json.get("retcode")).equals("0")){
                String _dataJson = ""+json.get("data");
                logger.info("_dataJson---> {}", _dataJson);
                JSONObject _json2 = new JSONObject(_dataJson);
                if ((""+_json2.get("success")).equals("0")){
                    return   JSONArrayToListMap(_json2.optJSONArray("json_data"));
                }else
                {
                    logger.error("远程调用 根据SN获取银行卡未审核数，返回 success {}, message:{}", _json2.get("success"),  _json2.get("message"));
                }
            }else
            {
                logger.error("远程调用 根据SN获取银行卡未审核数，返回 retcode {}, retmsg:{}", json.get("retcode"), json.get("retmsg"));
            }
        } catch (Exception e) {
            logger.info("远程调用 根据SN获取银行卡未审核数，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  null;
    }

    public  List<Map<String, Object>>  CurrentToMap(PayCurrentVO payCurrentVO)
    {
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("CurrentToMapApi");
        params.setBusiId(150027L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("6584dd90e74845dd8ea2287fae795b51");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());
        params.setUrl(RemoteApiConfig.apiUrl);



        params.setJsonParams(JSONUtil.toJson(payCurrentVO));


        logger.info("远程调用 凭证类型进行分组统计,参数:{}", params.toJson());
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);
            logger.info("远程调用 凭证类型进行分组统计，返回字符串:{}", _re);
            JSONObject json = new JSONObject(_re);
            if ((""+json.get("retcode")).equals("0")){
                String _dataJson = ""+json.get("data");
                logger.info("_dataJson---> {}", _dataJson);
                JSONObject _json2 = new JSONObject(_dataJson);
                if ((""+_json2.get("success")).equals("0")){
                    return   JSONArrayToListMap(_json2.optJSONArray("json_data"));
                }else
                {
                    logger.error("远程调用 凭证类型进行分组统计，返回 success {}, message:{}", _json2.get("success"),  _json2.get("message"));
                }
            }else
            {
                logger.error("远程调用 凭证类型进行分组统计，返回 retcode {}, retmsg:{}", json.get("retcode"), json.get("retmsg"));
            }
        } catch (Exception e) {
            logger.info("远程调用 凭证类型进行分组统计，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 往来账分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageInfo PageCurrent(int pageIndex, int pageSize, PayCurrentVO payCurrentVO){
        PageInfo pageInfo = new PageInfo();
        String _re = "";
        RemoteApiParams params = new RemoteApiParams();
        params.setInterfaceName("PageCurrentApi");
        params.setBusiId(150028L);
        params.setInterfaceVersion("1.0.0.0");
        params.setKey("02fd60c8d6b7499c867c7922ab205dae");
        params.setClientType(1L);
        Date dt = new Date();
        params.setQid(dt.getTime());

        params.setUrl(RemoteApiConfig.apiUrl);

        JSONObject jobj = null;
        try {
            jobj = new JSONObject(JSONUtil.toJson(payCurrentVO));
            jobj.put("page", pageIndex);
            jobj.put("pageSize", pageSize);

        } catch (Exception e) {
            e.printStackTrace();
        }


        params.setJsonParams(jobj.toString());
        logger.info("远程调用 往来账分页查询,参数:{}", params.toJson());
        try {
            _re  = RemoteAPI.CallRemoteAPI(params);
            logger.info("远程调用 往来账分页查询，返回字符串:{}", _re);
            JSONObject json = new JSONObject(_re);
            if ((""+json.get("retcode")).equals("0")){
                String _dataJson = ""+json.get("data");
                logger.info("_dataJson---> {}", _dataJson);
                JSONObject _json2 = new JSONObject(_dataJson);
                if ((""+_json2.get("success")).equals("0")){
                    pageInfo = this.JsonToPageInfo(_json2.get("json_data").toString());
                }else
                {
                    logger.error("远程调用 往来账分页查询，返回 success {}, message:{}", _json2.get("success"),  _json2.get("message"));
                    return new PageInfo();
                }
            }else
            {
                logger.error("远程调用 往来账分页查询，返回 retcode {}, retmsg:{}", json.get("retcode"), json.get("retmsg"));
                return new PageInfo();
            }
        } catch (Exception e) {
            logger.info("远程调用 往来账分页查询接口，出错:{} ", e.getMessage());
            e.printStackTrace();
        }
        return  pageInfo;
    }


    public String CheckToken(String paramJson) {
        String userId = "";
        String token = "";
        if (StringUtils.isEmpty(paramJson)){
            return "请输入token，无法校验！";
        }else{
            try{
                JSONObject jsonObject = new JSONObject(paramJson);
                userId =  ""+jsonObject.get("user_id");
                token =   ""+jsonObject.get("token");

                if (StringUtils.isEmpty(userId)){
                    return "token无法校验,userId不能为空";
                }
                if (StringUtils.isEmpty(token)){
                    return "token无法校验,token不能为空";
                }
            }catch (Exception e){
                logger.error("token无法校验,原因：{}", e.getMessage() );
                return "token无法校验,原因：" + e.getMessage() ;
            }
        }

        logger.info("CheckToken:-->user_id = {}", userId);
        logger.info("CheckToken:-->token = {}", token);

        int _code = -1;
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("sysId", RemoteApiConfig.sysId);
            jobj.put("uId", userId);
            jobj.put("token", token);
        } catch (Exception e) {
            _code = -4;
            logger.info("CheckToken:-->JSONObject出错= {}", e.getMessage());
        }

        try {
            RemoteApiParams params = new RemoteApiParams();
            params.setInterfaceName("loginVerify");
            params.setBusiId(150006L);
            params.setInterfaceVersion("1.0.0.0");
            params.setKey("29a32a3703294de79d99929f7ed85c01");
            params.setClientType(1L);
            Date dt = new Date();
            params.setQid(dt.getTime());
            params.setUrl(RemoteApiConfig.caUrl);

            params.setJsonParams(jobj.toString());

            String _data  = RemoteAPI.CallRemoteAPI(params);

            try {

                logger.info("token校验，返回字符串:{}", _data);
                JSONObject json = new JSONObject(_data);
                if ((""+json.get("retcode")).equals("0")){
                    String _dataJson = ""+json.get("data");
                    logger.info("_dataJson---> {}", _dataJson);
                    JSONObject _json2 = new JSONObject(_dataJson);
                    _code = Integer.parseInt(_json2.get("errorId").toString());
                }else
                {
                    logger.error("token校验，，返回 retcode {}, retmsg:{}", json.get("retcode"), json.get("retmsg"));
                }
            } catch (Exception e) {
                logger.info("token校验，，出错:{} ", e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            _code = -5;
      }
        logger.info("CheckToken:-->_code = {}", _code);
        if (_code != 0) {
            logger.info("Token校验不通过,原因：{}", GetCheckValue(_code));
             return  GetCheckValue(_code);
        }else
        {
            return "";
        }
    }

    public String GetCheckValue(int _code) {
        String _retun = "";
        switch (_code) {
            case -1:
                _retun = "获取token接口参数出错!,错误代码:" + _code;
                break;
            case -2:
                _retun = "token接口出错!,错误代码:" + _code;
                break;
            case -3:
                _retun = "token无效,请重新登录!,错误代码:" + _code;
                break;
            case -4:
                _retun = "token，验证内部错误!,错误代码:" + _code;
                break;
            case -5:
                _retun = "token，验证内部错误!,错误代码:" + _code;
                break;
            case 0:
                _retun = "验证通过！";
            default:
                _retun = "token验证返回错误代码:" + _code;
                break;
        }

        return _retun;
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

    private PageInfo JsonToPageInfo(String strJson){
        PageInfo info = new PageInfo();
        try {
            JSONObject json = new JSONObject(strJson);
            info.setStartRow(Integer.parseInt(""+json.get("startRow")));
            info.setLastPage(Integer.parseInt(""+json.get("lastPage")));
            info.setPrePage(Integer.parseInt(""+json.get("prePage")));
            info.setHasNextPage(Boolean.parseBoolean(""+json.get("hasNextPage")));
            info.setNextPage(Integer.parseInt(""+json.get("nextPage")));
            info.setPageSize(Integer.parseInt(""+json.get("pageSize")));
            //info.setOrderBy(json.get("orderBy").toString());
            info.setEndRow(Integer.parseInt(""+json.get("endRow")));
            info.setPageNum(Integer.parseInt(""+json.get("pageNum")));
            info.setNavigatePages(Integer.parseInt(""+json.get("navigatePages")));
            Long total= Long.parseLong(json.get("total").toString());
            info.setTotal(total);
            info.setPages(Integer.parseInt(""+json.get("pages")));
            info.setSize((Integer.parseInt(""+json.get("size"))));
           // info.setFirstPage(Integer.parseInt(""+json.get("firstPage")));
            info.setIsLastPage(Boolean.parseBoolean(""+json.get("isLastPage")));
            info.setHasPreviousPage(Boolean.parseBoolean(""+json.get("hasPreviousPage")));
            info.setIsFirstPage(Boolean.parseBoolean(""+json.get("isFirstPage")));

            JSONArray ja =  json.getJSONArray("navigatepageNums");
            int[] numL = new int[ja.length()];
            for(int i=0; i<ja.length(); i++){
                numL[i] = ja.getInt(i);
            }
            info.setNavigatepageNums(numL);
            info.setList(JSONArrayToListMap(json.getJSONArray("list")));

        } catch (JSONException e) {
            logger.error("JsonToPageInfo出错，原因{}", e);
            e.printStackTrace();
        }

        return info;
    }
}