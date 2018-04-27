package com.emt.bpay.server.impl;

import com.emt.base.entity.ReturnObject;
import com.emt.bpay.config.Config;
import com.emt.bpay.dao.entity.PayAccountCard;
import com.emt.bpay.dao.inter.IPayAccountCardDao;
import com.emt.bpay.dao.inter.IPayAccountDao;
import com.emt.bpay.dao.pojo.BankInfoVO;
import com.emt.bpay.dao.pojo.PayAccountVO;
import com.emt.bpay.dao.pojo.SetAccountStatusVO;
import com.emt.bpay.dao.pojo.SetDepositVO;
import com.emt.bpay.rapi.BPayRemoteAPI;
import com.emt.bpay.server.inter.IPayAccountSv;
import com.emt.common.json.JSONHelper;
import com.emt.common.json.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dsj on 2017-03-16.
 */
@Service("payAccountSvImpl")
public class PayAccountSvImpl implements IPayAccountSv
{
    private static Logger logger = LoggerFactory
            .getLogger(PayAccountSvImpl.class);

    //@Resource(name = "payAccountDaoImpl", type = PayAccountDaoImpl.class)
    @Autowired
    private IPayAccountDao payAccountDao;

    //@Resource(name = "payAccountCardDaoImpl", type = PayAccountCardDaoImpl.class)
    @Autowired
    private IPayAccountCardDao payAccountCardDao;


    BPayRemoteAPI remoteAPI = new BPayRemoteAPI();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Override
    public List<Map<String, Object>> getAccountByPayeeId(String company_code, String payee_id)
    {
        return payAccountDao.getAccountByPayeeId(company_code, payee_id);
    }

    @Override
    public PageInfo pageAccount(int pageIndex, int pageSize, PayAccountVO bPayAccountVO)
    {
        return payAccountDao.pageAccount(pageIndex, pageSize, bPayAccountVO);
    }

    @Override
    public ReturnObject setDeposit(SetDepositVO vo)
    {
        logger.info("设置保底金额Sv");
        List<String> _list = new ArrayList<String>();
        Set<ConstraintViolation<SetDepositVO>> set = validator.validate(vo);
        for (ConstraintViolation<SetDepositVO> constraintViolation : set)
        {
            _list.add(constraintViolation.getMessage());
        }
        if (_list.size() > 0)
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, _list, null, _list.size());
        } else
        {
            int _count = payAccountDao.setDeposit(vo);
            _list.clear();
            _list.add(String.format("成功更新%s条记录", _count));
            logger.info("设置保底金额完成，消息：{} ", _list);
            return new ReturnObject(ReturnObject.SuccessEnum.success, _list, null, _count);
        }
    }

    /**
     * @param vo
     * @return
     * @category 修改帐户状态
     */
    @Override
    public int setAccountStatus(SetAccountStatusVO vo)
    {
        logger.info("修改帐户状态Sv");
        return payAccountDao.setAccountStatus(vo);
    }

    public static String CreateVerificationCode(BankInfoVO info)
    {
        String verificationCode = SecureUtil.md5(Config._AccountKey + info.getCompany_code() + info.getPayee_id() + info.getBank_name() + info.getBank_city_name() + info.getBank_account() + info.getBank_account_no());
        return verificationCode;
    }

    public static String CreateVerificationCode(BankInfoVO info, String amount)
    {
        String verificationCode = SecureUtil.md5(Config._AccountKey + info.getCompany_code() + info.getPayee_id() + info.getBank_name() + info.getBank_city_name() + info.getBank_account() + info.getBank_account_no() + amount);
        return verificationCode;
    }


    /**
     * 同步云商网点信息，暂时屏蔽
     *
     * @param company_code
     * @param list
     * @return
     */
    @Override
    public ReturnObject SyncPayAccountData(String company_code, List<Map<String, Object>> list)
    {
        return null;
       /* List<String> msg = new ArrayList<String>();
        if(CollectionUtils.isEmpty(list)){
            msg.add("无数据");
            return new ReturnObject(SuccessEnum.fail, "失败", msg, msg.size());
        }
        int countInsert = 0;
        int countUpdate = 0;
        Map<String, Object> rd = new HashMap<>();
        for(Map<String, Object> temp : list){
            String payee_id = temp.get("payee_id").toString();
            String payee_name = temp.get("payee_name").toString();
            String agency_id = temp.get("agency_id").toString();
            String agency_name = temp.get("agency_name").toString();
            List<Map<String, Object>> records = bPayAccountDao.SelectPayAccountByPayeeId(company_code, payee_id);
            rd.clear();
            if(CollectionUtils.isEmpty(records)){
                //说明不存在，则新增
                rd.put("company_code", company_code);
                rd.put("payee_type", 1);
                rd.put("payee_id", payee_id);
                rd.put("payee_name", payee_name);
                rd.put("agency_id", agency_id);
                rd.put("agency_name", agency_name);
                rd.put("create_oper", "sys");
                rd.put("create_oper_id", "sys");
                bPayAccountDao.Insert(rd);
                countInsert ++;
            }else{
                //存在，判断字段是否存在更新
                Map<String, Object> record = records.get(0);
                if(!payee_name.equals(record.get("payee_name"))
                        || !agency_id.equals(record.get("agency_id"))
                        || !agency_name.equals(record.get("agency_name"))){
                    rd.put("payee_name", payee_name);
                    rd.put("agency_id", agency_id);
                    rd.put("agency_name", agency_name);
                    rd.put(BaseSqlBuilder.where_prelude + "id", records.get(0).get("id"));
                    bPayAccountDao.Update(rd);
                    countUpdate ++;
                }
            }
        }
        msg.add("本次操作，新增："+countInsert+"条;更新："+countUpdate+"条");
        return new ReturnObject(SuccessEnum.success, "成功", msg, msg.size());*/
    }

    @Override
    public int InsertCard(PayAccountCard payAccountCard)
    {

        BankInfoVO bank = new BankInfoVO();
        bank.setCompany_code(payAccountCard.getCompany_code());
        bank.setPayee_id(payAccountCard.getPayee_id());
        bank.setBank_name(payAccountCard.getBank_name());
        bank.setBank_city_name(payAccountCard.getBank_city_name());
        bank.setBank_account(payAccountCard.getAccount_name());
        bank.setBank_account_no(payAccountCard.getAccount_id());
        String verificationCode = CreateVerificationCode(bank);
        payAccountCard.setVerification_code(verificationCode);

        return payAccountCardDao.Insert(payAccountCard);
    }


    @Override
    public int UpdateCard(PayAccountCard payAccountCard)
    {
        BankInfoVO bank = new BankInfoVO();
        bank.setCompany_code(payAccountCard.getCompany_code());
        bank.setPayee_id(payAccountCard.getPayee_id());
        bank.setBank_name(payAccountCard.getBank_name());
        bank.setBank_city_name(payAccountCard.getBank_city_name());
        bank.setBank_account(payAccountCard.getAccount_name());
        bank.setBank_account_no(payAccountCard.getAccount_id());
        String verificationCode = CreateVerificationCode(bank);
        payAccountCard.setVerification_code(verificationCode);
        return payAccountCardDao.Update(payAccountCard);
    }

    @Override
    public int DeleteCard(PayAccountCard payAccountCard)
    {
        return payAccountCardDao.Delete(payAccountCard);
    }

    /**
     * 根据网点编号获取该网点银行卡号
     *
     * @param payAccountCard
     * @return
     */
    @Override
    public List<Map<String, Object>> SelectCard(PayAccountCard payAccountCard)
    {
        return payAccountCardDao.Select(payAccountCard);
    }


    /**
     * 根据网点编号获取该网点银行卡号
     *
     * @param company_code
     * @param payee_id
     * @return
     */
    public List<Map<String, Object>> loadBankCardByPayeeId(String company_code, String payee_id)
    {

        return payAccountCardDao.loadBankCardByPayeeId(company_code, payee_id);
    }

    /**
     * 根据银行卡号获取银行卡银行
     *
     * @param payAccountCard
     * @return
     */
    public List<Map<String, Object>> selectBankCardById(PayAccountCard payAccountCard)
    {
        return payAccountCardDao.selectBankCardById(payAccountCard);
    }

    @Override
    public boolean verificationCodeCheck(List<Map<String, Object>> list)
    {
        //begin 获取银行卡信息
        PayAccountCard payAccountCard = new PayAccountCard();
        payAccountCard.setCompany_code("" + list.get(0).get("company_code"));
        payAccountCard.setPayee_id("" + list.get(0).get("payee_id"));
        payAccountCard.setAccount_id("" + list.get(0).get("bank_account_no"));
        List<Map<String, Object>> bankList = selectBankCardById(payAccountCard);
        if (bankList != null && bankList.size() > 0)
        {
            /* begin 验证码 */
            BankInfoVO bank = new BankInfoVO();
            bank.setCompany_code("" + list.get(0).get("company_code"));
            bank.setPayee_id("" + list.get(0).get("payee_id"));
            bank.setBank_name("" + bankList.get(0).get("bank_name"));
            bank.setBank_city_name("" + bankList.get(0).get("bank_city_name"));
            bank.setBank_account("" + bankList.get(0).get("account_name"));
            bank.setBank_account_no("" + bankList.get(0).get("account_id"));


			/*begin 验证银行卡信息校验*/
            String accountVerificationCode = PayAccountSvImpl.CreateVerificationCode(bank);
            if (!accountVerificationCode.equals(bankList.get(0).get("verification_code")))
            {
                logger.error("网点编号{}，银行信息验证码不正确，程序的码：{}, 数据库中的码：{}  !!", bankList.get(0).get("payee_id"),
                        accountVerificationCode, bankList.get(0).get("verification_code"));
                return false;
            }
		    /*end 验证银行卡信息校验*/

            String verificationCode = PayAccountSvImpl.CreateVerificationCode(bank, list.get(0).get("amount").toString());
            if (!verificationCode.equals(list.get(0).get("verification_code")))
            {
                logger.error("网点编号{}，提现单号：{},提现验证码不正确，银行：{} <> 提现：{} !!", list.get(0).get("payee_id"), list.get(0).get("check_no"), verificationCode, list.get(0).get("verification_code"));
                return false;
            } else
            {
                return true;
            }
        } else
        {
            logger.error("网点编号{}，提现单号：{},银行卡号：{},银行帐户没有查到!", list.get(0).get("payee_id"), list.get(0).get("check_no"), list.get(0).get("bank_account_no"));
            return false;
        }
    }

    /**
     * 设置别名或设置默认标识
     *
     * @param company_code
     * @param payee_id
     * @param account_id
     * @param alias
     * @param default_id
     * @return
     */
    @Override
    public ReturnObject SetAliasOrDefault(String company_code, String payee_id, String account_id, String alias, int default_id)
    {
        List<String> msg = new ArrayList<String>();
        if (StrUtil.isEmpty(company_code))
            msg.add("套帐号不能为空!");
        if (StrUtil.isEmpty(payee_id))
            msg.add("网点编号不能为空!");
        if (StrUtil.isEmpty(account_id))
            msg.add("银行卡号不能为空!");

        PayAccountCard payAccountCard = new PayAccountCard();
        payAccountCard.setAccount_id(account_id);
        payAccountCard.setCompany_code(company_code);
        payAccountCard.setPayee_id(payee_id);
        List<Map<String, Object>> list = this.selectBankCardById(payAccountCard);
        if (list == null || list.size() == 0)
            msg.add("没有该网点银行卡号!");

        if (msg.size() > 0)
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, msg, null, msg.size());
        }
        if (payAccountCardDao.SetAliasOrDefault(company_code, payee_id, account_id, alias, default_id) > 0)
        {
            msg.clear();
            ;
            msg.add("提交成功!");
        }
        return new ReturnObject(ReturnObject.SuccessEnum.success, msg, null, msg.size());
    }


    /**
     * 根据网点编号获取网点总帐信息
     *
     * @param company_code
     * @param payee_id
     * @return
     */
    @Override
    public ReturnObject LedgersByPayeeId(String company_code, String payee_id)
    {
        List<String> msg = new ArrayList<String>();
        if (StrUtil.isEmpty(company_code))
            msg.add("套帐号不能为空!");
        if (StrUtil.isEmpty(payee_id))
            msg.add("网点编号不能为空!");
        if (msg.size() > 0)
        {
            return new ReturnObject(ReturnObject.SuccessEnum.fail, msg, null, msg.size());
        } else
        {
            msg.clear();
            msg.add("获取总帐信息");
            List<Map<String, Object>> list = payAccountDao.LedgersByPayeeId(company_code, payee_id);
            return new ReturnObject(ReturnObject.SuccessEnum.success, msg, list, msg.size());
        }
    }

    /***
     * 获取银行卡列表
     * @param token
     * @param company_code
     * @param payee_id
     * @return
     */
    @Override
    public  ReturnObject BankCardByPayeeId(String token, String company_code, String payee_id){
        String _return = "";
        List<String> msg = new ArrayList<String>();
        try {
            _return = remoteAPI.BankCardByPayeeId(token, company_code, payee_id);
            logger.info("BankCardByPayeeId 调用返回字符串:{}", _return);
            JSONObject json = new JSONObject(_return);
            if ("0".equals(json.get("retcode").toString())) {
                JSONObject _data = (JSONObject)json.get("data");
                if ("0".equals(_data.get("success").toString())){
                    return new ReturnObject(ReturnObject.SuccessEnum.success,"成功",
                            JSONHelper.JSONArrayToListMap(_data.optJSONArray("json_data")),_data.optJSONArray("json_data").length());
                }else
                {
                    msg.add("调用远程API内部业务出错，返回success:" + _data.get("success").toString());
                    return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
                }
            }else
            {
                msg.add("调用远程API出错，返回retcode:" + json.get("retcode").toString());
                return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
            }

        } catch (Exception e) {
            logger.info("BankCardByPayeeId 出错了:{}", e.getMessage());
            msg.add("调用远程API出错，出错了:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
    }

    /**
     * 插入银行卡申请
     */
    @Override
    public  ReturnObject InsertPayBankCardApply(String bankCarkJson, MultipartFile file) {

        String _return = "";
        List<String> msg = new ArrayList<String>();
        try {
            _return = remoteAPI.InsertPayBankCardApply(bankCarkJson, file);
            logger.info("InsertPayBankCardApply 调用返回字符串:{}", _return);
            JSONObject json = new JSONObject(_return);
            if ("0".equals(json.get("retcode").toString())) {
                JSONObject _data = (JSONObject)json.get("data");
                if ("0".equals(_data.get("success").toString())){
                    return new ReturnObject(ReturnObject.SuccessEnum.success,"成功",
                            JSONHelper.JSONArrayToListMap(_data.optJSONArray("json_data")),_data.optJSONArray("json_data").length());
                }else
                {
                   // msg.add("调用远程API内部业务出错，返回success:" + _data.get("success").toString());
                    logger.info("message-->{}",_data.optJSONArray("message"));
                    return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败",
                            JSONHelper.JSONArrayToList(_data.optJSONArray("message")),_data.optJSONArray("message").length());
                }
            }else
            {
                msg.add("调用远程API出错，返回retcode:" + json.get("retcode").toString());
                return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());
            }

        } catch (Exception e) {
            logger.info("InsertPayBankCardApply 出错了:{}", e.getMessage());
            msg.add("调用远程API出错，出错了:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReturnObject(ReturnObject.SuccessEnum.fail,"失败", msg, msg.size());


    }


    /**
     * 查询银行卡变更申请记录
     * @param pageIndex
     * @param pageSize
     * @param paramJson
     * @return
     */
    @Override
  public   PageInfo  BankCardApplyByPage(int pageIndex, int pageSize, String paramJson){
       return remoteAPI.BankCardApplyByPage(pageIndex, pageSize, paramJson);
  }
}

