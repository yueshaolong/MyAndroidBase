package com.ysl.myandroidbase.leak;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ysl.myandroidbase.R;

import java.lang.ref.WeakReference;

public class LeakActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv1);

        Message msg = Message.obtain();
        my my = new my();
        my.sendMessageDelayed(msg,1000*60*5);
        MyLeak.getInstance(this);
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private Handler handler2 = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    public static class  my extends  Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    MyHandler ttsHandler = new MyHandler(this);
    static class MyHandler extends Handler {
                WeakReference<LeakActivity> mActivity;

                 MyHandler(LeakActivity activity) {
                    mActivity = new WeakReference<LeakActivity>(activity);
                 }

                 @Override
                 public void handleMessage(Message msg) {
                     LeakActivity theActivity = mActivity.get();
                     switch (msg.what) {
                     case 0:
                         break;
                     }
                 }
    };

}
