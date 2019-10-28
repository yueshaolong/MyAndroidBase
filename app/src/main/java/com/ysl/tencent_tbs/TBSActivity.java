package com.ysl.tencent_tbs;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.myandroidbase.R;

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
    private String filePath;
    private List<String> datas = new ArrayList<>();
    private List<String> paths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        initDatas();
        initPaths();
        TBSActivityPermissionsDispatcher.needWithPermissionCheck(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                return new RecyclerView.ViewHolder(LayoutInflater.from(TBSActivity.this)
                        .inflate(R.layout.item_tbs, parent, false)) {
                };

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                holder.itemView.findViewById(R.id.item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        filePath = getFilePath(position);
                        FileDisplayActivity.show(TBSActivity.this, filePath);
                    }
                });
                ((TextView) holder.itemView.findViewById(R.id.item_tv)).setText(getDatas().get(position));
            }

            @Override
            public int getItemCount() {
                return getDatas().size();
            }
        });
    }

    private void initPaths() {
    }

    private void initDatas() {
        datas.add("网络获取并打开doc文件");
        datas.add("打开本地doc文件");
        datas.add("打开本地txt文件");
        datas.add("打开本地excel文件");
        datas.add("打开本地ppt文件");
        datas.add("打开本地pdf文件");
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
                path = "http://www.hrssgz.gov.cn/";
                break;
            case 1:
                path =  "test.docx";
                break;
            case 2:
                path = "test.txt";
                break;
            case 3:
                path = "test.xlsx";
                break;
            case 4:
                path = "test.pptx";
                break;
            case 5:
                path = "test.pdf";
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
