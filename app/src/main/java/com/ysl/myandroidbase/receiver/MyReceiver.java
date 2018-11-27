package com.ysl.myandroidbase.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Toast.makeText(context, "收到广播："+intent.getAction(), Toast.LENGTH_SHORT).show();
        System.out.println("收到广播："+intent.getAction());
    }
}
