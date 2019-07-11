package com.ysl.util;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static void main(String[] args) {
        System.out.println(get7DayAgoTime());
    }
}
