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
        RowView rowView = null;
        rowViews.clear();//因为onMeasure方法会执行多次，这里必须做清理
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //判断是否要换行
            if (i == 0 || rowView.rowWidth + childWidth > widthSize){
                rowView = new RowView();
                rowViews.add(rowView);
            }
            rowView.views.add(child);
            rowView.rowWidth += childWidth;
            rowView.rowHeight = Math.max(rowView.rowHeight, childHeight);
        }

        int flowLayoutWidth = 0;
        int flowLayoutHeight = 0;
        for (RowView r : rowViews) {
            flowLayoutWidth = Math.max(flowLayoutWidth, r.rowWidth);
            flowLayoutHeight += r.rowHeight;
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : flowLayoutWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : flowLayoutHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        for (RowView rowView : rowViews) {//循环行
            int left = 0;
            for (View v : rowView.views) {//循环每行的view
                int measuredWidth = v.getMeasuredWidth();
                int measuredHeight = v.getMeasuredHeight();
                int right = left + measuredWidth;
                int bottom = top + measuredHeight;
                v.layout(left, top, right, bottom);
                left += measuredWidth;
            }
            top += rowView.rowHeight;
        }
    }

    private class RowView{
        List<View> views = new ArrayList<>();
        int rowWidth = 0;
        int rowHeight = 0;

        @Override
        public String toString() {
            return "RowView{" +
                    "views=" + views.size() +
                    ", rowWidth=" + rowWidth +
                    ", rowHeight=" + rowHeight +
                    '}';
        }
    }
}
