package com.ysl.appframework.mvvm.viewmodel;

import com.ysl.appframework.mvvm.model.Callback;
import com.ysl.appframework.mvvm.model.UserInfo;
import com.ysl.myandroidbase.databinding.ActivitySampleMvvmBinding;

public class SampleViewModel extends AbstractViewModel<ActivitySampleMvvmBinding> {

    public SampleViewModel(ActivitySampleMvvmBinding viewDataBinding) {
        super(viewDataBinding);
    }

    public  void getUserInfo(String uid, Callback<UserInfo> callback) {
        //从网络或缓存获取信息
        int age = (uid == null || uid.length() == 0) ? 100 : uid.length();
        UserInfo userInfo = new UserInfo(age, uid);
        callback.onCallBack(userInfo);
    }
}