package com.emt.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DateHelper {

    public static int daysBetween(Date now, Date returnDate) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(now);
        cReturnDate.setTime(returnDate);
        setTimeToMidnight(cNow);
        setTimeToMidnight(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return millisecondsToDays(intervalMs);
    }

    private static int millisecondsToDays(long intervalMs) {
        return (int) (intervalMs / (1000 * 86400));
    }

    private static void setTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    /**
     * 间隔天数
     * @param date1
     * @param date2
     * @return
     */
    public static long differ(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // return date1.getTime() / (24*60*60*1000) - date2.getTime() /
        // (24*60*60*1000);
        try {
            return (sdf.parse(sdf.format(date2)).getTime() - sdf.parse(sdf.format(date1)).getTime()) / 86400000; // 用立即数，减少乘法计算的开销
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static String getDateBeforeString(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return new SimpleDateFormat("yyyy年MM月dd日").format(now.getTime());
    }
    
    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBeforeDate(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static String getDateAfterString(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return new SimpleDateFormat("yyyy年MM月dd日").format(now.getTime());
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfterDate(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }
    
    /**
     * @category 得到几秒钟后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfterSecond(Date d, long Second) {
        //d.getTime() 毫秒单位的long数据
        return new Date(d.getTime() + Second * 1000  );
    }
    
    /**
     * @category 得到几分钟后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfterMinute(Date d, int Minute) {
        //d.getTime() 毫秒单位的long数据
        return new Date(d.getTime() + Minute * 1000 * 60 );
    }
    
    public static String getDileiStr(int tatal, int count) {
        if (count == 1) {
            return "" + new Random().nextInt(tatal);
        } else {
            // 使用SET以此保证写入的数据不重复
            Set<Integer> set = new HashSet<Integer>();
            // 随机数
            Random random = new Random();

            while (set.size() < count) {
                // nextInt返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）
                // 和指定值（不包括）之间均匀分布的 int 值。
                set.add(random.nextInt(tatal));
            }
            String ss = "";
            int fl = 0;
            for (Integer integer : set) {
                if (fl == 0) {
                    ss += integer;
                    fl = 1;
                } else {
                    ss += "," + integer;
                }
            }
            return ss;
        }
    }

    public static String toMd5(String s) {
        char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        byte[] bytes = s.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        md.update(bytes);
        bytes = md.digest();
        int j = bytes.length;
        char[] chars = new char[j * 2];
        int k = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            chars[k++] = hexChars[b >>> 4 & 0xf];
            chars[k++] = hexChars[b & 0xf];
        }
        return new String(chars);
    }
    public static Date String2Date(String _dt){
        return DataType.getAsDate(DataType.transfer(_dt, "Date"));
    }
}
