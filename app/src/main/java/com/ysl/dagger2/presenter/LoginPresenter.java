package com.ysl.dagger2.presenter;

import android.content.Context;
import android.widget.Toast;

import com.ysl.dagger2.model.User;
import com.ysl.dagger2.view.ICommonView;

public class LoginPresenter {
    ICommonView iView;


    public LoginPresenter(ICommonView iView){
        this.iView = iView;
    }

    public void login(User user){
        Context mContext = iView.getContext();
        Toast.makeText(mContext,"login......",Toast.LENGTH_SHORT).show();
    }
}
