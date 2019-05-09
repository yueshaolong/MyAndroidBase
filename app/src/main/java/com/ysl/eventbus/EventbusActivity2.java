package com.ysl.eventbus;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.ysl.myandroidbase.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class EventbusActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setMessage(""+ new Random().nextInt(10));
                EventBus.getDefault().post(messageEvent);
                finish();
            }
        });
    }
}
