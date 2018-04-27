package com.emt.epay.dao.impl;

import com.emt.epay.dao.entity.EpayOrderScan;
import com.emt.epay.dao.inter.IEpayOrderScanDao;
import com.emt.epay.dao.mapper.EpayOrderScanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018-03-27.
 */
@Repository("epayOrderScanDaoImpl")
public class EpayOrderScanDaoImpl implements IEpayOrderScanDao {

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
