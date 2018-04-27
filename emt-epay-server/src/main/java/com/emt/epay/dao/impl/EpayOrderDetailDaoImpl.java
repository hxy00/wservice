package com.emt.epay.dao.impl;

import com.emt.epay.dao.inter.IEpayOrderDetailDao;
import com.emt.epay.dao.mapper.EpayOrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author hdf
 *
 */
@Repository("epayOrderDetailDaoImpl")
public class EpayOrderDetailDaoImpl implements IEpayOrderDetailDao {

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
