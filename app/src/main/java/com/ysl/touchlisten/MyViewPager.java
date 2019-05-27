package com.ysl.touchlisten;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    //内部解决：配合listview中重写的dispatchTouchEvent()方法来解决事件冲突
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            System.out.println("pager down");
            super.onInterceptTouchEvent(ev);
            return false;
        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){
            System.out.println("pager move");
        }
        return true;
    }






    /*//外部解决：就是在父view中直接判断滑动方向来决定是否拦截事件
    private float x;
    private float y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            x = ev.getX();
            y = ev.getY();
            System.out.println("pager down");
            return false;
        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){
            System.out.println("pager move");
            float x1 = ev.getX();
            float y1 = ev.getY();
            if (Math.abs(x1-x) > Math.abs(y1-y)){
                //水平滑动直接拦截
                return true;
            }else {
                return false;
            }
        }
        return false;
    }*/


}
