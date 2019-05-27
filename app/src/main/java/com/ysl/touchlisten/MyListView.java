package com.ysl.touchlisten;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    /*private float x;
    private float y;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = ev.getX();
                y = ev.getY();
                System.out.println("list down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("list move");
                float x1 = ev.getX();
                float y1 = ev.getY();
                if (Math.abs(x1-x) < Math.abs(y1-y)){
                    //垂直滑动拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            default:
        }

        return super.dispatchTouchEvent(ev);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
