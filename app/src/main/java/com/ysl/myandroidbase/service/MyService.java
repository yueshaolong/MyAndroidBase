package com.ysl.myandroidbase.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ysl.myandroidbase.ServiceActivity;

public class MyService extends Service {
    public static final String TAG = "MyService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind is invoke");
//        String data = intent.getStringExtra("data");
//        Log.d(TAG, "onBind is invoke--->"+data);
          return new MyBinder();
    }

    public class MyBinder extends Binder{
        public void setData(String data){
            Toast.makeText(MyService.this, data, Toast.LENGTH_SHORT).show();
        }
        public MyService getMyService(){
            return MyService.this;
        }
        public void setActivity(Activity activity){
            ((ServiceActivity)activity).setOnActivityDataChangedListener(new ServiceActivity.OnActivityDataChangedListener() {
                @Override
                public void onActivityDataChanged(String string) {
                    Log.d(TAG, "onActivityDataChanged is invoke  "+string);
                }
            });
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate is invoke");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy is invoke");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand is invoke");
//        String data = intent.getStringExtra("data");
//        Log.d(TAG, "onStartCommand is invoke--->"+data);
        if (callback != null) {
            callback.dataCallBack("哈哈哈，service的数据变了。service");
        } else {
            throw new IllegalArgumentException("activity must invoke setOnActivityDataChangedListener()");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind is invoke");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind is invoke");
    }

    public interface CallBack{
        void dataCallBack(String data);
    }
    public void setDataCallBack(CallBack callBack){
        this.callback = callBack;
    }
    private CallBack callback;
}
