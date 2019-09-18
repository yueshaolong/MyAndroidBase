package com.ysl.tencent_tbs;

import com.ysl.http.ApiUrl;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;

public interface DownloadFile {
    @GET(ApiUrl.URL_DOC)
    Observable<Response<ResponseBody>> getDocFile();
}
