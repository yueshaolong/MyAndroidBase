package com.ysl.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PicUtil {

    public static boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;
    public static String filePath;
    public static final int DAY = 0;
    public static final int WEEK = 1;
    public static final int MONTH = 2;

    public static void deletePic(Context context, Set<String> deleteSet){
        filePath = UtilFilePath.getPictureDirPath(context).getAbsolutePath();
        for (String path : deleteSet) {
            if (isAndroidQ) {
                context.getContentResolver().delete(Uri.parse(path), null, null);
            }else {
                File file = new File(path);
                if(file.exists()){
                    deleteImage(context, path);
                }
            }
        }
    }
    public static boolean deleteImage(Context context, String imgPath) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = MediaStore.Images.Media.query(resolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=?",
                new String[] { imgPath }, null);
        boolean result = false;
        if (null != cursor && cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uri = ContentUris.withAppendedId(contentUri, id);
            int count = context.getContentResolver().delete(uri, null, null);
            result = count == 1;
        } else {
            File file = new File(imgPath);
            result = file.delete();
        }
        return result;
    }

    public static Map<String, List<String>> getMonthData(Context context) {
        if (isAndroidQ) {
            return querySignImage("Pictures/preventpro/", context, MONTH);
        } else {
            filePath = UtilFilePath.getPictureDirPath(context).getAbsolutePath();
            // 图片列表
            Map<String, List<String>> monthMap = new HashMap<>();
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
                String name = file.getName().replace(".jpg", "");
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
    }

    public static String getMonthPic(long time){
        return DateUtils.dateToString(new Date(time), "yy/MM");
    }

    public static Map<String, List<String>> getWeekData(Context context) {
        if (isAndroidQ) {
            return querySignImage("Pictures/preventpro/", context, WEEK);
        } else {
            filePath = UtilFilePath.getPictureDirPath(context).getAbsolutePath();
            // 图片列表
            Map<String, List<String>> weekMap = new HashMap<>();
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
                String name = file.getName().replace(".jpg", "");
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
    }

    public static String getWeekPic(long time) {
        try {
            Calendar cal = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
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

    public static Map<String, List<String>> getDayData(Context context) {
        if (isAndroidQ) {
            return querySignImage("Pictures/preventpro/", context, DAY);
        } else {
            filePath = UtilFilePath.getPictureDirPath(context).getAbsolutePath();
            // 图片列表
            Map<String, List<String>> dayMap = new HashMap<>();
            // 得到该路径文件夹下所有的文件
            File fileAll = new File(filePath);
            File[] files = fileAll.listFiles();
            // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
            if(files == null || files.length == 0){
                return dayMap;
            }
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String path = file.getPath();
                String name = file.getName().replace(".jpg", "");
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
    }

    public static File[] arraySort(File[] input){
        for (int i=0;i<input.length-1;i++){
            for (int j=0;j<input.length-i-1;j++) {
                if(input[j].getName().compareToIgnoreCase(input[j+1].getName())>0){
                    File temp=input[j];
                    input[j]=input[j+1];
                    input[j+1]=temp;
                }
            }
        }
        return input;
    }

    public static String getDayPic(long time){
        return DateUtils.dateToString(new Date(time), "yy/MM/dd");
    }

    public static boolean checkIsImageFile(String fName) {
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1).toLowerCase();
        return FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp");
    }

    public static List<String> getImgPath(Context context) {
        filePath = UtilFilePath.getPictureDirPath(context).getAbsolutePath();
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        if(files == null || files.length == 0){
            return imagePathList;
        }
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (File file : files) {
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }
    //在公共文件夹下查询图片
    //这里的filepath在androidQ中表示相对路径
    //在androidQ以下是绝对路径
    public static Map<String, List<String>> querySignImage(String filePath, Context context, int typeShow) {
        // 图片列表
        Map<String, List<String>> dayMap = new HashMap<>();
        try {
            //兼容androidQ和以下版本
            String queryPathKey = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q ?
                    MediaStore.Images.Media.RELATIVE_PATH : MediaStore.Images.Media.DATA;
            //查询的条件语句
            String selection = queryPathKey + "=? ";
            //查询的sql
            //Uri：指向外部存储Uri
            //projection：查询那些结果
            //selection：查询的where条件
            //sortOrder：排序
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID,
                            queryPathKey,
                            MediaStore.Images.Media.MIME_TYPE,
                            MediaStore.Images.Media.DISPLAY_NAME},
                    selection,
                    new String[]{filePath},
                    null);
            //是否查询到了
            if (cursor != null && cursor.moveToFirst()) {
                //循环取出所有查询到的数据
                do {
                    //一张图片的基本信息
                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));//uri的id，用于获取图片
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH));//图片的相对路径
                    String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));//图片类型
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));//图片名字
                    System.out.println("id="+id+", path="+path+", type="+type+", name="+name);
                    //根据图片id获取uri，这里的操作是拼接uri
                    Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
                    long value = Long.valueOf(name.replace(".jpg", ""));
                    String dayPic = null;
                    switch (typeShow) {
                        case DAY:
                            dayPic = getDayPic(value);
                            break;
                        case WEEK:
                            dayPic = getWeekPic(value);
                            break;
                        case MONTH:
                            dayPic = getMonthPic(value);
                            break;
                        default:
                            break;
                    }

                    if (dayMap.containsKey(dayPic)) {
                        Objects.requireNonNull(dayMap.get(dayPic)).add(uri.toString());
                    }else {
                        List<String> imagePathList = new ArrayList<>();
                        imagePathList.add(uri.toString());
                        dayMap.put(dayPic, imagePathList);
                    }

                    //官方代码：
//                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
//                    if (uri != null) {
//                        //通过流转化成bitmap对象
//                        InputStream inputStream = context.getContentResolver().openInputStream(uri);
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    }
                } while (cursor.moveToNext());
            }
            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayMap;
    }


    //判断公有目录文件是否存在，自Android Q开始，公有目录File API都失效，不能直接通过new File(path).exists();
    // 判断公有目录文件是否存在，正确方式如下：
    public static boolean isAndroidQFileExists(Context context, String path){
        if (context == null) {
            return false;
        }
        AssetFileDescriptor afd = null;
        ContentResolver cr = context.getContentResolver();
        try {
            Uri uri = Uri.parse(path);
            afd = cr.openAssetFileDescriptor(Uri.parse(path), "r");
            if (afd == null) {
                return false;
            } else {
                afd.close();
            }
        } catch (Exception e) {
            return false;
        }finally {
            try {
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
