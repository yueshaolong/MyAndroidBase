package com.ysl.photo;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysl.myandroidbase.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;

public class PhotoActivity extends AppCompatActivity {

    private Button photo;
    private ImageView iv;
    private String path;
    private Uri imageUri;

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
                            System.out.println("---------->"+path);
//                            Uri imageUri = FileProvider.getUriForFile(getApplicationContext(),
//                                    "com.ysl.myandroidbase.provider", file);
                            imageUri = createImageUri();
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            PhotoActivity.this.startActivityForResult(openCameraIntent, 0);
                        });
                    }
                });

    }
    private Uri createImageUri() {
        //设置保存参数到ContentValues中
        ContentValues contentValues = new ContentValues();
        //设置文件名
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis()+"");
        //兼容Android Q和以下版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
            //RELATIVE_PATH是相对路径不是绝对路径;照片存储的地方为：内部存储/Pictures/preventpro
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyAndroidBase/Photo");
        } else {
            contentValues.put(MediaStore.Images.Media.DATA,
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
        }
        //设置文件类型
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG");
        //执行insert操作，向系统文件夹中添加文件
        //EXTERNAL_CONTENT_URI代表外部存储器，该值不变
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return uri;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 0:
                    if (!TextUtils.isEmpty(path) && resultCode == -1) {
                        // 获得图片
                        Bitmap mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        //添加时间水印
                        Bitmap newBitmap = AddTimeWatermark(mBitmap);
                        saveBitmapFile(newBitmap);

                        Glide.with(getApplicationContext())
                                .load(imageUri)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(iv);
                    }
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void saveBitmapFile(Bitmap bitmap){
        File file=new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(getContentResolver().openOutputStream(imageUri));
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
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .getAbsolutePath() + File.separator + "MyAndroidBase" + File.separator + "Photo";
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
