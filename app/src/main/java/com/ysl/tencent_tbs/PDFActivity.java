package com.ysl.tencent_tbs;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.ysl.myandroidbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFActivity extends AppCompatActivity {
    @BindView(R.id.pdf)
    PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);

        loadPDF();
    }

    private void loadPDF() {
        pdfView.fromAsset("syf.pdf")
                .defaultPage(0)//设置默认显示第0页
                .swipeHorizontal(false)//pdf文档翻页是否是水平翻页，默认是左右滑动翻页
                .enableSwipe(true)//是否允许翻页，默认是允许翻
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                    }
                })//绘图监听
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {

                    }
                })//设置加载监听
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                })//设置翻页监听
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {

                    }
                })
                .load();
    }
}
