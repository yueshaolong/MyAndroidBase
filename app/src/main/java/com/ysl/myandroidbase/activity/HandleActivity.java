package com.ysl.myandroidbase.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class HandleActivity extends AppCompatActivity {
    public static final int MSG_CODE = 0;
    public static final int MSG_CODE2 = 1;
    public static final String TAG = "HandleActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doThread();
        new MyThread().start();
    }

    //启动一个耗时线程
    public void doThread() {
        new Thread() {
            public void run() {
                try {
                    Log.e("HandleActivity子线程Name:", Thread.currentThread().getName());
                    //在子线程运行
                    Thread.sleep(2000);
                    //完成后，发送消息
                    Message message = Message.obtain();
                    message.what = MSG_CODE;
                    Bundle bundle = new Bundle();
                    bundle.putString("data", "hi");
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CODE:
                    Log.d(TAG, msg.getData().getString("data")+"  线程："+Thread.currentThread().getName());
                    break;
                case MSG_CODE2:
                    Log.d(TAG, msg.getData().getString("data")+"  线程："+Thread.currentThread().getName());
                    break;
            }
        }
    };
}
