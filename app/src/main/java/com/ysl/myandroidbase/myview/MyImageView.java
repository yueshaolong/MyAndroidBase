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
    private float radius;
    private int diameter;
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

        int widthMeasure = 0;
        int heightMeasure = 0;

        System.out.println("父控件的要求：widthMeasureSpec = "+widthMeasureSpec+", heightMeasureSpec = "+heightMeasureSpec);

        /**
         * 第一种使用方法：先调用super.onMeasure()，然后再调用getMeasuredWidth() 和 getMeasuredHeight()；
         * 获取到父类给我们测量的值后，就可以根据自己的需求来设置宽高了，比如让宽高相等...
         */
        // 先用 getMeasuredWidth() 和 getMeasuredHeight() 取到 super.onMeasure() 的计算结果
        /*super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasure = getMeasuredWidth();//如果不调用super.onMeasure()，此值为0.
        heightMeasure = getMeasuredHeight();//如果不调用super.onMeasure()，此值为0.
        System.out.println("调用super后测量的结果：widthMeasure = "+widthMeasure+", heightMeasure = "+heightMeasure);*/

        /**
         * 第二种使用方法：（不能调用super.onMeasure()方法，可以注释掉或直接删除）
         * 1.通过MeasureSpec.getMode()和MeasureSpec.getSize()获取测量模式和测量大小
         * 2.根据不同的模式进行赋值：
         * MeasureSpec.EXACTLY（精确模式，表现形式为xml文件中layout开头的宽高值设置为具体数值或match_parent）
         * 表示精确的方式，直接返回MeasureSpec.getSize()返回的值
         * MeasureSpec.AT_MOST（xml中使用wrap_content）
         * 表示view想要多大；注意：：此时就需要计算了，view不能想要多大就多大，它的最大值不能超过MeasureSpec.getSize()测量
         * 出来的值（即不能超过父view指定的值），两者做比较，取小者即可。
         */
        //获取控件被要求的宽度大小和模式
        //宽度模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //宽度大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        System.out.println("MeasureSpec测量的结果：widthMode = "+widthMode+", widthSize = "+widthSize);
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                widthMeasure = widthSize;//精确模式（250dp或match_parent）
                break;
            case MeasureSpec.AT_MOST://最大值不能超过上面计算的widthSize
                int w = getPaddingLeft() + getPaddingRight() + width;
                System.out.println(widthSize+"<>"+w);
                widthMeasure = w > widthSize ? widthSize : w;
                break;
            case MeasureSpec.UNSPECIFIED://这种情况一般不存在
            default:
                widthMeasure = getPaddingLeft() + getPaddingRight() + width;
                break;
        }
        System.out.println("最终的宽度："+widthMeasure);

        //高度和上面的宽度计算方式一样
        //获取控件的高度大小和模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        System.out.println("不调用super，MeasureSpec测量的结果：heightMode = "+heightMode+", heightSize = "+heightSize);
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                heightMeasure = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                int h = getPaddingTop() + getPaddingBottom() + height;
                System.out.println(heightSize+"<>"+h);
                heightMeasure = h > heightSize ? heightSize : h;
                break;
            case MeasureSpec.UNSPECIFIED://这种情况一般不存在
            default:
                heightMeasure = getPaddingTop() + getPaddingBottom() + height;
                break;
        }
        System.out.println("最终的高度："+heightMeasure);

        /**
         * 第三种方式；调用resolveSize(int size, int measureSpec)方法，进行测算；这是系统的方法。
         * 第一个参数传入自己想要的大小；第二个参数传入父view规定的尺寸大小。
         * 事实上，可以点进去看resolveSize(int size, int measureSpec)的源码；源码中的处理方式大致上和
         * 第二种方法差不多，都是要通过父view的规定模式和自己想要的大小进行判断，最终确定控件的大小。
         *//*
        widthMeasure = resolveSize(getPaddingLeft() + getPaddingRight() + width, widthMeasureSpec);
        heightMeasure = resolveSize(getPaddingTop() + getPaddingBottom() + height, heightMeasureSpec);
        System.out.println("调用resolveSize(int size, int measureSpec)方法，测量的结果：widthMeasure = "
                +widthMeasure+", heightMeasure = "+heightMeasure);*/

        //通过计算，获得自己想要的半径
        if (widthMeasure > heightMeasure){
            diameter = heightMeasure;
        }else {
            diameter = widthMeasure;
        }
        System.out.println("最终的直径："+diameter);

        /**宽高都计算好了，千万别忘记调用setMeasuredDimension()把它们都存起来，否则白忙活了。*/
        setMeasuredDimension(widthMeasure, heightMeasure);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int width1 = getWidth();
        int height1 = getHeight();
        System.out.println("onLayout--->width = "+width+", height = "+height+"，width1 = "+width1+", height1 = "+height1);

        /*LayoutParams layoutParams = getLayoutParams();
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
        paint.setStrokeWidth(diameter/10);
        paint.setAntiAlias(true);
        canvas.drawCircle(diameter/2, diameter/2, diameter/2-diameter/10/2, paint);
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
