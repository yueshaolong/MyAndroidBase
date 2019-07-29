package com.example.news;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;

public class NewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        findViewById(R.id.news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC.obtainBuilder("CCActivity")
                        .setActionName("back")
                        .build()
                        .callAsync();
            }
        });
    }
}
