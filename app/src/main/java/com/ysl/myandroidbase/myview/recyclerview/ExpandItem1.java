package com.ysl.myandroidbase.myview.recyclerview;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ExpandItem1 extends AbstractExpandableItem<ExpandItem2> implements MultiItemEntity {
    public String text;

    public ExpandItem1(String text) {
        this.text = text;
    }

    @Override
    public int getLevel() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

}
