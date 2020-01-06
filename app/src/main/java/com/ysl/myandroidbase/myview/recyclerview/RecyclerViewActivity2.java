package com.ysl.myandroidbase.myview.recyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.myandroidbase.R;
import com.ysl.util.PicUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecyclerViewActivity2 extends AppCompatActivity {
    private static final String TAG = "RecyclerViewActivity";
    private RecyclerView rv;
    private SwitchMultiButton switchMultiButton;
    private GridViewAdapter adapter;
    private Map<String, List<String>> imgPath;
    private List<FenZuAdapterData> adapterDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview2);

        switchMultiButton = findViewById(R.id.switch_button);
        rv = findViewById(R.id.rv);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rv.setLayoutManager(gridLayoutManager);
        //GridView分组
        imgPath = PicUtil.getDayData(this);
        adapterDataList = new ArrayList<>();
        initFenZuData2(imgPath);

        switchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                switch (position) {
                    case 0:
                        imgPath = PicUtil.getDayData(RecyclerViewActivity2.this);
                        break;
                    case 1:
                        imgPath = PicUtil.getWeekData(RecyclerViewActivity2.this);
                        break;
                    case 2:
                        imgPath = PicUtil.getMonthData(RecyclerViewActivity2.this);
                        break;
                    default:

                        break;
                }

                rv.removeAllViewsInLayout();
                initFenZuData2(imgPath);
//                adapter.notifyDataSetChanged();
            }
        });


    }







    private void initFenZuData2(Map<String, List<String>> imgPath) {
        adapterDataList.clear();
//        adapterDataList.add(new FenZuAdapterData(true,"2019/09/24",""));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2265802320,83551290&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3226137502,1338046373&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3226137502,1338046373&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3441742992,2765570575&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(true,"2019/10/25",""));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=657651020,1259448861&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=657651020,1259448861&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=657651020,1259448861&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=657651020,1259448861&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4139999780,2210457878&fm=26&gp=0.jpg"));
//        adapterDataList.add(new FenZuAdapterData(false,"","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4139999780,2210457878&fm=26&gp=0.jpg"));

//        adapterDataList.add(new FenZuAdapterData(true,"2019/10/06",""));
        ArrayList<String> strings = new ArrayList<>(imgPath.keySet());
        System.out.println("=====================");
        System.out.println(strings);
        Collections.sort(strings);
        System.out.println(strings);
        Collections.reverse(strings);
        System.out.println(strings);
        for (String day : strings) {
            List<String> pathList = imgPath.get(day);
            if (pathList != null && pathList.size() > 0) {
                //添加日期头
                adapterDataList.add(new FenZuAdapterData(true, day, ""));
                //添加日期下的图片
                for (String path : pathList) {
                    adapterDataList.add(new FenZuAdapterData(false, "", path));
                }
            }
        }

        adapter = new GridViewAdapter(this, adapterDataList);
        rv.setAdapter(adapter);
    }
}
