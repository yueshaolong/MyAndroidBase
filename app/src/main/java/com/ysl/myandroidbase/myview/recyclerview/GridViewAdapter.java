package com.ysl.myandroidbase.myview.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.ysl.myandroidbase.R;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<FenZuAdapterData> dataList;
    private Context mContext;

    public GridViewAdapter(Context context, List<FenZuAdapterData> dataList){
        this.mContext = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(dataList.get(viewType).isHeader){
            View v = LayoutInflater.from(mContext).inflate(R.layout.header, parent, false);
            HeaderViewHolder titleViewHolder = new HeaderViewHolder(v);
            return titleViewHolder;
        }else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_grid, parent, false);
            PicViewHolder myViewHolder = new PicViewHolder(v);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(dataList.get(position).isHeader){
            HeaderViewHolder titleViewHolder = (HeaderViewHolder) holder;
            titleViewHolder.tv.setText(dataList.get(position).header);
        }else {
            PicViewHolder picViewHolder = (PicViewHolder)holder;
            Glide.with(mContext).load(dataList.get(position).url)
                    .into(picViewHolder.photo_view);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据设置的spanCount个单元格
        final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        //Sets the source to get the number of spans occupied by each item in the adapter.
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(dataList.get(position).isHeader){
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public HeaderViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv);
        }
    }
    private static class PicViewHolder extends RecyclerView.ViewHolder{
        PhotoView photo_view;
        ImageView edit_pic;
        public PicViewHolder(View v) {
            super(v);
            photo_view = v.findViewById(R.id.photo_view);
            edit_pic = v.findViewById(R.id.edit_pic);
        }
    }
}
