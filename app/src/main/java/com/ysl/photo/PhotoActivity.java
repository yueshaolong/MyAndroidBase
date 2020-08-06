package com.ysl.photo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysl.myandroidbase.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;

public class PhotoActivity extends AppCompatActivity {

    private Button photo;
    private ImageView iv;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo);

        photo = findViewById(R.id.photo);
        iv = findViewById(R.id.iv_photo);

        Disposable subscribe = new RxPermissions(this).request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        photo.setOnClickListener(v -> {
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File file = new File(getPictureDirPath().getAbsolutePath(),
                                    System.currentTimeMillis() + ".jpg");
                            path = file.getPath();
                            Uri imageUri = FileProvider.getUriForFile(getApplicationContext(),
                                    "com.ysl.myandroidbase.provider", file);
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            PhotoActivity.this.startActivityForResult(openCameraIntent, 0);
                        });
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (!TextUtils.isEmpty(path) && resultCode == -1) {
                    // 获得图片
                    Bitmap mBitmap = BitmapFactory.decodeFile(path);
                    //添加时间水印
                    Bitmap newBitmap = AddTimeWatermark(mBitmap);
                    saveBitmapFile(newBitmap);

                    Glide.with(getApplicationContext())
                            .load(path)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(iv);
                }
                break;
        }
    }
    public void saveBitmapFile(Bitmap bitmap){
        File file=new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Bitmap AddTimeWatermark(Bitmap mBitmap) {
        //获取原始图片与水印图片的宽与高
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        Bitmap mNewBitmap = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mNewBitmap);
        //向位图中开始画入MBitmap原始图片
        mCanvas.drawBitmap(mBitmap,0,0,null);
        //添加文字
        Paint mPaint = new Paint();
        String mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss EEEE").format(new Date());
        //String mFormat = TingUtils.getTime()+"\n"+" 纬度:"+GpsService.latitude+"  经度:"+GpsService.longitude;
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(sp2px(getApplicationContext(),50));
        //水印的位置坐标
        mPaint.setTextAlign(Paint.Align.CENTER);
        //根据路径得到Typeface
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/xs.ttf");
        mPaint.setTypeface(typeface);
        mCanvas.rotate(-45, (mBitmapWidth * 1) / 2, (mBitmapHeight * 1) / 2);
        mCanvas.drawText(mFormat, (mBitmapWidth * 1) / 2, (mBitmapHeight * 1) / 2, mPaint);
        mCanvas.save();
        mCanvas.restore();

        return mNewBitmap;
    }

    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static File getPictureDirPath() {
        File mIVMSFolder = null;
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "MyAndroidBase" + File.separator + "Photo";
            mIVMSFolder = new File(path);
            if ((null != mIVMSFolder) && (!mIVMSFolder.exists())) {
                mIVMSFolder.mkdirs();
//                mIVMSFolder.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIVMSFolder;
    }
}
