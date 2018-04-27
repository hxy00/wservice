package com.emt.epay.server.inter;

import com.emt.epay.dao.entity.EpayOrderScan;

public interface IEpayOrderScanSv {
    /**
     * 保存已扫描订单数据
     *
     * @param epayOderScan
     * @return
     */
    int saveScaned(EpayOrderScan epayOderScan);
}
