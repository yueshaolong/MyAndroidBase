package com.ysl.photo;

public class AppConstant {

    /**
     * 摄像头模式
     */
    public final static int CAMERA_MODE = 0;
    /**
     * 视频播放器模式
     */
    public final static int VIDEO_MODE = 1;

    /**
     * 视频最长的时长是10s
     */
    public final static int VIDEO_MAX_TIME = 10;

    /**
     * 视频播放模式
     */
    public final static int VIDEO_PLAY_MODE = 0;

    /**
     * 视频录像模式
     */
    public final static int VIDEO_RECORD_MODE = 1;

    /**
     * 拍照模式
     */
    public final static int VIDEO_TAKE_PHOTO = 2;

    /**
     * 当前面板是预览状态
     */
    public final static int TEXTURE_PREVIEW_STATE = 0;

    /**
     * 当前面板是录像状态
     */
    public final static int TEXTURE_RECORD_STATE = 1;

    /**
     * 当前面板是图片状态
     */
    public final static int TEXTURE_PHOTO_STATE = 2;

    /**
     * 当前面板是视频播放状态
     */

    public final static int TEXTURE_PLAY_STATE = 3;

    /**
     * 当前是摄像头模式还是视频播放模式
     */
    public int MODE;

    /**
     * 当前的模式，默认为拍照模式
     */
    public int NOW_MODE = VIDEO_TAKE_PHOTO;


    /**
     * 感觉数组
     */
    public static String[] senseArr = {"DISABLED", "FACE_PRIORITY", "ACTION", "PORTRAIT", "LANDSCAPE", "NIGHT"
            , "NIGHT_PORTRAIT", "THEATRE", "BEACH", "SNOW", "SUNSET", "STEADYPHOTO", "FIREWORKS",
            "SPORTS", "PARTY", "CANDLELIGHT", "BARCODE"};

    public static String[] effectArr = {"aqua", "blackboard", "monoColor", "negative", "posterization", "sepia"
            , "solarisation", "whiteboard", "off"};

    public interface KEY{
        String IMG_PATH = "IMG_PATH";
        String VIDEO_PATH = "VIDEO_PATH";
        String PIC_WIDTH = "PIC_WIDTH";
        String PIC_HEIGHT = "PIC_HEIGHT";
    }

    public interface RESULT_CODE {
        int RESULT_OK = -1;
        int RESULT_CANCELED = 0;
        int RESULT_ERROR = 1;
    }

    /**
     * 底部状态 ： 曝光
     */
    public static final int SHOW_AE = 1;
    /**
     * 白平衡
     */
    public static final int SHOW_AWB = 2;
    /**
     * 感光度
     */
    public static final int SHOW_ISO = 3;
    /**
     * 放大 倍数
     */
    public static final int SHOW_ZOOM = 4;
    /**
     * effect
     */
    public static final int SHOW_EFFECT = 5;
    /**
     * sense
     */
    public static final int SHOW_SENSE = 6;

    /**
     * 手动设置
     */
    public static final int SHOW_SETTING = 6;


    /**
     * lab  rgb 数组
     */
    public static double[][] labmap = {{37.986,13.555,14.059},{65.711,18.13,17.81},{49.927,-4.88,-21.925},
            {43.139,-13.095,21.905} ,{55.112,8.844,-25.399},{70.719,-33.397,-0.199},
            {62.661,36.067,57.096},{40.02,10.41,-45.964},{51.124,48.239,16.248},
            {30.325,22.976,-21.587},{72.532,-23.709,57.255},{71.941,19.363,67.857},
            {28.778,14.179,-50.297},{55.261,-38.342,31.37},{42.101,53.378,28.19},
            {81.733,4.039,79.819} ,{51.935,49.986,-14.574},{51.038,-28.631,-28.638},
            {96.539,-0.425,1.186},{81.257,-0.638,-0.335},{66.766,-0.734,-0.504},
            {50.867,-0.153,-0.27} ,{35.656,-0.421,-1.231},{20.461,-0.079,-0.973}};

    public static double [][] rgbmap = {{115,82,68},{194,150,130},{98,122,157},{87,108,67},
            {133,128,177},{103,189,170},{214,126,44},{80,91,166},{193,90,99},{94,60,108},
            {157,188,64},{224,163,46},{56,61,150},{70,148,73},{175,54,60},{231,199,31},
            {187,86,149},{8,133,161},{243,243,242},{200,200,200},{160,160,160},
            {122,122,121},{85,85,85},{52,52,52}};

}
