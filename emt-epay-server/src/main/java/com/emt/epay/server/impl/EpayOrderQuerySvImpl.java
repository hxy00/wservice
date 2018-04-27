package com.emt.epay.server.impl;

import com.emt.epay.dao.entity.EpayOrderScan;
import com.emt.epay.dao.inter.IEpayOrderDetailDao;
import com.emt.epay.dao.inter.IEpayOrderScanDao;
import com.emt.epay.qapi.inter.IEpayQueryApi;
import com.emt.epay.qapi.util.Global;
import com.emt.epay.server.inter.IEpayOrderQuerySv;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-03-28.
 */
@Repository("epayOrderQuerySvImpl")
public class EpayOrderQuerySvImpl implements IEpayOrderQuerySv {
    private Logger logger = LoggerFactory.getLogger(EpayOrderQuerySvImpl.class);

    //根据操作系统设置读取证书路径
    private static String operatingSystem = Global.getConfig("epay.OS.switch");

    @Autowired
    private IEpayOrderDetailDao iEpayOrderDetailDao;

    @Autowired
    private IEpayOrderScanDao iEpayOrderScanDao;

    @Autowired
    private IEpayQueryApi iEpayQueryApi;

    /**
     * 查询hours小时以内已支付的订单
     *
     * @param hours
     * @return
     */
    @Override
    public void queryOrderState(int hours) {
        List<Map<String, Object>> lstMap = iEpayOrderDetailDao.payList(hours);
        if (null == lstMap || lstMap.size() == 0) {
            return;
        }
        for (Map<String, Object> oMap : lstMap) {
            String orderId = MapUtils.getString(oMap, "orderid");
            String payCompany = MapUtils.getString(oMap, "payCompany");
            String tranStat = MapUtils.getString(oMap, "tranStat");

            logger.info("[queryOrderState] 订单号：{}， 支付方式：{}, 状态：{}", orderId, payCompany, tranStat);
            String method = oMap.get("payCompany") + "_query";
            try {
                Method md = getClass().getDeclaredMethod(method, Map.class);
                md.invoke(this, oMap);
            } catch (Exception e) {
                logger.error("[queryOrderState]" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 支付宝支付
     *
     * @param pMap
     * @return
     */
    public void alipay_query(Map<String, Object> pMap) {
        try {
            String payCompany = MapUtils.getString(pMap, "payCompany");
            String amount = MapUtils.getString(pMap, "amount");
            int amountInt = Integer.valueOf(amount);
            //获取查询数据
            Map<String, Object> qMap = iEpayQueryApi.queryFromAliPay(pMap);
            if (null == qMap || qMap.size() == 0) {
                return;
            }

            String orderId = MapUtils.getString(qMap, "orderId");
            String status = MapUtils.getString(qMap, "status");
            String tranData = MapUtils.getString(qMap, "tranData");
            String notifyData = MapUtils.getString(qMap, "notifyData");
            String comment = MapUtils.getString(qMap, "comment");
            String tranDate = MapUtils.getString(qMap, "tranDate");
            String orderDate = MapUtils.getString(pMap, "orderDate");

            //判断查询结果
            if ("1".equals(status)) {//对未支付成功的订单作预警处理
                logger.info("[alipay_query]支付成功的订单处理");

                //更改为：已扫描（组合主键：orderId，payCompany）
                int i = iEpayOrderDetailDao.modifyIsScan(orderId, payCompany);
                logger.info("[alipay_query]更新为已扫描，处理结果：" + i);

            } else {
                logger.info("[alipay_query]对未支付成功的订单作预警处理！！！");

            }

            //保存扫描结果
            EpayOrderScan epayOrderScan = new EpayOrderScan();
            epayOrderScan.setOrderId(orderId);
            epayOrderScan.setPayCompany(payCompany);
            epayOrderScan.setAmount(amountInt);
            epayOrderScan.setStatus(status);
            epayOrderScan.setComment(comment);
            epayOrderScan.setOrderDate(orderDate);
            epayOrderScan.setTranDate(tranDate);
            epayOrderScan.setSubData(tranData);
            epayOrderScan.setNotifyData(notifyData);
            epayOrderScan.setReserver1("");
            epayOrderScan.setReserver2("");
            int i = iEpayOrderScanDao.saveScaned(epayOrderScan);
            logger.info("[alipay_query]保存已扫描数据，处理结果：" + i);
        } catch (Exception e) {
            logger.info("[alipay_query]查询出异常：" + e.getMessage());
        }
    }

    public void alipay_pc_query(Map<String, Object> pMap) {
        alipay_query(pMap);
    }

    public void alipay_wap_query(Map<String, Object> pMap) {
        alipay_query(pMap);
    }

    /**
     * 农行支付
     *
     * @param pMap
     * @return
     */
    public void abc_pay_pc_query(Map<String, Object> pMap) {
        try {
            String payCompany = MapUtils.getString(pMap, "payCompany");
            String amount = MapUtils.getString(pMap, "amount");
            int amountInt = Integer.valueOf(amount);
            //获取查询数据
            Map<String, Object> qMap = iEpayQueryApi.queryFromABC(pMap);
            if (null == qMap || qMap.size() == 0) {
                return;
            }

            String orderId = MapUtils.getString(qMap, "orderId");
            String status = MapUtils.getString(qMap, "status");
            String tranData = MapUtils.getString(qMap, "tranData");
            String notifyData = MapUtils.getString(qMap, "notifyData");
            String comment = MapUtils.getString(qMap, "comment");
            String tranDate = MapUtils.getString(qMap, "tranDate");
            String orderDate = MapUtils.getString(pMap, "orderDate");

            //判断查询结果
            if ("1".equals(status)) {//对未支付成功的订单作预警处理
                logger.info("[abc_pay_query]支付成功的订单处理");

                //更改是否已扫描标识
                int i = iEpayOrderDetailDao.modifyIsScan(orderId, payCompany);
                logger.info("[abc_pay_query]更新为已扫描，处理结果：" + i);

            } else {
                logger.info("[abc_pay_query]对未支付成功的订单作预警处理！！！");

            }

            //保存扫描结果
            EpayOrderScan epayOrderScan = new EpayOrderScan();
            epayOrderScan.setOrderId(orderId);
            epayOrderScan.setPayCompany(payCompany);
            epayOrderScan.setAmount(amountInt);
            epayOrderScan.setStatus(status);
            epayOrderScan.setComment(comment);
            epayOrderScan.setOrderDate(orderDate);
            epayOrderScan.setTranDate(tranDate);
            epayOrderScan.setSubData(tranData);
            epayOrderScan.setNotifyData(notifyData);
            epayOrderScan.setReserver1("");
            epayOrderScan.setReserver2("");
            int i = iEpayOrderScanDao.saveScaned(epayOrderScan);
            logger.info("[abc_pay_query]保存已扫描数据，处理结果：" + i);
        } catch (Exception e) {
            logger.info("[abc_pay_query]查询出异常：" + e.getMessage());
        }
    }

    public void abc_pay_wap_query(Map<String, Object> pMap) {
        abc_pay_pc_query(pMap);
    }

    /**
     * 建行支付查询
     *
     * @param pMap
     * @return
     */
    public void ccb_pay_query(Map<String, Object> pMap) {
        try {
            String payCompany = MapUtils.getString(pMap, "payCompany");
            String amount = MapUtils.getString(pMap, "amount");
            int amountInt = Integer.valueOf(amount);
            //获取查询数据
            Map<String, Object> qMap = iEpayQueryApi.queryFromCCB(pMap);
            if (null == qMap || qMap.size() == 0) {
                return;
            }

            String orderId = MapUtils.getString(qMap, "orderId");
            String status = MapUtils.getString(qMap, "status");
            String tranData = MapUtils.getString(qMap, "tranData");
            String notifyData = MapUtils.getString(qMap, "notifyData");
            String comment = MapUtils.getString(qMap, "comment");
            String tranDate = MapUtils.getString(qMap, "tranDate");
            String orderDate = MapUtils.getString(pMap, "orderDate");

            //判断查询结果
            if ("1".equals(status)) {//对未支付成功的订单作预警处理
                logger.info("[ccb_pay_query]支付成功的订单处理");

                //更改是否已扫描标识
                int i = iEpayOrderDetailDao.modifyIsScan(orderId, payCompany);
                logger.info("[ccb_pay_query]更新为已扫描，处理结果：" + i);

            } else {
                logger.info("[ccb_pay_query]对未支付成功的订单作预警处理！！！");

            }

            //保存扫描结果
            EpayOrderScan epayOrderScan = new EpayOrderScan();
            epayOrderScan.setOrderId(orderId);
            epayOrderScan.setPayCompany(payCompany);
            epayOrderScan.setAmount(amountInt);
            epayOrderScan.setStatus(status);
            epayOrderScan.setComment(comment);
            epayOrderScan.setOrderDate(orderDate);
            epayOrderScan.setTranDate(tranDate);
            epayOrderScan.setSubData(tranData);
            epayOrderScan.setNotifyData(notifyData);
            epayOrderScan.setReserver1("");
            epayOrderScan.setReserver2("");
            int i = iEpayOrderScanDao.saveScaned(epayOrderScan);
            logger.info("[ccb_pay_query]保存已扫描数据，处理结果：" + i);
        } catch (Exception e) {
            logger.info("[ccb_pay_query]查询出异常：" + e.getMessage());
        }
    }

    public void ccb_wap_query(Map<String, Object> pMap) {
        ccb_pay_query(pMap);
    }

    /**
     * 工行支付状态查询
     *
     * @param pMap
     * @return
     */
    public void icbc_pc_query(Map<String, Object> pMap) {
        try {
            String payCompany = MapUtils.getString(pMap, "payCompany");
            String amount = MapUtils.getString(pMap, "amount");
            int amountInt = Integer.valueOf(amount);
            //获取查询数据
            Map<String, Object> qMap = iEpayQueryApi.queryFromICBC(pMap);
            if (null == qMap || qMap.size() == 0) {
                return;
            }

            String orderId = MapUtils.getString(qMap, "orderId");
            String status = MapUtils.getString(qMap, "status");
            String tranData = MapUtils.getString(qMap, "tranData");
            String notifyData = MapUtils.getString(qMap, "notifyData");
            String comment = MapUtils.getString(qMap, "comment");
            String tranDate = MapUtils.getString(qMap, "tranDate");
            String orderDate = MapUtils.getString(pMap, "orderDate");

            //判断查询结果
            if ("1".equals(status)) {//对未支付成功的订单作预警处理
                logger.info("[icbc_pay_query]支付成功的订单处理");

                //更改是否已扫描标识
                int i = iEpayOrderDetailDao.modifyIsScan(orderId, payCompany);
                logger.info("[icbc_pay_query]更新为已扫描，处理结果：" + i);

            } else {
                logger.info("[icbc_pay_query]对未支付成功的订单作预警处理！！！");

            }

            //保存扫描结果
            EpayOrderScan epayOrderScan = new EpayOrderScan();
            epayOrderScan.setOrderId(orderId);
            epayOrderScan.setPayCompany(payCompany);
            epayOrderScan.setAmount(amountInt);
            epayOrderScan.setStatus(status);
            epayOrderScan.setComment(comment);
            epayOrderScan.setOrderDate(orderDate);
            epayOrderScan.setTranDate(tranDate);
            epayOrderScan.setSubData(tranData);
            epayOrderScan.setNotifyData(notifyData);
            epayOrderScan.setReserver1("");
            epayOrderScan.setReserver2("");
            int i = iEpayOrderScanDao.saveScaned(epayOrderScan);
            logger.info("[icbc_pay_query]保存已扫描数据，处理结果：" + i);
        } catch (Exception e) {
            logger.info("[icbc_pay_query]查询出异常：" + e.getMessage());
        }
    }

    public void icbc_wap_query(Map<String, Object> pMap) {
        icbc_pc_query(pMap);
    }

    /**
     * 银联“网银支付”-查询订单状态
     *
     * @param pMap
     * @return
     */
    public void unionpay_query(Map<String, Object> pMap) {
        try {
            String payCompany = MapUtils.getString(pMap, "payCompany");
            String amount = MapUtils.getString(pMap, "amount");
            int amountInt = Integer.valueOf(amount);
            //获取查询数据
            Map<String, Object> qMap = iEpayQueryApi.queryFromUnionpay(pMap);
            if (null == qMap || qMap.size() == 0) {
                return;
            }

            String orderId = MapUtils.getString(qMap, "orderId");
            String status = MapUtils.getString(qMap, "status");
            String tranData = MapUtils.getString(qMap, "tranData");
            String notifyData = MapUtils.getString(qMap, "notifyData");
            String comment = MapUtils.getString(qMap, "comment");
            String tranDate = MapUtils.getString(qMap, "tranDate");
            String orderDate = MapUtils.getString(pMap, "orderDate");

            //判断查询结果
            if ("1".equals(status)) {//对未支付成功的订单作预警处理
                logger.info("[unionpay_query]支付成功的订单处理");

                //更改是否已扫描标识
                int i = iEpayOrderDetailDao.modifyIsScan(orderId, payCompany);
                logger.info("[unionpay_query]更新为已扫描，处理结果：" + i);

            } else {
                logger.info("[unionpay_query]对未支付成功的订单作预警处理！！！");

            }

            //保存扫描结果
            EpayOrderScan epayOrderScan = new EpayOrderScan();
            epayOrderScan.setOrderId(orderId);
            epayOrderScan.setPayCompany(payCompany);
            epayOrderScan.setAmount(amountInt);
            epayOrderScan.setStatus(status);
            epayOrderScan.setComment(comment);
            epayOrderScan.setOrderDate(orderDate);
            epayOrderScan.setTranDate(tranDate);
            epayOrderScan.setSubData(tranData);
            epayOrderScan.setNotifyData(notifyData);
            epayOrderScan.setReserver1("");
            epayOrderScan.setReserver2("");
            int i = iEpayOrderScanDao.saveScaned(epayOrderScan);
            logger.info("[unionpay_query]保存已扫描数据，处理结果：" + i);
        } catch (Exception e) {
            logger.info("[unionpay_query]查询出异常：" + e.getMessage());
        }
    }

    /**
     * 银联支付
     *
     * @param pMap
     * @return
     */
    public void unionpay_b2c_query(Map<String, Object> pMap) {
        pMap.put("merId", "826520148160055");
        if("Linux".equals(operatingSystem)){
            pMap.put("config", "acp_sdk_b2c_pc_linux.properties");
        } else {
            pMap.put("config", "acp_sdk_b2c_pc_windows.properties");
        }
        unionpay_query(pMap);
    }

    public void unionpay_b2b_query(Map<String, Object> pMap) {
        pMap.put("merId", "826520149000003");
        if("Linux".equals(operatingSystem)){
            pMap.put("config", "acp_sdk_b2b_linux.properties");
        } else {
            pMap.put("config", "acp_sdk_b2b_windows.properties");
        }
        unionpay_query(pMap);
    }

    public void unionpay_b2c_wap_query(Map<String, Object> pMap) {
        pMap.put("merId", "826520149000004");
        if("Linux".equals(operatingSystem)){
            pMap.put("config", "acp_sdk_b2c_wap_linux.properties");
        } else {
            pMap.put("config", "acp_sdk_b2c_wap_windows.properties");
        }
        unionpay_query(pMap);
    }

    public void unionpay_b2c_controls_pay(Map<String, Object> pMap) {
        pMap.put("merId", "826520149000004");
        if("Linux".equals(operatingSystem)){
            pMap.put("config", "acp_sdk_b2c_wap_linux.properties");
        } else {
            pMap.put("config", "acp_sdk_b2c_wap_windows.properties");
        }
        unionpay_query(pMap);
    }

    /**
     * apple pay query
     *
     * @param pMap
     * @return
     */
    public void unionpay_b2c_applepay_query(Map<String, Object> pMap) {
        pMap.put("merId", "826520148160057");
        if("Linux".equals(operatingSystem)){
            pMap.put("config", "acp_sdk_apple_linux.properties");
        } else {
            pMap.put("config", "acp_sdk_apple_windows.properties");
        }
        unionpay_query(pMap);
    }

    /**
     * 银联“商城支付”-查询订单状态
     *
     * @param pMap
     * @return
     */
    public void unionpay_emt_query(Map<String, Object> pMap) {
        pMap.put("merId", "105520159982268");
        if("Linux".equals(operatingSystem)){
            pMap.put("config", "acp_sdk_emt_linux.properties");
        } else {
            pMap.put("config", "acp_sdk_emt_windows.properties");
        }
        unionpay_query(pMap);
    }

    /**
     * 微信支付-订单状态查询
     *
     * @param pMap
     * @return
     */
    public void weixin_query(Map<String, Object> pMap) {
        try {
            String payCompany = MapUtils.getString(pMap, "payCompany");
            String amount = MapUtils.getString(pMap, "amount");
            int amountInt = Integer.valueOf(amount);
            //获取查询数据
            Map<String, Object> qMap = iEpayQueryApi.queryFromWXPay(pMap);
            if (null == qMap || qMap.size() == 0) {
                return;
            }

            String orderId = MapUtils.getString(qMap, "orderId");
            String status = MapUtils.getString(qMap, "status");
            String tranData = MapUtils.getString(qMap, "tranData");
            String notifyData = MapUtils.getString(qMap, "notifyData");
            String comment = MapUtils.getString(qMap, "comment");
            String tranDate = MapUtils.getString(qMap, "tranDate");
            String orderDate = MapUtils.getString(pMap, "orderDate");

            //判断查询结果
            if ("1".equals(status)) {//对未支付成功的订单作预警处理
                logger.info("[unionpay_query]支付成功的订单处理");

                //更改是否已扫描标识
                int i = iEpayOrderDetailDao.modifyIsScan(orderId, payCompany);
                logger.info("[weixinpay_query]更新为已扫描，处理结果：" + i);

            } else {
                logger.info("[weixinpay_query]对未支付成功的订单作预警处理！！！");

            }

            //保存扫描结果
            EpayOrderScan epayOrderScan = new EpayOrderScan();
            epayOrderScan.setOrderId(orderId);
            epayOrderScan.setPayCompany(payCompany);
            epayOrderScan.setAmount(amountInt);
            epayOrderScan.setStatus(status);
            epayOrderScan.setComment(comment);
            epayOrderScan.setOrderDate(orderDate);
            epayOrderScan.setTranDate(tranDate);
            epayOrderScan.setSubData(tranData);
            epayOrderScan.setNotifyData(notifyData);
            epayOrderScan.setReserver1("");
            epayOrderScan.setReserver2("");
            int i = iEpayOrderScanDao.saveScaned(epayOrderScan);
            logger.info("[weixinpay_query]保存已扫描数据，处理结果：" + i);
        } catch (Exception e) {
            logger.info("[weixinpay_query]查询出异常：" + e.getMessage());
        }
    }

    public void weixinApp_query(Map<String, Object> pMap) {
        this.weixin_query(pMap);
    }

    public void weixinPay_query(Map<String, Object> pMap) {
        this.weixin_query(pMap);
    }

    public void weixinShop_query(Map<String, Object> pMap) {
        this.weixin_query(pMap);
    }


    /**
     * 中行pc b2c
     *
     * @param pMap
     * @return
     */
    public void boc_b2c_pc_query(Map<String, Object> pMap) {
        try {
            String payCompany = MapUtils.getString(pMap, "payCompany");
            String amount = MapUtils.getString(pMap, "amount");
            int amountInt = Integer.valueOf(amount);
            //获取查询数据
            Map<String, Object> qMap = iEpayQueryApi.queryFromBOC(pMap);
            if (null == qMap || qMap.size() == 0) {
                return;
            }

            String orderId = MapUtils.getString(qMap, "orderId");
            String status = MapUtils.getString(qMap, "status");
            String tranData = MapUtils.getString(qMap, "tranData");
            String notifyData = MapUtils.getString(qMap, "notifyData");
            String comment = MapUtils.getString(qMap, "comment");
            String tranDate = MapUtils.getString(qMap, "tranDate");
            String orderDate = MapUtils.getString(pMap, "orderDate");

            //判断查询结果
            if ("1".equals(status)) {//对未支付成功的订单作预警处理
                logger.info("[boc_pay_query]支付成功的订单处理");

                //更改是否已扫描标识
                int i = iEpayOrderDetailDao.modifyIsScan(orderId, payCompany);
                logger.info("[boc_pay_query]更新为已扫描，处理结果：" + i);

            } else {
                logger.info("[boc_pay_query]对未支付成功的订单作预警处理！！！");

            }

            //保存扫描结果
            EpayOrderScan epayOrderScan = new EpayOrderScan();
            epayOrderScan.setOrderId(orderId);
            epayOrderScan.setPayCompany(payCompany);
            epayOrderScan.setAmount(amountInt);
            epayOrderScan.setStatus(status);
            epayOrderScan.setComment(comment);
            epayOrderScan.setOrderDate(orderDate);
            epayOrderScan.setTranDate(tranDate);
            epayOrderScan.setSubData(tranData);
            epayOrderScan.setNotifyData(notifyData);
            epayOrderScan.setReserver1("");
            epayOrderScan.setReserver2("");
            int i = iEpayOrderScanDao.saveScaned(epayOrderScan);
            logger.info("[boc_pay_query]保存已扫描数据，处理结果：" + i);
        } catch (Exception e) {
            logger.info("[boc_pay_query]查询出异常：" + e.getMessage());
        }
    }

    /**
     * 中行wap b2c
     *
     * @param pMap
     * @return
     */
    public void boc_b2c_wap_query(Map<String, Object> pMap) {
        boc_b2c_pc_query(pMap);
    }

}
