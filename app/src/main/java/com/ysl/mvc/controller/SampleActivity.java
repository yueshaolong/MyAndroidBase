package com.ysl.mvc.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ysl.mvc.model.Callback;
import com.ysl.mvc.model.MyModel;
import com.ysl.mvc.model.UserInfo;
import com.ysl.myandroidbase.R;

public class SampleActivity extends AppCompatActivity {
    private MyModel myModel;
    Button button;
    EditText editText;
    TextView tvAge,tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);

        button = findViewById(R.id.btn);
        editText = findViewById(R.id.edt);
        tvName = findViewById(R.id.tvName);
        tvAge = findViewById(R.id.tvAge);

        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        myModel=new MyModel();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfo(editText.getText().toString());
                imm.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(),0);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myModel.onDestroy();
    }

    /**
     * 获取用户信息
     * @param uid
     */
    private void getUserInfo(String uid) {
        myModel.getUserInfo(uid, new Callback<UserInfo>() {
            @Override
            public void onCallBack(UserInfo userInfo) {
                setDataToView(userInfo);
            }
        });
    }

    /**
     * 设置用户信息到view
     */
    private void setDataToView(UserInfo userInfo) {
        tvAge.setText(""+userInfo.getAge());
        tvName.setText(userInfo.getName());
    }
}
