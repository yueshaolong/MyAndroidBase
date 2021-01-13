package com.ysl.myandroidbase.myview;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

public class FlowLayoutAct extends AppCompatActivity {

    @BindView(R.id.flow)
    TagFlowLayout flowLayout;

    private List<String> list;
    private TagAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_flowlayout);
        list = new ArrayList<>();
        for (int i = 0; i <100; i++) {
            list.add("Android开发");
            list.add("Java");
            list.add("IOS");
            list.add("python语言");
            list.add("ABC的发 生必然会覅 士大夫你的 烦恼四点零 分南斯");
        }

        adapter = new TagAdapter<String>(list) {
            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                view.setBackground(getResources().getDrawable(R.drawable.shape_rect_blue_10));
                ((TextView)view).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                view.setBackground(getResources().getDrawable(R.drawable.shape_rect_blue_hollow_10));
                ((TextView)view).setTextColor(getResources().getColor(R.color.blue));
            }

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = new TextView(getApplicationContext());
                tv.setPadding(28, 10, 28, 10);
                tv.setText(s);
                tv.setTextColor(getResources().getColor(R.color.blue));
                tv.setMaxEms(20);
                tv.setSingleLine();
                tv.setBackgroundResource(R.drawable.shape_rect_blue_hollow_10);
                return tv;
            }
        };

        adapter.setSelectedList(0);
        flowLayout.setAdapter(adapter);

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(FlowLayoutAct.this,list.get(position),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                System.out.println("选中："+Arrays.toString(selectPosSet.toArray()));
            }
        });
    }
}
