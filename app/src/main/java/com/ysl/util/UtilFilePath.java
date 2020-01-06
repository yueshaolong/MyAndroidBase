package com.ysl.util;


import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 获取抓拍、录像路径类
 *
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



    /**
     * 获取录像目录
     *
     * @return Video dir path.
     * @since V1.0
     */
    public static File getVideoDirPath() {
        File SDFile = null;
        File mIVMSFolder = null;
        try {
            SDFile = Environment.getExternalStorageDirectory();
            mIVMSFolder = new File(SDFile.getAbsolutePath() + File.separator + "BlueSky");
            if ((null != mIVMSFolder) && (!mIVMSFolder.exists())) {
                mIVMSFolder.mkdir();
                mIVMSFolder.createNewFile();
            }
        } catch (IOException e) {
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
