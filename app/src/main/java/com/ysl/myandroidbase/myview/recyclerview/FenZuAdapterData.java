package com.ysl.myandroidbase.myview.recyclerview;


import com.chad.library.adapter.base.entity.SectionEntity;

public class FenZuAdapterData extends SectionEntity<String> {

    public String url;
    public String header;

    public FenZuAdapterData(boolean isHeader, String header, String url) {
        super(isHeader, header);
        this.header = header;
        this.url = url;
    }

}
