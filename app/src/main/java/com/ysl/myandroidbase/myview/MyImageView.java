package com.ysl.myandroidbase.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.ysl.myandroidbase.R;

import java.io.InputStream;

public class MyImageView extends AppCompatImageView {

    private Paint paint;
    private Context context;
    private float radius;
    private int width;
    private int height;

    public MyImageView(Context context) {
        super(context);
        init(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        paint = new Paint();
        paint.setTextSize(10);

        String text = "hello";
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.width();
        int height = rect.height();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyImageView, 0, 0);
        radius = typedArray.getFloat(R.styleable.MyImageView_radius, 200);
        this.width = typedArray.getInteger(R.styleable.MyImageView_width, 500);
        this.height = typedArray.getInteger(R.styleable.MyImageView_height, 500);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*//获得原有的宽高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        //通过计算，获得自己想要的宽高
        if (measuredHeight > measuredWidth){
            measuredHeight = measuredWidth;
        }else {
            measuredWidth = measuredHeight;
        }
        //把高度传给父view保存
        setMeasuredDimension(measuredWidth, measuredHeight);*/

        //获取控件的宽度大小和模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                widthMeasureSpec = getPaddingLeft() + getPaddingRight() + widthSize;
                break;
            case MeasureSpec.AT_MOST:
                widthMeasureSpec = getPaddingLeft() + getPaddingRight() + width;
                break;
        }
        //获取控件的高度大小和模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                heightMeasureSpec = getPaddingTop() + getPaddingBottom() + heightSize;
                break;
            case MeasureSpec.AT_MOST:
                heightMeasureSpec = getPaddingTop() + getPaddingBottom() + height;
                break;
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        /*int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        LayoutParams layoutParams = getLayoutParams();
        int width2 = layoutParams.width;
        int height2 = layoutParams.height;
        layout(width2/2 - width/2, height2/2 - height/2, width2/2 + width/2, height2/2 + height/2);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#8800FF00"));

        paint.setColor(Color.RED);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(radius/10);
        paint.setAntiAlias(true);
        canvas.drawCircle(width/2, height/2, radius, paint);
        postInvalidate();

        /*paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRect(150,150,350,350, paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(100);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(250, 250, paint);

        float[] points = {0, 0, 200, 200, 300, 200, 200, 300, 300, 300};
        canvas.drawPoints(points,2,8, paint);

        paint.setStrokeWidth(10);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Style.STROKE);
        canvas.drawOval(150, 200, 350, 300, paint);

        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(5);
        canvas.drawLine(200, 200, 300, 300, paint);

        paint.setTextSize(50);
        canvas.drawText("hello", 220, 270, paint);*/

//        Shader shader = new LinearGradient(50, 50, 450, 450, Color.parseColor("#00FF00"),
//                Color.parseColor("#FF0000"), Shader.TileMode.CLAMP);
//        Shader shader = new RadialGradient(250, 250, 200, Color.parseColor("#FF0000"),
//                 Color.parseColor("#00FF00"), TileMode.REPEAT);
//        Shader shader = new SweepGradient(250, 250, Color.parseColor("#FF0000"),
//                Color.parseColor("#00FF00"));

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.take_phones);
//        @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.talkback2);
//        Bitmap bitmap = new BitmapDrawable(is).getBitmap();
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.take_phones)).getBitmap();
//        Shader shader = new BitmapShader(bitmap,TileMode.REPEAT, TileMode.REPEAT);
//        paint.setShader(shader);
//        canvas.drawCircle(250, 250, 200, paint);
    }
}
