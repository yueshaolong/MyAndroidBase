package com.ysl.myandroidbase.handler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.service.carrier.CarrierService;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ysl.myandroidbase.R;

public class HandleActivity extends AppCompatActivity {
    public static final int MSG_CODE = 0;
    public static final int MSG_CODE2 = 1;
    public static final String TAG = "HandleActivity";
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv1);
//        doThread();
//        new MyThread().start();
//        asyncTask.execute();
//        tv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                asyncTask.cancel(false);
//            }
//        });

        HandlerThread handlerThread = new HandlerThread("ysl");
        handlerThread.start();
        Handler mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Log.e("handlerThread子线程Name:", Thread.currentThread().getId()+"="+Thread.currentThread().getName());
                        break;
                }
            }
        };
        mHandler.sendEmptyMessage(0);
        Log.e("handlerThread主线程Name:", Thread.currentThread().getId()+"="+Thread.currentThread().getName());
    }

    private AsyncTask asyncTask = new AsyncTask() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute调用了");
            tv.setText("准备加载");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Log.d(TAG, "doInBackground调用了");
            try {
                int count = 0;
                int length = 1;
                while (count < 99) {

                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate调用了");
            tv.setText("loading..." + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d(TAG, "onPostExecute调用了");
            tv.setText("加载完成");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(TAG, "onCancelled调用了");
            tv.setText("取消加载");
        }

    };

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
