package com.emt.bpay.dao.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface PayCheckMapper
{

    /**
     *  获取提现申请未结束的列表
     * @return
     */
    List<Map<String, Object>> getUnWithdrawMoney();

    List<Map<String, Object>> buy(@Param("buy_time")String buy_time );
}
