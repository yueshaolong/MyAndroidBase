package com.ysl.eventbus;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ysl.myandroidbase.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventbusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);


        findViewById(R.id.tv1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
//                        MessageEvent messageEvent = new MessageEvent();
//                        messageEvent.setMessage("haha");
//                        EventBus.getDefault().post(messageEvent);
                        startActivity(new Intent(EventbusActivity.this, EventbusActivity2.class));
                    }
                }).start();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(MessageEvent messageEvent){
        ((TextView) findViewById(R.id.tv1)).setText(messageEvent.getMessage());
        Toast.makeText(this, messageEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
