package com.ysl.windowmanager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class WindowActivity extends AppCompatActivity {

    private static final String TAG = "WindowActivity";
    @BindView(R.id.btn_show)
    Button btn_show;
    @BindView(R.id.btn_hide)
    Button btn_hide;
    private WindowManager windowManager;
    private ImageView imageView;
    private LayoutParams layoutParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        ButterKnife.bind(this);
        WindowActivityPermissionsDispatcher.needWithPermissionCheck(this);

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        imageView = new ImageView(this);
        imageView.setBackground(getResources().getDrawable(R.drawable.girl));

        layoutParams = new LayoutParams();
        layoutParams.width = 300;
        layoutParams.height = 500;
        layoutParams.x = 100;
        layoutParams.y = 100;
        layoutParams.gravity = Gravity.START|Gravity.TOP;
        layoutParams.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_FOCUSABLE;
    }

    boolean isShow;
    @OnClick({R.id.btn_hide, R.id.btn_show})
    public void showOrHide(View view){
        System.out.println("点击");
        switch (view.getId()){
            case R.id.btn_show:
                if (imageView != null && layoutParams != null && !isShow) {
                    Log.i(TAG, "showOrHide: show");
                    windowManager.addView(imageView,layoutParams);
                    isShow = true;
                }
                break;
            case R.id.btn_hide:
                if (imageView != null && isShow) {
                    Log.i(TAG, "showOrHide: hide");
                    windowManager.removeView(imageView);
                    isShow = false;
                }
                break;
            default:
        }
    }

    @NeedsPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
    void need() {
        Toast.makeText(this, "need", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WindowActivityPermissionsDispatcher.onActivityResult(this, requestCode);
    }

    @OnShowRationale(Manifest.permission.SYSTEM_ALERT_WINDOW)
    void show(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("此APP需要以下权限，下一步将请求权限")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.SYSTEM_ALERT_WINDOW)
    void denied() {
        Toast.makeText(this, "已拒绝权限", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.SYSTEM_ALERT_WINDOW)
    void neverAsk() {
        Toast.makeText(this, "已拒绝，并不再询问", Toast.LENGTH_SHORT).show();
    }
}
