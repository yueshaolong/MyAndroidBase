package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.ViewConfigurationCompat;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private int flowLayoutHeight;//此自定义控件的真正高度
    private int heightSize;//父类能给的最大高度
    private int scaledPagingTouchSlop;
    private boolean scrollable;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //获取最小滑动距离
        scaledPagingTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
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
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //3. 把上一步确定的限制信息，传递给每一个子View，然后子View开始measure自己的尺寸
        int childCount = getChildCount();
        RowView rowView = null;
        rowViews.clear();//因为onMeasure方法会执行多次，这里必须做清理
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams)child.getLayoutParams();
            childWidth += 30;

            //判断是否要换行
            if (i == 0 || rowView.rowWidth + childWidth > widthSize){
                if (rowView != null && rowView.views.size() == 1
                        && rowView.views.get(0).getLayoutParams().height == LayoutParams.MATCH_PARENT
                        && rowView.views.get(0).getLayoutParams().width == LayoutParams.MATCH_PARENT){
                    rowView.rowHeight = 150;
                }
                rowView = new RowView();
                rowViews.add(rowView);
            }
            rowView.views.add(child);
            rowView.rowWidth += childWidth;
            if (child.getLayoutParams().height != LayoutParams.MATCH_PARENT) {
                rowView.rowHeight = Math.max(rowView.rowHeight, childHeight);
            }
        }

        //对高度为MATCH_PARENT的再测量一次
        for (RowView r : rowViews) {
            for (View v : r.views) {
                if (v.getLayoutParams().height == LayoutParams.MATCH_PARENT) {
                    v.measure(getChildMeasureSpec(widthMeasureSpec, 0, v.getLayoutParams().width),
                            getChildMeasureSpec(heightMeasureSpec, 0, r.rowHeight));
                }
            }
        }

        int flowLayoutWidth = 0;
        flowLayoutHeight = 0;
        for (RowView r : rowViews) {
            flowLayoutWidth = Math.max(flowLayoutWidth, r.rowWidth);
            flowLayoutHeight += (r.rowHeight + 30);
        }

        scrollable = flowLayoutHeight > heightSize;

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
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams)v.getLayoutParams();
                int l1 = marginLayoutParams.leftMargin;
                int r1 = marginLayoutParams.rightMargin;
                int t1 = marginLayoutParams.topMargin;
                int b1 = marginLayoutParams.bottomMargin;

                l1 = 0;
                r1 = 30;
                t1 = 0;
                b1 = 10;

                left += l1;
                int right = left + measuredWidth;
                int bottom = top + measuredHeight;
                v.layout(left, top, right, bottom);
                left += (measuredWidth + r1);
            }
            top += (rowView.rowHeight + 30);
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

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    float lastX = 0;
    float lastY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {//一个down事件，多个move事件
        Log.i(TAG, "onInterceptTouchEvent: "+ ev.getAction());
        boolean intercept = false;
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //move的时候，判断和上一次的距离，大于抖动距离了，认为是在滑动，拦截事件
                float dx = x - lastX;
                float dy = y - lastY;
                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > scaledPagingTouchSlop){
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        lastX = x;
        lastY = y;

        return intercept;
    }
    float mLastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: "+ event.getAction());

        if (!scrollable){
            return super.onTouchEvent(event);
        }

        float currY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = currY;
                break;
            case MotionEvent.ACTION_MOVE:
                //相邻两个事件滑动的距离
                float dy = mLastY - currY;

                /*//使用scrollBy
                int scrollY = (int) dy + getScrollY();
                if (scrollY < 0){
                    scrollY = 0;
                }
                if (scrollY > flowLayoutHeight - heightSize){
                    scrollY = flowLayoutHeight - heightSize;
                }
                dy = scrollY - getScrollY();
                scrollBy(0, (int) dy);*/

                //使用scrollTo
                int scrollY = (int) dy + getScrollY();
                if (scrollY < 0){
                    scrollY = 0;
                }
                if (scrollY > flowLayoutHeight - heightSize){
                    scrollY = flowLayoutHeight - heightSize;
                }
                scrollTo(0, scrollY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        mLastY = currY;
        return super.onTouchEvent(event);
    }
}
