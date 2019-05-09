package com.ysl.dagger2;

import android.app.Activity;

import com.ysl.dagger2.view.LoginActivity;
import com.ysl.dagger2.view.SingletonTestActivity;

import javax.inject.Singleton;

import dagger.Component;

//第一步 添加@Component
//第二步 添加module
//@MyScope
@Singleton
@Component(modules = {LoginModel.class}, dependencies = {AppComponent.class})
public interface LoginComponent {
    //第三步  写一个方法 绑定Activity /Fragment
    void inject(LoginActivity activity);
    void inject(SingletonTestActivity singletonTest);
}
