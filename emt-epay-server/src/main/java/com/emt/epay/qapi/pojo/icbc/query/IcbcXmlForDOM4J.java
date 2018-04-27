package com.emt.epay.qapi.pojo.icbc.query;

import com.alibaba.druid.util.StringUtils;
import com.emt.common.security.BaseCoder;
import com.emt.common.utils.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class IcbcXmlForDOM4J {
    // DOM4j解析XML
    public static Map<String, Object>  parse_wap_b2c(String protocolXML) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        try {

            Document doc = (Document) DocumentHelper.parseText(protocolXML);
            Element books = doc.getRootElement();
            // Iterator users_subElements = books.elementIterator("UID");//指定获取那个元素
            Iterator Elements = books.elementIterator();
            while (Elements.hasNext()) {
                Element element = (Element) Elements.next();

                if( !StringUtils.isEmpty( element.getTextTrim() )){
                    mapResult.put(element.getName(), element.getTextTrim());
                }
                // 订单信息
                if ("orderInfo".equals(element.getName())) {
                    Map<String, String> orderInfoMap = new HashMap<String, String>();
                    List subElements = element.elements();      
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        orderInfoMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("orderInfo", orderInfoMap);
                }
                //结果信息
                if ("bank".equals(element.getName())) {
                    Map<String, String> bankMap = new HashMap<String, String>();
                    List subElements = element.elements();
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        bankMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("bank", bankMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapResult;
    }

    // DOM4j解析XML
    public static Map<String, Object>  parse_pc_b2c(String protocolXML) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        try {

            Document doc = (Document) DocumentHelper.parseText(protocolXML);
            Element books = doc.getRootElement();
            // Iterator users_subElements = books.elementIterator("UID");//指定获取那个元素
            Iterator Elements = books.elementIterator();
            while (Elements.hasNext()) {
                Element element = (Element) Elements.next();

                if( !StringUtils.isEmpty( element.getTextTrim() )){
                    mapResult.put(element.getName(), element.getTextTrim());
                }
                // 订单信息
                if ("orderInfo".equals(element.getName())) {
                    Map<String, Object> orderInfoMap = new HashMap<String, Object>();
                    List subElements = element.elements();
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        orderInfoMap.put(ele.getName(), ele.getTextTrim());
                        
                        if("subOrderInfoList".equals(ele.getName())){
                        	List subOrderInfos = ele.elements();
                        	Map<String, List<Map<String, String>>> subOrderInfoList = new HashMap<String, List<Map<String, String>>>();
                        	List<Map<String, String>> subOrderList = new ArrayList<Map<String, String>>();
                        	for(int m = 0; m < subOrderInfos.size(); m++){
                        		Element subOrderInfo = (Element)subOrderInfos.get(m);
                        		Map<String, String> subOrderInfoMap = new HashMap<String, String>();
                        		List subOrderElements = subOrderInfo.elements();
                        		for(int f = 0; f < subOrderElements.size(); f++){
                        			Element subOrder = (Element)subOrderElements.get(f);
                        			subOrderInfoMap.put(subOrder.getName(), subOrder.getTextTrim());
                        		}
                        		subOrderList.add(subOrderInfoMap);
                        	}
                        	subOrderInfoList.put("subOrderInfos", subOrderList);
                        	orderInfoMap.put("subOrderInfoList", subOrderInfoList);
                        }
                    }
                    mapResult.put("orderInfo", orderInfoMap);
                }
                
                if("custom".equals(element.getName())){
                	Map<String, String> customMap = new HashMap<String, String>();
                	List subElements = element.elements();
                	//得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        customMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("custom", customMap);
                }
                
                //结果信息
                if ("bank".equals(element.getName())) {
                    Map<String, String> bankMap = new HashMap<String, String>();
                    List subElements = element.elements();
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        bankMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("bank", bankMap);
                }
                
                if("extend".equals(element.getName())){
                	Map<String, String> extendMap = new HashMap<String, String>();
                    List subElements = element.elements();
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        extendMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("extend", extendMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapResult;
    }
    
    // DOM4j解析XML
    //APIVersion  001.001.002.001
    public static Map<String, Object>  parseB2C002(String protocolXML) {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        try {

            Document doc = (Document) DocumentHelper.parseText(protocolXML);
            Element books = doc.getRootElement();
            // Iterator users_subElements = books.elementIterator("UID");//指定获取那个元素
            Iterator Elements = books.elementIterator();
            while (Elements.hasNext()) {
                Element element = (Element) Elements.next();

                if( !StringUtils.isEmpty( element.getTextTrim() )){
                    mapResult.put(element.getName(), element.getTextTrim());
                }
                // 订单信息
                if ("pub".equals(element.getName())) {
                    Map<String, String> orderInfoMap = new HashMap<String, String>();
                    List subElements = element.elements();      
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        orderInfoMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("pub", orderInfoMap);
                }
                //结果信息
                if ("in".equals(element.getName())) {
                    Map<String, String> bankMap = new HashMap<String, String>();
                    List subElements = element.elements();
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        bankMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("in", bankMap);
                }
                
                //结果信息
                if ("out".equals(element.getName())) {
                    Map<String, String> bankMap = new HashMap<String, String>();
                    List subElements = element.elements();
                    //得到全部子节点
                    for (int i = 0; i < subElements.size(); i++) {
                        Element ele = (Element) subElements.get(i);
                        bankMap.put(ele.getName(), ele.getTextTrim());
                    }
                    mapResult.put("out", bankMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapResult;
    }
    
    private static String getXML() {
        StringBuffer stringBuilder = new StringBuffer();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
        stringBuilder.append("<B2CRes>");
        stringBuilder.append("<interfaceName>ICBC_WAPB_B2C</interfaceName>");
        stringBuilder.append("<interfaceVersion>1.0.0.6</interfaceVersion>");
        stringBuilder.append("<orderInfo>");
        stringBuilder.append("<orderDate>" + DateUtils.DateTimeToYYYYMMDDhhmmss() + "</orderDate>");
        stringBuilder.append("<orderid>" + DateUtils.DateTimeToYYYYMMDDhhmmss() + "001</orderid>");
        stringBuilder.append("<amount>20</amount>");
        stringBuilder.append("<installmentTimes>1</installmentTimes>");
        stringBuilder.append("<merAcct>2403025119200022508</merAcct>");
        stringBuilder.append("<merID>2403EC26104821</merID>");
        stringBuilder.append("<curType>001</curType>");
        stringBuilder.append("<verifyJoinFlag>0</verifyJoinFlag>");
        stringBuilder.append("<JoinFlag>0</JoinFlag>");
        stringBuilder.append("<UserNum></UserNum>");
        stringBuilder.append("</orderInfo>");
        stringBuilder.append("<bank>");
        stringBuilder.append("<TranSerialNo>" + DateUtils.DateTimeToYYYYMMDDhhmmss() + "</TranSerialNo>");// 银行端指令流水号
        stringBuilder.append("<notifyDate>" + DateUtils.DateTimeToYYYYMMDDhhmmss() + "</notifyDate>");
        /*
         * 1-“交易成功，已清算”； 2-“交易失败”； 3-“交易可疑”
         */
        stringBuilder.append("<tranStat>1</tranStat>");
        stringBuilder.append("<comment>成功</comment>");
        stringBuilder.append("</bank>");
        stringBuilder.append("</B2CRes> ");

        return stringBuilder.toString();
    }
}
