package com.ysl.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ysl.mvp.model.Callback;
import com.ysl.mvp.model.UserInfo;
import com.ysl.mvp.presenter.MyPresenter;
import com.ysl.myandroidbase.R;

public class MVPActivity extends AppCompatActivity implements MyView{
    Button button;
    EditText editText;
    TextView tvAge,tvName;
    private MyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        setPresenter(new MyPresenter());

        button = findViewById(R.id.btn);
        editText = findViewById(R.id.edt);
        tvName = findViewById(R.id.tvName);
        tvAge = findViewById(R.id.tvAge);

        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(),0);
                presenter.getUserInfo(editText.getText().toString(), new Callback<UserInfo>() {
                    @Override
                    public void onCallBack(UserInfo userInfo) {
                        setDataToView(userInfo);
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setPresenter(MyPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setDataToView(UserInfo userInfo) {
        tvAge.setText(String.valueOf(userInfo.getAge()));
        tvName.setText(userInfo.getName());
    }

    public static void main(String[] args) {
        String str=null;
        str=String.format("Hi,%s", "王力");
        System.out.println(str);
        str=String.format("Hi,%s:%s.%s", "王南","王力","王张");
        System.out.println(str);
        System.out.printf("字母a的大写是：%c %n", 'A');
        System.out.printf("3>7的结果是：%b %n", 3>7);
        System.out.printf("100的一半是：%d %n", 100/2);
        System.out.printf("100的16进制数是：%x %n", 100);
        System.out.printf("100的8进制数是：%o %n", 100);
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50*0.85);
        System.out.printf("上面价格的16进制数是：%a %n", 50*0.85);
        System.out.printf("上面价格的指数表示：%e %n", 50*0.85);
        System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50*0.85);
        System.out.printf("上面的折扣是%d%% %n", 85);
        System.out.printf("字母A的散列码是：%h %n", 'A');
    }
}
