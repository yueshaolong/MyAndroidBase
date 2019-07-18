package com.ysl.myandroidbase.myview.zidingyiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ysl.myandroidbase.R;

public class MyImageText extends View {

    private static final String TAG ="MyImageText";
    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;
    private String text;
    private int textColor;
    private int textSize;
    private int imageScaleType;
    private Bitmap bitmap;
    private Paint mPaint;
    private Rect mTextBound;
    private Rect rect;
    private int mWidth = 0;
    private int mHeight = 0;
    private int imageId;

    public MyImageText(Context context) {
        this(context, null);
    }

    public MyImageText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyImageText);
        text = ta.getString(R.styleable.MyImageText_text);
        textColor = ta.getColor(R.styleable.MyImageText_textColor, Color.BLACK);
        textSize = ta.getDimensionPixelSize(R.styleable.MyImageText_textSize, 16);
        imageId = ta.getResourceId(R.styleable.MyImageText_image, R.drawable.girl);
        bitmap = BitmapFactory.decodeResource(getResources(), imageId);
        imageScaleType = ta.getInt(R.styleable.MyImageText_imageScaleType, 0);
        ta.recycle();  //注意回收

        Log.i(TAG, "MyImageText: text" + text);
        Log.i(TAG, "MyImageText: imageId" + imageId);

        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(textSize);
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds(text, 0, text.length(), mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);



        switch (widthMode){
            case MeasureSpec.EXACTLY:
                mWidth = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mWidth = Math.min(widthSize, getPaddingLeft() + getPaddingRight() +
                        Math.max(mTextBound.width(), bitmap.getWidth()));
                break;
            default:
                break;
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                mHeight = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mHeight = Math.min(heightSize, getPaddingTop() + getPaddingBottom() +
                        mTextBound.height() + bitmap.getHeight());
                break;
            default:
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(textColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(text, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom() - 10, mPaint);
        } else {
            //正常情况，将字体居中
            canvas.drawText(text, mWidth / 2 - mTextBound.width() * 1.0f / 2,
                    mHeight - getPaddingBottom() - 10, mPaint);
        }
        //取消使用掉的快
        rect.bottom -= mTextBound.height();

        if (imageScaleType == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(bitmap, null, rect, mPaint);
        } else if (imageScaleType == IMAGE_SCALE_CENTER){
            //计算居中的矩形范围
            rect.left = mWidth / 2 - bitmap.getWidth() / 2;
            rect.right = mWidth / 2 + bitmap.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - bitmap.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + bitmap.getHeight() / 2;
            canvas.drawBitmap(bitmap, null, rect, mPaint);
        }


    }

}
