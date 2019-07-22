package com.ysl.myandroidbase.myview.recyclerview;


import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysl.myandroidbase.R;

import java.util.List;

public class Adapter extends BaseQuickAdapter<AdapterData, BaseViewHolder> {
    public Adapter(int layoutResId, @Nullable List<AdapterData> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, AdapterData item) {
        Glide.with(mContext).load(item.url).into((ImageView) helper.getView(R.id.iv_cover));
        helper.addOnClickListener(R.id.btn_share)
        .addOnClickListener(R.id.btn_ex)
        .addOnLongClickListener(R.id.btn_share)
        .addOnLongClickListener(R.id.btn_ex)
        .addOnClickListener(R.id.iv_cover);
    }
}
