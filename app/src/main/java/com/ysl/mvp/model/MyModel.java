package com.ysl.mvp.model;

public class MyModel implements BaseModel {
    @Override
    public void onDestroy() {

    }

    public void  getUserInfo(String uid, Callback<UserInfo> callback) {
        UserInfo userInfo = new UserInfo(uid.length(), uid);
        callback.onCallBack(userInfo);
    }
}
