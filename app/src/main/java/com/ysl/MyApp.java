package com.ysl;

import android.app.Notification;
import android.database.sqlite.SQLiteDatabase;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.example.base.BaseApplication;
import com.example.base.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.ysl.dagger2.AppComponent;
import com.ysl.dagger2.AppModule;
import com.ysl.dagger2.DaggerAppComponent;
import com.ysl.greendao.DaoMaster;
import com.ysl.greendao.DaoSession;
import com.ysl.myandroidbase.BuildConfig;
import com.ysl.myandroidbase.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class MyApp extends BaseApplication {

    public static final String TAG = "MyApp";
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

        //CC组件化路由
        CC.enableDebug(BuildConfig.DEBUG);//debug模式下，会在Logcat中输出一些CC框架内部的执行日志
        CC.enableVerboseLog(BuildConfig.DEBUG);//开启日志跟踪后，会在Logcat中输出CC调用的详细流程，将打印出每一个执行时的CC对象或CCResult对象的详细信息
        CC.enableRemoteCC(BuildConfig.DEBUG);//开启跨app组件调用

        //极光推送
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        setPushNotificationBuilder();

        //腾讯bugly 设置上报进程为主进程
        String packageName = getPackageName();
        String processName = getProcessName(Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(this, "ea5f6fd324", BuildConfig.DEBUG, strategy);

        //初始化greenDao
        initGreenDao();

        //使用腾讯文件系统TBS
        initTbs();

        //处理RxJava的异常
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                CrashReport.postCatchedException(throwable);
            }
        });
    }

    private void initTbs() {
        QbSdk.initX5Environment(this,new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d(TAG, "onCoreInitFinished: ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //这里被回调，并且b=true说明内核初始化并可以使用
                //如果b=false,内核会尝试安装，你可以通过下面监听接口获知
                Log.d(TAG, "onViewInitFinished: b="+b);
            }
        });

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                //tbs内核下载完成回调
                Log.d(TAG, "onDownloadFinish: i="+i);
            }

            @Override
            public void onInstallFinish(int i) {
                //内核安装完成回调，
                Log.d(TAG, "onInstallFinish: i="+i);
            }

            @Override
            public void onDownloadProgress(int i) {
                //下载进度监听
                Log.d(TAG, "onDownloadProgress: i="+i);
            }
        });
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     * */
    private void initGreenDao() {
        //数据库升级写法
//        MyDaoMaster helper = new MyDaoMaster(this, "aserbaos.db");
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aserbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        //数据库加密密码为“aserbao"的写法
//        Database db = helper.getEncryptedWritableDb("aserbao");
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;
    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 获取进程号对应的进程名
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void setPushNotificationBuilder() {
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
