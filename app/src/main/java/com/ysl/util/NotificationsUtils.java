package com.ysl.util;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.ysl.myandroidbase.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class NotificationsUtils {

    public static boolean checkNotifySetting(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，
        // 低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        return manager.areNotificationsEnabled();
    }

    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                    .getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //通知栏权限
    public static void checkNotify(Context context){
        if(checkNotifySetting(context)) return;
        DialogUtil.showTipsDialog(context, (dialog, view1) -> {
            switch (view1.getId()) {
                case R.id.confirm:
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);

                        //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                        intent.putExtra(EXTRA_APP_PACKAGE, context.getPackageName());
                        intent.putExtra(EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);

                        //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                        intent.putExtra("app_package", context.getPackageName());
                        intent.putExtra("app_uid", context.getApplicationInfo().uid);

                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
                        Intent intent = new Intent();

                        //下面这种方案是直接跳转到当前应用的设置界面。
                        //https://blog.csdn.net/ysy950803/article/details/71910806
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                    dialog.dismiss();
                    break;
                case R.id.cancel:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }, "您尚未开启通知权限，点击去开启！");

    }



}
