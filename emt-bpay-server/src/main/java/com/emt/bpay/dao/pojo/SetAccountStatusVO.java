package com.emt.bpay.dao.pojo;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by dsj on 2017/4/11.
 */
public class SetAccountStatusVO
{
    @NotNull(message = "请传入帐户ID")
    private Long id;

    @NotNull(message = "帐户状态不是有效的值")
    @Range(min=0, max=3, message = "帐户状态不是有效的值")
    private Integer new_account_status;

    private String create_oper;

    private String create_oper_id;

    private String create_ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNew_account_status() {
        return new_account_status;
    }

    public void setNew_account_status(Integer new_account_status) {
        this.new_account_status = new_account_status;
    }

    public String getCreate_oper() {
        return create_oper;
    }

    public void setCreate_oper(String create_oper) {
        this.create_oper = create_oper;
    }

    public String getCreate_oper_id() {
        return create_oper_id;
    }

    public void setCreate_oper_id(String create_oper_id) {
        this.create_oper_id = create_oper_id;
    }

    public String getCreate_ip() {
        return create_ip;
    }

    public void setCreate_ip(String create_ip) {
        this.create_ip = create_ip;
    }

}
