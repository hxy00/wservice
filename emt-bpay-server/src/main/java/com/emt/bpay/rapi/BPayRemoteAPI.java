package com.emt.bpay.rapi;

import com.emt.base.entity.RemoteApiParams;
import com.emt.common.json.JSONArray;
import com.emt.common.json.JSONException;
import com.emt.common.json.JSONObject;
import com.emt.common.rapi.RemoteAPI;
import com.emt.common.utils.FTPUtil;
import com.emt.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.emt.common.json.JSONHelper.JSONArrayToListMap;

public class BPayRemoteAPI {

    private static Logger logger = LoggerFactory
            .getLogger(BPayRemoteAPI.class);

/*
    @Value("${myProps.bpay-remote-api-url}")
    String url;
*/

    /***
     * 获取银行卡列表
     * @param token
     * @param company_code
     * @param payee_id
     * @return
     */
    public String BankCardByPayeeId(String token, String company_code, String payee_id) throws Exception {
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

        JSONObject jobj = new JSONObject();
        jobj.put("company_code", company_code);
        jobj.put("payee_id", payee_id);
        jobj.put("sys_id", RemoteApiConfig.sysId);
        jobj.put("user_id",   payee_id);
        jobj.put("token",  token);

        params.setJsonParams(jobj.toString());
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

    /**
     * 插入银行卡申请
     * @return
     */
    public String InsertPayBankCardApply(String accountJson, MultipartFile file){
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
/*        jobj.put("company_code", company_code);
        jobj.put("payee_id", payee_id);
        jobj.put("sys_id", "200001");
        jobj.put("user_id",   payee_id);
        jobj.put("token",  token);*/

        params.setJsonParams(jobj.toString());
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

    public String CheckToken(String paramJson) {
        String userId = "";
        String token = "";
        if (StringUtils.isEmpty(paramJson)){
            return "请输入token，无法校验！";
        }else{
            try{
                JSONObject jsonObject = new JSONObject(paramJson);
                userId =  ""+jsonObject.get("userId");
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

        logger.info("CheckToken:-->userId = {}", userId);
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
            info.setOrderBy(json.get("orderBy").toString());
            info.setEndRow(Integer.parseInt(""+json.get("endRow")));
            info.setPageNum(Integer.parseInt(""+json.get("pageNum")));
            info.setNavigatePages(Integer.parseInt(""+json.get("navigatePages")));
            Long total= Long.parseLong(json.get("total").toString());
            info.setTotal(total);
            info.setPages(Integer.parseInt(""+json.get("pages")));
            info.setSize((Integer.parseInt(""+json.get("size"))));
            info.setFirstPage(Integer.parseInt(""+json.get("firstPage")));
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