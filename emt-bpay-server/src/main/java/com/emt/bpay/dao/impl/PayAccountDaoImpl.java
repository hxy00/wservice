package com.emt.bpay.dao.impl;

import com.emt.bpay.dao.inter.IPayAccountDao;
import com.emt.bpay.dao.mapper.PayAccountMapper;
import com.emt.bpay.dao.pojo.PayAccountVO;
import com.emt.bpay.dao.pojo.SetAccountStatusVO;
import com.emt.bpay.dao.pojo.SetDepositVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/3/16.
 */
@Repository("payAccountDaoImpl")
public class PayAccountDaoImpl implements IPayAccountDao
{
    private static Logger logger = LoggerFactory
            .getLogger(PayAccountDaoImpl.class);

    @Autowired
    private PayAccountMapper payAccountMapper;

    /**
     * @param company_code
     * @param payee_id
     * @return
     * @category 获取帐户信息
     */
    @Override
    public List<Map<String, Object>> getAccountByPayeeId(String company_code, String payee_id)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("company_code", company_code);
        params.put("payee_id", payee_id);
        return payAccountMapper.getAccountByPayeeId(params);
    }


    /**
     * 帐户分页查询
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    @Override
    public PageInfo pageAccount(int pageIndex, int pageSize, PayAccountVO vo)
    {
        PageHelper.startPage(pageIndex, pageSize);
        PageHelper.orderBy("id desc");
        List<Map<String, Object>> records = payAccountMapper.pageAccount(vo);
        PageInfo pageInfo = new PageInfo<Map<String, Object>>(records);
        return pageInfo;
    }

    /***
     * 设置保底金额
     * @param vo
     * @return
     */
    @Override
    public int setDeposit(SetDepositVO vo)
    {
        logger.info("设置保底金额Dao");
        int _return = 0;
        List<Map<String, Object>> list =   payAccountMapper.setDeposit(vo);
        if (list != null && list.size() > 0) {
            _return = Integer.parseInt(list.get(0).get("success").toString());

        } else {
            _return = 0;
        }
        return _return;


    }

    /**
     * @param vo
     * @return
     * @category 修改帐户状态
     */
    @Override
    public int setAccountStatus(SetAccountStatusVO vo)
    {
        logger.info("修改帐户状态");
        int _return = 0;
        List<Map<String, Object>> list =   payAccountMapper.setAccountStatus(vo);
        if (list != null && list.size() > 0) {
            _return = Integer.parseInt(list.get(0).get("success").toString());

        } else {
            _return = 0;
        }
        return _return;
    }

    /**
     * 根据网点编号获取网点总帐信息
     * @param company_code
     * @param payee_id
     * @return
     */
    @Override
    public  List<Map<String, Object>> LedgersByPayeeId(String company_code,String payee_id){
        logger.info("获取网点总帐信息Dao");
        return payAccountMapper.LedgersByPayeeId(company_code, payee_id);
    }

}
