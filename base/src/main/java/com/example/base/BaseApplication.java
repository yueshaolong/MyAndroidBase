package com.example.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    public static Context application;
    public static boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
