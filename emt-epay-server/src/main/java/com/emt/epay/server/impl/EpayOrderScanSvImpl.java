package com.emt.epay.server.impl;

import com.emt.epay.dao.entity.EpayOrderScan;
import com.emt.epay.dao.mapper.EpayOrderScanMapper;
import com.emt.epay.server.inter.IEpayOrderScanSv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018-03-27.
 */
@Repository("epayOrderScanSvImpl")
public class EpayOrderScanSvImpl implements IEpayOrderScanSv {

    @Autowired
    private EpayOrderScanMapper epayOrderScanMapper;

    /**
     * 保存已扫描订单数据
     *
     * @param epayOrderScan
     * @return
     */
    @Override
    public int saveScaned(EpayOrderScan epayOrderScan) {
        return epayOrderScanMapper.saveScaned(epayOrderScan);
    }
}
