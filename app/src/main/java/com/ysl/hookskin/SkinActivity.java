package com.ysl.hookskin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ysl.myandroidbase.R;

public class SkinActivity extends AppCompatActivity {

    private Button btn;
    private SkinFactory skinFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        skinFactory = new SkinFactory();
        skinFactory.setDelegate(getDelegate());
        LayoutInflater.from(this).setFactory2(skinFactory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSkin();
            }
        });
    }

    private void changeSkin() {
        skinFactory.changeSkin();
    }
}
