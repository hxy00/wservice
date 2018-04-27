package com.emt.bpay.dao.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by dsj on 2017/3/27.
 */

public class SetDepositVO
{
    @NotEmpty(message = "套帐号不能为空!")
    private String company_code;

    @NotEmpty(message = "网点编号不能为空!")
    private String payee_id;

    @NotNull(message = "请对保底金额设置!")
    @Min(value = 0, message = "不能小于0")
    private Integer new_deposit;

    private String create_oper;

    private String create_oper_id;

    private String create_ip;

    public String getCompany_code()
    {
        return company_code;
    }

    public void setCompany_code(String company_code)
    {
        this.company_code = company_code;
    }

    public String getPayee_id()
    {
        return payee_id;
    }

    public void setPayee_id(String payee_id)
    {
        this.payee_id = payee_id;
    }

    public Integer getNew_deposit()
    {
        return new_deposit;
    }

    public void setNew_deposit(Integer new_deposit)
    {
        this.new_deposit = new_deposit;
    }

    public String getCreate_oper()
    {
        return create_oper;
    }

    public void setCreate_oper(String create_oper)
    {
        this.create_oper = create_oper;
    }

    public String getCreate_oper_id()
    {
        return create_oper_id;
    }

    public void setCreate_oper_id(String create_oper_id)
    {
        this.create_oper_id = create_oper_id;
    }

    public String getCreate_ip()
    {
        return create_ip;
    }

    public void setCreate_ip(String create_ip)
    {
        this.create_ip = create_ip;
    }
}
