package com.emt.bpay.pojo;

public class PayCurrentVO
{
    private String user_id;
    private String token;
    private String sys_id;
    private String company_code;
    private String  payee_id;
    private String  doc_type;
    private String  doc_code;
    private String  b_reg_time;
    private String  e_reg_time;
    private String  ext_sql;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSys_id() {
        return sys_id;
    }

    public void setSys_id(String sys_id) {
        this.sys_id = sys_id;
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

    public String getB_reg_time() {
        return b_reg_time;
    }

    public void setB_reg_time(String b_reg_time) {
        this.b_reg_time = b_reg_time;
    }

    public String getE_reg_time() {
        return e_reg_time;
    }

    public void setE_reg_time(String e_reg_time) {
        this.e_reg_time = e_reg_time;
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
