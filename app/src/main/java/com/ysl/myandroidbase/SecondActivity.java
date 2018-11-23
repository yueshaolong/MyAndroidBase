package com.ysl.myandroidbase;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ysl.myandroidbase.bean.Cat;

public class SecondActivity extends Activity {
    public static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate is invoke  ");

        /*findViewById(R.id.tv2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
        /*String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0);
        TextView tv2 = findViewById(R.id.tv2);
        tv2.setText(name+"_"+age);
        String name1 = getIntent().getStringExtra("name1");
        int age1 = getIntent().getIntExtra("age1", 0);
        TextView tv3 = findViewById(R.id.tv3);
        tv3.setText(name1+"_"+age1);*/

        Cat cat = getIntent().getParcelableExtra("cat");
        TextView tv3 = findViewById(R.id.tv3);
        tv3.setText(cat.name+"_"+cat.age);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent is invoke");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState is invoke");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState is invoke");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart is invoke  ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart is invoke  ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume is invoke  ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause is invoke");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop is invoke");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy is invoke");
    }
}
