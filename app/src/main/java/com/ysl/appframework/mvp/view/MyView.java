package com.ysl.appframework.mvp.view;

import com.ysl.appframework.mvp.model.UserInfo;
import com.ysl.appframework.mvp.presenter.MyPresenter;

public interface MyView extends BaseView <MyPresenter>{
    void setDataToView(UserInfo userInfo);
}
