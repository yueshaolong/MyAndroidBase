package com.ysl.mvp.view;

import com.ysl.mvp.model.UserInfo;
import com.ysl.mvp.presenter.MyPresenter;

public interface MyView extends BaseView <MyPresenter>{
    void setDataToView(UserInfo userInfo);
}
