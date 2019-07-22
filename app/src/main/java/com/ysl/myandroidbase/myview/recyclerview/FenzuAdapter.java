package com.ysl.myandroidbase.myview.recyclerview;


import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysl.myandroidbase.R;

import java.util.List;

public class FenzuAdapter extends BaseSectionQuickAdapter<FenZuAdapterData, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public FenzuAdapter(int layoutResId, int sectionHeadResId, List<FenZuAdapterData> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FenZuAdapterData item) {
        Glide.with(mContext).load(item.url).into((ImageView) helper.getView(R.id.iv_cover));
    }

    @Override
    protected void convertHead(BaseViewHolder helper, FenZuAdapterData item) {
        helper.setText(R.id.tv, item.header);
        helper.setTextColor(R.id.tv, mContext.getResources().getColor(R.color.RED));
    }
}
