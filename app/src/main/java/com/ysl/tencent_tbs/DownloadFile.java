package com.ysl.tencent_tbs;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DownloadFile {
    @GET("bgxz/sydwrybgxz/201101/P020110110748901718161.doc")
    Observable<File> getDocFile();
}
