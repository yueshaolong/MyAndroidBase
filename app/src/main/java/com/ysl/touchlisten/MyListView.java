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

    //内部解决：配合viewpager中重新的onInterceptTouchEvent()来解决冲突
    private float x;
    private float y;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = ev.getX();
                y = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                System.out.println("list down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("list move");
                float x1 = ev.getX();
                float y1 = ev.getY();
                if (Math.abs(x1-x) > Math.abs(y1-y)){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
        }
        return super.dispatchTouchEvent(ev);
    }

    //listview嵌套scrollview时，只显示一行的解决办法
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
}
