package com.ysl.dagger2.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.ysl.MyApp;
import com.ysl.dagger2.DaggerLoginComponent;
import com.ysl.dagger2.model.MySingleton;
import com.ysl.dagger2.model.U;
import com.ysl.dagger2.model.User;
import com.ysl.dagger2.presenter.LoginPresenter;
import com.ysl.myandroidbase.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ICommonView{
    @BindView(R.id.btn_login)
    Button btn;
    /***
     * 第二步  使用Inject 注解，获取到user对象的实例
     */
    @Inject
    User user;
    @Inject
    U u;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    MyApp myApp;
    @Inject
    LoginPresenter loginPresenter;
    @Inject
    MySingleton mySingleton;
    @Inject
    MySingleton mySingleton1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        /***
         * 第一步 添加依赖关系
         */
        //DaggerLoginComponent.create().inject(this);//方法一
        //DaggerLoginComponent.builder().build().inject(this);//方法二
        //DaggerLoginComponent.builder().loginModel(new LoginModel()).build().inject(this);//方法三
        //上面两个方法是完全等价的；可以查看源码：
        /*public static Builder builder() {
            return new Builder();
        }
        public static LoginComponent create() {
            return new Builder().build();
        }*/
        //.create就是.builder().build()的一步调用
        //TODO 注意：这三种方法只适合@model类中有无参构造方法时使用；没有构造其实就是有一个无参构造；

//        DaggerLoginComponent.builder().loginModel(new LoginModel(this)).build().inject(this);
        //TODO 注意：当@model类中没有无参构造时，必须使用此方法，为model实例化

        DaggerLoginComponent.builder().appComponent(MyApp.getAppComponent()).build().inject(this);
    }


    @OnClick(R.id.btn_login) void login(){
        user.show();//u:ysl
        System.out.println(u.getMessage());//ysl

        System.out.println(sharedPreferences);//android.app.SharedPreferencesImpl@5c49587

        System.out.println(myApp);//com.ysl.MyApp@7acfcb4 TODO Application和所有调用getApplicationContext()是同一个对象
        System.out.println(getApplicationContext());//com.ysl.MyApp@7acfcb4

        System.out.println(this);//com.ysl.dagger2.view.LoginActivity@914882e
        System.out.println(getContext());//com.ysl.dagger2.view.LoginActivity@914882e//TODO this和getContext()也是同一个对象

        loginPresenter.login(user);

        System.out.println(mySingleton);//单例模式了，两个对象是一样的
        System.out.println(mySingleton1);

        startActivity(new Intent(this, SingletonTestActivity.class));
    }

    @Override
    public Context getContext() {
        return this;
    }

}
