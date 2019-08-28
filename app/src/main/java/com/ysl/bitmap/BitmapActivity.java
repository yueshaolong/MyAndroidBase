package com.ysl.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BitmapActivity extends AppCompatActivity {
    public static final String TAG = "BitmapActivity";
    @BindView(R.id.btn_iv)
    Button btn_iv;
    @BindView(R.id.iv)
    ImageView iv;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);

        ImageCache.getInstance().init(this, Environment.getExternalStorageDirectory() + "/bitmap");
    }

    @OnClick(R.id.btn_iv)
    public void getIv(){
        Bitmap bitmap = getBitmap();
        iv.setImageBitmap(bitmap);
    }

    private Bitmap getBitmap() {
        // 原始方法获取bitmap
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mv);
        Log.d(TAG, "普通加载getBitmap: width="+bitmap.getWidth()
                +", height="+bitmap.getHeight()
                +", AllocationByteCount="+bitmap.getAllocationByteCount()
                +", ByteCount="+bitmap.getByteCount()
                +", Config="+bitmap.getConfig()
                +", Density="+bitmap.getDensity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "getBitmap:  ColorSpace="+bitmap.getColorSpace());
        }

        //第一种优化，压缩的bitmap
        bitmap = ImageResize.resizeBitmap(this, R.drawable.icon_mv, 80, 80,
                false, bitmap);
        Log.d(TAG, "压缩后getBitmap: width="+bitmap.getWidth()
                +", height="+bitmap.getHeight()
                +", AllocationByteCount="+bitmap.getAllocationByteCount()
                +", ByteCount="+bitmap.getByteCount()
                +", Config="+bitmap.getConfig()
                +", Density="+bitmap.getDensity());

        // 第二种优化,内存缓存和磁盘缓存
        bitmap = ImageCache.getInstance().getBitmapFromMemory(String.valueOf(0));
        Log.e(TAG, "使用内存缓存" + bitmap);
        if (bitmap == null) {
            bitmap = ImageCache.getInstance().getReusable(60, 60, 1);
            Log.e(TAG, "使用复用缓存" + bitmap);

            bitmap = ImageCache.getInstance().getBitmapFromDisk(String.valueOf(0), bitmap);
            Log.e(TAG, "使用磁盘缓存" + bitmap);

            if (bitmap == null) {
                // 网络获取,这里演示用本地图片
                bitmap = ImageResize.resizeBitmap(this, R.drawable.icon_mv, 80,
                        80, false, bitmap);
                Log.d(TAG, "网络获取getBitmap: width="+bitmap.getWidth()
                        +", height="+bitmap.getHeight()
                        +", AllocationByteCount="+bitmap.getAllocationByteCount()
                        +", ByteCount="+bitmap.getByteCount()
                        +", Config="+bitmap.getConfig()
                        +", Density="+bitmap.getDensity()
                        +", 压缩后放入内存和磁盘。");
                //放入内存
                ImageCache.getInstance().putBitmap2Memory(String.valueOf(0), bitmap);
                //放入磁盘
                ImageCache.getInstance().putBitmap2Disk(String.valueOf(0), bitmap);
            }else {
                Log.d(TAG, "磁盘缓存getBitmap: width="+bitmap.getWidth()
                        +", height="+bitmap.getHeight()
                        +", AllocationByteCount="+bitmap.getAllocationByteCount()
                        +", ByteCount="+bitmap.getByteCount()
                        +", Config="+bitmap.getConfig()
                        +", Density="+bitmap.getDensity());
            }
        }else {
            Log.d(TAG, "内存缓存getBitmap: width="+bitmap.getWidth()
                    +", height="+bitmap.getHeight()
                    +", AllocationByteCount="+bitmap.getAllocationByteCount()
                    +", ByteCount="+bitmap.getByteCount()
                    +", Config="+bitmap.getConfig()
                    +", Density="+bitmap.getDensity());
        }


        return bitmap;
    }
}
