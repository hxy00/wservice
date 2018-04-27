package com.emt.epay.qapi.impl;

import com.abc.pay.client.JSON;
import com.abc.pay.client.ebus.QueryOrderRequest;
import com.emt.common.json.JSONObject;
import com.emt.common.security.BaseCoder;
import com.emt.epay.qapi.BaseSv;
import com.emt.epay.qapi.inter.IEpayQueryApi;
import com.emt.epay.qapi.pojo.*;
import com.emt.epay.qapi.pojo.alipay.query.AlipaySubmit;
import com.emt.epay.qapi.pojo.alipay.query.AlipayXmlForDOM4J;
import com.emt.epay.qapi.pojo.alipay.query.ReturnCode;
import com.emt.epay.qapi.pojo.alipay.query.httpClient.HttpProtocolHandler;
import com.emt.epay.qapi.pojo.alipay.query.httpClient.HttpRequest;
import com.emt.epay.qapi.pojo.alipay.query.httpClient.HttpResponse;
import com.emt.epay.qapi.pojo.alipay.query.httpClient.HttpResultType;
import com.emt.epay.qapi.pojo.boc.query.BocPKCSTool;
import com.emt.epay.qapi.pojo.ccb.query.CcbClientUtil;
import com.emt.epay.qapi.pojo.ccb.query.CcbMD5;
import com.emt.epay.qapi.pojo.ccb.query.CcbXmlForDOM4J;
import com.emt.epay.qapi.pojo.icbc.query.IcbcHttpsClientSv0;
import com.emt.epay.qapi.pojo.icbc.query.IcbcXmlForDOM4J;
import com.emt.epay.qapi.pojo.unionpay.query.LogUtil;
import com.emt.epay.qapi.pojo.unionpay.query.SDKConfig;
import com.emt.epay.qapi.pojo.unionpay.query.SDKUtil;
import com.emt.epay.qapi.pojo.wxpay.query.WeixinXmlForDOM4J;
import com.emt.epay.qapi.util.HttpsUtil;
import com.emt.epay.qapi.util.ToolsUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-03-28.
 */
@Repository("epayQueryApiImpl")
public class EpayQueryApiImpl extends BaseSv implements IEpayQueryApi {
    private static Logger logger = LoggerFactory.getLogger(EpayQueryApiImpl.class);

    /**
     * 从农行查询订单状态
     *
     * @param pMap
     * @return
     */
    @Override
    public Map<String, Object> queryFromABC(Map<String, Object> pMap) throws Exception {
        logger.info("[abc_pay_query]查询开始：" + MapUtils.getString(pMap, "orderId"));
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String orderId = MapUtils.getString(pMap, "orderid");
        String sysId = MapUtils.getString(pMap, "Emt_sys_id");
        if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(sysId))
            throw new Exception("[abc_pay_query]查询参数为空！");

        try {
            QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
            queryOrderRequest.queryRequest.put("PayTypeID", "ImmediatePay");
            queryOrderRequest.queryRequest.put("OrderNo", orderId);
            queryOrderRequest.queryRequest.put("QueryDetail", "0");//0：状态查询；1：详细查询

            Map<String, Object> tranMap = new HashMap<String, Object>();
            tranMap.put("PayTypeID", "ImmediatePay");
            tranMap.put("OrderNo", orderId);
            tranMap.put("QueryDetail", "0");

            int payIndex = AbcPayConfig.getPayIndex(sysId);

            JSON json = queryOrderRequest.extendPostRequest(payIndex);
            if (null == json) {
                logger.info("[abc_pay_query]没有查询到数据");
                throw new Exception("[abc_pay_query]没有查询到数据！");
            }
            String ReturnCode = json.GetKeyValue("ReturnCode");
            String ErrorMessage = json.GetKeyValue("ErrorMessage");
            logger.info("[abc_pay_query] ReturnCode   = [" + ReturnCode + "]");
            logger.info("[abc_pay_query] ErrorMessage = [" + ErrorMessage + "]");

            String order = json.GetKeyValue("Order");
            //BASE64 解码
            String retData = new String(BaseCoder.decryptBASE64(order), "GBK");

            if (ReturnCode.equals("0000") && null != retData) {
                JSONObject jsonObject = new JSONObject(retData);
                String orderNo = jsonObject.getString("OrderNo");
                String status = jsonObject.getString("Status");//01:未支付 02:无回应 03:已请款 04:成功 05:已退款 07:授权确认成功 00:授权已取消 99:失败
                String _status = "04".equals(status) ? "1" : status;

                String _orderDate = jsonObject.getString("OrderDate");
                String _orderTime = jsonObject.getString("OrderTime");

                resultMap.put("orderId", orderNo);
                resultMap.put("status", _status);
                resultMap.put("tranData", ToolsUtil.mapObjToJson(tranMap));
                resultMap.put("notifyData", retData);
                resultMap.put("comment", AbcPayConfig.getMessage(status));
                resultMap.put("tranDate", _orderDate + _orderTime);

                logger.info("[abc_pay_query]查询返回：" + resultMap.toString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.info("[abc_pay_query]查询接口出错！");
            throw new Exception("[abc_pay_query]查询接口出错！");
        }
        logger.info("[abc_pay_query]查询结束：" + orderId);
        return resultMap;
    }

    /**
     * 从支付宝查询订单状态
     *
     * @param pMap
     * @return
     */
    @Override
    public Map<String, Object> queryFromAliPay(Map<String, Object> pMap) throws Exception {
        logger.info("[alipay_query]查询开始：" + MapUtils.getString(pMap, "orderId"));
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String orderId = MapUtils.getString(pMap, "orderid");
        String sysId = MapUtils.getString(pMap, "Emt_sys_id");
        if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(sysId))
            throw new Exception("[alipay_query]查询参数为空！");

        try {
            //把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            //支付宝交易号(查询时支付宝交易号与商户网站订单号不能同时为空)
            String trade_no = "";

            String partner = AlipayConfig.getPartner(sysId);
            String key = AlipayConfig.getKey(sysId);
            sParaTemp.put("service", "single_trade_query");
            sParaTemp.put("partner", partner);
            sParaTemp.put("_input_charset", AlipayConfig.input_charset);
            sParaTemp.put("trade_no", trade_no);
            sParaTemp.put("out_trade_no", orderId);

            //建立请求
            String resultXml = AlipaySubmit.buildRequest("", "", sParaTemp, key);
            logger.info("[alipay_query]支付宝返回数据如下：{}", resultXml);
            if (null == resultXml || "".equals(resultXml)) {
                logger.info("[alipay_query]没有查询到数据");
                throw new Exception("[alipay_query]没有查询到数据");
            }
            Map<String, Object> xmlMap = AlipayXmlForDOM4J.getMapByXml(resultXml.trim());
            logger.info("[alipay_query]查询支付状态结果：{},xmlMap:{}", xmlMap);
            if (xmlMap == null || xmlMap.size() <= 0) {
                logger.info("[alipay_query]没有查询到数据");
                throw new Exception("[alipay_query]没有查询到数据");
            }
            String is_success = xmlMap.get("is_success").toString();
            logger.info("[alipay_query]is_success：{}", is_success);
            if ("T".equals(is_success)) {
                Map<String, String> request = (Map<String, String>) xmlMap.get("request");
                Map<String, String> response = (Map<String, String>) xmlMap.get("response");

                String gmt_payment = MapUtils.getString(response, "gmt_payment");//response.get("gmt_create").toString();
                String trade_status = MapUtils.getString(response, "trade_status");//response.get("trade_status").toString();
                String comment = ReturnCode.getMessage(trade_status);

                String status = "TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status) ? "1" : trade_status;

                resultMap.put("orderId", orderId);
                resultMap.put("status", status);
                resultMap.put("tranData", ToolsUtil.mapToJson(sParaTemp));
                resultMap.put("notifyData", ToolsUtil.mapObjToJson(xmlMap));
                resultMap.put("comment", comment);
                resultMap.put("tranDate", gmt_payment);

                logger.info("[alipay_query]查询返回：" + resultMap.toString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.info("[alipay_query]查询出现异常" + e.getMessage());
            throw new Exception("[alipay_query]查询出现异常" + e.getMessage());
        }
        logger.info("[alipay_query]查询结束：" + orderId);
        return resultMap;
    }

    /**
     * 从中行查询订单状态
     *
     * @param pMap
     * @return
     */
    @Override
    public Map<String, Object> queryFromBOC(Map<String, Object> pMap) throws Exception {
        logger.info("[boc_pay_query]查询开始：" + MapUtils.getString(pMap, "orderId"));
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String orderId = MapUtils.getString(pMap, "orderid");
        String sysId = MapUtils.getString(pMap, "Emt_sys_id");
        if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(sysId))
            throw new Exception("[boc_pay_query]查询参数为空！");

        try {
            // 取得参数
            String merchantNo = BocPayConfig.getB2cMerNo(sysId);

            //加签
            StringBuilder plainTextBuilder = new StringBuilder();
            plainTextBuilder.append(merchantNo).append(":").append(orderId);
            String plainText = plainTextBuilder.toString();
            logger.info("[plainText]=[" + plainText + "]");
            byte plainTextByte[] = plainText.getBytes("UTF-8");
            //获取私钥证书
            BocPKCSTool tool = BocPKCSTool.getSigner(BocPayConfig.getKeystoreFileB2C(), BocPayConfig.signkeyPassword, BocPayConfig.signkeyPassword, "PKCS7");
            //签名
            String signData = tool.p7Sign(plainTextByte);

            logger.info("---------- CommonQueryOrder send message ----------");
            logger.info("[merchantNo]=[" + merchantNo + "]");
            logger.info("[orderNos]=[" + orderId + "]");
            logger.info("[signData]=[" + signData + "]");
            Map<String, String> tranData = new HashMap<>();
            tranData.put("merchantNo", merchantNo);
            tranData.put("orderId", orderId);
            tranData.put("signData", signData);

            String action = BocPayConfig.pgwPortalUrl + "/CommonQueryOrder.do";
            logger.info("[action]=[" + action + "]");

            HttpRequest request = new HttpRequest(HttpResultType.BYTES);
            NameValuePair[] nameValuePair = new NameValuePair[3];
            nameValuePair[0] = new NameValuePair("merchantNo", merchantNo);
            nameValuePair[1] = new NameValuePair("orderNos", orderId);
            nameValuePair[2] = new NameValuePair("signData", signData);
            request.setParameters(nameValuePair);
            request.setUrl(action);
            HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
            HttpResponse postRes = httpProtocolHandler.execute(request,"","");
            if (postRes == null) {
                throw new Exception("[boc_pay_query]查询返回为空！");
            }

            String resultXml = postRes.getStringResult();

            logger.info("[boc_pay_query]中行查询返回数据如下：{}", resultXml);
            if (TextUtils.isEmpty(resultXml)) {
                logger.info("[boc_pay_query]没有查询到数据");
                throw new Exception("[boc_pay_query]没有查询到数据");
            }
            Map<String, Object> xmlMap = BocPayConfig.getMapByXml(resultXml.trim());
            logger.info("[boc_pay_query]查询支付状态结果：{},xmlMap:{}", xmlMap);
            if (xmlMap == null || xmlMap.size() <= 0) {
                logger.info("[boc_pay_query]没有查询到数据");
                throw new Exception("[boc_pay_query]没有查询到数据");
            }

            Map<String, String> hMap = (Map<String, String>) MapUtils.getObject(xmlMap, "header");
            Map<String, String> bMap = (Map<String, String>) MapUtils.getObject(xmlMap, "body");

            String hdlSts = MapUtils.getString(hMap, "hdlSts");
            logger.info("[boc_pay_query]hdlSts：{}", hdlSts);
            if ("A".equals(hdlSts)) {
                String orderStatus = MapUtils.getString(bMap, "orderStatus");
                String gmt_create = MapUtils.getString(bMap, "payTime");
                logger.info("[boc_pay_query]orderStatus：{}", orderStatus);

                resultMap.put("orderId", orderId);
                resultMap.put("status", orderStatus);//订单状态：0-未处理 1-支付 4-未明 5-失败
                resultMap.put("tranData", ToolsUtil.mapToJson(tranData));
                resultMap.put("notifyData", ToolsUtil.mapObjToJson(xmlMap));
                resultMap.put("comment", BocPayConfig.getMessage(orderStatus));
                resultMap.put("tranDate", gmt_create);

                logger.info("[boc_pay_query]查询返回：" + resultMap.toString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.info("[boc_pay_query]查询失败：" + e.getMessage());
            throw new Exception("[boc_pay_query]查询失败：" + e.getMessage());
        }
        logger.info("[boc_pay_query]查询结束：" + orderId);
        return resultMap;
    }

    /**
     * 从建行查询订单状态
     *
     * @param pMap
     * @return
     */
    @Override
    public Map<String, Object> queryFromCCB(Map<String, Object> pMap) throws Exception {
        logger.info("[ccb_pay_query]查询开始：" + MapUtils.getString(pMap, "orderId"));
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String orderId = MapUtils.getString(pMap, "orderid");
        String sysId = MapUtils.getString(pMap, "Emt_sys_id");
        String orderDate = MapUtils.getString(pMap, "orderDate");
        if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(sysId))
            throw new Exception("[ccb_pay_query]查询参数为空！");

        try {
            String QPOSID = CcbConfig.getPOSID(sysId);

            String bankURL = CcbConfig.queryUrl;
            logger.info("[ccb_pay_query]bankURL:{}", bankURL);
            boolean isToday = isToday(orderDate);
            String KIND = isToday ? "0" : "1";
            logger.info("[ccb_pay_query]KIND:{}", KIND);

            Map<String, Object> map = new HashMap<String, Object>();
            StringBuffer p = new StringBuffer();
            p.append("MERCHANTID=").append(CcbConfig.QMERCHANTID);
            p.append("&BRANCHID=").append(CcbConfig.QBRANCHID);        //分行代码
            p.append("&POSID=").append(QPOSID);            //柜台号
            p.append("&ORDERDATE=").append("");
            p.append("&BEGORDERTIME=").append("");
            p.append("&ENDORDERTIME=").append("");
            p.append("&ORDERID=").append(orderId);
            p.append("&QUPWD=").append("");
            p.append("&TXCODE=").append(CcbConfig.QTXCODE);            //交易码TXCODE=410408，这个参数的值是固定的，不可以修改
            p.append("&TYPE=").append("0");                            //流水类型 0支付流水 1退款流水
            p.append("&KIND=").append(KIND);                        //流水状态 0 未结算流水 1 已结算流水
            p.append("&STATUS=").append("1");                        //交易状态 0失败 1成功 2不确定 3全部（已结算流水查询不支持全部）
            p.append("&SEL_TYPE=").append(CcbConfig.QSEL_TYPE);        //查询方式  1页面形式 2文件返回形式 (提供TXT和XML格式文件的下载) 3XML页面形式
            p.append("&PAGE=").append("1");
            p.append("&OPERATOR=").append("");
            p.append("&CHANNEL=").append("");
            logger.info("[ccb_pay_query]p:{}", p.toString());
            String sign = CcbMD5.md5Str(p.toString());
            logger.info("[ccb_pay_query]sign:{}", sign);

            map.put("MERCHANTID", CcbConfig.QMERCHANTID);
            map.put("BRANCHID", CcbConfig.QBRANCHID);
            map.put("POSID", QPOSID);
            map.put("ORDERDATE", "");
            map.put("BEGORDERTIME", "");
            map.put("ENDORDERTIME", "");
            map.put("ORDERID", orderId);
            map.put("QUPWD", CcbConfig.QUPWD);
            map.put("TXCODE", CcbConfig.QTXCODE);
            map.put("TYPE", "0");
            map.put("KIND", KIND);
            map.put("STATUS", "1");
            map.put("SEL_TYPE", CcbConfig.QSEL_TYPE);
            map.put("PAGE", "1");
            map.put("OPERATOR", "");
            map.put("CHANNEL", "");
            map.put("MAC", sign);
            logger.info("[ccb_pay_query]map:{}", map);

            String resultXml = CcbClientUtil.httpsPost(bankURL, map, "GB2312");
            logger.info("[ccb_pay_query]建行返回数据如下：{}", resultXml);
            if (TextUtils.isEmpty(resultXml)) {
                logger.info("[ccb_pay_query]没有查询到数据");
                throw new Exception("[ccb_pay_query]没有查询到数据");
            }
            Map<String, Object> xmlMap = CcbXmlForDOM4J.getMapByXml(resultXml.trim());
            logger.info("[ccb_pay_query]建行返回数据后，xml 转 map：{}", xmlMap);
            logger.info("[ccb_pay_query]查询支付状态结果：{},xmlMap:{}", xmlMap);
            if (xmlMap == null || xmlMap.size() <= 0) {
                logger.info("[ccb_pay_query]没有查询到数据");
                throw new Exception("[ccb_pay_query]没有查询到数据");
            }
            // 验证返回签名
            Map<String, String> orderInfoMap = (Map<String, String>) xmlMap.get("QUERYORDER");
            logger.info("[ccb_pay_query]建行返回数据后，获取QUERYORDER：{}的值", orderInfoMap);
            if (orderInfoMap == null || orderInfoMap.size() <= 0) {
                logger.info("[ccb_pay_query]没有查询到数据");
                throw new Exception("[ccb_pay_query]没有查询到数据");
            }
            String returnCode = xmlMap.get("RETURN_CODE").toString();
            if ("000000".equals(returnCode)) {
                String status = MapUtils.getString(orderInfoMap, "STATUSCODE");//"1":成功
                String tranDate = MapUtils.getString(orderInfoMap, "ORDERDATE");

                resultMap.put("orderId", orderId);
                resultMap.put("status", status);
                resultMap.put("tranData", ToolsUtil.mapObjToJson(map));
                resultMap.put("notifyData", ToolsUtil.mapObjToJson(xmlMap));
                resultMap.put("comment", CcbConfig.getMessage(status));
                resultMap.put("tranDate", tranDate);

                logger.info("[ccb_pay_query]查询返回：" + resultMap.toString());

                return resultMap;
            }
        } catch (Exception e) {
            logger.info("[ccb_pay_query]查询出错：" + e.getMessage());
            throw new Exception("[ccb_pay_query]查询出错：" + e.getMessage());
        }
        logger.info("[ccb_pay_query]查询结束：" + orderId);
        return resultMap;
    }

    /**
     * 从工行查询订单状态
     *
     * @param pMap
     * @return
     */
    @Override
    public Map<String, Object> queryFromICBC(Map<String, Object> pMap) throws Exception {
        logger.info("[icbc_pay_query]查询开始：" + MapUtils.getString(pMap, "orderId"));
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String orderId = MapUtils.getString(pMap, "orderid");
        String sysId = MapUtils.getString(pMap, "Emt_sys_id");
        String orderDate = MapUtils.getString(pMap, "orderDate");
        if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(sysId))
            throw new Exception("[icbc_pay_query]查询参数为空！");

        try {
            IcbcHttpsClientSv0 icbcHttpsClientSv0 = new IcbcHttpsClientSv0();
            String protocolXML = icbcHttpsClientSv0.getHttpsClientPost(sysId, orderId, orderDate);
            String errorDesc = IcbcConfig.getErrorDesc(protocolXML);
            if (errorDesc != null) {
                logger.info("[icbc_pay_query]查询出错：" + errorDesc);
                throw new Exception("[icbc_pay_query]查询出错：" + errorDesc);
            }
            Map<String, Object> queryMap = IcbcXmlForDOM4J.parseB2C002(protocolXML);

            logger.info("[icbc_pay_query]查询结果：" + queryMap);
            Map<String, Object> in = (Map<String, Object>) queryMap.get("in");
            Map<String, Object> out = (Map<String, Object>) queryMap.get("out");

            String tranDate = MapUtils.getString(in, "tranDate");
            String tranTime = MapUtils.getString(out, "tranTime");

            String tranStat = MapUtils.getString(out, "tranStat");//1:交易成功，已清算
            resultMap.put("orderId", orderId);
            resultMap.put("status", tranStat);
            resultMap.put("tranData", protocolXML);
            resultMap.put("notifyData", ToolsUtil.mapObjToJson(queryMap));
            resultMap.put("comment", IcbcConfig.getMessage(tranStat));
            resultMap.put("tranDate", tranDate + tranTime);

            logger.info("[icbc_pay_query]查询返回：" + resultMap.toString());
            logger.info("[icbc_pay_query]查询结束：" + orderId);
            return resultMap;
        } catch (Exception e) {
            logger.info("[icbc_pay_query]查询出错：" + e.getMessage());
            throw new Exception("[icbc_pay_query]查询出错：" + e.getMessage());
        }
    }

    /**
     * 从银联查询订单状态
     *
     * @param pMap
     * @return
     */
    @Override
    public Map<String, Object> queryFromUnionpay(Map<String, Object> pMap) throws Exception {
        logger.info("[unionpay_query]查询开始：" + MapUtils.getString(pMap, "orderId"));
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String orderId = MapUtils.getString(pMap, "orderid");
        String sysId = MapUtils.getString(pMap, "Emt_sys_id");
        String orderDate = MapUtils.getString(pMap, "orderDate");
        String merId = MapUtils.getString(pMap, "merId");
        String config = MapUtils.getString(pMap, "config");
        if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(sysId) || TextUtils.isEmpty(orderDate)
                || TextUtils.isEmpty(merId) || TextUtils.isEmpty(config))
            throw new Exception("[unionpay_query]查询参数为空！");

        logger.info("[unionpay_query]查询支付状态开始，查询orderId:{},tranDate:{}", orderId, orderDate);

        try {
            Map<String, String> map = new HashMap<>();
            map.put("version", SDKUtil.version);
            map.put("encoding", SDKUtil.encoding_UTF8);
            map.put("signMethod", "01");
            map.put("txnType", "00");
            map.put("txnSubType", "00");
            map.put("bizType", "000000");
            //商户信息
            map.put("accessType", "0");
            map.put("merId", merId);
            //订单信息
            map.put("orderId", orderId);
            map.put("txnTime", orderDate);

            SDKConfig.getConfig("pay/unopPay/" + config);
            Map<String, String> mapRet = SDKUtil.signData(map, SDKUtil.encoding_UTF8);
            map.put("certId", mapRet.get("certId"));
            map.put("signature", mapRet.get("signature"));

            String retString = HttpsUtil.doPostHttps("https://gateway.95516.com/gateway/api/queryTrans.do", map, "UTF-8");
            logger.info("[unionpay_query]查询支付状态查询结果：{}", retString);
            if (null == retString || "".equals(retString)) {
                logger.info("[unionpay_query]没有查询到数据");
                throw new Exception("[unionpay_query]没有查询到数据");
            }

            Map<String, String> retMap = new HashMap<String, String>();
            String[] array = retString.split("\\&");
            if (null == array || array.length <= 0) {
                logger.info("[unionpay_query]没有查询到数据");
                throw new Exception("[unionpay_query]没有查询到数据");
            }
            for (int i = 0; i < array.length; i++) {
                String item = array[i];
                String[] tempArray = item.split("\\=");
                if (tempArray.length > 1) {
                    retMap.put(tempArray[0], tempArray[1]);
                } else {
                    retMap.put(tempArray[0], "");
                }
            }
            if (retMap == null || retMap.size() <= 0) {
                logger.info("[unionpay_query]没有查询到数据");
                throw new Exception("[unionpay_query]没有查询到数据");
            }

            //签名验证
            if (!SDKUtil.validate(retMap, SDKUtil.encoding_UTF8)) {
                LogUtil.writeLog("[unionpay_query]验证签名结果[失败].");
                //验签失败，需解决验签问题
                throw new Exception("[unionpay_query]验证签名结果[失败]");
            }

            String respCode = MapUtils.getString(retMap, "respCode");//retMap.get("respCode").toString();
            if (respCode.equals("00")) {//操作成功
                String origRespCode = MapUtils.getString(retMap, "origRespCode");//交易成功时返回"00"
                String orderIdRet = MapUtils.getString(retMap, "orderId");
                String txnTime = MapUtils.getString(retMap,"txnTime");
                String origRespMsg = MapUtils.getString(retMap,"origRespMsg");
                String status = "00".equals(origRespCode) ? "1" : origRespCode;

                resultMap.put("orderId", orderId);
                resultMap.put("status", status);
                resultMap.put("tranData", ToolsUtil.mapToJson(map));
                resultMap.put("notifyData", ToolsUtil.mapToJson(retMap));
                resultMap.put("comment", origRespMsg);
                resultMap.put("tranDate", txnTime);

                logger.info("[unionpay_query]查询返回：" + resultMap.toString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.info("[unionpay_query]查询出错：" + e.getMessage());
            throw new Exception("[unionpay_query]查询出错：" + e.getMessage());
        }
        logger.info("[unionpay_query]查询结束：" + orderId);
        return resultMap;
    }

    /**
     * 从微信平台查询订单状态
     *
     * @param pMap
     * @return
     */
    @Override
    public Map<String, Object> queryFromWXPay(Map<String, Object> pMap) throws Exception {
        logger.info("[weixinpay_query]查询开始：" + MapUtils.getString(pMap, "orderId"));
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String orderId = MapUtils.getString(pMap, "orderid");
        String sysId = MapUtils.getString(pMap, "Emt_sys_id");
        String shopCode = MapUtils.getString(pMap, "shopCode");
        if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(sysId) || TextUtils.isEmpty(shopCode))
            throw new Exception("[weixinpay_query]查询参数为空！");

        Map<String, String> wxpMap = WeixinConfig.getWXConfigure(shopCode);
        logger.info("[weixinpay_query]微信支付查询订单状态，获取数据：", wxpMap);
        if (null == wxpMap || wxpMap.size() == 0) {
            throw new Exception("配置参数为空，请检查!");
        }

        String appId = MapUtils.getString(wxpMap, "appId");
        String mchId = MapUtils.getString(wxpMap, "mchId");
        String appSecret = MapUtils.getString(wxpMap, "appSecret");

        logger.info("[weixinpay_query]微信支付查询订单状态提交参数appid={}，mch_id={}，appSecret={}", appId, mchId, appSecret);
        try {
            Map<String, String> ppMap = new HashMap<String, String>();
            Long randomLong = System.currentTimeMillis();
            ppMap.put("appid", appId);
            ppMap.put("mch_id", mchId);
            ppMap.put("out_trade_no", orderId);
            ppMap.put("nonce_str", randomLong.toString());
            List<String> lstSign = new ArrayList<String>();
            lstSign.add("sign");
            lstSign.add("sign_type");
            ppMap = ToolsUtil.paraFilter(ppMap, lstSign, true);
            String prestr = ToolsUtil.createLinkString(ppMap);
            String mySign = wxSign(prestr, appSecret);
            ppMap.put("sign", mySign);

            String xml = ToolsUtil.mapToXml(ppMap, null, null);

            URL url = new URL(WeixinConfig.orderQueryUrlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
            conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
            conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
            conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
            conn.setUseCaches(false); // Post 请求不能使用缓存
            // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
            conn.setRequestProperty("Content-Length", xml.length() + "");
            String encode = "utf-8";
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), encode);
            out.write(xml);
            out.flush();
            out.close();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("连接微信服务器出现异常：" + conn.getResponseCode());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            StringBuffer strBuf = new StringBuffer();
            while ((line = in.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            in.close();
            String resultXml = strBuf.toString();
            logger.info("[weixinpay_query]微信支付查询订单状态返回：resultXml={}", resultXml);

            if (TextUtils.isEmpty(resultXml) || TextUtils.isEmpty(appSecret)) {
                throw new Exception("验签参数为空，请检查!");
            }
            Map<String, String> retMap = WeixinXmlForDOM4J.xmlToMap(resultXml);
            if (null == retMap || retMap.size() <= 0) {
                logger.info("[weixinpay_query]没有查询到数据");
                throw new Exception("[weixinpay_query]没有查询到数据");
            }

            // 验证数据是否被篡改
            String sign = MapUtils.getString(retMap, "sign");
            List<String> listSign = new ArrayList<String>();
            listSign.add("sign");
            listSign.add("sign_type");
            retMap = ToolsUtil.paraFilter(retMap, listSign, true);
            String mpStr = ToolsUtil.createLinkString(retMap);
            mySign = wxSign(mpStr, appSecret).toUpperCase();
            if (!mySign.equals(sign)) {// 判断数据是否被篡改
                logger.info("[weixinpay_query]微信支付接口返回数据被篡改！");
                throw new Exception("[weixinpay_query]微信支付接口返回数据被篡改！");
            }
            //获取数据
            String return_code = MapUtils.getString(retMap, "return_code");
            String result_code = MapUtils.getString(retMap, "result_code");
            if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
                String trade_state = MapUtils.getString(retMap, "trade_state");
                String time_end = MapUtils.getString(retMap, "time_end");

                String status = "SUCCESS".equals(trade_state) ? "1" : trade_state;

                resultMap.put("orderId", orderId);
                resultMap.put("status", status);
                resultMap.put("tranData", ToolsUtil.mapToJson(ppMap));
                resultMap.put("notifyData", ToolsUtil.mapToJson(retMap));
                resultMap.put("comment", WeixinConfig.getMessage(trade_state));
                resultMap.put("tranDate", time_end);

                logger.info("[weixinpay_query]查询返回：" + resultMap.toString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.info("[weixinpay_query]查询数据出错：" + e.getMessage());
            throw new Exception("[weixinpay_query]查询数据出错：" + e.getMessage());
        }
        logger.info("[weixinpay_query]查询结束：" + orderId);
        return resultMap;
    }
}
