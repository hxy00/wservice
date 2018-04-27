package com.emt.bpay.dao.pojo;

/**
 * Created by dsj on 2017/4/14.
 */
public class BankInfoVO
{
    private String company_code;
    private String payee_id;
    private String bank_code;
    private String bank_name;
    private String bank_city_name;
    private String bank_account;
    private String bank_account_no;

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

    public String getBank_city_name()
    {
        return bank_city_name;
    }

    public void setBank_city_name(String bank_city_name)
    {
        this.bank_city_name = bank_city_name;
    }

    public String getBank_account()
    {
        return bank_account;
    }

    public void setBank_account(String bank_account)
    {
        this.bank_account = bank_account;
    }

    public String getBank_account_no()
    {
        return bank_account_no;
    }

    public void setBank_account_no(String bank_account_no)
    {
        this.bank_account_no = bank_account_no;
    }

/*	public BankInfoVo(String company_code, String payee_id, String bank_name, String bank_city_name, String bank_account, String bank_account_no) {
        super();
		this.company_code = company_code;
		this.payee_id = payee_id;
		this.bank_name = bank_name;
		this.bank_city_name = bank_city_name;
		this.bank_account = bank_account;
		this.bank_account_no = bank_account_no;
	}
*/
}
