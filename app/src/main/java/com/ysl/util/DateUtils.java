package com.ysl.util;

import android.text.format.DateFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String formatPattern = "yyyy-MM-dd";

    public static final String formatPattern_all = "yyyy-MM-dd HH:mm:ss";

    public static final String format_hm = "HH:mm:ss";
    public static final String format_md = "MM-dd";

    // mysql 常用的日期转换
    public static final String formatMysqlByDay = "%Y-%c-%d"; // 按天
    public static final String formatMysqlByHonour = "%Y-%c-%d %H"; // 按小时
    public static final String formatMySqlByMonth = "%Y-%c"; // 按月

    /**
     * 日期转换字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String parseTime(String time, String parsePattern, String formatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);
        try {
            Date date = sdf.parse(time);
            return dateToString(date, formatPattern);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取当日的起始时间
     *
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取当日的结束时间
     *
     * @return
     */
    public static Date getNowEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取传入日期的起始时间
     *
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        dateStart.set(Calendar.MILLISECOND, 0);
        return dateStart.getTime();
    }

    /**
     * 获取传入日期的结束时间
     *
     * @return
     */
    public static Date getNowEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        dateEnd.set(Calendar.MILLISECOND, 999);
        return dateEnd.getTime();
    }

    public static String get7DayAgoTime(){
        long day7AgoMill = getStartTime().getTime() - 7 * 24 * 60 * 60 * 1000;
        Date date = new Date(day7AgoMill);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern_all);
        return simpleDateFormat.format(date);
    }

    /*将int转为低字节在前，高字节在后的byte数组
    b[0] = 11111111(0xff) & 01100001
    b[1] = 11111111(0xff) & (n >> 8)00000000
    b[2] = 11111111(0xff) & (n >> 8)00000000
    b[3] = 11111111(0xff) & (n >> 8)00000000
    */
    public static byte[] IntToByteArray(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }
    //将低字节在前转为int，高字节在后的byte数组(与IntToByteArray1想对应)
    public static int ByteArrayToInt(byte[] bArr) {
        if(bArr.length!=4){
            return -1;
        }
        return (int) ((((bArr[3] & 0xff) << 24)
                | ((bArr[2] & 0xff) << 16)
                | ((bArr[1] & 0xff) << 8)
                | ((bArr[0] & 0xff) << 0)));
    }
    /*将int转为低字节在后，高字节在前的byte数组
    b[0] = 11111111(0xff) & 01100001
    b[1] = 11111111(0xff) & 00000000
    b[2] = 11111111(0xff) & 00000000
    b[3] = 11111111(0xff) & 00000000
    */
    public static byte[] IntToByteArray2(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }
    //将高字节在前转为int，低字节在后的byte数组(与IntToByteArray2想对应)
    public static int ByteArrayToInt2(byte[] bArr) {
        if(bArr.length!=4){
            return -1;
        }
        return (int) ((((bArr[0] & 0xff) << 24)
                | ((bArr[1] & 0xff) << 16)
                | ((bArr[2] & 0xff) << 8)
                | ((bArr[3] & 0xff) << 0)));
    }


    /**
     * 将byte数组转化成String,为了支持中文，转化时用GBK编码方式
     */
    public static String ByteArrayToString(byte[] valArr,int maxLen) {
        String result=null;
        int index = 0;
        while(index < valArr.length && index < maxLen) {
            if(valArr[index] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(valArr, 0, temp, 0, index);
        try {
            result= new String(temp,"GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 将String转化为byte,为了支持中文，转化时用GBK编码方式
     */
    public static byte[] StringToByteArray(String str){
        byte[] temp = null;
        try {
            temp = str.getBytes("GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void main(String[] args) {
//        System.out.println(get7DayAgoTime());

//        System.out.println(Arrays.toString(IntToByteArray(2345)));
//        System.out.println(ByteArrayToInt(IntToByteArray(2345)));
//
//        System.out.println(Arrays.toString(IntToByteArray2(6789)));
//        System.out.println(ByteArrayToInt2(IntToByteArray2(6789)));
//
//        System.out.println(Arrays.toString(StringToByteArray("sdfhweui")));
//        System.out.println(ByteArrayToString(StringToByteArray("sdfhweui"), StringToByteArray("sdfhweui").length));

//        double d = 1.239;
//        double d = 1.2345;
        double d = 1.2355;
//        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_EVEN);
//        System.out.println(bg);

//        System.out.println(Math.round(d * 100)/100.0);

//        System.out.println(Double.valueOf(new DecimalFormat("#.##").format(d)));

//        System.out.println(String.format(Locale.getDefault(),"%.2f", d));

//        System.out.println(Double.parseDouble(String.format("%.2f", d)));

        System.out.println(String.format(Locale.getDefault(),"%.2f", Double.valueOf("1.2467")));
    }
}
