package com.ysl.cc;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.ysl.myandroidbase.R;

/**
 * cc组件化工具
 */
public class CCActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc);
        findViewById(R.id.btn_cc).setOnClickListener(v -> {
            //同步调用，直接返回结果
//            CCResult result = CC.obtainBuilder("NewsActivity")
//                    .setActionName("showNews")
//                    .build()
//                    .call();
            //或 异步调用，不需要回调结果
//            String callId = CC.obtainBuilder("NewsActivity")
//                    .setActionName("showNews")
//                    .build()
//                    .callAsync();
            //或 异步调用，在子线程执行回调
//            String callId1 = CC.obtainBuilder("NewsActivity")
//                    .setActionName("showNews")
//                    .build()
//                    .callAsync(new IComponentCallback() {
//                        @Override
//                        public void onResult(CC cc, CCResult result) {
//                            //此onResult在子线程中运行
//                        }
//                    });
            //或 异步调用，在主线程执行回调
            String callId2 = CC.obtainBuilder("NewsActivity")
                    .setActionName("showNews")
                    .build()
                    .callAsyncCallbackOnMainThread(new IComponentCallback() {
                        @Override
                        public void onResult(CC cc, CCResult result) {
                            //此onResult在主线程中运行
                            String toast = "跳转 " + (result.isSuccess() ? "success" : "failed");
                            Toast.makeText(CCActivity.this, toast, Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
