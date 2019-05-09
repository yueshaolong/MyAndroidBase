package com.ysl.myandroidbase.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService {
    public static final String TAG = "MyIntentService";

    public MyIntentService() {
        super(TAG);
        Log.d(TAG, "MyIntentService() is invoke "+Thread.currentThread().getId()+"="+Thread.currentThread().getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate is invoke "+Thread.currentThread().getId()+"="+Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand is invoke "+Thread.currentThread().getId()+"="+Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //做耗时操作
        Log.d(TAG, "onHandleIntent is invoke "+Thread.currentThread().getId()+"="+Thread.currentThread().getName());
        int age = intent.getIntExtra("age",0);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "age = "+age);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy is invoke "+Thread.currentThread().getId()+"="+Thread.currentThread().getName());
    }
}
