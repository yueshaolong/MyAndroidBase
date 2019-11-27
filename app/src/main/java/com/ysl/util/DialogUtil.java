package com.ysl.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.ysl.myandroidbase.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogUtil {

    public static void showTipsDialog(Context context, OnClickListener listener, String title){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_tips, null, false);
        ((TextView)view.findViewById(R.id.title)).setText(title);

        DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(view))
                .setContentBackgroundResource(R.color.transparent)
                .setMargin(DisplayUtil.dip2px(context, 40), 0,
                        DisplayUtil.dip2px(context, 40), 0)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOnClickListener(listener)
                .create()
                .show();
    }

    public static void showListDialog(Context context, OnItemClickListener listener, String title,
                                      List<ICheckType> checkType, ICheckType checked){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_header, null, false);
        ((TextView)view.findViewById(R.id.title)).setText(title);

        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ListHolder())
                .setHeader(view)
                .setAdapter(new ListDialogAdapter(context, checkType, checked))
                .setOnItemClickListener(listener)
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .create();
        dialogPlus.show();
        dialogPlus.getHeaderView().findViewById(R.id.cancel).setOnClickListener(view1 -> {
            if(dialogPlus.isShowing()){
                dialogPlus.dismiss();
            }
        });
    }

    public static class ListDialogAdapter extends BaseAdapter {

        private List<ICheckType> checkType;
        private Context context;
        private ICheckType checked;

        public ListDialogAdapter(Context context, List<ICheckType> checkType, ICheckType checked) {
            this.checkType = checkType;
            this.context = context;
            this.checked = checked;
        }

        @Override
        public int getCount() {
            return checkType.size();
        }

        @Override
        public Object getItem(int position) {
            return checkType.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderList viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_list_item, null, false);
                viewHolder = new ViewHolderList(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolderList) convertView.getTag();
            }

            viewHolder.tv_check_type.setText(checkType.get(position).getValue());
            if(checked != null && checked.equals(checkType.get(position))){
                viewHolder.iv_choice.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_choice.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }
    }

    public static class ViewHolderList{
        @BindView(R.id.iv_choice)
        ImageView iv_choice;
        @BindView(R.id.tv_check_type)
        TextView tv_check_type;
        public ViewHolderList(View view){
            ButterKnife.bind(this, view);
        }
    }

    public static void showListDialog(Context context, OnItemClickListener listener, String title,
                                      List<String> stringList, String checked){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_header, null, false);
        ((TextView)view.findViewById(R.id.title)).setText(title);
        (view.findViewById(R.id.cancel)).setVisibility(View.GONE);

        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ListHolder())
                .setHeader(view)
                .setAdapter(new StringListDialogAdapter(context, stringList, checked))
                .setOnItemClickListener(listener)
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .create();
        dialogPlus.show();
        dialogPlus.getHeaderView().findViewById(R.id.cancel).setOnClickListener(view1 -> {
            if(dialogPlus.isShowing()){
                dialogPlus.dismiss();
            }
        });
    }
    public static class StringListDialogAdapter extends BaseAdapter {

        private List<String> checkType;
        private Context context;
        private String checked;

        public StringListDialogAdapter(Context context, List<String> checkType, String checked) {
            this.checkType = checkType;
            this.context = context;
            this.checked = checked;
        }

        @Override
        public int getCount() {
            return checkType.size();
        }

        @Override
        public Object getItem(int position) {
            return checkType.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderList viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_list_item, null, false);
                viewHolder = new ViewHolderList(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolderList) convertView.getTag();
            }

            viewHolder.tv_check_type.setText(checkType.get(position));
            if(!TextUtils.isEmpty(checked) && checked.equals(checkType.get(position))){
                viewHolder.iv_choice.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_choice.setVisibility(View.GONE);
            }

            return convertView;
        }
    }
}
