package com.ysl.myandroidbase.myview.recyclerview;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysl.myandroidbase.R;

import java.util.List;

public class ItemDragAdapter extends BaseItemDraggableAdapter<AdapterData, BaseViewHolder> {
    public ItemDragAdapter(int layoutResId, List<AdapterData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdapterData item) {
        Glide.with(mContext).load(item.url).into((ImageView) helper.getView(R.id.iv_cover));
    }
}
