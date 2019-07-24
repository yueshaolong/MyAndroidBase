package com.ysl.arouter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysl.myandroidbase.R;

//@Route(path="/arouter/ArouterActivity1")
public class ArouterActivity1 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter1);
        TextView tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(v -> ARouter.getInstance().build("/modulea/ArouterActivity2").navigation());
    }
}
