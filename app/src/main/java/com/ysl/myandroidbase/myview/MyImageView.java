package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.ysl.myandroidbase.R;

public class MyImageView extends AppCompatImageView {

    private Paint paint;
    private Context context;

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
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得原有的宽高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        //通过计算，获得自己想要的宽高
        if (measuredHeight > measuredWidth){
            measuredHeight = measuredWidth;
        }else {
            measuredWidth = measuredHeight;
        }
        //把高度传给父view保存
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*canvas.drawColor(Color.parseColor("#88008800"));

        paint.setColor(Color.RED);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(50);
        paint.setAntiAlias(true);
        canvas.drawCircle(250, 250, 200, paint);

        paint.setColor(Color.BLUE);
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
        Drawable drawable = context.getDrawable(R.drawable.ic_launcher_background);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Shader shader = new BitmapShader(bitmap,TileMode.CLAMP, TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(250, 250, 200, paint);
    }
}
