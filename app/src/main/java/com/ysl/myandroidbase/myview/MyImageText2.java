package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysl.myandroidbase.R;
import com.ysl.myandroidbase.Util;

public class MyImageText2 extends LinearLayout {

    private static final String TAG ="MyImageText2";
    private String text;
    private int textColor;
    private int imageId;
    private ImageView iv;
    private TextView tv;
    private int textSize;
    private float imageWidth;
    private float imageHeight;

    public MyImageText2(Context context) {
        this(context, null);
    }

    public MyImageText2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageText2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyImageText);
        text = ta.getString(R.styleable.MyImageText_text);
        textColor = ta.getColor(R.styleable.MyImageText_textColor, Color.BLACK);
        textSize = ta.getDimensionPixelSize(R.styleable.MyImageText_textSize, 6);
        imageWidth = ta.getDimension(R.styleable.MyImageText_imageWidth, 50);
        imageHeight = ta.getDimension(R.styleable.MyImageText_imageHeight, 50);
        imageId = ta.getResourceId(R.styleable.MyImageText_image, R.drawable.girl);
        ta.recycle();  //注意回收

        Log.i(TAG, "MyImageText: textSize" + textSize);
        Log.i(TAG, "MyImageText: imageWidth" + imageWidth);

        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_imagetext, this, true);
        iv = view.findViewById(R.id.iv);
        tv = view.findViewById(R.id.tv);
        iv.setImageResource(imageId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Util.dp2px(context, imageWidth),
                Util.dp2px(context, imageHeight));
        iv.setLayoutParams(params);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);//使图片充满控件大小
//        iv.setMaxWidth(Util.dp2px(context, imageWidth));
//        iv.setMaxHeight(Util.dp2px(context, imageHeight));
        tv.setText(text);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);


    }

}
