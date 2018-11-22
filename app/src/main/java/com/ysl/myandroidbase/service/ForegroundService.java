package com.ysl.myandroidbase.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ysl.myandroidbase.R;
import com.ysl.myandroidbase.ServiceActivity;
import com.ysl.myandroidbase.service.CancelNoticeService;

public class ForegroundService extends Service {
    public static final String TAG = "ForegroundService";
    public static final int NOTICE_ID = 100;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind is invoke");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate is invoke");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand is invoke");
        //使用startForeground方法开启前台服务
        startForeground(NOTICE_ID, getNotification());

        startService(new Intent(this, CancelNoticeService.class));

        return super.onStartCommand(intent, flags, startId);
    }

    private Notification getNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ServiceActivity.class), 0);
        return new Notification.Builder(this)
                .setContentIntent(pendingIntent)
                .setContentTitle("设置下拉列表里的标题")
                .setContentText("设置要显示的内容")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis() - 24*60*60*1000)//设置通知上的时间，now,1d...
                .setShowWhen(true)//设置是否显示时间
                .setAutoCancel(true)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        Log.d(TAG, "onDestroy is invoke");
    }
}
