package com.ysl.myandroidbase.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ysl.myaidl.ICatService;
import com.ysl.myaidl.bean.Cat;
import com.ysl.myandroidbase.R;
import com.ysl.myandroidbase.service.MyAIDLService;

public class Main2Activity extends AppCompatActivity {
    public static final String TAG = "Main2Activity";
    private ICatService iCatService;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = findViewById(R.id.tv);
        findViewById(R.id.bind).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MyAIDLService.class);
                /*Intent intent = new Intent();
                intent.setAction("com.ysl.myandroidbase.MyAIDLService");
                intent.setPackage("com.ysl.myandroidbaseserver");*/

                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });

    }

    private void setTv() {
        try {
            Cat cat = iCatService.getCat();
            int catCount = iCatService.getCatCount();
            tv.setText(cat.name+"_"+cat.price+"/n"+catCount);

            Cat cat1 = iCatService.addCatIn(new Cat("t", 1));
            Log.d(TAG, "In-->"+cat1.toString());
            Cat cat2 = iCatService.addCatOut(new Cat("t", 1));
            Log.d(TAG, "Out-->"+cat2.toString());
            Cat cat3 = iCatService.addCatInout(new Cat("t", 1));
            Log.d(TAG, "Inout-->"+cat3.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCatService = ICatService.Stub.asInterface(service);
            setTv();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
