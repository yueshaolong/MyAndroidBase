package com.ysl.modulea;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path="/modulea/ArouterActivity2")
public class ArouterActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter2);
        TextView tv2 = findViewById(R.id.tv2);
        tv2.setOnClickListener(v -> ARouter.getInstance().build("/arouter/ArouterActivity1").navigation());
    }
}
