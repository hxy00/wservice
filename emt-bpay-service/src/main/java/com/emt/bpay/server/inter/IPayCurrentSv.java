package com.emt.bpay.server.inter;

import com.emt.bpay.pojo.PayCurrentVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IPayCurrentSv {
    /**
     * 时间段，按凭证类型进行分组统计
     * @return
     */
    List<Map<String, Object>> DocTypeGroupReport(String paramJson);

    /**
     * 往来账数据

     * @return
     */
    List<Map<String, Object>>  CurrentToMap(PayCurrentVO payCurrentVO);

    /**
     * 往来账分页查询

     * @return
     */
    PageInfo CurrentByPage(int pageIndex, int pageSize, PayCurrentVO payCurrentVO);

}
