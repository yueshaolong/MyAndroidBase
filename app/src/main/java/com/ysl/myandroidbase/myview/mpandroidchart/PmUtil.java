package com.ysl.myandroidbase.myview.mpandroidchart;

import android.text.TextUtils;


import com.ysl.myandroidbase.R;

import java.util.Random;

/**
 * Created by wangjl on 2018/5/9 0009.
 * email: wang6600@hotmail.com
 */
public class PmUtil {

    private PmUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断风向,风力为0时，显示无风。
     *
     * @param windPower 风力
     * @param dic       风向
     */
    public static String getWindDic(String windPower, String dic) {
        if (Double.valueOf(windPower) == 0) {
            return "无风";
        }
        if (dic == null || dic.equals("")) {
            String[] windDirection = {"东风", "南风", "西风", "北风", "东南风", "东北风", "西南风", "西北风"};
            return windDirection[new Random().nextInt(8)];
        }
        char[] obj = dic.toCharArray();
        String windHtm = "";
        switch (obj.length) {
            case 1:
                if (containsOBJ(obj, "E")) {
                    windHtm = "东";
                }
                if (containsOBJ(obj, "W")) {
                    windHtm = "西";
                }
                if (containsOBJ(obj, "S")) {
                    windHtm = "南";
                }
                if (containsOBJ(obj, "N")) {
                    windHtm = "北";
                }
                break;
            case 2:
                if (containsOBJ(obj, "E") && !containsOBJ(obj, "W")) {
                    windHtm = "东";
                }
                if (containsOBJ(obj, "W") && !containsOBJ(obj, "E")) {
                    windHtm = "西";
                }
                if (containsOBJ(obj, "S") && !containsOBJ(obj, "N")) {
                    windHtm += "南";
                }
                if (containsOBJ(obj, "N") && !containsOBJ(obj, "S")) {
                    windHtm += "北";
                }
                break;
            case 3:
                if ((containsOBJ(obj, "E") && containsOBJ(obj, "W"))
                        || (containsOBJ(obj, "S") && containsOBJ(obj, "N"))) {
                    windHtm = "旋风";
                } else {
                    if (containsOBJ(obj, "E") && !containsOBJ(obj, "W")) {
                        windHtm = "东";
                    }
                    if (containsOBJ(obj, "W") && !containsOBJ(obj, "E")) {
                        windHtm = "西";
                    }
                    if (containsOBJ(obj, "S") && !containsOBJ(obj, "N")) {
                        windHtm += "南";
                    }
                    if (containsOBJ(obj, "N") && !containsOBJ(obj, "S")) {
                        windHtm += "北";
                    }
                }
                break;
            case 4:
                if ((containsOBJ(obj, "E") && containsOBJ(obj, "W"))
                        || (containsOBJ(obj, "S") && containsOBJ(obj, "N"))) {
                    windHtm = "旋风";
                } else {
                    if (containsOBJ(obj, "E") && !containsOBJ(obj, "W")) {
                        windHtm = "东";
                    }
                    if (containsOBJ(obj, "W") && !containsOBJ(obj, "E")) {
                        windHtm = "西";
                    }
                    if (containsOBJ(obj, "S") && !containsOBJ(obj, "N")) {
                        windHtm += "南";
                    }
                    if (containsOBJ(obj, "N") && !containsOBJ(obj, "S")) {
                        windHtm += "北";
                    }
                }
                break;
        }
        return windHtm + "风";
    }

    /***/
    private static boolean containsOBJ(char[] arr, String obj) {
        for (char str : arr) {
            if (obj.equals(String.valueOf(str))) {
                return true;
            }
        }
        return false;
    }

    public static int getPMColor(String pm10) {
        int tmpColor = R.color.red;
        if (TextUtils.isEmpty(pm10))
            tmpColor = R.color.pm_noline;
        else {
            Double value = Double.valueOf(pm10);
            if (value == 0) {
                tmpColor = R.color.pm_noline;
            } else if (value > 0 && value <= 50) {
                tmpColor = R.color.pm_you;
            } else if (value > 50 && value <= 150) {
                tmpColor = R.color.pm_liang;
            } else if (value > 150 && value <= 250) {
                tmpColor = R.color.pm_qing;
            } else if (value > 250 && value <= 350) {
                tmpColor = R.color.pm_center;
            } else if (value > 350 && value <= 420) {
                tmpColor = R.color.pm_zhong;
            } else if (value > 420) {
                tmpColor = R.color.pm_zhongplus;
            }
        }
        return tmpColor;
    }
}