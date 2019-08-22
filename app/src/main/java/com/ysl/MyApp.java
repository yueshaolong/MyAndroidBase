package com.ysl;

import android.Manifest;
import android.app.Notification;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.billy.cc.core.component.CC;
import com.example.base.BaseApplication;
import com.example.base.LogUtil;
import com.example.base.LoggerUtil;
import com.squareup.leakcanary.LeakCanary;
import com.ysl.dagger2.AppComponent;
import com.ysl.dagger2.AppModule;
import com.ysl.dagger2.DaggerAppComponent;
import com.ysl.myandroidbase.BuildConfig;
import com.ysl.myandroidbase.R;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;

public class MyApp extends BaseApplication {


    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        isDebug = BuildConfig.DEBUG;
        if (isDebug) {
            LogUtil.LOG_LEVEL = 5;
        } else {
            LogUtil.LOG_LEVEL = 7;
        }


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(BuildConfig.DEBUG);

        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);

//        Set<String> tags = new HashSet<>();
//        tags.add("le");
//        JPushInterface.setTags(this, 0, tags);
//        JPushInterface.checkTagBindState(this, 0, "le");
//        JPushInterface.setAlias(this, 1, "le");
//        JPushInterface.setMobileNumber(this, 2, "18203651402");


        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.drawable.jpush_notification_icon;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
        MultiActionsNotificationBuilder builder2 = new MultiActionsNotificationBuilder(this);
        //添加按钮，参数（按钮图片、按钮文字、扩展数据）
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(2, builder2);
        CustomPushNotificationBuilder builder3 = new
                CustomPushNotificationBuilder(this,
                R.layout.customer_notitfication_layout,
                R.id.icon,
                R.id.title,
                R.id.text,
                R.id.time);
        // 指定定制的 Notification Layout
        builder3.statusBarDrawable = R.drawable.news1;
        // 指定最顶层状态栏小图标
        builder3.layoutIconDrawable = R.drawable.news2;
        // 指定下拉状态栏时显示的通知图标
        JPushInterface.setPushNotificationBuilder(3, builder3);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }


}
