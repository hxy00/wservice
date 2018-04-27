package com.emt.epay.dao.inter;

import com.emt.epay.dao.entity.EpayOrderScan;

public interface IEpayOrderScanDao {
    /**
     * 保存已扫描订单数据
     *
     * @param epayOderScan
     * @return
     */
    int saveScaned(EpayOrderScan epayOderScan);
}
