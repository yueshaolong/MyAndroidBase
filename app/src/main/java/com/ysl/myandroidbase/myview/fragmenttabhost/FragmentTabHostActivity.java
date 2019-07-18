package com.ysl.myandroidbase.myview.fragmenttabhost;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;

public class FragmentTabHostActivity extends AppCompatActivity {
    private Class[] classes = new Class[] {
            BlankFragment.class,
            BlankFragment.class,
            BlankFragment.class,
            BlankFragment.class,
            BlankFragment.class };
    private int images[] = new int[] {
            R.drawable.tab_1_selector,
            R.drawable.tab_2_selector,
            R.drawable.tab_3_selector,
            R.drawable.tab_4_selector,
            R.drawable.tab_5_selector };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmenttabhost);

        String[] tabIndicatorArray = getResources().getStringArray(R.array.arr_tab_indicator);

//        FragmentTabHost tabHost = findViewById(android.R.id.tabhost);
        MyFragmentTabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        for (int i = 0; i < images.length; i++) {
            View indicatorView = getLayoutInflater().inflate(R.layout.list_item_viewpagerindicator, null);
            ImageView iv = indicatorView.findViewById(R.id.ima_indicator);
            iv.setImageResource(images[i]);
            TextView tv = indicatorView.findViewById(R.id.tv_title_indicator);
            tv.setText(tabIndicatorArray[i]);

            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabIndicatorArray[i]).setIndicator(indicatorView);
            tabHost.addTab(tabSpec, classes[i], null);
        }
    }
}
