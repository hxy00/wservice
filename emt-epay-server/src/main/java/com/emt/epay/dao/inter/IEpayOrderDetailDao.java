package com.emt.epay.dao.inter;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IEpayOrderDetailDao{

    /**
     * 获取hour小时内的订单数据
     *
     * @param hour
     * @return
     */
    List<Map<String, Object>> payList(@Param("hour") int hour);

    /**
     * 更新银行卡
     *
     * @param orderId
     * @param payCompany
     * @return
     */
    int modifyIsScan(@Param("orderId") String orderId, @Param("payCompany") String payCompany);
}
