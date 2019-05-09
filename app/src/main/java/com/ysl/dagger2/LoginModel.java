package com.ysl.dagger2;

import com.ysl.dagger2.model.MySingleton;
import com.ysl.dagger2.model.U;
import com.ysl.dagger2.model.User;
import com.ysl.dagger2.presenter.LoginPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//第一步 添加@Module 注解
@Module
public class LoginModel {
    //当model类的构造有参数时，activity的调用必须使用
    //DaggerLoginComponent.builder().loginModel(new LoginModel(this)).build().inject(this);
    //其它两种方法，DaggerLoginComponent.create().inject(this);编译器生成的文件没有create方法，编译报错
    // DaggerLoginComponent.builder().build().inject(this);查看编译器生成的代码有loginmodel的非空检查，编译期不会报错，但app运行时报空指针
//    public LoginModel(Activity activity) {
//    }
//    public LoginModel() {
//    }


    //第二步 使用Provider 注解 实例化对象
//    @Provides
//    User provideUser(){
////        return new User();//无参的
//        return new User("ysl", "123");//有参，基本类型的参数
//    }

    @Provides
    User provideUser(U u){
        return new User(u);//有参，非基本类型的参数
    }
    @Provides
    U provideU(){
        return new U("ysl");
    }

    @Provides
    LoginPresenter provideLoginPresenter(){
        return new LoginPresenter();
    }

    @Provides
    @Singleton
//    @MyScope
    MySingleton provideMySingleton(){
        return new MySingleton();
    }
}
