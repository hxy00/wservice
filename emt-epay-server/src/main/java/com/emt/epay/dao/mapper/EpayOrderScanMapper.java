package com.emt.epay.dao.mapper;

import com.emt.epay.dao.entity.EpayOrderScan;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hdf on 2018/3/27
 */
@Mapper
public interface EpayOrderScanMapper {
    /**
     * 保存已扫描订单数据
     *
     * @param epayOrderScan
     * @return
     */
    int saveScaned(EpayOrderScan epayOrderScan);

}
