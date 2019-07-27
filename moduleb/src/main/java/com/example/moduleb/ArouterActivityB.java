package com.example.moduleb;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path="/moduleb/ArouterActivityB")
public class ArouterActivityB extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouterb);
        findViewById(R.id.btnb).setOnClickListener(v -> ARouter.getInstance().build("/modulea/ArouterActivityA").navigation());
    }
}
