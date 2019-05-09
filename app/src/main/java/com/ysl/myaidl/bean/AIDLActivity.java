package com.ysl.myaidl.bean;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ysl.myaidl.ICatService;
import com.ysl.myandroidbase.R;

public class AIDLActivity extends AppCompatActivity {
    public static final String TAG = "AIDLActivity";
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
                Intent intent = new Intent(AIDLActivity.this, MyAIDLService.class);

//                Intent intent = new Intent();
//                intent.setAction("com.ysl.myandroidbase.MyAIDLService");
//                intent.setPackage("com.ysl.myandroidbaseserver");
                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });

    }

    private void setTv() {
        try {
            Cat cat = iCatService.getCat();
            int catCount = iCatService.getCatCount();
            tv.setText(cat.name+"_"+cat.price+"/n"+catCount);

            Cat t1 = new Cat("t", 1);
            Cat cat1 = iCatService.addCatIn(t1);
            Log.d(TAG, "In-t1->"+t1.toString()+"/"+t1.hashCode());//In-t1->Cat{name='t', age=0, price=1}/71956984
            Log.d(TAG, "In-->"+cat1.toString()+"/"+cat1.hashCode());//In-->Cat{name='In', age=0, price=100}/192491217

            Cat t2 = new Cat("t", 1);
            Cat cat2 = iCatService.addCatOut(t2);
            Log.d(TAG, "Out-t2->"+t2.toString()+"/"+t2.hashCode());//Out-t2->Cat{name='Out', age=0, price=101}/92458294
            Log.d(TAG, "Out-->"+cat2.toString()+"/"+cat2.hashCode());//Out-->Cat{name='Out', age=0, price=101}/16516151
            //<因为使用了out,把我们原来的对象修改了;虽然两个对象的属性是一样的，但客户端和服务端还是两个不同的对象的，因为存在于两个不同的进程中>

            Cat t3 = new Cat("t", 1);
            Cat cat3 = iCatService.addCatInout(t3);
            Log.d(TAG, "Inout-t3->"+t3.toString()+"/"+t3.hashCode());//Inout-t3->Cat{name='Inout', age=0, price=102}/237026724
            Log.d(TAG, "Inout-->"+cat3.toString()+"/"+cat3.hashCode());//Inout-->Cat{name='Inout', age=0, price=102}/244377357
            //<因为使用了out,把我们原来的对象修改了;虽然两个对象的属性是一样的，但客户端和服务端还是两个不同的对象的，因为存在于两个不同的进程中>

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
