package com.emt.epay.qapi.pojo;

import com.emt.common.utils.StringUtils;

public class AbcPayConfig {
	
	/**
	 * 获取配置文件中所配置的下标数据
	 * @param sysId
	 * @return
	 */
	public static int getPayIndex(String sysId){
		int payIndex = 1;
		switch (sysId) {
		case "900001":
			payIndex = 1;
			break;
		case "400001":
			payIndex = 2;
			break;
		default:
			break;
		}
		return payIndex;
	}
	
	/**
	 * 获取shopCode
	 * @param index
	 * @return
	 */
	public static String getShopCode(int index){
		String shopCode = "103882399990090";
		switch (index) {
		case 1:	
			shopCode = "103882399990090";
			break;
		case 2:
			shopCode = "103882399990122";
			break;
		default:
			break;
		}
		return shopCode;
	}

	//01:未支付 02:无回应 03:已请款 04:成功 05:已退款 07:授权确认成功 00:授权已取消 99:失败
	public static String getMessage(String code){
		String returnMessage = "未知参数";
		if (StringUtils.isEmpty(code)) {
			return returnMessage;
		}
		switch (code) {
			case "01":
				returnMessage = "未支付";
				break;
			case "02":
				returnMessage = "无回应";
				break;
			case "03":
				returnMessage = "已请款";
				break;
			case "04":
				returnMessage = "成功";
				break;
			case "05":
				returnMessage = "已退款";
				break;
			case "07":
				returnMessage = "授权确认成功";
				break;
			case "00":
				returnMessage = "授权已取消";
				break;
			case "99":
				returnMessage = "失败";
				break;
			default:
				break;
		}
		return returnMessage;
	}
}
