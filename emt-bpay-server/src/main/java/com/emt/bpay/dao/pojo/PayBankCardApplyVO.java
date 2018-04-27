package com.emt.bpay.dao.pojo;

import com.emt.bpay.dao.entity.PayBankCardApply;

public class PayBankCardApplyVO extends PayBankCardApply
{
    private String ExtSQL;

    public String getExtSQL() {
        return ExtSQL;
    }

    public void setExtSQL(String extSQL) {
        ExtSQL = extSQL;
    }
}