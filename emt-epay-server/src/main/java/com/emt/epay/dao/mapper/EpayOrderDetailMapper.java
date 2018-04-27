package com.emt.epay.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by hdf on 2018/3/27
 */
@Mapper
public interface EpayOrderDetailMapper {

    /**
     * 获取hour小时内的订单数据
     *
     * @param hour
     * @return
     */
    List<Map<String, Object>> payList(@Param("hour") int hour);

    /**
     * 更改是否被扫描
     *
     * @param orderId
     * @param payCompany
     * @return
     */
    int modifyIsScan(@Param("orderId") String orderId, @Param("payCompany") String payCompany);

}
