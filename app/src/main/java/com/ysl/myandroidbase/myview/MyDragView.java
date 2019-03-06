package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyDragView extends ViewGroup {

    private ViewDragHelper dragHelper;
    private View dragView;
    private View autoBackView;
    private View edgeTrackerView;
    private int left;
    private int top;

    public MyDragView(Context context) {
        super(context);
        init(context, null);
    }

    public MyDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        dragHelper = ViewDragHelper.create(this, 100f, new Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                return view == dragView || view == autoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            //第二个view

            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                if (capturedChild == autoBackView){
                    left = capturedChild.getLeft();
                    top = capturedChild.getTop();
                }
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == autoBackView){
                    dragHelper.settleCapturedViewAt(left, top);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                dragHelper.captureChildView(edgeTrackerView, pointerId);
            }
        });
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(0);
        autoBackView = getChildAt(1);
        edgeTrackerView = getChildAt(2);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(0, widthMeasureSpec);
        int height = resolveSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int leftMargin = 0;
        int rightMargin = 0;
        for (int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            System.out.println("measuredWidth="+measuredWidth+", measuredHeight="+measuredHeight);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams)child.getLayoutParams();
            leftMargin = marginLayoutParams.leftMargin;
            rightMargin = marginLayoutParams.rightMargin;
            System.out.println("leftMargin="+leftMargin+", rightMargin="+rightMargin);

            child.layout(leftMargin + (leftMargin + measuredWidth + rightMargin) * i, 0, (leftMargin + measuredWidth + rightMargin) * (i + 1), measuredHeight);
//            if (i == 0){
//                child.layout(leftMargin, 0, leftMargin + measuredWidth + rightMargin, measuredHeight);
//            }else if (i == 1){
//                child.layout(leftMargin * 2 + measuredWidth + rightMargin , 0, leftMargin * 2 + measuredWidth * 2 + rightMargin * 2, measuredHeight);
//            }else {
//                child.layout(leftMargin * 3 + measuredWidth * 2 + rightMargin * 2 , 0, leftMargin * 3 + measuredWidth * 3 + rightMargin * 3, measuredHeight);
//            }
        }

        //可以使用回调里的onViewCaptured()方法获取到捕获到view时的坐标
//        left = autoBackView.getLeft();
//        top = autoBackView.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }
}
