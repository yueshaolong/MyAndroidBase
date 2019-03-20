package com.ysl.mvp.presenter;

import com.ysl.mvp.model.Callback;
import com.ysl.mvp.model.UserInfo;

public class MyPresenter implements BasePresenter {
    @Override
    public void onDestroy() {

    }

    public void  getUserInfo(String uid, Callback<UserInfo> callback) {
        System.out.println("present------>"+uid);
        UserInfo userInfo = new UserInfo(uid.length(), uid);
        callback.onCallBack(userInfo);
    }
}
