package com.ysl.myandroidbase.myview.zidingyiview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import androidx.customview.widget.ViewDragHelper.Callback;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //ysl 目标版本在AndroidP以上的适配工作
        //当目标版本从Android P开始，Canvas.clipPath(@NonNull Path path, @NonNull Region.Op op) ; 已经被废弃，
        // 而且是包含异常风险的废弃API，只有 Region.Op.INTERSECT 和 Region.Op.DIFFERENCE 得到兼容，
        // 目前不清楚google此举目的如何，仅仅如此简单就抛出异常提示开发者适配，几乎所有的博客解决方案都是如下简单粗暴：
        /*Path path = new Path();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            canvas.clipPath(path);
        } else {
            canvas.clipPath(path, Region.Op.XOR);// REPLACE、UNION 等
        }
        //但我们一定需要一些高级逻辑运算效果怎么办？如小说的仿真翻页阅读效果，解决方案如下，用Path.op代替，
        // 先运算Path，再给canvas.clipPath使用：
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            Path mPathXOR = new Path();
            mPathXOR.moveTo(0,0);
            mPathXOR.lineTo(getWidth(),0);
            mPathXOR.lineTo(getWidth(),getHeight());
            mPathXOR.lineTo(0,getHeight());
            mPathXOR.close();
            //以上根据实际的Canvas或View的大小，画出相同大小的Path即可
            mPathXOR.op(path, Path.Op.XOR);
            canvas.clipPath(mPathXOR);
        }else {
            canvas.clipPath(path, Region.Op.XOR);
        }*/
    }
}
