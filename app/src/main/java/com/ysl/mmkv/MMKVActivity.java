package com.ysl.mmkv;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tencent.mmkv.MMKV;
import com.ysl.myandroidbase.R;

import java.util.Arrays;
import java.util.HashSet;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MMKVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkv);
        Button button = findViewById(R.id.btn);
        Button button2 = findViewById(R.id.btn2);

        MMKVActivityPermissionsDispatcher.needWithPermissionCheck(this);

        final SharedPreferences sp = getSharedPreferences("a", MODE_PRIVATE);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                long start = System.currentTimeMillis();
                for (int i = 0; i < 100; i++){
                    sp.edit().putInt(""+i, i).commit();
                }
//                long end = System.currentTimeMillis();
//                System.out.println("sp耗时："+(end-start));
            }
        });

        //第一步；两种方式都可以
        String initialize = MMKV.initialize(this);
//        String initialize = MMKV.initialize(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/app/");//指定想要存储得路径
        //第二步
        final MMKV preferences = MMKV.defaultMMKV();

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mmkv.clearAll();
                long start = System.currentTimeMillis();
//                for (int i = 0; i < 10000; i++){
                    mmkv.encode("a", 10);
                    mmkv.encode("b", 100);
//                }
//                long end = System.currentTimeMillis();
//                System.out.println("mmkv耗时："+(end-start));
                String[] keys = mmkv.allKeys();
                System.out.println("keys:"+ Arrays.toString(keys));*/

//                MMKV preferences = MMKV.mmkvWithID("myData");
                preferences.importFromSharedPreferences(sp);
                sp.edit().clear().commit();
                // just use it as before
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("bool", true);
                editor.putInt("int", Integer.MIN_VALUE);
                editor.putLong("long", Long.MAX_VALUE);
                editor.putFloat("float", -3.14f);
                editor.putString("string", "hello, imported");
                HashSet<String> set = new HashSet<String>();
                set.add("W"); set.add("e"); set.add("C"); set.add("h"); set.add("a"); set.add("t");
                editor.putStringSet("string-set", set);
                // commit() is not needed any more
                //editor.commit();
                System.out.println("keys:"+ Arrays.toString(preferences.allKeys()));
            }
        });
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void need() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MMKVActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void show(final PermissionRequest request) {
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denied() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void never() {
    }
}
