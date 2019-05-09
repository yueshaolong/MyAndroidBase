package com.ysl.myandroidbase.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

public class CancelNoticeService extends Service {
    public static final String TAG = "CancelNoticeService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
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
        startForeground(ForegroundService.NOTICE_ID, new Notification());
        stopForeground(true);
        stopSelf();//停止此服务减少资源消耗
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy is invoke");
    }
}