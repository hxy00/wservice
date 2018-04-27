package com.emt.bpay.dao.pojo;

import java.util.Date;

/**
 * Created by dsj on 2017/3/24.
 */
public class PayAccountVO
{
    private Long id;

    private String company_code;//套账号

    private String payee_id;//收款方编号

    private Integer payee_type;//收款方类型

    private String payee_name;//收款方名称

    private String agency_name; //代理商

    private String bank_code;//开户行代码

    private String bank_name;//开户行名称

    private String account_name;//开户名

    private String account_id;//账号

    private String currency;//收款币种

    private String swift_code;//收款行标识

    private String country;//收款银行所在国家

    private String permanent_state;

    private String address;

    private String tel;

    private Integer audit_status;//审核标志

    private String auditor_id;//审核人编码

    private String auditor;//审核人姓名

    private Date auditor_time;//审核时间

    private Integer account_status;//状态

    private Date update_time;

    private String update_oper;

    private String update_oper_id;

    private Date create_time;

    private String create_oper;

    private String create_oper_id;

    private String remark;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

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

    public Integer getPayee_type()
    {
        return payee_type;
    }

    public void setPayee_type(Integer payee_type)
    {
        this.payee_type = payee_type;
    }

    public String getPayee_name()
    {
        return payee_name;
    }

    public void setPayee_name(String payee_name)
    {
        this.payee_name = payee_name;
    }

    public String getAgency_name()
    {
        return agency_name;
    }

    public void setAgency_name(String agency_name)
    {
        this.agency_name = agency_name;
    }

    public String getBank_code()
    {
        return bank_code;
    }

    public void setBank_code(String bank_code)
    {
        this.bank_code = bank_code;
    }

    public String getBank_name()
    {
        return bank_name;
    }

    public void setBank_name(String bank_name)
    {
        this.bank_name = bank_name;
    }

    public String getAccount_name()
    {
        return account_name;
    }

    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
    }

    public String getAccount_id()
    {
        return account_id;
    }

    public void setAccount_id(String account_id)
    {
        this.account_id = account_id;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getSwift_code()
    {
        return swift_code;
    }

    public void setSwift_code(String swift_code)
    {
        this.swift_code = swift_code;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getPermanent_state()
    {
        return permanent_state;
    }

    public void setPermanent_state(String permanent_state)
    {
        this.permanent_state = permanent_state;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public Integer getAudit_status()
    {
        return audit_status;
    }

    public void setAudit_status(Integer audit_status)
    {
        this.audit_status = audit_status;
    }

    public String getAuditor_id()
    {
        return auditor_id;
    }

    public void setAuditor_id(String auditor_id)
    {
        this.auditor_id = auditor_id;
    }

    public String getAuditor()
    {
        return auditor;
    }

    public void setAuditor(String auditor)
    {
        this.auditor = auditor;
    }

    public Date getAuditor_time()
    {
        return auditor_time;
    }

    public void setAuditor_time(Date auditor_time)
    {
        this.auditor_time = auditor_time;
    }

    public Integer getAccount_status()
    {
        return account_status;
    }

    public void setAccount_status(Integer account_status)
    {
        this.account_status = account_status;
    }

    public Date getUpdate_time()
    {
        return update_time;
    }

    public void setUpdate_time(Date update_time)
    {
        this.update_time = update_time;
    }

    public String getUpdate_oper()
    {
        return update_oper;
    }

    public void setUpdate_oper(String update_oper)
    {
        this.update_oper = update_oper;
    }

    public String getUpdate_oper_id()
    {
        return update_oper_id;
    }

    public void setUpdate_oper_id(String update_oper_id)
    {
        this.update_oper_id = update_oper_id;
    }

    public Date getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Date create_time)
    {
        this.create_time = create_time;
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
