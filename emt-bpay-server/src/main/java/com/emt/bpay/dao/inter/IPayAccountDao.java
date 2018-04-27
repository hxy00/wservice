package com.emt.bpay.dao.inter;

import com.emt.bpay.dao.pojo.PayAccountVO;
import com.emt.bpay.dao.pojo.SetAccountStatusVO;
import com.emt.bpay.dao.pojo.SetDepositVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/3/16.
 */
public interface IPayAccountDao
{
    /**
 * @category 获取帐户信息
 * @param company_code
 * @param payee_id
 * @return
 */
    List<Map<String, Object>> getAccountByPayeeId(String company_code, String payee_id);

    /**
     * 帐户分页查询
     * @param pageIndex
     * @param pageSize
     * @param bPayAccountVO
     * @return
     */
    PageInfo pageAccount(int pageIndex, int pageSize, PayAccountVO bPayAccountVO);

    /***
     * 设置保底金额
     * @param vo
     * @return
     */
    int setDeposit(SetDepositVO vo);


    /**
     * @category 修改帐户状态
     * @param vo
     * @return
     */
     int setAccountStatus(SetAccountStatusVO vo);


    /**
     * 根据网点编号获取网点总帐信息
     * @param company_code
     * @param payee_id
     * @return
     */
    List<Map<String, Object>> LedgersByPayeeId(String company_code, String payee_id);

}
