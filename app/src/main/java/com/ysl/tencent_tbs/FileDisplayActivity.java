package com.ysl.tencent_tbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.smtt.sdk.TbsReaderView;
import com.ysl.myandroidbase.R;

import java.io.File;


public class FileDisplayActivity extends AppCompatActivity {
    private String TAG = "FileDisplayActivity";
    private TbsReaderView mTbsReaderView;

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, FileDisplayActivity.class);
        intent.putExtra("path", url);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);
        TBSUtils.initTbs(this);
        initTbsView();
        init();
    }
    private void initTbsView() {
        mTbsReaderView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                Log.d(TAG, "onCallBackAction: " + integer);
            }
        });
        ViewGroup viewById = findViewById(android.R.id.content);
        viewById.addView(mTbsReaderView);
    }
    public void init() {
        Intent intent = this.getIntent();
        if(intent == null) return;
        String path = intent.getStringExtra("path");
        Log.d(TAG, "文件path:" + path);
        if (!TextUtils.isEmpty(path)){
            setTbsTemp();
            if (path.toLowerCase().startsWith("http")) {
                File fileN = TBSUtils.createFile(TBSUtils.getFileName(path));
                if (!fileN.exists() || fileN.length() == 0) {
                    TBSUtils.downloadFile(path, fileN);
                }
                openFile(fileN.getAbsolutePath());
            } else {
                openFile(path);
            }
        }
    }

    private void openFile(String path) {
        boolean bool = mTbsReaderView.preOpen(TBSUtils.getFileType(path), false);
        if (bool) {
            Bundle bundle = new Bundle();
            bundle.putString("filePath", path);
            bundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp");
            mTbsReaderView.openFile(bundle);
        }
    }


    private void setTbsTemp() {
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = "/storage/emulated/0/TbsReaderTemp";
        File bsReaderTempFile = new File(bsReaderTemp);

        if (!bsReaderTempFile.exists()) {
            Log.d(TAG, "准备创建/storage/emulated/0/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                Log.e(TAG, "创建/storage/emulated/0/TbsReaderTemp失败！！！！！");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onStopDisplay();
    }
    /**
     * 在停止展示时记得要调用onStop方法，否则再次打开文件会显示一直加载中。
     */
    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
