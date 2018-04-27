package com.emt.common.utils;

import com.emt.common.httpclient.HttpClientEx;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IPUtils {

    /*
     * 放入IP地址，返回IP信息对象
     * */
    public static Map<String, Object> getIpInfo(String ipstr) throws Exception
    {
        Map<String, Object> ipEntity = new HashMap<String, Object>();  
        //String city = getResult("http://ip.taobao.com/service/getIpInfo.php","202.98.222.67");
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("ip", ipstr);
        String ResponseBody = HttpClientEx.remoteGet("http://ip.taobao.com/service/getIpInfo.php", mapParams);
        
        ObjectMapper mapper = new ObjectMapper();    
        try {
            ipEntity =mapper.readValue(ResponseBody, Map.class);            
            if(ipEntity!=null){
                System.out.println("ipEntity.toJson():\n"+ipEntity.toString());
            }

        } catch (JsonParseException e) {            
            e.printStackTrace();
        } catch (JsonMappingException e) {            
            e.printStackTrace();
        } catch (IOException e) {            
            e.printStackTrace();
        }             
        return ipEntity;
    }
    
/*    public static void main(String args[])
    {

        Map<String, Object> ipEntity = null;
        try {
             ipEntity = IPUtils.getIpInfo("202.98.222.67");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
           
    }*/
}

class IpData{
    private Integer code;
    private String data;
    
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    
}
