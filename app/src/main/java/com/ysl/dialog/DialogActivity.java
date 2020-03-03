package com.ysl.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.ysl.myandroidbase.R;
import com.ysl.util.DialogUtil;
import com.ysl.util.ICheckType;
import com.ysl.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity {

    private String type = "日常班中巡检";
    ICheckType tp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button14, R.id.button15, R.id.button16})
    public void c(View view) {
        switch (view.getId()) {
            case R.id.button14:
                DialogUtil.showTipsDialog(this, new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                                    case R.id.confirm:
                                        ToastUtils.showToast(DialogActivity.this, "点击确认");
                                        dialog.dismiss();
                                        break;
                                    case R.id.cancel:
                                        ToastUtils.showToast(DialogActivity.this, "点击取消");
                                        dialog.dismiss();
                                        break;
                                    default:

                                        break;
                                }
                    }
                }, "是否提醒当前待办任务负责人完成任务?");
//                DialogPlus.newDialog(this)
//                        .setContentHolder(new ViewHolder(R.layout.dialog_content))
////                        .setHeader(R.layout.dialog_header)
//                        .setCancelable(true)
//                        .setGravity(Gravity.TOP)
//                        .setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(DialogPlus dialog, View view) {
//                                switch (view.getId()) {
//                                    case R.id.confirm:
//                                        ToastUtils.showToast(DialogActivity.this, "点击确认");
//                                        dialog.dismiss();
//                                        break;
//                                    case R.id.cancel:
//                                        ToastUtils.showToast(DialogActivity.this, "点击取消");
//                                        dialog.dismiss();
//                                        break;
//                                    default:
//
//                                        break;
//                                }
//                            }
//                        })
//                        .create()
//                        .show();
                break;
            case R.id.button15:
                List<String> checkType = new ArrayList<>();
                checkType.add("每周项目部综合性检查");
                checkType.add("项目季节性检查");
                checkType.add("日常班中巡检");
                DialogUtil.showListDialog(this, new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        type = checkType.get(position);
                        dialog.dismiss();
                    }
                }, "选择排查类型", checkType, type);
                break;
            case R.id.button16:
                List<ICheckType> checkType2 = new ArrayList<>();
                ICheckType c1 = new ICheckType() {
                    @Override
                    public String getValue() {
                        return "所有人";
                    }

                    @Override
                    public String getKey() {
                        return "syr";
                    }


                };
                ICheckType c2 = new ICheckType() {
                    @Override
                    public String getValue() {
                        return "hhh";
                    }

                    @Override
                    public String getKey() {
                        return "jj";
                    }


                };
                ICheckType c3 = new ICheckType() {
                    @Override
                    public String getValue() {
                        return "算电费";
                    }

                    @Override
                    public String getKey() {
                        return "shd";
                    }


                };
                checkType2.add(c1);
                checkType2.add(c2);
                checkType2.add(c3);
                checkType2.add(c1);
                checkType2.add(c2);
                checkType2.add(c3);
                DialogUtil.showGridDialog(this, new DialogUtil.OnConfirmListener() {
                    @Override
                    public void onConfirm(ICheckType checked) {
                        System.out.println(checked.getKey() + "=======选择的============" + checked.getValue());
                    }
                }, "选择排查类型", checkType2, tp);

                break;
            default:
        }
    }
}
