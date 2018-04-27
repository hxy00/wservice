package com.emt.epay.server.inter;

/**
 * Created by Administrator on 2018-03-28.
 */
public interface IEpayOrderQuerySv {

    /**
     * 查询银行支付状态
     * @Title: QueryBankState
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param hours
     * @throws
     */
    void queryOrderState(int hours);



}
