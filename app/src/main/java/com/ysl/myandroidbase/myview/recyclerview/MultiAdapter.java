package com.ysl.myandroidbase.myview.recyclerview;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysl.myandroidbase.R;

import java.util.List;

public class MultiAdapter extends BaseMultiItemQuickAdapter<MultiAdapterData, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultiAdapter(List<MultiAdapterData> data) {
        super(data);
        addItemType(MultiAdapterData.IMAGE_TYPE, R.layout.activity_cardview);
        addItemType(MultiAdapterData.TEXT_TYPE, R.layout.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiAdapterData item) {
        switch (helper.getItemViewType()) {
            case MultiAdapterData.IMAGE_TYPE:
                Glide.with(mContext).load(item.url).into((ImageView) helper.getView(R.id.iv_cover));
                break;
            case MultiAdapterData.TEXT_TYPE:
                helper.setText(R.id.tv, item.text);
                break;
            default:

                break;
        }
    }
}
