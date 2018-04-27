package com.emt.common.utils;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;


public class HttpUtil {

    public static final String XML_CONTENT_TYPE = "text/xml; charset=GBK";
    public static final String HTML_CONTENT_TYPE = "text/html; charset=UTF-8";
    public static final String XML_CONTENT_TYPE_FOR_SAVE = "text/xml; charset=UTF-8";
    public static final String SETNAME_CLASS_PREFIX = "${";
    public static final String SETNAME_CLASS_SUFFIX = "}";
    public static final String CHARSET_ISO8859 = "ISO-8859-1";
    public static final String CHARSET_GB2312 = "GB2312";
    public static final String CHARSET_UTF8 = "UTF8";
    public static final String CHARSET_GBK = "GBK";
    public static final String CHARSET_GB18030 = "GB18030";
    private static String Version = "1.0";
    private static String Encoding = CHARSET_UTF8;

    public static String getVersion() {
        return Version;
    }

    public static String getEncoding() {
        return Encoding;
    }

    public static Object getObject(ServletRequest request, String paraName) throws Exception {
        Object result = request.getAttribute(paraName);
        if (result == null) {
            result = request.getParameter(paraName);            
            //if (result != null)
            //    result = new String(((String) result).getBytes("ISO-8859-1"), getEncoding());
        }
        return result;
    }

    public static Object getObject(ServletRequest request, String paraName, String pCharset) throws Exception {
        Object result = request.getAttribute(paraName);
        if (result == null) {
            result = request.getParameter(paraName);
            //if (result != null)
                //result = new String(((String) result).getBytes("ISO-8859-1"), pCharset);
        }
        return result;
    }

    public static String getParameter(ServletRequest request, String paraName) throws Exception {
        return DataType.transferToString(getObject(request, paraName), "String");
    }

    public static String getParameter(ServletRequest request, String paraName, String pCharset) throws Exception {
        return DataType.transferToString(getObject(request, paraName, pCharset), "String");
    }

    public static String getAsString(ServletRequest request, String paraName) throws Exception {
        return DataType.transferToString(getObject(request, paraName), "String");
    }

    public static int getAsInt(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsInt(DataType.transfer(getObject(request, paraName), "Integer"));
    }

    public static long getAsLong(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsLong(DataType.transfer(getObject(request, paraName), "Long"));
    }

    public static short getAsShort(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsShort(DataType.transfer(getObject(request, paraName), "Short"));
    }

    public static char getAsChar(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsChar(DataType.transfer(getObject(request, paraName), "Char"));
    }

    public static double getAsDouble(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsDouble(DataType.transfer(getObject(request, paraName), "Double"));
    }

    public static float getAsFloat(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsFloat(DataType.transfer(getObject(request, paraName), "Float"));
    }

    public static boolean getAsBoolean(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsBoolean(DataType.transfer(getObject(request, paraName), "Boolean"));
    }

    public static byte getAsByte(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsByte(DataType.transfer(getObject(request, paraName), "Byte"));
    }

    public static Date getAsDate(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsDate(DataType.transfer(getObject(request, paraName), "Date"));
    }

    public static Time getAsTime(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsTime(DataType.transfer(getObject(request, paraName), "Time"));
    }

    public static Timestamp getAsDateTime(ServletRequest request, String paraName) throws Exception {
        return DataType.getAsDateTime(DataType.transfer(getObject(request, paraName), "DateTime"));
    }
 
    public static String getEncoding(String str) {      
        String encode = "GB2312";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s = encode;      
               return s;      
            }      
        } catch (Exception exception) {      
        }      
        encode = "ISO-8859-1";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s1 = encode;      
               return s1;      
            }      
        } catch (Exception exception1) {      
        }      
        encode = "UTF-8";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s2 = encode;      
               return s2;      
            }      
        } catch (Exception exception2) {      
        }      
        encode = "GBK";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s3 = encode;      
               return s3;      
            }      
        } catch (Exception exception3) {      
        }      
       return "";      
    }    
    
    public static String getRequestAllHeaderData(HttpServletRequest request)
    {
		if (request == null) {
			return "";
		}
    	StringBuffer sb = new StringBuffer();  
        Enumeration<String> en = request.getHeaderNames();  
        while (en.hasMoreElements()) {  
            String name = (String) en.nextElement();  
            String value = request.getHeader(name);  
            sb.append(name + "=" + value + "\r\n");  
        }  
       return sb.toString();
    }
}
