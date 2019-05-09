package com.ysl;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.ysl.dagger2.AppComponent;
import com.ysl.dagger2.AppModule;
import com.ysl.dagger2.DaggerAppComponent;

public class MyApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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
