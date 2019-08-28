package com.ysl.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

public class ImageResize {
    public static final String TAG = "ImageResize";

    /**
     * 返回压缩图片
     *
     * @param context
     * @param id
     * @param maxW
     * @param maxH
     * @param hasAlpha
     * @return
     */
    public static Bitmap resizeBitmap(Context context, int id, int maxW, int maxH, boolean hasAlpha,
                                      Bitmap reusable) {

        Resources resources = context.getResources();

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true后，再去解析，就只解析 out 参数
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(resources, id, options);

        int w = options.outWidth;
        int h = options.outHeight;
        Log.d(TAG, "resizeBitmap: w="+w+", h="+h+", outMimeType="+options.outMimeType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "resizeBitmap:  outConfig="+options.outConfig);
        }


        options.inSampleSize = calcuteInSampleSize(w, h, maxW, maxH);
        Log.d(TAG, "resizeBitmap: inSampleSize="+options.inSampleSize);

        if (!hasAlpha) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }

        options.inJustDecodeBounds = false;

        // 复用, inMutable 为true 表示易变
//        options.inMutable = true;
//        options.inBitmap = reusable;


        return BitmapFactory.decodeResource(resources, id, options);

    }

    /**
     * 计算 缩放系数
     *
     * @param w  300
     * @param h  449
     * @param maxW
     * @param maxH
     * @return
     */
    private static int calcuteInSampleSize(int w, int h, int maxW, int maxH) {

        int inSampleSize = 1;

        if (w > maxW && h > maxH) {
            inSampleSize = 2;

            while (w / inSampleSize > maxW && h / inSampleSize > maxH) {
                inSampleSize *= 2;
            }

        }

        return inSampleSize;
    }
}
