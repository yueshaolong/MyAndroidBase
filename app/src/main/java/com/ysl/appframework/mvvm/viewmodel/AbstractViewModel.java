package com.ysl.appframework.mvvm.viewmodel;

import androidx.databinding.ViewDataBinding;

public abstract class AbstractViewModel<T extends ViewDataBinding> implements BaseViewModel {
    public T mViewDataBinding;
    public AbstractViewModel(T viewDataBinding) {
        this.mViewDataBinding = viewDataBinding;
    }

    @Override
    public void onDestroy() {
        mViewDataBinding.unbind();
    }
}