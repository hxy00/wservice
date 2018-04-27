package com.emt.bpay.server.inter;

import com.emt.base.entity.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface IPayAccountSv {

    /**
     * 网点银行帐户信息
     * @param paramJson
     * @return
     */
    ReturnObject PayAccountByPayeeId(String paramJson);


    /***
     * 根据SN获取银行卡信息
     * @param paramJson
     * @return
     */
     ReturnObject BankCardBySn(String paramJson);

    /***
     * 获取银行卡列表
     * @return
     */
    ReturnObject BankCardByPayeeId(String paramJson);

    /**
     * 插入银行卡申请
     */
    ReturnObject InsertPayBankCardApply(String bankCarkJson, MultipartFile file);


    /**
     * 取消银行卡申请
     * @param accountJson
     * @return
     */
    ReturnObject  CancelApply(String accountJson);
    /**
     * 根据SN获取银行卡未审核数
     * @param sn
     * @return
     */
    int  UnVerifyApplyNum(String sn);

    /**
     * 查询银行卡变更申请记录
     * @param pageIndex
     * @param pageSize
     * @param paramJson
     * @return
     */
    PageInfo  BankCardApplyByPage(int pageIndex, int pageSize, String paramJson);
}
