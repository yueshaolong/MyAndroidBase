package com.ysl.myandroidbase.leak;

import android.content.Context;

public class MyLeak {
    private static MyLeak myLeak;
    private Context context;

    private MyLeak(){}
    public static MyLeak getInstance(Context context){
        if (myLeak == null)
            myLeak = new MyLeak();
        myLeak.context = context.getApplicationContext();
        return myLeak;
    }
}
