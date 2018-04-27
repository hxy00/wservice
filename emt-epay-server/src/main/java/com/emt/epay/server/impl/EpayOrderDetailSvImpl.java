package com.emt.epay.server.impl;

import com.emt.epay.dao.mapper.EpayOrderDetailMapper;
import com.emt.epay.server.inter.IEpayOrderDetailSv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-03-27.
 */
@Repository("epayOrderDetailSvImpl")
public class EpayOrderDetailSvImpl implements IEpayOrderDetailSv{

    @Autowired
    private EpayOrderDetailMapper epayOrderDetailMapper;

    /**
     * 获取hour小时内的订单数据
     *
     * @param hour
     * @return
     */
    @Override
    public List<Map<String, Object>> payList(int hour) {
        return epayOrderDetailMapper.payList(hour);
    }

    /**
     * 更新银行卡
     *
     * @param orderId
     * @param payCompany
     * @return
     */
    @Override
    public int modifyIsScan(String orderId, String payCompany) {
        return epayOrderDetailMapper.modifyIsScan(orderId, payCompany);
    }
}
