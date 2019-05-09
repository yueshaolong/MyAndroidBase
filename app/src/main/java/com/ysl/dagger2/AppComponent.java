package com.ysl.dagger2;

import android.content.SharedPreferences;

import com.ysl.MyApp;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {
    SharedPreferences provideSharedPreferences();
    MyApp provideApplication();
}
