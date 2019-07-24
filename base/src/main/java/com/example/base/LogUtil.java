package com.example.base;

import android.util.Log;

public class LogUtil {
    public static int LOG_LEVEL = 1;//开发时设置1，表示都输出。发布时设置为6，表示禁止输出
    public static int VERBOSE = 2;
    public static int DEBUG = 3;
    public static int INFO = 4;
    public static int WARN = 5;
    public static int ERROR = 6;

    public static void v(String tag, String msg) {
        if (LOG_LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_LEVEL <= DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }

}
