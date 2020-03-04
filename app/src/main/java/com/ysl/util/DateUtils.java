package com.ysl.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String formatPattern = "yyyy-MM-dd";
    public static final String formatPattern_1 = "yyyy/MM/dd";
    public static final String formatPattern_p = "yyyy_MM_dd_HHmmss";
    public static final String formatPattern_all = "yyyy-MM-dd HH:mm:ss";
    public static final String formatPattern_2 = "HH:mm";

    public static final String format_hm = "HH:mm:ss";
    public static final String format_md = "MM-dd";
    public static final String format_ym = "yyyy-MM";
    public static final String format_y = "yyyy";

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

    public static void main(String[] args){
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
//        double d = 1.2355;
//        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_EVEN);
//        System.out.println(bg);

//        System.out.println(Math.round(d * 100)/100.0);

//        System.out.println(Double.valueOf(new DecimalFormat("#.##").format(d)));

//        System.out.println(String.format(Locale.getDefault(),"%.2f", d));

//        System.out.println(Double.parseDouble(String.format("%.2f", d)));

//        System.out.println(String.format(Locale.getDefault(),"%.2f", Double.valueOf("1.2467")));

//        String s = dateToString(new Date(), formatPattern_p);
//        System.out.println(s);

//        getWeek("20190928");
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            Date date = sdf.parse("20191001");
//            long time = date.getTime();
//            System.out.println(time);
//            System.out.println(sdf.parse("20191007").getTime());
//            System.out.println(sdf.parse("20191008").getTime());
//
//            System.out.println(sdf.parse("20191208").getTime());
//            System.out.println(sdf.parse("20191108").getTime());
//            System.out.println(sdf.parse("20190908").getTime());
//            System.out.println(sdf.parse("20190808").getTime());
//            System.out.println(sdf.parse("20190708").getTime());
//            System.out.println(sdf.parse("20190608").getTime());
//            System.out.println(sdf.parse("20190508").getTime());
//            System.out.println(sdf.parse("20190408").getTime());
//            System.out.println(sdf.parse("20190308").getTime());
//            System.out.println(sdf.parse("20190208").getTime());
//            System.out.println(sdf.parse("20190108").getTime());
//            System.out.println(sdf.parse("20170508").getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


//        List<String> date = new ArrayList<>();
//        date.add("2017/10/01");
//        date.add("2017/08/01");
//        date.add("2013/10/01");
//        date.add("2019/10/06");
//        date.add("2019/10/03");
//        date.add("2018/10/03");
//        date.add("2018/01/03");
//        date.add("2018/11/03");
//        Collections.reverse(date);
//        System.out.println(date);
//        Collections.sort(date);
//        System.out.println(date);
//        Collections.reverse(date);
//        System.out.println(date);

//        System.out.println(0 % 15 == 0 ? 0 / 15 : 0 / 15 + 1);
//        System.out.println(2 % 15 == 0 ? 2 / 15 : 2 / 15 + 1);
//        System.out.println(12 % 15 == 0 ? 12 / 15 : 12 / 15 + 1);
//        System.out.println(15 % 15 == 0 ? 15 / 15 : 15 / 15 + 1);
//        System.out.println(20 % 15 == 0 ? 20 / 15 : 20 / 15 + 1);
//        System.out.println(30 % 15 == 0 ? 30 / 15 : 30 / 15 + 1);

//        List<String> d = null;
//        System.out.println(d.isEmpty());

//        System.out.println(formatDuring(23493567));

//        Date date = null;
//        try {
//            String s = dateToString(new Date(), formatPattern);
//            System.out.println(s);
//            date = new SimpleDateFormat(formatPattern_all).parse(s+" 18:00:00");
//            System.out.println(date.getTime());
//            System.out.println(DateUtils.dateToString(date, formatPattern_all));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        System.out.println("2019/11/02".replace("/", "-"));


    }

    public static String getWeek(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            cal.setTime(new Date(1569600000000l));
            // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            // 获得当前日期是一个星期的第几天  
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // 获取该周第一天
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
            String beginDate = sdf.format(cal.getTime());
            System.out.println(beginDate);
            // 获取该周最后一天
            cal.add(Calendar.DATE, 6);
            String endDate = sdf.format(cal.getTime());
            System.out.println(endDate);
            return beginDate+"_"+endDate;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " 天 " + getTv(hours) + " 时 " + getTv(minutes) + " 分 "
                + getTv(seconds) + " 秒 ";
    }
    private static String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//小于10,,前面补位一个"0"
        }
    }
    /**
     *
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return  输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }
}
