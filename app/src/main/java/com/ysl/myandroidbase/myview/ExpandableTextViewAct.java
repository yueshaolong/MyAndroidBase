package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.ysl.myandroidbase.R;
import com.ysl.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class ExpandableTextViewAct extends AppCompatActivity {
    private TextView introduceTV;

    // 介绍是否展开
    private boolean isExpand;
    // 介绍动作runnable
    private Runnable resumeRunnable;
    private String describtionStr = "好像看起来差不多，不过UI验收的时候就过不去了，要求下拉箭头需要跟在文字后面，且要有省略号表示更多内容，那现成的控件是不能使用了，只好自己想办法。因为下拉箭头要跟随文字，所以我们也不能使用drawableRight方法，想了想，那就用SpannableString加ImageSpan来实现试试，大致思路：先判断TextView在页面上可以展示几行";
    private SuperTextView stv;
    private Runnable resumeRunnable1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_expandable_tv);
        introduceTV = findViewById(R.id.textView4);
        stv = findViewById(R.id.stv_risk_item);

        introduceTV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 擅长
                introduceTV.setText(describtionStr);
                resumeRunnable = new LineContent(introduceTV, describtionStr, ExpandableTextViewAct.this, "tv");
                introduceTV.post(resumeRunnable);
                introduceTV.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        stv.getLeftBottomTextView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 擅长
                stv.getLeftBottomTextView().setText(describtionStr);
                resumeRunnable1 = new LineContent(stv.getLeftBottomTextView(), describtionStr, ExpandableTextViewAct.this, "stv");
                stv.getLeftBottomTextView().post(resumeRunnable1);
                stv.getLeftBottomTextView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private class LineContent implements Runnable {
        private TextView textView;
        private String mContent;
        private String tag;
        private Context context;

        public LineContent(TextView textView, String mContent, Context context, String tag) {
            this.textView = textView;
            this.mContent = mContent;
            this.context = context;
            this.tag = tag;
        }

        public void run() {
            if (null != textView && !TextUtils.isEmpty(mContent)) {
                GetLineContent();
            }
        }

        private void GetLineContent() {
            // 得到TextView的布局
            Layout layout = textView.getLayout();
            // 得到TextView显示有多少行
            int lines = textView.getLineCount();
            try {
                if (isExpand) {
                    setGoodAtText(mContent + "\u3000 ", isExpand);
                } else {
                    if (lines > 3) {
                        StringBuffer threeLinesContent = new StringBuffer();
                        for (int i = 0; i < 3; i++) {
                            threeLinesContent.append(mContent.substring(layout.getLineStart(i), layout.getLineEnd(i)));
                            String s = threeLinesContent.substring(0, threeLinesContent.length() - 3) + "...\u3000 ";
                            setGoodAtText(s, false);
                            textView.setOnClickListener(v -> {
                                if (isExpand) {
                                    isExpand = false;
                                } else {
                                    isExpand = true;
                                }
                                Runnable runnable = null;
                                switch (tag){
                                    case "tv":
                                        runnable = resumeRunnable;
                                        break;
                                    case "stv":
                                        runnable = resumeRunnable1;
                                        break;
                                }
                                textView.post(runnable);
                            });
                        }
                    }
                }
            } catch (Exception e) {
                // Do nothing
            }
        }

            private void setGoodAtText(String textContent, boolean expand) {
                SpannableString ss = new SpannableString(textContent);
                Drawable drawable;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getResources().getDrawable(expand ? R.mipmap.close : R.mipmap.open, null);
                } else {
                    drawable = getResources().getDrawable(expand ? R.mipmap.close : R.mipmap.open);
                }
                drawable.setBounds(0, 0, Util.dp2px(context,12), Util.dp2px(context,12));
                ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                ss.setSpan(imageSpan, textContent.length() - 1, textContent.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                textView.setText(ss);
            }
        }

    @Override
    protected void onDestroy() {
        if (null != resumeRunnable) {
            resumeRunnable = null;
        }
        if (null != resumeRunnable1) {
            resumeRunnable1 = null;
        }
        super.onDestroy();
    }

}
