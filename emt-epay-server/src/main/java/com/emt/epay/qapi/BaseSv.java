package com.emt.epay.qapi;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018-03-28.
 */
public class BaseSv {
    /**
     * 判断时间是否在当天
     * @Title: isToday
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param orderDate
     * @param @return  参数说明
     * @return boolean    返回类型
     * @throws
     */
    protected boolean isToday(String orderDate){
        if (null == orderDate || "".equals(orderDate)) {
            return false;//说明是以前没有记录的数据
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());
        String orderDateFmt = orderDate.substring(0, 8);
        if (today.equals(orderDateFmt)) {
            return true;
        }
        return false;
    }

    /**
     * 微信签名
     * @param text
     * @param key
     * @return
     * @throws Exception
     */
    protected String wxSign(String text, String key) throws Exception {
        text = text + "&key=" + key;
        return DigestUtils.md5Hex(text.getBytes("UTF-8"));
    }
}
