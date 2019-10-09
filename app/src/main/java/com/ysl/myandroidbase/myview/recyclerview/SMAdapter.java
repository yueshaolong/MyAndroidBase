package com.ysl.myandroidbase.myview.recyclerview;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysl.myandroidbase.R;

import java.util.List;

public class SMAdapter extends BaseSectionMultiItemQuickAdapter<SMData, BaseViewHolder> {

    public SMAdapter(int sectionHeadResId, List<SMData> data) {
        super(sectionHeadResId, data);
        addItemType(SMData.IMAGE_TYPE, R.layout.activity_cardview);
        addItemType(SMData.TEXT_TYPE, R.layout.header);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SMData item) {
        helper.setText(R.id.tv, item.myHeader.headerStr);
        helper.setTextColor(R.id.tv, mContext.getResources().getColor(R.color.RED));
        helper.setBackgroundColor(R.id.tv, mContext.getResources().getColor(R.color.white));
    }

    @Override
    protected void convert(BaseViewHolder helper, SMData item) {
        switch (helper.getItemViewType()) {
            case SMData.IMAGE_TYPE:
                Glide.with(mContext).load(item.content.contentStr).into((ImageView) helper.getView(R.id.iv_cover));
                break;
            case SMData.TEXT_TYPE:
                helper.setText(R.id.tv, item.content.contentStr);
                break;
            default:

                break;
        }
    }
}
