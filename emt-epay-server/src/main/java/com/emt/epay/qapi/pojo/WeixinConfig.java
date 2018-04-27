package com.emt.epay.qapi.pojo;

import com.emt.common.utils.PropertiesLoader;
import com.emt.common.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public class WeixinConfig {
	/**
	 * 公众账号ID
	 */
//	public static String appid = "wxae16715868c86c35";

	/**
	 * AppStore版本的微信公众号ID
	 */
	public static String appStoreAppId = "wxae05add98258a724";

	/**
	 * AppStore版本的微信商户号
	 */
	public static String appStoreMch_Id = "1294121001";

	/**
	 * 商户号
	 */
//	public static String mch_id = "1294158901";

	/**
	 * 密钥
	 */
	public static String appSecret = "2E090BCC8329A298315E4891A01D42CC";
	
//	public static String appStoreSecret = "7a231e584a72a121d651df361fab418e";

	/**
	 * 提交的URL
	 */
	public static String suburl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static String orderQueryUrlString = "https://api.mch.weixin.qq.com/pay/orderquery";

	public static String notify_url = "";
	
	/***********************商城系统微信支付配置如下(公众号支付)********************************************************************/
	/**
	 * 公众账号ID
	 */
	public static String appid_shop = "wx7a2dfd9101d0bece";
	
	/**
	 * 商户号
	 */
	public static String mch_id_shop = "1220734401";
	
	/**
	 * 密钥
	 */
	public static String appSecret_shop = "a27ac11c644038a10399f4aabemao888";
	
	
	/***************************微信小程序配置******************************************************************************************/
	//属性文件加载对象
	private static PropertiesLoader loader = new PropertiesLoader("WeixinApp.properties");
	//保存全局属性值
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 根据系统id和appType（appStore、weixinApp）获取相关配置参数
	 * @param sysId
	 * @param appType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getWXConfigure(String sysId, String appType){
		Map<String, String> retMap = new HashMap<String, String>();
		String appId = WeixinConfig.appStoreAppId;
		String mchId = WeixinConfig.appStoreMch_Id;
		String appSecret = WeixinConfig.appSecret;
		switch (sysId) {
		case "900001":
			if("weixinPay".equals(appType)){
				appId = WeixinConfig.appStoreAppId;
				mchId = WeixinConfig.appStoreMch_Id;
				appSecret = WeixinConfig.appSecret;
			} else {
				try {
					String weixinApp = getConfig(appType);
					ObjectMapper mapper = new ObjectMapper();
					Map<String, String> pMap = mapper.readValue(weixinApp, Map.class);
					//从Map中获取数据
					appId = pMap.get("appId");
					mchId = pMap.get("mchId");
					appSecret = pMap.get("appSecret");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case "400001":
			appId = WeixinConfig.appid_shop;
			mchId = WeixinConfig.mch_id_shop;
			appSecret = WeixinConfig.appSecret_shop;
			break;
		default:
			appId = WeixinConfig.appStoreAppId;
			mchId = WeixinConfig.appStoreMch_Id;
			appSecret = WeixinConfig.appSecret;
			break;
		}
		retMap.put("appId", appId);
		retMap.put("mchId", mchId);
		retMap.put("appSecret", appSecret);
		return retMap;
	}
	
	/**
	 * 根据shopCode获取相关配置参数
	 * @param shopCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getWXConfigure(String shopCode){
		Map<String, String> retMap = new HashMap<String, String>();
		String appId = WeixinConfig.appStoreAppId;
		String mchId = WeixinConfig.appStoreMch_Id;
		String appSecret = WeixinConfig.appSecret;
		switch (shopCode) {
		case "1294121001"://云商
			appId = WeixinConfig.appStoreAppId;
			mchId = WeixinConfig.appStoreMch_Id;
			appSecret = WeixinConfig.appSecret;
			break;
		case "1294158901"://云商小程序
			try {
				String weixinApp = getConfig("weixinApp");
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> pMap = mapper.readValue(weixinApp, Map.class);
				//从Map中获取数据
				appId = pMap.get("appId");
				mchId = pMap.get("mchId");
				appSecret = pMap.get("appSecret");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "1220734401"://商城
			appId = WeixinConfig.appid_shop;
			mchId = WeixinConfig.mch_id_shop;
			appSecret = WeixinConfig.appSecret_shop;
			break;
		default:
			appId = WeixinConfig.appStoreAppId;
			mchId = WeixinConfig.appStoreMch_Id;
			appSecret = WeixinConfig.appSecret;
			break;
		}
		retMap.put("appId", appId);
		retMap.put("mchId", mchId);
		retMap.put("appSecret", appSecret);
		return retMap;
	}

	/**
	 * 判断接口名称是否存在
	 * @param interfaceName
	 * @return
	 */
	public static boolean isNormalInterName(String interfaceName){
		if ("weixinPay".equals(interfaceName) || "weixinApp".equals(interfaceName) || "weixinShop".equals(interfaceName)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据商户号获取接口名称
	 * @param mchId
	 * @return
	 */
	public static String getInterName(String mchId){
		String interName = null;
		switch (mchId) {
		case "1294121001":
			interName = "weixinPay";
			break;
		case "1294158901":
			interName = "weixinApp";
			break;
		case "1220734401":
			interName = "weixinShop";
			break;
		default:
			interName = "weixinPay";
			break;
		}
		return interName;
	}

	public static  String getMessage(String tranStatus){
		String comment = "未支付";
		switch (tranStatus) {
			case "SUCCESS":
				comment = "支付成功";
				break;
			case "REFUND":
				comment = "转入退款";
				break;
			case "NOTPAY":
				comment = "未支付";
				break;
			case "CLOSED":
				comment = "已关闭";
				break;
			case "REVOKED":
				comment = "已撤销（刷卡支付）";
				break;
			case "USERPAYING":
				comment = "用户支付中";
				break;
			case "PAYERROR":
				comment = "支付失败(其他原因，如银行返回失败)";
				break;
			default:
				break;
		}
		return comment;
	}
}
