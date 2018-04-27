package com.emt.bpay.dao.mapper;

import com.emt.bpay.dao.entity.PayAccountCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/4/14.
 */
@Mapper
public interface PayAccountCardMapper
{
    /**
     * 插入银行卡
     * @param payAccountCard
     * @return
     */
    int Insert(PayAccountCard payAccountCard);

    /**
     * 更新银行卡
     * @param payAccountCard
     * @return
     */
    int Update(PayAccountCard payAccountCard);


    /**
     * 删除银行卡
     * @param payAccountCard
     * @return
     */
    int Delete(PayAccountCard payAccountCard);

    /**
     * 根据网点编号获取该网点银行卡号
     * @param company_code
     * @param payee_id
     * @return
     */
    List<Map<String, Object>>  loadBankCardByPayeeId(@Param("company_code")String company_code, @Param("payee_id")String payee_id);



    /**
     * 根据网点编号获取该网点银行卡号
     * @param payAccountCard
     * @return
     */
    List<Map<String, Object>> Select(PayAccountCard payAccountCard);


    /**
     * 根据银行卡号获取银行卡银行
     * @param payAccountCard
     * @return
     */
    List<Map<String, Object>>  selectBankCardById(PayAccountCard payAccountCard);

    /**
     * 设置别名或设置默认标识
     * @param company_code
     * @param payee_id
     * @param account_id
     * @param alias
     * @param default_id
     * @return
     */
    int SetAliasOrDefault(@Param("company_code")String company_code, @Param("payee_id")String payee_id,
                          @Param("account_id")String account_id, @Param("alias")String alias, @Param("default_id")int default_id);
}
