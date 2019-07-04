package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {

    private Paint paint;

    public MyViewGroup(Context context) {
        super(context);
        init(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;
        int widthS = 0;
        int heightL = 0;
        int widthX = 0;
        int heightR = 0;
        //先测量children的宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //for循环计算子view的宽高
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            int childViewWidth = childView.getMeasuredWidth();
            int childViewHeight = childView.getMeasuredHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams)childView.getLayoutParams();
            //获取子view的宽高
            if (i < 2) {//上面的宽度
                widthS += childViewWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            } else {//下面的宽度
                widthX += childViewWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            }
            if (i == 0 || i == 2){//左边的高度
                heightL += childViewHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }else {//右边的高度
                heightR += childViewHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
        }

        //定义自己的宽高
        /*int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                width = widthSize;//精确模式（250dp或match_parent）
                break;
            case MeasureSpec.AT_MOST://最大值不能超过上面计算的widthSize
                System.out.println(widthSize+"<>"+Math.max(widthS, widthX));
                width = Math.max(widthS, widthX) > widthSize ? widthSize : Math.max(widthS, widthX);
                break;
        }*/
        width = resolveSize(Math.max(widthS, widthX), widthMeasureSpec);


        /*int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                System.out.println(heightSize+"<>"+Math.max(heightL, heightR));
                height = Math.max(heightL, heightR) > heightSize ? heightSize : Math.max(heightL, heightR);
                break;
        }*/
        height = resolveSize(Math.max(heightL, heightR), heightMeasureSpec);

        //保存
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //ViewGroup中的onLayout方法是抽象的，这里就要自己去定义子view的具体位置了。
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            int childViewWidth = childView.getMeasuredWidth();
            int childViewHeight = childView.getMeasuredHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams)childView.getLayoutParams();
            int l1 = marginLayoutParams.leftMargin;
            int r1 = marginLayoutParams.rightMargin;
            int t1 = marginLayoutParams.topMargin;
            int b1 = marginLayoutParams.bottomMargin;

            //获取子view的宽高
            switch (i) {
                case 0:
                    childView.layout(l1,t1,l1+childViewWidth+r1, t1+childViewHeight+b1);
                    break;
                case 1:
                    childView.layout(measuredWidth-(childViewWidth+l1+r1),t1,
                            measuredWidth-r1, childViewHeight+t1+b1);
                    break;
                case 2:
                    childView.layout(l1,measuredHeight-(childViewHeight+l1+t1),
                            l1+childViewWidth+r1, measuredHeight-b1);
                    break;
                case 3:
                    childView.layout(measuredWidth-(childViewWidth+l1+r1),
                            measuredHeight-(childViewHeight+t1+b1),
                            measuredWidth-r1, measuredHeight-b1);
                    break;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
