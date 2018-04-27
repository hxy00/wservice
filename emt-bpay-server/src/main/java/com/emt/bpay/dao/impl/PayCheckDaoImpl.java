package com.emt.bpay.dao.impl;

import com.emt.bpay.dao.inter.IPayCheckDao;
import com.emt.bpay.dao.mapper.PayCheckMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("payCheckDaoImpl")
public class PayCheckDaoImpl implements IPayCheckDao
{
    private static Logger logger = LoggerFactory
            .getLogger(PayCheckDaoImpl.class);

    @Autowired
    private PayCheckMapper checkMapper;


    /**
     * 获取提现申请未结束的列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getUnWithdrawMoney()
    {
        logger.info("------------------------------------------------进入PayCheck  Dao  getUnWithdrawMoney ---------------------------------");
        return checkMapper.getUnWithdrawMoney();
    }


    @Override
    public List<Map<String, Object>> buy(String buy_time ){
        return  checkMapper.buy(buy_time);
    }
}
