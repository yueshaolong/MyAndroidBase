package com.example.news;

import android.app.Application;

import com.billy.cc.core.component.CC;
/**
 * cc组件化工具
 */
public class NewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(BuildConfig.DEBUG);
    }
}
