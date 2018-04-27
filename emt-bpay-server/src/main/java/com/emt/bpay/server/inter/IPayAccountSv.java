package com.emt.bpay.server.inter;

import com.emt.base.entity.ReturnObject;
import com.emt.bpay.dao.entity.PayAccountCard;
import com.emt.bpay.dao.pojo.PayAccountVO;
import com.emt.bpay.dao.pojo.SetAccountStatusVO;
import com.emt.bpay.dao.pojo.SetDepositVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017-03-16.
 */
public interface IPayAccountSv {
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
    ReturnObject setDeposit(SetDepositVO vo);


    /**
     * @param vo
     * @return
     * @category 修改帐户状态
     */
    int setAccountStatus(SetAccountStatusVO vo);



    /**
     * @category 同步账户信息
     * @param company_code
     * @param list
     * @return
     */
    ReturnObject SyncPayAccountData(String company_code,List<Map<String, Object>> list);

    /**
     * 增加银行卡
     * @param payAccountCard
     * @return
     */
    int InsertCard(PayAccountCard payAccountCard);


    /**
     * 更新银行卡
     * @param payAccountCard
     * @return
     */
    int UpdateCard(PayAccountCard payAccountCard);

    int DeleteCard(PayAccountCard payAccountCard);

    /**
     * 根据网点编号获取该网点银行卡号
     * @param payAccountCard
     * @return
     */
    List<Map<String, Object>>  SelectCard(PayAccountCard payAccountCard);

    /**
     * 根据网点编号获取该网点银行卡号
     * @param company_code
     * @param payee_id
     * @return
     */
    List<Map<String, Object>>  loadBankCardByPayeeId(String company_code, String payee_id);

    /**
     * 根据银行卡号获取银行卡银行
     * @param payAccountCard
     * @return
     */
    List<Map<String, Object>>  selectBankCardById(PayAccountCard payAccountCard);

    boolean verificationCodeCheck(List<Map<String, Object>> list);

    /**
     * 设置别名或设置默认标识
     * @param company_code
     * @param payee_id
     * @param account_id
     * @param alias
     * @param default_id
     * @return
     */
    ReturnObject SetAliasOrDefault(String company_code, String payee_id,String account_id, String alias, int default_id);

    /**
     * 根据网点编号获取网点总帐信息
     * @param company_code
     * @param payee_id
     * @return
     */
    ReturnObject LedgersByPayeeId(String company_code, String payee_id);

    /***
     * 获取银行卡列表
     * @param token
     * @param company_code
     * @param payee_id
     * @return
     */
    ReturnObject BankCardByPayeeId(String token, String company_code, String payee_id);

    /**
     * 插入银行卡申请
     * @return
     */
    ReturnObject InsertPayBankCardApply(String accountJson, MultipartFile file);


    /**
     * 查询银行卡变更申请记录
     * @param pageIndex
     * @param pageSize
     * @param paramJson
     * @return
     */
    PageInfo  BankCardApplyByPage(int pageIndex, int pageSize, String paramJson);
}
