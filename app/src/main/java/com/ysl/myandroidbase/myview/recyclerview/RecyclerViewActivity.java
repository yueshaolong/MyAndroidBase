package com.ysl.myandroidbase.myview.recyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.myandroidbase.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        rv = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        List<AdapterData> adapterDataList = new ArrayList<>();
        adapterDataList.add(new AdapterData("http://img2.imgtn.bdimg.com/it/u=2594954460,618903872&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img5.imgtn.bdimg.com/it/u=3432186896,3159823243&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img1.imgtn.bdimg.com/it/u=2119156634,1655677451&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img3.imgtn.bdimg.com/it/u=2998393093,2914316040&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img0.imgtn.bdimg.com/it/u=1023086798,886608466&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img1.imgtn.bdimg.com/it/u=2959979664,2288857181&fm=26&gp=0.jpg"));
        Adapter adapter = new Adapter(R.layout.activity_cardview, adapterDataList);
        rv.setAdapter(adapter);
    }
}
