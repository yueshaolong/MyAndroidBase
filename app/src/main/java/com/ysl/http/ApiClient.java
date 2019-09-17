package com.ysl.http;


import com.ysl.myandroidbase.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    /*超时时间-默认6秒*/
    private static int connectionTime = 60;
    private static int readTime = 60;
    private static int writeTime = 100;

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static <T> T initService(Class<T> clazz, String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(connectionTime, TimeUnit.SECONDS);
        builder.readTimeout(readTime, TimeUnit.SECONDS);
        builder.writeTimeout(writeTime, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        return retrofit.create(clazz);
    }

}

