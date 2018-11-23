package com.ysl.myandroidbase;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.ysl.myandroidbase.service.ForegroundService;
import com.ysl.myandroidbase.service.MyIntentService;
import com.ysl.myandroidbase.service.MyService;
import com.ysl.myandroidbase.service.MyService.CallBack;
import com.ysl.myandroidbase.service.MyService.MyBinder;

public class ServiceActivity extends AppCompatActivity{
    public static final String TAG = "ServiceActivity";
    private Intent intent;
    private MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Log.d(TAG, "onCreate is invoke");

        intent = new Intent(ServiceActivity.this, MyService.class);
//        intent.putExtra("data", "ysl");
        findViewById(R.id.startService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
                if (onActivityDataChangedListener != null) {
                    onActivityDataChangedListener.onActivityDataChanged("哈哈哈，activity的数据变了。activity");
                } else {
                    throw new IllegalArgumentException("activity must invoke setOnActivityDataChangedListener()");
                }
            }
        });
        findViewById(R.id.stopService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
        findViewById(R.id.bindService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.unbindService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });

        findViewById(R.id.intentService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ServiceActivity.this, MyIntentService.class);
                intent.putExtra("age", 15);
                startService(intent);
            }
        });
        findViewById(R.id.foregroundService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ServiceActivity.this, ForegroundService.class);
                startService(intent);
            }
        });
        findViewById(R.id.stopForegroundService).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState is invoke");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState is invoke");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart is invoke");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart is invoke");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume is invoke");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause is invoke");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop is invoke");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy is invoke");
    }

    private OnActivityDataChangedListener onActivityDataChangedListener;

    public interface OnActivityDataChangedListener {
        void onActivityDataChanged(String string);
    }

    public void setOnActivityDataChangedListener(OnActivityDataChangedListener onActivityDataChangedListener) {
        this.onActivityDataChangedListener = onActivityDataChangedListener;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected is invoke");
            MyBinder myBinder = (MyBinder) service;
//            myBinder.setData("bindService启动服务了");
            myBinder.getMyService().setDataCallBack(new CallBack() {
                @Override
                public void dataCallBack(String data) {
                    Log.d(TAG, "setDataCallBack is invoke   "+data);
                }
            });
            myBinder.setActivity(ServiceActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected is invoke");
        }
    };
}
