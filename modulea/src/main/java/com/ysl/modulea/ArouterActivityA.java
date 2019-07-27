package com.ysl.modulea;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path="/modulea/ArouterActivityA")
public class ArouterActivityA extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aroutera);
        findViewById(R.id.btna).setOnClickListener(v -> ARouter.getInstance().build("/moduleb/ArouterActivityB").navigation());
    }
}
