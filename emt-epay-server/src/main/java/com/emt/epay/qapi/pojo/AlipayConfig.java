package com.emt.epay.qapi.pojo;

public class AlipayConfig {

	/**
	 * 合作身份者ID，以2088开头由16位纯数字组成的字符串
	 */
	public static String partner = "2088121467981861";
	public static String partner_shop = "2088511535991599";

	/**
	 * 收款支付宝账号，一般情况下收款账号就是签约账号
	 */
	public static String seller_email = "maotaiyun@163.com";
	public static String seller_email_shop = "mtjtwssc@126.com";

	/**
	 * 商户的私钥
	 */
	public static String key = "xjss1jg8pi0otcfdbe759bh9dafqg6my";
	public static String key_shop = "zlqlaznm9bgn0gvcf4mc2uavjfa1pgke";

	/**
	 * 字符编码格式 目前支持 gbk 或 utf-8
	 */
	public static String input_charset = "utf-8";

	/**
	 * 签名方式 不需修改
	 */
	public static String sign_type = "MD5";

	/**
	 * 交易类型，默认为1（商品购买）
	 */
	public static String payment_type = "1";

	/**
	 * 支付宝授权获取code地址
	 */
	public static String authorize_url = "https://openauth.alipay.com/oauth2/authorize.htm";

	/**
	 * 支付宝通过授权Code获取授权令牌地址
	 */
	public static String api_url = "https://openapi.alipay.com/gateway.do";

	/**
	 * 支付宝AppKey
	 */
	public static String client_id = "2015121400971325";

	/**
	 * 授权回调地址
	 */
	public static String private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALmADoc/vNZ5WihtzgxgtKCdoXCnivezf7iMytNVGfvw1FmbgXWNeAFFjm1z0sozZIhS+8ZyOOez6vWktXO1nvPrHFoEo6d7WhmvMCd9/8Y8o2IPLUOtXzmU/5IBXkPkMArdo+V2LaxHyxvgmz+HzLHbeMNduLVrU5SMj0hKwbYJAgMBAAECgYEAoOi9MYMPenf/xgwh7UjBxoWV8dPTzzRMeTjgA9vRU2M/wI8NCHxz+Z5tgvIyiiV0cnCRaD3SsOK30OdUYw/L1c4WJPMpdTwySpVzDjtoFG3a36KrHt0HxcjjYNSyaZP0BW/XmpuldN905pt2Qw7pURltNSMfIOsqgfnUcR6im4UCQQDe6zgkZqKZjCBK026il8hMEbxaOwU9we1PwL2J9HGgO9lm0XV/FHAnLtpq48K+6f5xmT5hekNTgv20o43rIq9LAkEA1QdJ8Gu99p/z8cNRp/caYKDu7PwSZ0fejp1ZFYgZboPYwKfV4Sr75jSXvRsbw243tV8mYarXS7BzFjvO0m9XewJAR8wSxQaZkELlk7QNiijAxl9f/t/LjFyNhoZZhblJb4ZXOxpzcX6vsK8SgiLhcoQDA6uLv/GVBazG8gtLhlQhiQJBAIQhPjoyCaRTt9IHjLk6qhCvISK6gouu5xrb+6pAPU3v11w/fYhoyc8SZd90VfcUgNctSjgf5+PZcLikpfad+ssCQQCoW2FPOIIT0UOZM+WYpAd0J2d537gcGqg23198cdf/ec8WhrI1Z1oJAPJZLGZakWy+ReoYKr9WspnhQpXqH0eC";

	/**
	 * DES加密Key
	 */
	public static String des_key = "ise5xLm1BBk=";
	
	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";
	
	/**
	 * 获取partner ID
	 * @param sysId
	 * @return
	 */
	public static String getPartner(String sysId){
		String partner = null;
		switch (sysId) {
		case "900001":
			partner = AlipayConfig.partner;
			break;
		case "400001":
			partner = AlipayConfig.partner_shop;
			break;
		default:
			partner = AlipayConfig.partner;
			break;
		}
		return partner;
	}
	
	/**
	 * 获取支付宝账号
	 * @param sysId
	 * @return
	 */
	public static String getSellerEmail(String sysId){
		String sellerEmail = null;
		switch (sysId) {
		case "900001":
			sellerEmail = AlipayConfig.seller_email;
			break;
		case "400001":
			sellerEmail = AlipayConfig.seller_email_shop;
			break;
		default:
			sellerEmail = AlipayConfig.seller_email;
			break;
		}
		return sellerEmail;
	}
	
	/**
	 * 获取支付宝密钥
	 * @param sysId
	 * @return
	 */
	public static String getKey(String sysId){
		String key = null;
		switch (sysId) {
		case "900001":
			key = AlipayConfig.key;
			break;
		case "400001":
			key = AlipayConfig.key_shop;
			break;
		default:
			key = AlipayConfig.key;
			break;
		}
		return key;
	}
}