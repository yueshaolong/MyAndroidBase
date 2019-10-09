package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.ysl.myandroidbase.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private Banner banner;
    private Banner banner2;
    private TextBannerView tvBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        banner = findViewById(R.id.ban1);
        setLocalImage();

        banner2 = findViewById(R.id.ban2);
        setNetImage();

        tvBanner = findViewById(R.id.tv_banner);
        setVerticalText();
    }

    private void setVerticalText() {
        //设置数据
        List<String> list = new ArrayList<>();
        list.add("学好Java、Android、C#、C、ios、html+css+js");
//        list.add("走遍天下都不怕！！！！！");
//        list.add("不是我吹，就怕你做不到，哈哈");
//        list.add("superluo");
//        list.add("你是最棒的，奔跑吧孩子！");

        //调用setDatas(List<String>)方法后,TextBannerView自动开始轮播
        //注意：此方法目前只接受List<String>类型
        tvBanner.setDatas(list);



        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        /**这里可以设置带图标的数据（1.0.2新方法），比setDatas方法多了带图标参数；
         第一个参数：数据 。
         第二参数：drawable.
         第三参数:drawable尺寸。
         第四参数:图标位置仅支持Gravity.LEFT、Gravity.TOP、Gravity.RIGHT、Gravity.BOTTOM
         */
//        tvBanner.setDatasWithDrawableIcon(list,drawable,18, Gravity.LEFT);


        //设置TextBannerView点击监听事件，返回点击的data数据, 和position位置
        tvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Log.i("点击了：",String.valueOf(position)+">>"+data);
                tvBanner.stopViewAnimator();
            }
        });
    }

    private void setNetImage() {
        List<String> images = new ArrayList<>();
        images.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        images.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        images.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        images.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        List<String> titles = new ArrayList<>();
        titles.add("美眉");
        titles.add("美女");
        titles.add("上课");
        titles.add("开始");
        banner2.setImages(images)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load((String) path).into(imageView);
                    }
                })
                .isAutoPlay(true)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)//圆形指示器和标题，并且水平分布
                .setOnBannerListener(position -> {
                    Toast.makeText(BannerActivity.this, images.get(position), Toast.LENGTH_SHORT).show();
                })
                .setBannerTitles(titles)
//                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(2000)
                .start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvBanner.startViewAnimator();
    }


    private void setLocalImage() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.girl);
        images.add(R.drawable.talkback2);
        images.add(R.mipmap.circle_pre);
        images.add(R.mipmap.home);
        images.add(R.mipmap.message);
        images.add(R.mipmap.mine);
        images.add(R.mipmap.found);
        images.add(R.mipmap.take_phones);

        List<String> titles = new ArrayList<>();
        for (Integer i : images) {
            titles.add(getResources().getResourceName(i));
        }

        banner.setImages(images)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        imageView.setBackgroundResource((Integer) path);
                    }
                })
                .isAutoPlay(true)
//                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)//圆形指示器
//                .setBannerStyle(BannerConfig.NUM_INDICATOR)//数字指示器
//                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)//圆形指示器和标题，并且垂直分布
//                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)//圆形指示器和标题，并且水平分布
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)//数字指示器和标题，并且水平分布
                .setOnBannerListener(position -> {
                    Toast.makeText(BannerActivity.this, images.get(position), Toast.LENGTH_SHORT).show();
                })
                .setBannerTitles(titles)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setDelayTime(2000)
                .start();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
        tvBanner.stopViewAnimator();
    }
}
