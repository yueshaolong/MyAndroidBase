package com.ysl.myandroidbase.myview.recyclerview;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ysl.myandroidbase.R;
import com.ysl.util.ToastUtils;
import com.ysl.util.Util;

import java.util.List;

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.header);
        addItemType(TYPE_LEVEL_1, R.layout.header);
        addItemType(TYPE_LEVEL_2, R.layout.header);
    }


    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                ExpandItem1 expandItem1 = (ExpandItem1) item;
                helper.setText(R.id.tv, expandItem1.text);
                setPosition(helper, 20);
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (expandItem1.isExpanded()) {
                        collapse(pos);
                        ToastUtils.showToast(mContext,"收起：" + expandItem1.text);
                    } else {
                        expand(pos);
                        ToastUtils.showToast(mContext,"展开：" + expandItem1.text);
                    }
                });
                break;
            case TYPE_LEVEL_1:
                ExpandItem2 expandItem2 = (ExpandItem2) item;
                helper.setText(R.id.tv, expandItem2.text);
                helper.setBackgroundColor(R.id.item, mContext.getResources().getColor(R.color.RED));
                setPosition(helper, 50);
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (expandItem2.isExpanded()) {
                        collapse(pos);
                        ToastUtils.showToast(mContext,"收起：" + expandItem2.text);
                    } else {
                        expand(pos);
                        ToastUtils.showToast(mContext,"展开：" + expandItem2.text);
                    }
                });
                break;
            case TYPE_LEVEL_2:
                ExpandItem3 expandItem3 = (ExpandItem3) item;
                helper.setText(R.id.tv, expandItem3.text);
                helper.setBackgroundColor(R.id.item, mContext.getResources().getColor(R.color.colorPrimary));
                setPosition(helper, 80);
                helper.itemView.setOnClickListener(v ->
                        ToastUtils.showToast(mContext,"点击：" + expandItem3.text));
                break;
            default:

                break;
        }
    }

    private void setPosition(BaseViewHolder helper, int marginLeft) {
        TextView view = helper.getView(R.id.tv);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getLayoutParams());
        layoutParams.gravity = Gravity.LEFT;
        layoutParams.leftMargin = Util.dp2px(mContext, marginLeft);
        view.setLayoutParams(layoutParams);
    }
}
