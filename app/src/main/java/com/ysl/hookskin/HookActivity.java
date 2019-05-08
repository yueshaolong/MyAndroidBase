package com.ysl.hookskin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ysl.myandroidbase.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class HookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv1);

        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击事件触发！");
                Toast.makeText(HookActivity.this, "点击事件触发！", Toast.LENGTH_LONG).show();
            }
        });
        HookClickHelper.hook(this, tv);

    }


}