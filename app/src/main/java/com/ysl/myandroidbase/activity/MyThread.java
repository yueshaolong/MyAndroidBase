package com.ysl.myandroidbase.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import static com.ysl.myandroidbase.activity.HandleActivity.MSG_CODE2;

public class MyThread extends Thread {

    public static final String TAG = "MyThread";

    @Override
    public void run() {
        super.run();
        try {
            Log.e("MyThread子线程Name:", Thread.currentThread().getName());

            //在子线程运行
            Thread.sleep(2000);
            Looper.prepare();
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 0:
                            Log.d(TAG, "收到消息  线程："+Thread.currentThread().getName());
                            break;
                    }
                }
            };
            //完成后，发送消息
            handler.sendEmptyMessage(0);
            Looper.loop();

            /*Message message = Message.obtain();
            message.what = MSG_CODE2;
            Bundle bundle = new Bundle();
            bundle.putString("data", "sdfbsdkjgsdklj");
            message.setData(bundle);
            HandleActivity.handler.sendMessage(message);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
