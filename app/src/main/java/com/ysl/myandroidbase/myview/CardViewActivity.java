package com.ysl.myandroidbase.myview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;
import com.ysl.util.ToastUtils;

public class CardViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        Button share = findViewById(R.id.btn_share);
        Button ex = findViewById(R.id.btn_ex);

        share.setOnClickListener(v -> {
            ToastUtils.showToast(this, "share");
        });
    }
}
