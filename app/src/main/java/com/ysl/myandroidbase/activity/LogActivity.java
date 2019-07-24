package com.ysl.myandroidbase.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.base.LogUtil;
import com.example.base.LoggerUtil;
import com.ysl.myandroidbase.BuildConfig;
import com.ysl.myandroidbase.R;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LogActivity extends AppCompatActivity {
    private static final String TAG = "LogActivity";
    public static Logger logger = Logger.getLogger("LogActivity");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.d(TAG, "测试logd！");
        LogUtil.i(TAG, "测试logi！");
        LogUtil.w(TAG, "测试logw！");
        LogUtil.e(TAG, "测试loge！");

        LogActivityPermissionsDispatcher.needWithPermissionCheck(this);
        logger.debug("c测试logd！");
        logger.info("c测试logi！");
        logger.warn("c测试logw！");
        logger.error("c测试loge！");
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void need() {
        if(BuildConfig.DEBUG){
            LoggerUtil.configLogger(Level.ALL);//显示所有日志
        }else {
            LoggerUtil.configLogger(Level.OFF);//关闭日志
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
