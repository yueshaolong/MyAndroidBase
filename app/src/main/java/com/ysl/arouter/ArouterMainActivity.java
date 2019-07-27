package com.ysl.arouter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysl.myandroidbase.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/main/ArouterMainActivity")
public class ArouterMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
        findViewById(R.id.btnm).setOnClickListener(v -> ARouter.getInstance().build("/modulea/ArouterActivityA").navigation());
    }
}
