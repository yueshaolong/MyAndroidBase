package com.ysl.myandroidbase.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ysl.myandroidbase.R;

public class WebViewActivity extends AppCompatActivity {
    public static final String TAG = "WebViewActivity";
    private WebView wv;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button = findViewById(R.id.b);
        wv = findViewById(R.id.wv);

        WebSettings settings = wv.getSettings();

        //允许使用js
        settings.setJavaScriptEnabled(true);

        //允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        settings.setDisplayZoomControls(false);

        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
//        settings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
//        settings.setLoadWithOverviewMode(true);

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //设置可以访问文件
        settings.setAllowFileAccess(true);

        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);

        //设置编码格式
        settings.setDefaultTextEncodingName("utf-8");

//        wv.loadUrl("http://www.baidu.com");

        //加载asset文件夹下html
        // 格式规定为:file:///android_asset/文件名.html
        wv.loadUrl("file:///android_asset/testJS.html");

        //使用webview显示html代码
//        wv.loadDataWithBaseURL(null,"<html><head><title> 欢迎您 </title></head>" +
//                "<body><h2>使用webview显示 html代码</h2></body></html>", "text/html" , "utf-8", null);

        //添加js监听 这样html就能调用客户端
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript要调用的Java对象
        //参数2：Java对象名,这个名字可以任意起，但要和js调用的地方一致
        wv.addJavascriptInterface(this,"android");

        //WebViewClient主要帮助WebView处理各种通知、请求事件
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG,"onPageStarted--->开始加载了--->"+url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG,"onPageFinished--->加载结束了--->"+url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"shouldOverrideUrlLoading--->url--->"+url);
                if(url.equals("http://www.google.com/")){
                    Toast.makeText(WebViewActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
                    return true;//表示我已经处理过了
                }
                //true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器。默认false。
                try {
                    if(url.startsWith("http:") || url.startsWith("https:") ) {
                        view.loadUrl(url);
                    }else{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
//                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d(TAG,"onReceivedTitle--->获取到网页的标题--->"+title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG,"onJsAlert--->获取到JS--->"+message);
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(wv.getContext());
                localBuilder.setTitle("Alert")
                        .setMessage(message)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定",null)
                        .setCancelable(false)
                        .create()
                        .show();

                //注意:
                //必须要这一句代码:result.confirm()表示:
                //处理结果为确定状态同时唤醒WebCore线程
                //否则不能继续点击按钮
                result.confirm();
                //返回true，拦截系统的alert提示；
                return true;
//                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG,"onProgressChanged--->正在加载中--->"+newProgress);
            }
        });

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 注意调用的JS方法名要对应上
                // 调用javascript的callJS()方法
                if (VERSION.SDK_INT < VERSION_CODES.KITKAT) {
                    wv.loadUrl("javascript:callJS()");
                } else {
                    wv.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            System.out.println("value -----------------》 "+value);
                            Toast.makeText(WebViewActivity.this, "evaluateJavascript:"+value, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG,"是否有上一个页面:"+wv.canGoBack());
        if (wv.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            wv.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    @Override
    protected void onDestroy() {
        if(wv != null){
            wv.clearHistory();
            ((ViewGroup)wv.getParent()).removeView(wv);
            wv.destroy();
            wv = null;
        }
        super.onDestroy();
    }

    /**
     * JS调用android的方法
     */
    @JavascriptInterface
    public void  getClient(String str){
        Log.d(TAG,"html调用客户端:"+str);
        Toast.makeText(WebViewActivity.this, str, Toast.LENGTH_SHORT).show();
    }
}
