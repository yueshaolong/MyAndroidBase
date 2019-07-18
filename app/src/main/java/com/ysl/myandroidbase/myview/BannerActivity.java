package com.ysl.myandroidbase.myview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.ysl.myandroidbase.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private Banner banner;
    private Banner banner2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        banner = findViewById(R.id.ban1);
        setLocalImage();

        banner2 = findViewById(R.id.ban2);
        setNetImage();
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
    }
}
