package com.emt.bpay.dao.inter;

import java.util.List;
import java.util.Map;

public interface IPayCheckDao
{
    /**
     *  获取提现申请未结束的列表
     * @return
     */
    List<Map<String, Object>> getUnWithdrawMoney();


    List<Map<String, Object>> buy(String buy_time );
}
