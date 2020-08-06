package com.ysl.util;


import android.content.Context;
import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;

import java.io.File;

/**
 * 创建文件类
 */
public class UtilFilePath {

    /**
     * 获取图片目录
     * @return Pictrue dir path.
     */
    public static File getPictureDirPath(Context context) {
        File mIVMSFolder = null;
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .getAbsolutePath() + File.separator + "preventpro";
//            if (App.isAndroidQ) {
//                path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
//                        + File.separator + "Photo";
//            } else {
//                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                        .getAbsolutePath() + File.separator + "preventpro";
//            }
            mIVMSFolder = new File(path);
            if (!mIVMSFolder.exists()) {
                mIVMSFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIVMSFolder;
    }

    public static File getLogDirPath() {
        File mIVMSFolder = null;
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .getAbsolutePath() + File.separator + AppUtils.getAppName();
            mIVMSFolder = new File(path);
            if (!mIVMSFolder.exists()) {
                mIVMSFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIVMSFolder;
    }

    public static String byte2FitMemorySize(long byteNum) {
        if (byteNum <= 0) {
            return String.format("%sB", 0);
        } else if (byteNum < 1024) {
            return String.format("%.2fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format("%.2fK", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format("%.2fM", (double) byteNum / 1048576);
        } else {
            return String.format("%.2fG", (double) byteNum / 1073741824);
        }
    }

}
