package com.emt.bpay.dao.inter;

import com.emt.bpay.dao.entity.PayAccountCard;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/4/14.
 */
public interface IPayAccountCardDao
{
    int Update(PayAccountCard payAccountCard);

    int Insert(PayAccountCard payAccountCard);

    int Delete(PayAccountCard payAccountCard);

    /**
     * 根据网点编号获取该网点银行卡号
     *
     * @param company_code
     * @param payee_id
     * @return
     */
    List<Map<String, Object>> loadBankCardByPayeeId(String company_code, String payee_id);

    /**
     * 根据网点编号获取该网点银行卡号
     *
     * @param payAccountCard
     * @return
     */
    List<Map<String, Object>> Select(PayAccountCard payAccountCard);

    /**
     * 根据银行卡号获取银行卡银行
     *
     * @param payAccountCard
     * @return
     */
    List<Map<String, Object>> selectBankCardById(PayAccountCard payAccountCard);

    /**
     * 设置别名或设置默认标识
     * @param company_code
     * @param payee_id
     * @param account_id
     * @param alias
     * @param default_id
     * @return
     */
    int SetAliasOrDefault(String company_code, String payee_id,String account_id, String alias, int default_id);
}
