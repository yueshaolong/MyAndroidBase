package com.ysl.touchlisten;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ysl.myandroidbase.R;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TouchActivity extends AppCompatActivity {
//    @BindView(R.id.vp)
//    ViewPager viewPager;
    @BindView(R.id.lv)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_activity2);
        ButterKnife.bind(this);

        String[] strings = new String[100];
        for (int i=0;i<100;i++){
            strings[i] = i+"";
        }

        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strings));

        /*MyListView listview1 = new MyListView(this);
        MyListView listview2 = new MyListView(this);
        MyListView listview3 = new MyListView(this);

        listview1.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strings));
        listview2.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strings));
        listview3.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strings));

        Vector<View> pages = new Vector<View>();
        pages.add(listview1);
        pages.add(listview2);
        pages.add(listview3);

        viewPager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(pages.get(position));
                return pages.get(position);
            }

            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });*/
    }
}
