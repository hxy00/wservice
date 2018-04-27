package com.emt.epay.qapi.pojo;

public class CcbConfig {
	
	public static String PrivateKey = "";
	
	/**
	 * 建行商户代码
	 */
	public static String PMERCHANTID = "105520373990014";
	
	//!!!多系统配置不同之处begin！！！*************************************************************
	/**
	 * 商户柜台代码[云商]
	 */
	public static String PPOSID = "327781065";
	
	/**
	 * 商户柜台代码[商城]
	 */
	public static String PPOSID_SHOP = "902839972";
	
	/**
	 * 柜台号[云商]
	 */
	public static String QPOSID = "327781065";
	
	/**
	 * 柜台号[商城]
	 */
	public static String QPOSID_SHOP = "902839972";
	
	/**
	 * 签名以及验签参数【云商系统】
	 */
	public static String PPUB = "30819c300d06092a864886f70d010101050003818a00308186028180730ad71c7e37c85184e77c2fd320cf74e07e5b9a5ab2ea69ade04e95690cfe20d876e870d729cd21c30e85171cb30d711b9582cbeee9ff4eb436a6e0d516b3efc8f36d0ea0fe74c681fd555819473b12e461f60764b46247e05c30de303c91b1d94a05b5b37f4f54e6bbec56d00a6b7bf7205afb1cc1150f2dbfafec0f45bed3020111";
	
	/**
	 * 商城系统
	 */
	public static String PPUB_SHOP = "30819d300d06092a864886f70d010101050003818b0030818702818100a8da38ea2712cd00ab459f14e8663238f5233a0ae0c632a19c5eda7862bf9a4d9059f5f94d4c02efbf99e7e3c9a18f395872e285437aabcc035ae63cebecbc46e16f53196d074c08879eba9587f240e710a80d8989de54e9489db01446a439615af3df60bb93608a101959756786816886177616ca13f63edc2e32fc4e5e6c4d020113";
	//!!!多系统配置不同之处end！！！*************************************************************
	
	/**
	 * 分行代码
	 */
	public static String PBRANCHID = "520000000";
	
	/**
	 * 支付交易码
	 */
	public static String PTXCODE = "520100";
	
	/**
	 * 接口类型(之前为0)
	 */
	public static String PTYPE = "1";
	
	/**
	 * 币种
	 */
	public static String PCURCODE = "01";
	
	public static String notify = "";
	
	public static String queryUrl = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain";
	
	/**
	 * 查询商户号
	 */
	public static String QMERCHANTID = "105520373990014";
	
	/**
	 * 查询用操作员号
	 */
	public static String QBRANCHID = "520000000";
	
	/**
	 * 流水类型，0支付流水，1退款流水
	 */
	public static String QTYPE = "0";
	
	/**
	 * 交易码
	 */
	public static String QTXCODE = "410408";
	
	/**
	 * 登录密码
	 */
	public static String QUPWD = "cmaotai11ww";
	
	/**
	 * 查询方式
	 * 1页面形式     
	 * 2文件返回形式 (提供TXT和XML格式文件的下载)  
	 * 3 XML页面形式
	 */
    public static String QSEL_TYPE = "3";
    
    /**
     * 根据系统Id获取 商户柜台代码
    * @Title: getPOSID 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param sysId
    * @param @return  参数说明 
    * @return String    返回类型 
    * @throws
     */
    public static String getPOSID(String sysId){
    	String POSID = null;
    	switch (sysId) {
		case "900001":
			POSID = CcbConfig.PPOSID;
			break;
		case "400001":
			POSID = CcbConfig.PPOSID_SHOP;
			break;
		default:
			POSID = CcbConfig.PPOSID;
			break;
		}
    	return POSID;
    }
    
    /**
     * 根据系统Id获取公钥
    * @Title: getPPUB 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param sysId
    * @param @return  参数说明 
    * @return String    返回类型 
    * @throws
     */
    public static String getPPUB(String sysId){
    	String pubId = null;
    	switch (sysId) {
		case "900001":
			pubId = CcbConfig.PPUB;
			break;
		case "400001":
			pubId = CcbConfig.PPUB_SHOP;
			break;
		default:
			pubId = CcbConfig.PPUB;
			break;
		}
    	return pubId;
    }
    
    /**
     * 获取公钥并做处理
    * @Title: getPUB_30 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param sysId
    * @param @return  参数说明 
    * @return String    返回类型 
    * @throws
     */
    public static String getPUB_30(String sysId) {
		String pub = null; 
		switch (sysId) {
		case "900001":
			pub = CcbConfig.PPUB.substring(CcbConfig.PPUB.length() - 30, CcbConfig.PPUB.length());
			break;
		case "400001":
			pub = CcbConfig.PPUB_SHOP.substring(CcbConfig.PPUB_SHOP.length() - 30, CcbConfig.PPUB_SHOP.length());
			break;
		default:
			pub = CcbConfig.PPUB.substring(CcbConfig.PPUB.length() - 30, CcbConfig.PPUB.length());
			break;
		}
		return pub;
	}
    
    /**
     * 备注1
     * @param sysId
     * @return
     */
    public static String getRemark1(String sysId){
    	String remark = null; 
		switch (sysId) {
		case "900001":
			remark = "yspay";
			break;
		case "400001":
			remark = "shoppay";
			break;
		default:
			remark = "yspay";
			break;
		}
		return remark;
    }
    
    /**
     * 备注2
     * @param sysId
     * @return
     */
    public static String getRemark2(String sysId){
    	String remark = null; 
		switch (sysId) {
		case "900001":
			remark = "yspay";
			break;
		case "400001":
			remark = "shoppay";
			break;
		default:
			remark = "yspay";
			break;
		}
		return remark;
    }
    
    public static String getShopCode(String sysId){
    	String remark = null; 
		switch (sysId) {
		case "900001":
			remark = QPOSID;
			break;
		case "400001":
			remark = QPOSID_SHOP;
			break;
		default:
			remark = QPOSID;
			break;
		}
		return remark;
    }

    public static  String getMessage(String statusCode){
    	String comment = "失败";
		switch (statusCode) {
			case "0":
				comment = "失败";
				break;
			case "1":
				comment = "成功";
				break;
			case "2":
				comment = "待银行确认";
				break;
			case "3":
				comment = "已部分退款";
				break;
			case "4":
				comment = "已全部退款";
				break;
			case "5":
				comment = "待银行确认";
				break;
			default:
				break;
		}
		return comment;
	}
}
