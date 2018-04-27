package com.emt.bpay.server.impl;

import com.emt.bpay.dao.inter.IPayCheckDao;
import com.emt.bpay.server.inter.IPayCheckSv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("payCheckSvImpl")
public class PayCheckSvImpl implements IPayCheckSv
{
    private static Logger logger = LoggerFactory
            .getLogger(PayCheckSvImpl.class);


    //@Resource(name = "payCheckDaoImpl", type = PayCheckDaoImpl.class)
    @Autowired
    private IPayCheckDao payCheckDao;

    /**
     *  获取提现申请未结束的列表
     * @return
     */
    @Override
    public List<Map<String, Object>> getUnWithdrawMoney(){
        logger.info("------------------------------------------------进入PayCheck  Server  getUnWithdrawMoney ---------------------------------");
        return  payCheckDao.getUnWithdrawMoney();
    }



    @Override
    public List<Map<String, Object>> buy(String buy_time ){
        return payCheckDao.buy(buy_time);
    }
}
