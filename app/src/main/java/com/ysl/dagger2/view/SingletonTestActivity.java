package com.ysl.dagger2.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.MyApp;
import com.ysl.dagger2.DaggerLoginComponent;
import com.ysl.dagger2.model.MySingleton;
import com.ysl.myandroidbase.R;

import javax.inject.Inject;

public class SingletonTestActivity  extends AppCompatActivity {
    @Inject
    MySingleton mySingleton;
    @Inject
    MySingleton mySingleton1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DaggerLoginComponent.builder().appComponent(MyApp.getAppComponent()).build().inject(this);

        show();
    }

    public void show(){
        System.out.println(mySingleton);
        System.out.println(mySingleton1);
    }
}
