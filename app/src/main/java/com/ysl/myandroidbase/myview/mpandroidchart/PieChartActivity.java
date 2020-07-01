package com.ysl.myandroidbase.myview.mpandroidchart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.ysl.myandroidbase.R;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private SwitchMultiButton switchMultiButton;
    private LineChart lineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        initView();
    }
    private PieChart pieChart,pie_chat2;

    private void initView() {
        pieChart = (PieChart) findViewById(R.id.pie_chat1);
        pie_chat2= (PieChart) findViewById(R.id.pie_chat2);
        barChart = (BarChart) findViewById(R.id.id_lChart_PM);
        switchMultiButton = (SwitchMultiButton) findViewById(R.id.id_smb_chart_date);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        showhodlePieChart();
        showRingPieChart();
        showBarChart();
        showLineChart();
    }

    private void showLineChart() {
        LineChartManager lineChartManager = new LineChartManager(this, lineChart);
        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            float val = (float) (Math.random() * 500);
            values.add(new Entry(i, val,getResources().getDrawable(R.mipmap.star)));
        }
        lineChartManager.setLineChart(values);
    }


    private void showBarChart() {
        List<String> xLabels = new ArrayList<>();
        xLabels.add("11时");
        xLabels.add("12时");
        xLabels.add("13时");
        xLabels.add("14时");
        xLabels.add("15时");
        xLabels.add("16时");
        xLabels.add("17时");
        xLabels.add("18时");
        BarChartManager.configBarChart(barChart, xLabels);
        List<Float> data = new ArrayList<>();
        data.add(67f);
        data.add(58f);
        data.add(55f);
        data.add(46f);
        data.add(4f);
        data.add(69f);
        data.add(92f);
        data.add(200f);
        List<Integer> colors = new ArrayList<>();
        List<BarEntry> entries = BarChartManager.formatChartData(this,data,colors);
        BarChartManager.setBarChart(barChart, entries, "PM柱状图",
                Color.parseColor("#FF9DAAC4"), colors);
    }

    private void showRingPieChart() {
//设置每份所占数量
        List<PieEntry> yvals = new ArrayList<>();
        yvals.add(new PieEntry(10.0f, "本科"));
        yvals.add(new PieEntry(7.0f, "硕士"));
        yvals.add(new PieEntry(4.0f, "博士"));
        yvals.add(new PieEntry(5.0f, "大专"));
        yvals.add(new PieEntry(1.0f, "其他"));
// 设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));
        colors.add(Color.parseColor("#f5a658"));

//        //设置每份所占数量
//        List<PieEntry> signInfo = new ArrayList<>();
//        signInfo.add(new PieEntry(10, "已签到"));
//        signInfo.add(new PieEntry(7, "缺勤"));
//        // 设置每份的颜色
//        List<Integer> colors = new ArrayList<>();
//        colors.add(getResources().getColor(R.color.RED));
//        colors.add(getResources().getColor(R.color.GREEN));


        PieChartManager pieChartManager = new PieChartManager(pie_chat2);
        pieChartManager.showRingPieChart(yvals,colors);
    }

    private void showhodlePieChart() {
        // 设置每份所占数量
        List<PieEntry> yvals = new ArrayList<>();
        yvals.add(new PieEntry(2.0f, "汉族"));
        yvals.add(new PieEntry(3.0f, "回族"));
        yvals.add(new PieEntry(4.0f, "壮族"));
        yvals.add(new PieEntry(5.0f, "维吾尔族"));
        yvals.add(new PieEntry(6.0f, "土家族"));
        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));
        colors.add(Color.parseColor("#58a9f5"));

        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yvals,colors);
    }
}
