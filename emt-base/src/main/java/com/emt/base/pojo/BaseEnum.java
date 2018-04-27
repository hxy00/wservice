package com.emt.base.pojo;

public class BaseEnum {
	/**
	 * @category 统计时间类型
	 * @author dsj
	 * 
	 */
	public enum ReportTimeType {
		/**
		 * @category 按年统计
		 */
		Y("Y"),
		/**
		 * @category 按月统计
		 */
		M("M"),
		/**
		 * @category 按天统计
		 */
		D("D"),

		/**
		 * 按小时统计
		 */
		H("H");

		private String reportTimeType;

		private ReportTimeType(String reportTimeType) {
			this.reportTimeType = reportTimeType;
		}

		public String getReportTimeType() {
			return reportTimeType;
		}
	}

	/**
	 * @category 区域类型
	 * @author dsj
	 * 
	 */
	public enum ReportRegionType {
		/**
		 * @category 国家
		 */
		G("G"), 
		
		/**
		 * 省
		 */
		S("S"),
		
		/**
		 * 市
		 */
		C("C"),
		
		/**
		 * @category 县
		 */
		X("X");

		private String reportRegionType;

		private ReportRegionType(String reportRegionType) {
			this.reportRegionType = reportRegionType;
		}

		public String getReportRegionType() {
			return reportRegionType;
		}

	}

}
