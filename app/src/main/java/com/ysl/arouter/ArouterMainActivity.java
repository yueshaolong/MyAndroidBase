package com.ysl.arouter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysl.myandroidbase.R;

/**
 * 阿里的组件化工具
 */
@Route(path = "/main/ArouterMainActivity")
public class ArouterMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
        findViewById(R.id.btnm).setOnClickListener(v -> ARouter.getInstance().build("/modulea/ArouterActivityA").navigation());
    }
}
