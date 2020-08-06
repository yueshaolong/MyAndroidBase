package com.ysl.tencent_tbs;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.ysl.myandroidbase.BuildConfig;
import com.ysl.util.UtilFilePath;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class TBSUtils {
    public static final String TAG = "TBSUtils";
    public static final Logger logger = Logger.getLogger(TAG);
    public static boolean isInitFinished;//此标记不是很准确，因为有的手机上发现onViewInitFinished不会回调
    public static void initTbs(Context context) {
//        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(context,new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                logger.info("onCoreInitFinished: ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //回调接口初始化完成接口回调，onViewInitFinished
                // 参数中为 true 代表 x5 内核初始化完成，
                //false 为系统内核初始化完成。此参数可以传 Null，无影响。
                isInitFinished = b;
                logger.info("onViewInitFinished: b="+b);
            }
        });
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                //tbs内核下载完成回调
                logger.info("onDownloadFinish: i="+i);
            }

            @Override
            public void onInstallFinish(int i) {
                //内核安装完成回调，
                logger.info("onInstallFinish: i="+i);
            }

            @Override
            public void onDownloadProgress(int i) {
                //下载进度监听
                logger.info("onDownloadProgress: i="+i);
            }
        });
    }

    public static void openFileReader(Context context, String filePath, ValueCallback<String> valueCallback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("local", "true");//“true”表示是进入文件查看器，如果不设置或设置为“false”，则进入 miniqb 浏览器模式。不是必须设置项。
        params.put("style", "0");//“0”表示文件查看器使用默认的 UI 样式。“1”表示文件查看器使用微信的 UI 样式。不设置此 key或设置错误值，则为默认 UI 样式。
//        params.put("topBarBgColor", "#4D7CFE");//定制文件查看器的顶部栏背景色。格式为“#xxxxxx”，例“#2CFC47”;不设置此 key 或设置错误值，则为默认 UI 样式。
        JSONObject Object = new JSONObject();
        try {
            Object.put("pkgName",context.getApplicationContext().getPackageName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("menuData",Object.toString());
//        QbSdk.getMiniQBVersion(context);
        int i = QbSdk.openFileReader(context, filePath, params, valueCallback);
//        1：用 QQ 浏览器打开
//        2：用 MiniQB 打开
//        3：调起阅读器弹框
//        -1：filePath 为空 打开失败
        logger.info("打开文件结果：i="+i);
    }
    public static void openFile(Context context, File file) {
        TBSUtils.openFileReader(context,file.getAbsolutePath(),new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.d("", "onReceiveValue: " + s);
            }
        });
    }
    public static File createFile(String filePath){
        File dir = new File(UtilFilePath.getLogDirPath()+ File.separator + "cache");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File fileN = new File(dir, filePath);
        Log.d("------>", "创建缓存文件： " + fileN.toString());
        if (fileN.exists()){
            if (fileN.length() > 0){
                Log.d(TAG, "createFile: 文件存在，不用下载了");
            }else {
                Log.d(TAG, "createFile: 文件存在，但大小为0，删除文件");
                fileN.delete();
            }
        }else {
            Log.d(TAG, "createFile: 文件不存在");
        }
        return fileN;
    }
    public static void downLoadFromNet(Context context, final String filePath) {
        File fileN = TBSUtils.createFile(TBSUtils.getFileName(filePath));
        if (fileN.exists() && fileN.length() > 0) {
            openFile(context, fileN);
            return;
        }
        doFile(context, filePath, fileN);
    }
    public static void doFile(Context context, String filePath, File fileN) {
        Log.d("", "downFile: filePath="+filePath);
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                File file = TBSUtils.downloadFile(filePath, fileN);
                emitter.onNext(file);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onComplete() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File fileN) {
                        openFile(context, fileN);
                    }
                });
    }
    public static File downloadFile(String filePath, File fileN) {
        HttpLoggingInterceptor httpLoggingInterceptor;
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor = new HttpLoggingInterceptor();
        } else {
            httpLoggingInterceptor = new HttpLoggingInterceptor(
                    message -> Log.d(TAG, "downFile: "+message));
        }
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(2000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Request request = new Request.Builder()
                .url(filePath)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    ResponseBody responseBody = response.body();
//                        Log.d(TAG, "下载文件-->onResponse："+responseBody.string());
//                        BaseBean baseBean = new Gson().fromJson(responseBody.string(), BaseBean.class);
//                        Log.d(TAG, "下载文件-->onResponse："+baseBean.state+":"+baseBean.message);
//                        if(!baseBean.state){
//                            downloadError(filePath);
//                            subscriber.onCompleted();
//                            return;
//                        }
                    is = responseBody.byteStream();
                    long total = responseBody.contentLength();

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
                        e.printStackTrace();
                    }
                }
            }
        });
        return fileN;
    }
    public static String getFileName(String url) {
        return Md5Tool.hashKey(url) + "."+ getFileType(url);
    }
    public static String getFileType(String paramString) {
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
