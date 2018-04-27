package com.emt.bpay.server.impl;

import com.emt.bpay.dao.inter.IPayCurrentDao;
import com.emt.bpay.dao.pojo.PayCurrentVO;
import com.emt.bpay.server.inter.IPayCurrentSv;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("payCurrentSvImpl")
public class PayCurrentSvImpl  implements IPayCurrentSv
{

    private static Logger logger = LoggerFactory
            .getLogger(PayCurrentSvImpl.class);


   // @Resource(name = "payCurrentDaoImpl", type = PayCurrentDaoImpl.class)
   @Autowired
    private IPayCurrentDao payCurrentDao;
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
        logger.info("------------------------------------------------进入时间段，按凭证类型进行分组统计 sv  ---------------------------------");
        return payCurrentDao.DocTypeGroupReport(companyCode, payeeId, bDate, eDate);
    }



    /**
     * 往来账分页查询

     * @return
     */
    @Override
   public PageInfo CurrentByPage(int pageIndex, int pageSize, PayCurrentVO payCurrentVO){
           return payCurrentDao.CurrentByPage(pageIndex, pageSize, payCurrentVO);
   }

    /**
     * 往来账数据

     * @return
     */
    @Override
    public  List<Map<String, Object>>  CurrentByMap(PayCurrentVO payCurrentVO){
        return  payCurrentDao.CurrentByMap(payCurrentVO);
    }
}
