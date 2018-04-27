package com.emt.base.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.emt.base.pojo.BaseEnum.ReportTimeType;


/**
 * @category 报表统计参数基础类
 * @author dsj
 *
 */
public class BaseReportParam {
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date b_date;
    
    /**
     * 结束时间 
     */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date e_date;
    
    /**
     * 统计时间类型
     */
    private ReportTimeType timeType;

	public Date getB_date() {
		return b_date;
	}

	public void setB_date(Date b_date) {
		this.b_date = b_date;
	}

	public Date getE_date() {
		return e_date;
	}

	public void setE_date(Date e_date) {
		this.e_date = e_date;
	}

	public ReportTimeType getTimeType() {
		return timeType;
	}

	public void setTimeType(ReportTimeType timeType) {
		this.timeType = timeType;
	}
    
    
}
