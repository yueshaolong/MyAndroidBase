package com.ysl.myandroidbase.myview;

import android.os.Bundle;
import android.view.View;

import com.allen.library.SuperTextView;
import com.ysl.myandroidbase.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SuperTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supertextview);

        SuperTextView stv = findViewById(R.id.stv1);
        stv.setCenterTextIsBold(true);

        SuperTextView stv3 = findViewById(R.id.stv3);
    }
}
