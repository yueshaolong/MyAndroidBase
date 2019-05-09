package com.ysl.myandroidbase.viewstub;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.ysl.myandroidbase.R;

public class MyViewStubActivity extends AppCompatActivity {
    private ViewStub viewStub;
    private TextView textView;
    private View inflate;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myviewstub);
        viewStub = findViewById(R.id.vs);
        //textView  = (TextView) findViewById(R.id.hello_tv);空指针,因为viewstub没有inflate
    }
    public  void inflate(View view){
        if (inflate == null) {//inflate只会进行一次，当第二次调用的时候，就会抛异常；也可以try catch进行处理
            inflate = viewStub.inflate();

            constraintLayout = findViewById(R.id.inflatedStart);
            System.out.println(constraintLayout);

            System.out.println("viewStub-------->"+viewStub);
            textView  = viewStub.findViewById(R.id.hello_tv);//获取到的textview是空的；
            System.out.println("viewStub textView-------->"+textView);//null

            textView  = constraintLayout.findViewById(R.id.hello_tv);
            System.out.println("constraintLayout textView-------->"+textView);

            textView  = findViewById(R.id.hello_tv);
            System.out.println("textView-------->"+textView);
        }
    }
    public void setData(View view){
        if (constraintLayout != null) {
            textView = constraintLayout.findViewById(R.id.hello_tv);
            textView.setText("HAVE DATA !!!");
        }
    }
    public void hide(View view){
        viewStub.setVisibility(View.GONE);
//        if (constraintLayout != null){
//            constraintLayout.setVisibility(View.GONE);
//        }
    }
    public void show(View view){
        viewStub.setVisibility(View.VISIBLE);
//        if (constraintLayout != null){
//            constraintLayout.setVisibility(View.VISIBLE);
//        }
    }
}
