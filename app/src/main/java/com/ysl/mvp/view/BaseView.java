package com.ysl.mvp.view;

import com.ysl.mvp.presenter.BasePresenter;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
