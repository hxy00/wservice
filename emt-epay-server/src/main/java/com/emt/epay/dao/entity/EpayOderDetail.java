package com.emt.epay.dao.entity;

import com.emt.base.dao.BaseDaoEntity;
import com.emt.common.utils.DateUtils;

import java.util.Date;

/**
 * OrderDetail表实体
 */
public class EpayOderDetail extends BaseDaoEntity {

    private String orderid; // 订单id
    private String payCompany; // 支付服务商 Varchar(32)
    private String interfaceName; // 接口名称 Varchar(32)
    private String interfaceVersion; // 接口版本号 Varchar(32)
    private Long Qid = System.currentTimeMillis(); // 请求序号 bigint
    private Integer amount; // 金额 integer
    private String orderDate = DateUtils.DateTimeToYYYYMMDDhhmmss(); // 交易日期
    private String merURL; // 通知商户URL Varchar(128)
    private String clientType; // 客户端类型 Varchar(4)
    private String merVAR; // 返回商户变量 Varchar(64)
    private String notifyType; // 通知类型 Varchar(32)
    private String notifyData;
    private String TranSerialNo; // 银行端指令流水号 bigint
    private String notifyDate; // 通知数据 Varchar(4098)
    private Integer tranStat = 0; // 支付结果 integer
    private String comment; // 说明 Varchar(1024)
    private Date Create_date = new Date(); // 写入日期 Datetime
    private Date Update_date = new Date(); // 最后修改日期 Datetime
    private Integer Emt_sys_id; // 系统平台id integer
    private String TranData; // 投递的交易数据xml格式的明文
    private String ResultUrl;
    private String phone;
    private String shopCode;
    private Integer isPost, Times, isSend, isScan;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPayCompany() {
        return payCompany;
    }

    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public Long getQid() {
        return Qid;
    }

    public void setQid(Long qid) {
        Qid = qid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getMerURL() {
        return merURL;
    }

    public void setMerURL(String merURL) {
        this.merURL = merURL;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getMerVAR() {
        return merVAR;
    }

    public void setMerVAR(String merVAR) {
        this.merVAR = merVAR;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(String notifyData) {
        this.notifyData = notifyData;
    }

    public String getTranSerialNo() {
        return TranSerialNo;
    }

    public void setTranSerialNo(String tranSerialNo) {
        TranSerialNo = tranSerialNo;
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    public Integer getTranStat() {
        return tranStat;
    }

    public void setTranStat(Integer tranStat) {
        this.tranStat = tranStat;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreate_date() {
        return Create_date;
    }

    public void setCreate_date(Date create_date) {
        Create_date = create_date;
    }

    public Date getUpdate_date() {
        return Update_date;
    }

    public void setUpdate_date(Date update_date) {
        Update_date = update_date;
    }

    public Integer getEmt_sys_id() {
        return Emt_sys_id;
    }

    public void setEmt_sys_id(Integer emt_sys_id) {
        Emt_sys_id = emt_sys_id;
    }

    public String getTranData() {
        return TranData;
    }

    public void setTranData(String tranData) {
        TranData = tranData;
    }

    public String getResultUrl() {
        return ResultUrl;
    }

    public void setResultUrl(String resultUrl) {
        ResultUrl = resultUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Integer getIsPost() {
        return isPost;
    }

    public void setIsPost(Integer isPost) {
        this.isPost = isPost;
    }

    public Integer getTimes() {
        return Times;
    }

    public void setTimes(Integer times) {
        Times = times;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Integer getIsScan() {
        return isScan;
    }

    public void setIsScan(Integer isScan) {
        this.isScan = isScan;
    }
}
