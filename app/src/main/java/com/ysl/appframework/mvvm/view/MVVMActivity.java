package com.ysl.appframework.mvvm.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ysl.appframework.mvvm.model.Callback;
import com.ysl.appframework.mvvm.model.UserInfo;
import com.ysl.appframework.mvvm.viewmodel.SampleViewModel;
import com.ysl.myandroidbase.R;
import com.ysl.myandroidbase.databinding.ActivitySampleMvvmBinding;

public class MVVMActivity extends AppCompatActivity{
    Button button;
    EditText editText;
    TextView tvAge,tvName;
    private SampleViewModel mSampleViewModel;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySampleMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sample_mvvm);
        mSampleViewModel = new SampleViewModel(binding);
        mSampleViewModel.mViewDataBinding.setMvvmActivity(this);

        button = findViewById(R.id.btn);
        editText = findViewById(R.id.edt);
        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


    }

    private void setDataToView(String input) {
        mSampleViewModel.getUserInfo(input, new Callback<UserInfo>() {
            @Override
            public void onCallBack(UserInfo userInfo) {
                mSampleViewModel.mViewDataBinding.setUserInfo(userInfo);
            }
        });
    }

    public void set(String input){
        System.out.println("点击设置"+input);
        imm.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(),0);
        setDataToView(input);
    }

}
