package com.ysl.util;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PicUtil {

    public static Map<String, List<String>> getMonthData() {
        // 图片列表
        Map<String, List<String>> monthMap = new HashMap<>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "pic";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        if(files == null || files.length == 0){
            return monthMap;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String path = file.getPath();
            String name = file.getName().replace("IMG_", "").replace(".jpg", "");
            if (checkIsImageFile(path)) {
                String monthPic = getMonthPic(Long.valueOf(name));
                if (monthMap.containsKey(monthPic)) {
                    Objects.requireNonNull(monthMap.get(monthPic)).add(path);
                }else {
                    List<String> imagePathList = new ArrayList<>();
                    imagePathList.add(path);
                    monthMap.put(monthPic, imagePathList);
                }
            }
        }
        // 返回得到的图片列表
        return monthMap;
    }

    public static String getMonthPic(long time){
        return DateUtils.dateToString(new Date(time), "yyyy/MM");
    }

    public static File[] arraySort(File[] input){
        for (int i=0;i<input.length-1;i++){
            for (int j=0;j<input.length-i-1;j++) {
                if(input[j].getName().compareToIgnoreCase(input[j+1].getName())<0){
                    File temp=input[j];
                    input[j]=input[j+1];
                    input[j+1]=temp;
                }
            }
        }
        return input;
    }

    public static Map<String, List<String>> getWeekData() {
        // 图片列表
        Map<String, List<String>> weekMap = new HashMap<>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "pic";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        if(files == null || files.length == 0){
            return weekMap;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String path = file.getPath();
            String name = file.getName().replace("IMG_", "").replace(".jpg", "");
            if (checkIsImageFile(path)) {
                String weekPic = getWeekPic(Long.valueOf(name));
                if (weekMap.containsKey(weekPic)) {
                    Objects.requireNonNull(weekMap.get(weekPic)).add(path);
                }else {
                    List<String> imagePathList = new ArrayList<>();
                    imagePathList.add(path);
                    weekMap.put(weekPic, imagePathList);
                }
            }
        }
        // 返回得到的图片列表
        return weekMap;
    }

    public static String getWeekPic(long time) {
        try {
            Calendar cal = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            cal.setTime(new Date(time));
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

    public static Map<String, List<String>> getDayData() {
        // 图片列表
        Map<String, List<String>> dayMap = new HashMap<>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "pic";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        if(files == null || files.length == 0){
            return dayMap;
        }
        arraySort(files);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String path = file.getPath();
            String name = file.getName().replace("IMG_", "").replace(".jpg", "");
            if (checkIsImageFile(path)) {
                String dayPic = getDayPic(Long.valueOf(name));
                if (dayMap.containsKey(dayPic)) {
                    Objects.requireNonNull(dayMap.get(dayPic)).add(path);
                }else {
                    List<String> imagePathList = new ArrayList<>();
                    imagePathList.add(path);
                    dayMap.put(dayPic, imagePathList);
                }
            }
        }
        // 返回得到的图片列表
        return dayMap;
    }

    public static String getDayPic(long time){
        return DateUtils.dateToString(new Date(time), "yyyy/MM/dd");
    }

    public static boolean checkIsImageFile(String fName) {
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1).toLowerCase();
        return FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp");
    }
}
