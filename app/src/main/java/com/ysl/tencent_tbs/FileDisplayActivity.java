package com.ysl.tencent_tbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.http.ApiClient;
import com.ysl.http.ApiUrl;
import com.ysl.myandroidbase.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class FileDisplayActivity extends AppCompatActivity {
    private String TAG = "FileDisplayActivity";
    SuperFileView mSuperFileView;
    String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);
        init();
    }

    public void init() {
        mSuperFileView = findViewById(R.id.mSuperFileView);
        mSuperFileView.setOnGetFilePathListener(new SuperFileView.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });

        Intent intent = this.getIntent();
        String path = (String) intent.getSerializableExtra("path");

        if (!TextUtils.isEmpty(path)) {
            Log.d(TAG, "文件path:" + path);
            setFilePath(path);
        }
        mSuperFileView.show();
    }


    private void getFilePathAndShowFile(SuperFileView mSuperFileView) {
        if (getFilePath().contains("http")) {//网络地址要先下载
            downLoadFromNet(getFilePath(),mSuperFileView);
        } else {
            try {
                File outFile = new File(getCacheDir(), getFilePath());
                if (getFilePath().contains("/")) {
                    outFile.getParentFile().mkdirs();
                }
                copy(getAssets().open(getFilePath()), outFile);

                mSuperFileView.displayFile(outFile);
            } catch (IOException e) {
                Log.e(TAG, "getFilePathAndShowFile: ", e);
            }
        }
    }

    public static void copy(InputStream inputStream, File output) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(output);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"FileDisplayActivity-->onDestroy");
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
    }


    public static void show(Context context, String url) {
        Intent intent = new Intent(context, FileDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("path", url);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public void setFilePath(String fileUrl) {
        this.filePath = fileUrl;
    }

    private String getFilePath() {
        return filePath;
    }

    private void downLoadFromNet(final String url, final SuperFileView mSuperFileView2) {
        //1.网络下载、存储路径、
        File cacheFile = new File(getCacheDir(), getFileName(ApiUrl.URL_DOC));
        Log.d(TAG, "缓存文件 = " + cacheFile.toString());
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                Log.d(TAG, "删除空文件！！");
                cacheFile.delete();
                return;
            }
        }

        ApiClient.initService(DownloadFile.class, url)
                .getDocFile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        Log.d(TAG, "下载文件-->onResponse");
                        boolean flag;
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;
                        try {
                            ResponseBody responseBody = response.body();
                            is = responseBody.byteStream();
                            long total = responseBody.contentLength();

                            File fileN = new File(getCacheDir(), getFileName(ApiUrl.URL_DOC));
                            Log.d(TAG, "创建缓存文件： " + fileN.toString());
                            if (!fileN.exists()) {
                                fileN.createNewFile();
                            }
                            fos = new FileOutputStream(fileN);
                            long sum = 0;
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                                sum += len;
                                int progress = (int) (sum * 1.0f / total * 100);
                                Log.d(TAG, "写入缓存文件" + fileN.getName() + "进度: " + progress);
                            }
                            fos.flush();
                            Log.d(TAG, "文件下载成功,准备展示文件。");
                            mSuperFileView2.displayFile(fileN);
                        } catch (Exception e) {
                            Log.d(TAG, "文件下载异常 = " + e.toString());
                        } finally {
                            try {
                                if (is != null)
                                    is.close();
                            } catch (IOException e) {
                            }
                            try {
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "文件下载失败"+e);
                        File file = new File(getCacheDir(), getFileName(ApiUrl.URL_DOC));
                        if (!file.exists()) {
                            Log.d(TAG, "删除下载失败文件");
                            file.delete();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getFileName(String url) {
        return Md5Tool.hashKey(url) + "."+ getFileType(url);
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d(TAG, "paramString---->null");
            return str;
        }
        Log.d(TAG,"paramString:"+paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG,"i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d(TAG,"paramString.substring(i + 1)------>"+str);
        return str;
    }


}
