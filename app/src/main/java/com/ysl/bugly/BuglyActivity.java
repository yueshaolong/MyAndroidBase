package com.ysl.bugly;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.bugly.crashreport.CrashReport;
import com.ysl.myandroidbase.R;
import com.ysl.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuglyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugly);
        ButterKnife.bind(this);
    }
    
    @OnClick({R.id.crash, R.id.testcrash,R.id.anrcrash})
    public void click(View view){
        switch (view.getId()) {
            case R.id.crash:
                CrashReport.testJavaCrash();
                break;
            case R.id.testcrash:
                String name = null;
                if (name.startsWith("/")) {
                    System.out.println("sdf");
                }
                break;
            case R.id.anrcrash:
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtils.showToast(this, "睡吧");
                break;
            default:
                
                break;
        }
    }
}
