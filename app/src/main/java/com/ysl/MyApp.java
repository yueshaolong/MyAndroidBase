package com.ysl;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base.BaseApplication;
import com.example.base.LogUtil;
import com.example.base.LoggerUtil;
import com.squareup.leakcanary.LeakCanary;
import com.ysl.dagger2.AppComponent;
import com.ysl.dagger2.AppModule;
import com.ysl.dagger2.DaggerAppComponent;
import com.ysl.myandroidbase.BuildConfig;

import org.apache.log4j.Level;

public class MyApp extends BaseApplication {


    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        isDebug = BuildConfig.DEBUG;
        if(isDebug){
            LogUtil.LOG_LEVEL = 5;
        }else {
            LogUtil.LOG_LEVEL = 7;
        }


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }


}
