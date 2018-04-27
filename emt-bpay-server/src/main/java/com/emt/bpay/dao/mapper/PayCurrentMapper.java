package com.emt.bpay.dao.mapper;

import com.emt.bpay.dao.pojo.PayCurrentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayCurrentMapper
{
    /**
     * 时间段，按凭证类型进行分组统计
     * @param companyCode 套帐号
     * @param payeeId     网点编号
     * @param bDate       开始时间
     * @param eDate       开始时间
     * @return
     */
    List<Map<String, Object>>  DocTypeGroupReport(@Param("companyCode")String companyCode, @Param("payeeId")String payeeId, @Param("bDate")String bDate, @Param("eDate")String eDate);

    /**
     * 往来账分页查询

     * @return
     */
    List<Map<String, Object>>  CurrentByPage(PayCurrentVO payCurrentVO);
}
