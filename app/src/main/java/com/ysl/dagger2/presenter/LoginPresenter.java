package com.ysl.dagger2.presenter;

import android.widget.Toast;

import com.ysl.MyApp;
import com.ysl.dagger2.model.User;

public class LoginPresenter {

    public LoginPresenter(){
    }

    public void login(User user){
        Toast.makeText(MyApp.getAppComponent().provideApplication(),"login......"+user.toString(),Toast.LENGTH_SHORT).show();
    }
}
