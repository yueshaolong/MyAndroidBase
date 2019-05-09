package com.ysl.dagger2.view;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.ysl.dagger2.model.User;
import com.ysl.dagger2.presenter.LoginPresenter;
import com.ysl.myandroidbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ICommonView{
    @BindView(R.id.btn_login)
    Button btn;
    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);
    }


    @OnClick(R.id.btn_login) void login(){
        loginPresenter.login(new User());
    }

    @Override
    public Context getContext() {
        return this;
    }
}
