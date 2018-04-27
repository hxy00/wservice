package com.emt.bpay.dao.impl;

import com.emt.bpay.dao.entity.PayAccountCard;
import com.emt.bpay.dao.inter.IPayAccountCardDao;
import com.emt.bpay.dao.mapper.PayAccountCardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/4/14.
 */
@Repository("payAccountCardDaoImpl")
public class PayAccountCardDaoImpl implements IPayAccountCardDao
{
    @Autowired
    private PayAccountCardMapper payAccountCardMapper;

    @Override
    public int Update(PayAccountCard payAccountCard) {
        // TODO Auto-generated method stub
        return payAccountCardMapper.Update(payAccountCard);
    }

    @Override
    public int Insert(PayAccountCard payAccountCard) {
        // TODO Auto-generated method stub
        return payAccountCardMapper.Insert(payAccountCard);
    }


    @Override
    public int Delete(PayAccountCard payAccountCard) {
        // TODO Auto-generated method stub
        return payAccountCardMapper.Delete(payAccountCard);
    }

    /**
     * 根据网点编号获取该网点银行卡号
     * @param company_code
     * @param payee_id
     * @return
     */
    @Override
    public List<Map<String, Object>>  loadBankCardByPayeeId(String company_code, String payee_id){
        return payAccountCardMapper.loadBankCardByPayeeId(company_code, payee_id);
    }


    /**
     * 根据网点编号获取该网点银行卡号
     * @param payAccountCard
     * @return
     */
    @Override
    public List<Map<String, Object>>  Select(PayAccountCard payAccountCard){
        return payAccountCardMapper.Select(payAccountCard);
    }
    /**
     * 根据银行卡号获取银行卡银行
     * @param payAccountCard
     * @return
     */
    @Override
    public  List<Map<String, Object>>  selectBankCardById(PayAccountCard payAccountCard){
        return payAccountCardMapper.selectBankCardById(payAccountCard);
    }

    /**
     * 设置别名或设置默认标识
     * @param company_code
     * @param payee_id
     * @param account_id
     * @param alias
     * @param default_id
     * @return
     */
    @Override
    public int SetAliasOrDefault(String company_code, String payee_id,String account_id, String alias, int default_id){
        return payAccountCardMapper.SetAliasOrDefault(company_code, payee_id, account_id, alias, default_id);
    }
}
