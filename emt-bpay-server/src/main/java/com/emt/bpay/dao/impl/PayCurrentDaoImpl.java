package com.emt.bpay.dao.impl;


import com.emt.bpay.dao.inter.IPayCurrentDao;
import com.emt.bpay.dao.mapper.PayCurrentMapper;
import com.emt.bpay.dao.pojo.PayCurrentVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("payCurrentDaoImpl")
public class PayCurrentDaoImpl  implements IPayCurrentDao
{

    private static Logger logger = LoggerFactory
            .getLogger(PayCurrentDaoImpl.class);

    @Autowired
    private PayCurrentMapper payCurrentMapper;

    /**
     * 时间段，按凭证类型进行分组统计
     * @param companyCode 套帐号
     * @param payeeId     网点编号
     * @param bDate       开始时间
     * @param eDate       开始时间
     * @return
     */
    @Override
    public  List<Map<String, Object>> DocTypeGroupReport(String companyCode, String payeeId, String bDate, String eDate){
        logger.info("时间段，按凭证类型进行分组统计， companyCode={},payeeId={},bDate={}, eDate={}",
                companyCode, payeeId,  bDate,  eDate);
        return  payCurrentMapper.DocTypeGroupReport(companyCode, payeeId, bDate, eDate);

    }

    @Override
    public PageInfo CurrentByPage(int pageIndex, int pageSize, PayCurrentVO payCurrentVO){
        PageHelper.startPage(pageIndex, pageSize);
        PageHelper.orderBy("id desc");
        List<Map<String, Object>> records = payCurrentMapper.CurrentByPage(payCurrentVO);
        PageInfo pageInfo = new PageInfo<Map<String, Object>>(records);
        return pageInfo;
    }

    /**
     * 往来账数据

     * @return
     */
    @Override
    public  List<Map<String, Object>>  CurrentByMap(PayCurrentVO payCurrentVO){
        return payCurrentMapper.CurrentByPage(payCurrentVO);
    }

}
