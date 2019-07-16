package com.ysl.myandroidbase.myview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class BadgeViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badgeview);

        TextView tv = findViewById(R.id.tv);
        Button btn1 = findViewById(R.id.btn_show);
        Button btn2 = findViewById(R.id.btn_hide);

        Badge badge = new QBadgeView(this).bindTarget(tv);
        badge.setExactMode(false);

        btn1.setOnClickListener(view -> {
            badge.setBadgeNumber(12);
        });
        btn2.setOnClickListener(view -> {
            badge.hide(true);
        });
    }
}
