package com.ysl.pikerview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.contrarywind.view.WheelView.DividerType;
import com.ysl.myandroidbase.R;
import com.ysl.myandroidbase.R2.string;
import com.ysl.util.DateUtils;
import com.ysl.util.Util;

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
    private TimePickerView tpv;
    private TimePickerBuilder tpb;
    private TextView title;
    private TextView an_day;
    private TextView an_month;
    private TextView an_year;
    private TextView cancel;
    private TextView confirm;
    private String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_picker);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.textView3, R.id.dx, R.id.button11, R.id.button12, R.id.button13})
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
                })
                        .setLineSpacingMultiplier(2.5f)
                        .setTitleText("选择时间")
                        .setTitleSize(20)//标题文字大小
                        .setContentTextSize(18)//滚轮文字大小
                        .build();
                pvTime.show();
                break;
            case R.id.dx:
                //时间选择器 ，自定义布局
                tpb = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        switch (tag){
                            case "an_day":
                                textView.setText(DateUtils.dateToString(date, DateUtils.formatPattern));
                                break;
                            case "an_month":
                                textView.setText(DateUtils.dateToString(date, DateUtils.format_ym));
                                break;
                            case "an_year":
                                textView.setText(DateUtils.dateToString(date, DateUtils.format_y));
                                break;
                        }

                    }
                })
                        .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                            @Override
                            public void customLayout(final View v) {
                                cancel = v.findViewById(R.id.cancel);
                                confirm = v.findViewById(R.id.confirm);
                                title = v.findViewById(R.id.title);
                                an_day = v.findViewById(R.id.an_day);
                                an_month = v.findViewById(R.id.an_month);
                                an_year = v.findViewById(R.id.an_year);
                                confirm.setOnClickListener(v12 -> {
                                    tpv.returnData();
                                    tpv.dismiss();
                                });
                                cancel.setOnClickListener(v13 -> tpv.dismiss());

                                an_day.setOnClickListener(v1 -> {
                                    boolean isfirst = true;
                                    if (tpv != null && tpv.isShowing()) {
                                        tpv.dismiss();
                                        isfirst = false;
                                    }
                                    tpb.setType(new boolean[]{true, true, true, false, false, false});
                                    tpv = tpb.build();
                                    tpv.show(isfirst);
                                    tag = "an_day";
                                    an_day.setTextColor(getResources().getColor(R.color.blue));
                                    an_month.setTextColor(getResources().getColor(R.color.black));
                                    an_year.setTextColor(getResources().getColor(R.color.black));
                                });
                                an_month.setOnClickListener(v14 -> {
                                    tpv.dismiss();
                                    tpb.setType(new boolean[]{true, true, false, false, false, false});
                                    tpv = tpb.build();
                                    tpv.show(false);
                                    tag = "an_month";
                                    an_day.setTextColor(getResources().getColor(R.color.black));
                                    an_month.setTextColor(getResources().getColor(R.color.blue));
                                    an_year.setTextColor(getResources().getColor(R.color.black));
                                });
                                an_year.setOnClickListener(v15 -> {
                                    tpv.dismiss();
                                    tpb.setType(new boolean[]{true, false, false, false, false, false});
                                    tpv = tpb.build();
                                    tpv.show(false);
                                    tag = "an_year";
                                    an_day.setTextColor(getResources().getColor(R.color.black));
                                    an_month.setTextColor(getResources().getColor(R.color.black));
                                    an_year.setTextColor(getResources().getColor(R.color.blue));
                                });

                            }
                        })
                        .setContentTextSize(18)
//                        .setType(new boolean[]{false, false, false, true, true, true})
//                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .setLineSpacingMultiplier(2.5f);
//                        .setTextXOffset(0, 0, 0, 40, 0, -40)
//                        .isCenterLabel(false);//是否只显示中间选中项的label文字，false则每项item全部都带有label。
                tpv = tpb.build();
                an_day.performClick();
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
//                        .setCancelText("取消")//取消按钮文字
//                        .setSubmitText("确定")//确认按钮文字
                        .setContentTextSize(18)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("待办定时提醒")//标题文字
//                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                        .isCyclic(false)//是否循环滚动
//                        .setTitleColor(Color.BLACK)//标题文字颜色
//                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setLineSpacingMultiplier(2.5f)
//                        .setTitleBgColor(0xFFF3F0F0)//标题背景颜色 Night mode
//                        .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setLabel("年","月","日","时","分","秒")
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime12.show();
                break;
            case R.id.button13:
                List<String> options1Items = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    options1Items.add("所有人");
                    options1Items.add("王某某");
                    options1Items.add("张实时");
                    options1Items.add("号XX");
                }
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
//                        .setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("人员选择")//标题
//                        .setSubCalSize(16)//确定和取消文字大小
                        .setTitleSize(20)//标题文字大小
                        .setContentTextSize(18)//滚轮文字大小
                        .setLineSpacingMultiplier(2.5f)
//                        .setTitleColor(Color.BLACK)//标题文字颜色
//                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFFF3F0F0)//标题背景颜色 Night mode
//                        .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(false)//是否显示为对话框样式
                        .isRestoreItem(true)
                        .build();

                pvOptions.setPicker(options1Items);//添加数据源
                pvOptions.show();
                break;
            default:

                break;
        }
    }
}
