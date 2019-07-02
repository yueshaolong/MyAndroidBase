package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    int flowLayoutWidth = 0;
    int flowLayoutHeight = 0;
//    int currWidth = 0;
//    int currHeight = 0;
    List<RowView> rowViews = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //2. 为每个子View计算测量的限制信息 Mode / Size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //3. 把上一步确定的限制信息，传递给每一个子View，然后子View开始measure自己的尺寸
        int childCount = getChildCount();
        RowView rowView = new RowView();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //判断是否要换行
            if (rowView.rowWidth + childWidth > widthSize){
                rowView = new RowView();
                rowView.views.add(child);
                rowView.rowWidth = childWidth;
                rowView.rowHeight = childHeight;
                rowViews.add(rowView);
                flowLayoutWidth = Math.max(flowLayoutWidth, rowView.rowWidth);
                flowLayoutHeight += rowView.rowHeight;
            }
            rowView.views.add(child);
            rowView.rowWidth += childWidth;
            rowView.rowHeight = Math.max(rowView.rowHeight, childHeight);
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : flowLayoutWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : flowLayoutHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private class RowView{
        List<View> views = new ArrayList<>();
        int rowWidth = 0;
        int rowHeight = 0;
    }
}
