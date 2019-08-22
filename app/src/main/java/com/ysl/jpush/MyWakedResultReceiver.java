package com.ysl.jpush;

import android.content.Context;

import cn.jpush.android.service.WakedResultReceiver;

public class MyWakedResultReceiver extends WakedResultReceiver {

    @Override
    public void onWake(int i) {
        super.onWake(i);
    }

    @Override
    public void onWake(Context context, int i) {
        super.onWake(context, i);
    }
}
