package com.ysl.myandroidbase;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);
        TextView tv2 = findViewById(R.id.tv2);
        tv2.setText(name+"_"+age);
        String name1 = getIntent().getStringExtra("name1");
        int age1 = getIntent().getIntExtra("age1", 0);
        TextView tv3 = findViewById(R.id.tv3);
        tv3.setText(name1+"_"+age1);
    }

}
