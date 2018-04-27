package com.emt.bpay.server.inter;

import com.emt.bpay.dao.pojo.PayCurrentVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IPayCurrentSv
{
    /**
     * 时间段，按凭证类型进行分组统计
     * @param companyCode 套帐号
     * @param payeeId     网点编号
     * @param bDate       开始时间
     * @param eDate       开始时间
     * @return
     */
    List<Map<String, Object>> DocTypeGroupReport(String companyCode, String payeeId, String bDate, String eDate);


    /**
     * 往来账分页查询

     * @return
     */
    PageInfo CurrentByPage(int pageIndex, int pageSize, PayCurrentVO payCurrentVO);


    /**
     * 往来账数据

     * @return
     */
    List<Map<String, Object>>  CurrentByMap(PayCurrentVO payCurrentVO);
}
