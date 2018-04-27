package com.emt.bpay.dao.mapper;

import com.emt.bpay.dao.pojo.PayAccountVO;
import com.emt.bpay.dao.pojo.SetAccountStatusVO;
import com.emt.bpay.dao.pojo.SetDepositVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017-03-16.
 */
@Mapper
public interface PayAccountMapper {
    /**
     * 查询网点信息
     * @param params
     * @return
     */
    List<Map<String, Object>> getAccountByPayeeId(Map<String, Object> params);


    /**
     * 帐户分页查询
     * @param bPayAccountVO
     * @return
     */
    List<Map<String, Object>>  pageAccount(PayAccountVO bPayAccountVO);

    /***
     * 设置保底金额
     * @param vo
     * @return
     */
    List<Map<String, Object>>  setDeposit(SetDepositVO vo);


    /**
     * @category 修改帐户状态
     * @param vo
     * @return
     */

    List<Map<String, Object>> setAccountStatus(SetAccountStatusVO vo);


    /**
     * 根据网点编号获取网点总帐信息
     * @param company_code
     * @param payee_id
     * @return
     */
    List<Map<String, Object>> LedgersByPayeeId(@Param("company_code")String company_code, @Param("payee_id")String payee_id);
}
