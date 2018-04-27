package com.emt.epay.qapi.inter;

import java.util.Map;

/**
 * Created by Administrator on 2018-03-28.
 */
public interface IEpayQueryApi {

    /**
     * 从农行查询订单状态
     * @param pMap
     * @return
     */
    Map<String, Object> queryFromABC(Map<String, Object> pMap) throws Exception ;

    /**
     * 从支付宝查询订单状态
     * @param pMap
     * @return
     */
    Map<String, Object> queryFromAliPay(Map<String, Object> pMap) throws Exception ;

    /**
     * 从中行查询订单状态
     * @param pMap
     * @return
     */
    Map<String, Object> queryFromBOC(Map<String, Object> pMap) throws Exception ;

    /**
     * 从建行查询订单状态
     * @param pMap
     * @return
     */
    Map<String, Object> queryFromCCB(Map<String, Object> pMap) throws Exception ;

    /**
     * 从工行查询订单状态
     * @param pMap
     * @return
     */
    Map<String, Object> queryFromICBC(Map<String, Object> pMap) throws Exception ;

    /**
     * 从银联查询订单状态
     * @param pMap
     * @return
     */
    Map<String, Object> queryFromUnionpay(Map<String, Object> pMap) throws Exception ;

    /**
     * 从微信平台查询订单状态
     * @param pMap
     * @return
     */
    Map<String, Object> queryFromWXPay(Map<String, Object> pMap) throws Exception;

}
