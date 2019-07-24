package com.example.base;

import android.os.Environment;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class LoggerUtil {
    private static final String TAG = "LoggerUtil";
    public static void configLogger(Level level) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/app/";
        File dir = new File(filePath);
        if (!dir.exists()) {
            try {
                //按照指定的路径创建文件夹
                dir.mkdirs();
            } catch (Exception e) {
                LogUtil.e(TAG,""+e);
            }
        }
        File file = new File(filePath + "/log.txt");
        if (!file.exists()) {
            try {
                //在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {
                LogUtil.e(TAG,""+e);
            }
        }

        LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(filePath + "log.txt");
        logConfigurator.setRootLevel(level);
        logConfigurator.setFilePattern("%d %-5p [%t][%c{2}]-[%l] %m%n");
        logConfigurator.setUseLogCatAppender(true);
        logConfigurator.setMaxFileSize(1024 * 1);
        logConfigurator.setMaxBackupSize(2);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.configure();
    }
}
