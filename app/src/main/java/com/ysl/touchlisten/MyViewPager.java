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
    private float x;
    private float y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x = ev.getX();
//                y = ev.getY();
//                System.out.println("pager down");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("pager move");
//                float x1 = ev.getX();
//                float y1 = ev.getY();
//                if (Math.abs(x1-x) > Math.abs(y1-y)){
//                    //水平滑动拦截
//                    return true;
//                }
//                break;
//            default:
//        }
        return false;
    }
}
