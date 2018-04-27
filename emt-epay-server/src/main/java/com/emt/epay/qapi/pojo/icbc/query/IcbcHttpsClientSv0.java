package com.emt.epay.qapi.pojo.icbc.query;

import com.emt.common.utils.SystemUtil;
import com.emt.epay.qapi.util.Config;
import com.emt.epay.qapi.util.Global;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class IcbcHttpsClientSv0 {
	//根据操作系统设置读取证书路径
	private static String operatingSystem = Global.getConfig("epay.OS.switch");
	private static String flag = operatingSystem.equals("Linux") ? "/" : "";
	
    private final static String httpUrl = "https://corporbank.icbc.com.cn:446/servlet/ICBCINBSEBusinessServlet";
    // 客户端密钥库
    private final static String sslKeyStorePassword = "123456";
    private final static String sslKeyStoreType = "JKS"; // 密钥库类型，有JKS PKCS12等
    // 客户端信任的证书
    private final static String sslTrustStorePassword = "123456";


    public static void main(String[] args) throws Exception{
        IcbcHttpsClientSv0 sv0 = new IcbcHttpsClientSv0();
        //sv0.getHttpsClientPost("MTYS201512098900984", "20151209175721");
        sv0.getHttpsClientPost("900001", "MTYS201512309680137",   "20151230093313");
    }
    private SSLContext getSSLContext(String sysId){
		SSLContext sslContext = null;
		switch (sysId) {
		case "900001":
			String keyStorePath = null;
			if("Linux".equals(operatingSystem)){
				keyStorePath = Config.getConfig("pay/icbc/icbc_conf_linux.properties", "mtys.ssl_key_store_path");
			} else {
				keyStorePath = Config.getConfig("pay/icbc/icbc_conf_windows.properties", "mtys.ssl_key_store_path");
			}
			System.setProperty("javax.net.ssl.keyStore", keyStorePath);
    		System.setProperty("javax.net.ssl.keyStorePassword", sslKeyStorePassword);
    		System.setProperty("javax.net.ssl.keyStoreType", sslKeyStoreType);        
    		System.setProperty("javax.net.ssl.trustStore", keyStorePath);
    		System.setProperty("javax.net.ssl.trustStorePassword", sslTrustStorePassword);
    		
    		try {
    			KeyStore kstore = KeyStore.getInstance(sslKeyStoreType);
    			kstore.load(new FileInputStream(keyStorePath), sslKeyStorePassword.toCharArray());
    			KeyManagerFactory keyFactory = KeyManagerFactory.getInstance("sunx509");
    			keyFactory.init(kstore, sslKeyStorePassword.toCharArray());
    			KeyStore tstore = KeyStore.getInstance("jks");
    			tstore.load(new FileInputStream(keyStorePath), sslTrustStorePassword.toCharArray());
    			TrustManager[] tm;
    			TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
    			tmf.init(tstore);
    			tm = tmf.getTrustManagers();
    			sslContext = SSLContext.getInstance("SSL");
    			sslContext.init(keyFactory.getKeyManagers(), tm, null);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
			break;
		case "400001":
			if("Linux".equals(operatingSystem)){
				keyStorePath = Config.getConfig("pay/icbc/icbc_conf_linux.properties", "shop.ssl_key_store_path");
			} else {
				keyStorePath = Config.getConfig("pay/icbc/icbc_conf_windows.properties", "shop.ssl_key_store_path");
			}
    		// 设置系统参数
    		System.setProperty("javax.net.ssl.keyStore", keyStorePath);
    		System.setProperty("javax.net.ssl.keyStorePassword", sslKeyStorePassword);
    		System.setProperty("javax.net.ssl.keyStoreType", sslKeyStoreType);        
    		System.setProperty("javax.net.ssl.trustStore", keyStorePath);
    		System.setProperty("javax.net.ssl.trustStorePassword", sslTrustStorePassword);
    		
    		try {
    			KeyStore kstore = KeyStore.getInstance(sslKeyStoreType);
    			kstore.load(new FileInputStream(keyStorePath), sslKeyStorePassword.toCharArray());
    			KeyManagerFactory keyFactory = KeyManagerFactory.getInstance("sunx509");
    			keyFactory.init(kstore, sslKeyStorePassword.toCharArray());
    			KeyStore tstore = KeyStore.getInstance("jks");
    			tstore.load(new FileInputStream(keyStorePath), sslTrustStorePassword.toCharArray());
    			TrustManager[] tm;
    			TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
    			tmf.init(tstore);
    			tm = tmf.getTrustManagers();
    			sslContext = SSLContext.getInstance("SSL");
    			sslContext.init(keyFactory.getKeyManagers(), tm, null);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
			break;
		default:
			if("Linux".equals(operatingSystem)){
				keyStorePath = Config.getConfig("pay/icbc/icbc_conf_linux.properties", "mtys.ssl_key_store_path");
			} else {
				keyStorePath = Config.getConfig("pay/icbc/icbc_conf_windows.properties", "mtys.ssl_key_store_path");
			}
    		// 设置系统参数
    		System.setProperty("javax.net.ssl.keyStore", keyStorePath);
    		System.setProperty("javax.net.ssl.keyStorePassword", sslKeyStorePassword);
    		System.setProperty("javax.net.ssl.keyStoreType", sslKeyStoreType);        
    		System.setProperty("javax.net.ssl.trustStore", keyStorePath);
    		System.setProperty("javax.net.ssl.trustStorePassword", sslTrustStorePassword);
    		
    		try {
    			KeyStore kstore = KeyStore.getInstance(sslKeyStoreType);
    			kstore.load(new FileInputStream(keyStorePath), sslKeyStorePassword.toCharArray());
    			KeyManagerFactory keyFactory = KeyManagerFactory.getInstance("sunx509");
    			keyFactory.init(kstore, sslKeyStorePassword.toCharArray());
    			KeyStore tstore = KeyStore.getInstance("jks");
    			tstore.load(new FileInputStream(keyStorePath), sslTrustStorePassword.toCharArray());
    			TrustManager[] tm;
    			TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
    			tmf.init(tstore);
    			tm = tmf.getTrustManagers();
    			sslContext = SSLContext.getInstance("SSL");
    			sslContext.init(keyFactory.getKeyManagers(), tm, null);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
			break;
		}
        return sslContext;
    }
    
    /**
     * 
    * @Title: getHttpsClientPost 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param orderNum  订单编号
    * @param tranDate  交易时间
    * @return
    * @return String    返回类型 
    * @throws
     */
    public String getHttpsClientPost(String sysId, String orderNum, String tranDate) {
        String result = "";
        SSLContext sslContext = getSSLContext(sysId);
        if(sslContext==null){
            return result;
        }
        
        try {
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
            SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
            Scheme sch = new Scheme("https", 8443, socketFactory);
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
            HttpPost httpPost = new HttpPost(httpUrl);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            
            nvps.add(new BasicNameValuePair("APIName", "EAPI"));
            nvps.add(new BasicNameValuePair("APIVersion", "001.001.002.001"));//b2b 001.001.001.001; b2c 001.001.002.001 、 001.001.005.001
            nvps.add(new BasicNameValuePair("MerReqData", IcbcHttpsClientSv0.getRequestXml(sysId, orderNum, tranDate)));
     
            //httpPost.setHeader(name, value);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
     
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
            //httpResponse.getEntity().
            
            String spt = System.getProperty("line.separator");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stb = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine()) != null) {
                stb.append(line);
            }
            buffer.close();
            result =URLDecoder.decode(stb.toString(),"gb2312");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private static String  getRequestXml(String sysId, String orderNum, String tranDate ){
    	StringBuffer stringBuilder = new StringBuffer();
    	switch (sysId) {
		case "900001":
			stringBuilder.append("<?xml  version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>");
    		stringBuilder.append("<ICBCAPI>");
    		stringBuilder.append("<in>");
    		stringBuilder.append("<orderNum>"+orderNum+"</orderNum>");//订单号20151126164059001
    		stringBuilder.append("<tranDate>"+tranDate+"</tranDate>");//交易日期 20151126164059
    		stringBuilder.append("<ShopCode>2403EC26104821</ShopCode>");//商家号码
    		stringBuilder.append("<ShopAccount>2403025119200022508</ShopAccount>");//云商账号
    		stringBuilder.append("</in>");
    		stringBuilder.append("</ICBCAPI>");
			break;
		case "400001":
			stringBuilder.append("<?xml  version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>");
    		stringBuilder.append("<ICBCAPI>");
    		stringBuilder.append("<in>");
    		stringBuilder.append("<orderNum>"+orderNum+"</orderNum>");//订单号20151126164059001
    		stringBuilder.append("<tranDate>"+tranDate+"</tranDate>");//交易日期 20151126164059
    		stringBuilder.append("<ShopCode>2402EE20110012</ShopCode>");//商家号码
    		stringBuilder.append("<ShopAccount>2402003409000081534</ShopAccount>");//商城账号
    		stringBuilder.append("</in>");
    		stringBuilder.append("</ICBCAPI>");
			break;
		default:
			stringBuilder.append("<?xml  version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>");
    		stringBuilder.append("<ICBCAPI>");
    		stringBuilder.append("<in>");
    		stringBuilder.append("<orderNum>"+orderNum+"</orderNum>");//订单号20151126164059001
    		stringBuilder.append("<tranDate>"+tranDate+"</tranDate>");//交易日期 20151126164059
    		stringBuilder.append("<ShopCode>2403EC26104821</ShopCode>");//商家号码
    		stringBuilder.append("<ShopAccount>2403025119200022508</ShopAccount>");//云商账号
    		stringBuilder.append("</in>");
    		stringBuilder.append("</ICBCAPI>");
			break;
		}
        return stringBuilder.toString();
    }

}
