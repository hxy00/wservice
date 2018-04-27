package com.emt.bpay.server.impl;

import com.emt.bpay.pojo.PayCurrentVO;
import com.emt.bpay.rapi.BPayRemoteAPI;
import com.emt.bpay.server.inter.IPayCurrentSv;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("payCurrentSvImpl")
public class PayCurrentSvImpl implements IPayCurrentSv {
    private static Logger logger = LoggerFactory
            .getLogger(PayCurrentSvImpl.class);

    BPayRemoteAPI remoteAPI = new BPayRemoteAPI();


    /**
     * 时间段，按凭证类型进行分组统计
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> DocTypeGroupReport(String paramJson) {
        return remoteAPI.DocTypeGroupReport(paramJson);
    }

    /**
     * 往来账数据

     * @return
     */
    @Override
    public  List<Map<String, Object>>  CurrentToMap(PayCurrentVO payCurrentVO){
        List<Map<String, Object>> list  = remoteAPI.CurrentToMap(payCurrentVO);
        if (list != null)
            return list;
        else
            return new ArrayList<Map<String, Object>>();
    }

    /**
     * 往来账分页查询

     * @return
     */
    @Override
    public PageInfo CurrentByPage(int pageIndex, int pageSize, PayCurrentVO payCurrentVO) {
        PageInfo page  = remoteAPI.PageCurrent(pageIndex, pageSize, payCurrentVO);
        if (page.getList() == null){
            page.setList(new ArrayList());
        }
        return page;
    }
}
