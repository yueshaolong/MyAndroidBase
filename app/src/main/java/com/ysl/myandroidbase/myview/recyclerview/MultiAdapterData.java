package com.ysl.myandroidbase.myview.recyclerview;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultiAdapterData implements MultiItemEntity {

    public static final int IMAGE_TYPE = 0;
    public static final int TEXT_TYPE = 1;

    private final int itemType;
    public MultiAdapterData(int itemType, String url, String text) {
        this.itemType = itemType;
        this.url = url;
        this.text = text;
    }

    public String url;
    public String text;

    @Override
    public int getItemType() {
        return itemType;
    }
}
