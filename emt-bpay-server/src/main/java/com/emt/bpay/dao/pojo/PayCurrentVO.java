package com.emt.bpay.dao.pojo;

public class PayCurrentVO
{
    private String company_code;
    private String  payee_id;
    private String  doc_type;
    private String  doc_code;
    private String  begin_reg_time;
    private String  end_reg_time;
    private String  ext_sql;

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

    public String getDoc_type()
    {
        return doc_type;
    }

    public void setDoc_type(String doc_type)
    {
        this.doc_type = doc_type;
    }

    public String getDoc_code()
    {
        return doc_code;
    }

    public void setDoc_code(String doc_code)
    {
        this.doc_code = doc_code;
    }

    public String getBegin_reg_time()
    {
        return begin_reg_time;
    }

    public void setBegin_reg_time(String begin_reg_time)
    {
        this.begin_reg_time = begin_reg_time;
    }

    public String getEnd_reg_time()
    {
        return end_reg_time;
    }

    public void setEnd_reg_time(String end_reg_time)
    {
        this.end_reg_time = end_reg_time;
    }

    public String getExt_sql()
    {
        return ext_sql;
    }

    public void setExt_sql(String ext_sql)
    {
        this.ext_sql = ext_sql;
    }
}
