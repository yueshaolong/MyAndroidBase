package com.ysl.photo;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysl.myandroidbase.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class TakePhotoActivity extends AppCompatActivity {
    private String path;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);
        Disposable subscribe = new RxPermissions(this).request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        findViewById(R.id.take_photo).setOnClickListener(v -> takePhoto());
                    } else {
                    }
                });
    }

    private void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(getPictureDirPath().getAbsolutePath(),
                System.currentTimeMillis() + ".jpg");
        path = file.getAbsolutePath();
//        imageUri = FileProvider.getUriForFile(getApplicationContext(),
//                "com.ysl.myandroidbase.provider", file);
        imageUri = createImageUri();
        System.out.println("-------->"+ imageUri);
        //-------->content://com.ysl.myandroidbase.provider/m/MyAndroidBase/mybase/1574394370534.jpg
        System.out.println("-------->"+path);
        //-------->/storage/emulated/0/MyAndroidBase/mybase/1574394370534.jpg
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            List<ResolveInfo> resInfoList = getApplicationContext().getPackageManager()
                    .queryIntentActivities(openCameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getApplicationContext().grantUriPermission(packageName, imageUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
        TakePhotoActivity.this.startActivityForResult(openCameraIntent, 0);
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
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyAndroidBase/mybase");
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case 0:
                    if (!TextUtils.isEmpty(path) && resultCode == -1) {
                        Bitmap mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        saveBitmapFile(mBitmap);
                    }
                    break;
                default:
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片并发送广播通知数据库刷新，只有这样才能在相册里面看到这张照片
     * @param bitmap
     */
    public void saveBitmapFile(Bitmap bitmap){
        //将要保存图片的路径
        File file = new File(path);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //发送广播给系统，刷新数据库
//        Uri uri = Uri.fromFile(file);
//        System.out.println("imageUri------>"+uri);
//        //imageUri------>file:///storage/emulated/0/MyAndroidBase/mybase/1574394370534.jpg
//        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
//        sendBroadcast(localIntent);

        //下面这种扫描的方法也可以刷新数据库
        new SingleMediaScanner(this.getApplicationContext(),
                getPictureDirPath().getAbsolutePath(),
                new SingleMediaScanner.ScanListener() {
                    @Override public void onScanFinish() {
                        Log.i("SingleMediaScanner", "scan finish!");
                    }
                });
    }
    public static File getPictureDirPath() {
        File mIVMSFolder = null;
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .getAbsolutePath() + File.separator + "MyAndroidBase" + File.separator + "mybase";
            mIVMSFolder = new File(path);
            if (!mIVMSFolder.exists()) {
                mIVMSFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIVMSFolder;
    }
}
