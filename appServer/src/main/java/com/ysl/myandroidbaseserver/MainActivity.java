package com.ysl.myandroidbaseserver;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myaidl.ICatService;
import com.ysl.myaidl.ICatService.Stub;
import com.ysl.myaidl.MyAIDLService;
import com.ysl.myaidl.bean.Cat;


public class MainActivity extends AppCompatActivity {
    private ICatService iCatService;
    private TextView tv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        Intent intent = new Intent(MainActivity.this, MyAIDLService.class);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ICatService iCatService = Stub.asInterface(service);
            try {
                Cat cat = iCatService.getCat();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
