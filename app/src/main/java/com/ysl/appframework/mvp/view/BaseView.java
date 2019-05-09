package com.ysl.appframework.mvp.view;

import com.ysl.appframework.mvp.presenter.BasePresenter;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
