package com.emt.bpay.server.inter;

import java.util.List;
import java.util.Map;

public interface IPayCheckSv
{
    /**
     *  获取提现申请未结束的列表
     * @param params
     * @return
     */
    List<Map<String, Object>> getUnWithdrawMoney();



    List<Map<String, Object>> buy(String buy_time );
}
