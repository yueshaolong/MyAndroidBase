package com.ysl.myandroidbase.myview.recyclerview;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.ysl.myandroidbase.R;
import com.ysl.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";
    private RecyclerView rv;
    private boolean isErr;
    private long delayMillis = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        rv = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        //加载普通的布局
//        loadList();
        //加载多种类型的布局
//        initMultiData();
        //加载分组布局
//        initFenZuData();
        //拖拽排序和滑动删除
//        dragDelete();
        //加载树形结构列表
        loadTreeList();
        //分组多类型布局
//        initSMAdapter();

    }

    private ExpandableItemAdapter loadTreeList() {
        List<MultiItemEntity> expandListData = getExpandListData(4);
        ExpandableItemAdapter expandableItemAdapter = new ExpandableItemAdapter(expandListData);
        expandableItemAdapter.expandAll();//是否展开所有的条目
        rv.setAdapter(expandableItemAdapter);
        return expandableItemAdapter;
    }
    private List<MultiItemEntity> getExpandListData(int count) {
        int lvCount = count;
        int lv1Count = 3;
        int lv2Count = 2;
        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < lvCount; i++) {
            ExpandItem1 item0 = new ExpandItem1("一级列表标题" + i);
            for (int j = 0; j < lv1Count; j++) {
                ExpandItem2 item1 = new ExpandItem2("二级列表标题" + j);
                for (int k = 0; k < lv2Count; k++) {
                    item1.addSubItem(new ExpandItem3("三级列表标题" + k));
                }
                item0.addSubItem(item1);
            }
            data.add(item0);
        }
        return data;
    }


    private void dragDelete() {
        ItemDragAdapter adapter = initDragData();

        //拖拽功能
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rv);
        adapter.enableDragItem(itemTouchHelper);
        adapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
                Log.i(TAG, "onItemDragStart: i="+i);
                ToastUtils.showToast(RecyclerViewActivity.this, "开始拖拽 i="+i);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {
                //会执行多次，i和i1是拖拽到的前后两个位置
                Log.i(TAG, "onItemDragMoving: i="+i+"  i1="+i1);
                ToastUtils.showToast(RecyclerViewActivity.this, "拖拽移动中 i="+i);
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {
                Log.i(TAG, "onItemDragEnd: i="+i);
                ToastUtils.showToast(RecyclerViewActivity.this, "拖拽结束 i="+i);
            }
        });

        //活动删除功能,从左向右滑动删除
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int i) {
                //最先执行
                Log.i(TAG, "onItemSwipeStart: i="+i);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int i) {
                //最后执行
                Log.i(TAG, "clearView: i="+i);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                Log.i(TAG, "onItemSwiped: i="+i);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float v, float v1, boolean b) {
                //会执行多次
                Log.i(TAG, "onItemSwipeMoving: v="+v+"  v1="+v1+"  b="+b);
            }
        });
    }

    private void loadList() {
        Adapter adapter = initData();
        clickListener(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);//缩放
        addHeader(adapter);
        loadMore(adapter);
    }

    private void loadMore(Adapter adapter) {
        adapter.setOnLoadMoreListener(() -> rv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (12 >= 1) {
                    //数据全部加载完毕
                    adapter.loadMoreEnd();
                } else {
                    if (isErr) {
                        //成功获取更多数据
//                        adapter.addData(data.getSampleData(PAGE_SIZE));
//                        mCurrentCounter = adapter.getData().size();
                        adapter.loadMoreComplete();
                    } else {
                        //获取更多数据失败
                        isErr = true;
                        Toast.makeText(RecyclerViewActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
                        adapter.loadMoreFail();
                    }
                }
            }
        }, delayMillis), rv);
    }

    private void addHeader(Adapter adapter) {
        View view = LayoutInflater.from(this).inflate(R.layout.header, null, false);
        adapter.addHeaderView(view);
    }

    private void clickListener(Adapter adapter) {
        //设置点击事件
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            ToastUtils.showToast(this, "点击事件：view="+view+",position="+position);
        });
        adapter.setOnItemLongClickListener((adapter12, view, position) -> {
            ToastUtils.showToast(this, "长按事件：view="+view+",position="+position);
            return true;
        });
        adapter.setOnItemChildClickListener((adapter13, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_share:
                    ToastUtils.showToast(this, "share点击事件：view="+view+",position="+position);
                    break;
                case R.id.btn_ex:
                    ToastUtils.showToast(this, "ex点击事件：view="+view+",position="+position);
                    break;
                case R.id.iv_cover:
                    ToastUtils.showToast(this, "图片点击事件：view="+view+",position="+position);
                    break;
                default:

                    break;
            }
        });
        adapter.setOnItemChildLongClickListener((adapter14, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_share:
                    ToastUtils.showToast(this, "share长按事件：view="+view+",position="+position);
                    break;
                case R.id.btn_ex:
                    ToastUtils.showToast(this, "ex长按事件：view="+view+",position="+position);
                    break;
                default:

                    break;
            }
            return true;
        });
    }

    private ItemDragAdapter initDragData() {
        List<AdapterData> adapterDataList = new ArrayList<>();
        adapterDataList.add(new AdapterData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2265802320,83551290&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3226137502,1338046373&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3441742992,2765570575&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1487351610,315303232&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img2.imgtn.bdimg.com/it/u=2594954460,618903872&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img5.imgtn.bdimg.com/it/u=3432186896,3159823243&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img1.imgtn.bdimg.com/it/u=2119156634,1655677451&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img3.imgtn.bdimg.com/it/u=2998393093,2914316040&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img0.imgtn.bdimg.com/it/u=1023086798,886608466&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img1.imgtn.bdimg.com/it/u=2959979664,2288857181&fm=26&gp=0.jpg"));
        ItemDragAdapter adapter = new ItemDragAdapter(R.layout.activity_cardview, adapterDataList);
        rv.setAdapter(adapter);
        return adapter;
    }

    private Adapter initData() {
        List<AdapterData> adapterDataList = new ArrayList<>();
        adapterDataList.add(new AdapterData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2265802320,83551290&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3226137502,1338046373&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3441742992,2765570575&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1487351610,315303232&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img2.imgtn.bdimg.com/it/u=2594954460,618903872&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img5.imgtn.bdimg.com/it/u=3432186896,3159823243&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img1.imgtn.bdimg.com/it/u=2119156634,1655677451&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img3.imgtn.bdimg.com/it/u=2998393093,2914316040&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img0.imgtn.bdimg.com/it/u=1023086798,886608466&fm=26&gp=0.jpg"));
        adapterDataList.add(new AdapterData("http://img1.imgtn.bdimg.com/it/u=2959979664,2288857181&fm=26&gp=0.jpg"));
        Adapter adapter = new Adapter(R.layout.activity_cardview, adapterDataList);
        rv.setAdapter(adapter);
        return adapter;
    }

    private MultiAdapter initMultiData() {
        List<MultiAdapterData> adapterDataList = new ArrayList<>();
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.TEXT_TYPE,"","文字"));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.IMAGE_TYPE,"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg",""));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.TEXT_TYPE,"","你好"));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.IMAGE_TYPE,"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2265802320,83551290&fm=26&gp=0.jpg",""));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.IMAGE_TYPE,"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3226137502,1338046373&fm=26&gp=0.jpg",""));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.TEXT_TYPE,"","哪里"));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.TEXT_TYPE,"","这里"));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.IMAGE_TYPE,"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3441742992,2765570575&fm=26&gp=0.jpg",""));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.IMAGE_TYPE,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1487351610,315303232&fm=26&gp=0.jpg",""));
        adapterDataList.add(new MultiAdapterData(MultiAdapterData.TEXT_TYPE,"","哇"));
        MultiAdapter adapter = new MultiAdapter(adapterDataList);
        rv.setAdapter(adapter);
        return adapter;
    }

    private FenzuAdapter initFenZuData() {
        List<FenZuAdapterData> adapterDataList = new ArrayList<>();
        adapterDataList.add(new FenZuAdapterData(true,"美女",""));
        adapterDataList.add(new FenZuAdapterData(false,"", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg"));
        adapterDataList.add(new FenZuAdapterData(false,"","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2265802320,83551290&fm=26&gp=0.jpg"));
        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3226137502,1338046373&fm=26&gp=0.jpg"));
        adapterDataList.add(new FenZuAdapterData(false,"","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3441742992,2765570575&fm=26&gp=0.jpg"));
        adapterDataList.add(new FenZuAdapterData(true,"野兽",""));
        adapterDataList.add(new FenZuAdapterData(false,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=657651020,1259448861&fm=26&gp=0.jpg"));
        adapterDataList.add(new FenZuAdapterData(false,"","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3996706903,3663179137&fm=11&gp=0.jpg"));
        adapterDataList.add(new FenZuAdapterData(false,"","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4139999780,2210457878&fm=26&gp=0.jpg"));
        FenzuAdapter adapter = new FenzuAdapter(R.layout.activity_cardview, R.layout.header, adapterDataList);
        rv.setAdapter(adapter);
        return adapter;
    }

    private SMAdapter initSMAdapter(){
        List<SMData> data = new ArrayList<>();
//        data.add(new SMData(true, "我的代办任务", "", -1));
//        data.add(new SMData(false, "", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg", SMData.IMAGE_TYPE));
//        data.add(new SMData(false, "", "nihaonihao", SMData.TEXT_TYPE));
//        data.add(new SMData(false, "", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg", SMData.IMAGE_TYPE));
//        data.add(new SMData(false, "", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg", SMData.IMAGE_TYPE));
//        data.add(new SMData(false, "", "nihaonihao", SMData.TEXT_TYPE));
//        data.add(new SMData(false, "", "nihaonihao", SMData.TEXT_TYPE));
//
//        data.add(new SMData(true, "已完成任务", "", -1));
//        data.add(new SMData(false, "", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1974166254,1619566506&fm=26&gp=0.jpg", SMData.IMAGE_TYPE));
//        data.add(new SMData(false, "", "哈哈哈哈哈", SMData.TEXT_TYPE));
//        data.add(new SMData(false, "", "哈哈哈哈哈", SMData.TEXT_TYPE));
//        data.add(new SMData(false, "", "哈哈哈哈哈", SMData.TEXT_TYPE));
//        data.add(new SMData(false, "", "哈哈哈哈哈", SMData.TEXT_TYPE));
//        data.add(new SMData(false, "", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=657651020,1259448861&fm=26&gp=0.jpg", SMData.IMAGE_TYPE));
//        data.add(new SMData(false, "", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=657651020,1259448861&fm=26&gp=0.jpg", SMData.IMAGE_TYPE));
//        data.add(new SMData(false, "", "哈哈哈哈哈", SMData.TEXT_TYPE));
//        data.add(new SMData(false, "", "哈哈哈哈哈", SMData.TEXT_TYPE));

        SMAdapter smAdapter = new SMAdapter(R.layout.header,data);
        rv.setAdapter(smAdapter);
        return smAdapter;
    }

}
