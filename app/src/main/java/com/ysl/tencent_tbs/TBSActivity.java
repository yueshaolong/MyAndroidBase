package com.ysl.tencent_tbs;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.myandroidbase.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class TBSActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private List<String> datas = new ArrayList<>();
    private List<String> paths = new ArrayList<>();
    private boolean isOpenInside;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        TBSActivityPermissionsDispatcher.needWithPermissionCheck(this);
        initDatas();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_open_way);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_openFile) {
                    isOpenInside = true;
                } else if (checkedId == R.id.rb_openFileReader) {
                    isOpenInside = false;
                }
            }
        });
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(TBSActivity.this)
                        .inflate(R.layout.item_tbs, parent, false)) {
                };

            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                ((TextView) holder.itemView.findViewById(R.id.item_tv)).setText(getDatas().get(position));
                holder.itemView.findViewById(R.id.item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFilePath(position);
                        if (TextUtils.isEmpty(filePath)) return;
                        if("http://debugtbs.qq.com".equals(filePath)){
                            //打开调试页面
                            Intent intent = new Intent(TBSActivity.this, BrowserActivity.class);
                            TBSActivity.this.startActivity(intent);
                            return;
                        }
                        if (isOpenInside) {
                            FileDisplayActivity.show(TBSActivity.this, filePath);
                        } else {
                            if (filePath.toLowerCase().startsWith("http")) {
                                TBSUtils.downLoadFromNet(TBSActivity.this, filePath);
                            } else {
                                TBSUtils.openFile(TBSActivity.this, new File(filePath));
                            }
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return getDatas().size();
            }
        });
    }

    private void initDatas() {
        datas.add("网络获取并打开doc文件");
        datas.add("打开本地doc文件");
        datas.add("打开本地txt文件");
        datas.add("打开本地excel文件");
        datas.add("打开本地ppt文件");
        datas.add("打开本地pdf文件");
        datas.add("内核调试");
    }

    private List<String> getDatas() {
        if (datas != null && datas.size() > 0) {
            return datas;
        } else {
            datas = new ArrayList<>();
            initDatas();
            return datas;
        }

    }

    private String getFilePath(int position) {
        String path = null;
        switch (position) {
            case 0:
                path = "http://rsj.gz.gov.cn/attachment/0/101/101466/6485013.docx";
                break;
            case 1:
                path =  "/storage/emulated/0/test.docx";
                break;
            case 2:
                path = "/storage/emulated/0/test.txt";
                break;
            case 3:
                path = "/storage/emulated/0/test.xlsx";
                break;
            case 4:
                path = "/storage/emulated/0/test.pptx";
                break;
            case 5:
                path = "/storage/emulated/0/test.pdf";
                break;
            case 6:
                path = "http://debugtbs.qq.com";
                break;
        }
        return path;
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void need() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TBSActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void rationale(final PermissionRequest request) {
    }

    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denied() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void askAgain() {
    }

}
