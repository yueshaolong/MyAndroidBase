package com.ysl.dagger2;

import android.content.Context;
import android.content.SharedPreferences;

import com.ysl.MyApp;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private MyApp application;

    public AppModule(MyApp application) {
        this.application = application;
    }

    //提供全局的sp对象
    @Provides
    SharedPreferences provideSharedPreferences(){
        return application.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    //提供全局的Application对象
    @Provides
    MyApp provideApplication(){
        return application;
    }

    //你还可以提供更多.......
}
