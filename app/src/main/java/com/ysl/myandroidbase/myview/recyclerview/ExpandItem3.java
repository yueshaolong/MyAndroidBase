package com.ysl.myandroidbase.myview.recyclerview;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ExpandItem3 implements MultiItemEntity {
    public String text;

    public ExpandItem3(String text) {
        this.text = text;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_2;
    }
}
