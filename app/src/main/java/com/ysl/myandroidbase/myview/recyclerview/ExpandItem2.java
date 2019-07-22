package com.ysl.myandroidbase.myview.recyclerview;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ExpandItem2 extends AbstractExpandableItem<ExpandItem3> implements MultiItemEntity {
    public String text;

    public ExpandItem2(String text) {
        this.text = text;
    }

    @Override
    public int getLevel() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }
}
