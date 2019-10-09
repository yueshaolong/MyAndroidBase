package com.ysl.pikerview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ysl.myandroidbase.R;
import com.ysl.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerActivity extends AppCompatActivity {
    @BindView(R.id.button11)
    Button button;
    @BindView(R.id.textView3)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_picker);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.textView3, R.id.button11, R.id.button12, R.id.button13})
    public void c(View view){
        switch (view.getId()) {
            case R.id.button11:
                TimePickerView pvTime = new TimePickerBuilder(PickerActivity.this,
                        new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Toast.makeText(PickerActivity.this,
                                DateUtils.dateToString(date, DateUtils.formatPattern_all),
                                Toast.LENGTH_SHORT).show();

                        textView.setText(DateUtils.dateToString(date, DateUtils.formatPattern_1));
                    }
                }).build();
                pvTime.show();
                break;
            case R.id.button12:
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.HOUR_OF_DAY, 18);
                selectedDate.set(Calendar.MINUTE, 0);
//                Calendar startDate = Calendar.getInstance();
//                startDate.set(2013,1,1);
//                Calendar endDate = Calendar.getInstance();
//                endDate.set(2020,1,1);
                TimePickerView pvTime12 = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        textView.setText(DateUtils.dateToString(date, DateUtils.formatPattern_2));
                    }
                })
                        .setType(new boolean[]{false, false, false, true, true, false})//分别对应年月日时分秒，默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentTextSize(18)//滚轮文字大小
                        .setTitleSize(16)//标题文字大小
                        .setTitleText("待办定时提醒")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTitleBgColor(0xFFF3F0F0)//标题背景颜色 Night mode
                        .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
                        .setLabel("年","月","日","时","分","秒")
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime12.show();
                break;
            case R.id.button13:
                List<String> options1Items = new ArrayList<>();
                options1Items.add("所有人");
                options1Items.add("王某某");
                options1Items.add("张实时");
                options1Items.add("号XX");
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this,
                        new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1);
                        textView.setText(tx);
                    }
                }

                )
                        .setSubmitText("确定")//确定按钮文字
                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("人员选择")//标题
                        .setSubCalSize(16)//确定和取消文字大小
                        .setTitleSize(16)//标题文字大小
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setTitleBgColor(0xFFF3F0F0)//标题背景颜色 Night mode
                        .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                        .setContentTextSize(18)//滚轮文字大小
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(false)//是否显示为对话框样式
                        .build();

                pvOptions.setPicker(options1Items);//添加数据源
                pvOptions.show();
                break;
            default:

                break;
        }
    }
}
