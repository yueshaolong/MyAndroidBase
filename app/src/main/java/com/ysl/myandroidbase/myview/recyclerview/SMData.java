package com.ysl.myandroidbase.myview.recyclerview;

import com.chad.library.adapter.base.entity.SectionMultiEntity;

public class SMData extends SectionMultiEntity {

    public static final int IMAGE_TYPE = 0;
    public static final int TEXT_TYPE = 1;
    public Content content;
    public MyHeader myHeader;

    public SMData(boolean isHeader, MyHeader myHeader, Content content) {
        super(isHeader, myHeader.headerStr);
        this.content = content;
        this.myHeader = myHeader;
    }

    @Override
    public int getItemType() {
        return content.contentType;
    }

    public class MyHeader{
        public String headerStr;
        public int headerType;
    }
    public class Content{
        public String contentStr;
        public int contentType;
    }
}
