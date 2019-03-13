package com.example.wanandroid.net.base;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 创建service实例
 */

public class ApiServiceManager {
    private Retrofit retrofit;
    private static ApiServiceManager INSTANCE;


    public ApiServiceManager() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ApiConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(ApiConfig.WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(ApiConfig.READ_TIME_OUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);


        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static ApiServiceManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiServiceManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiServiceManager();
                }
            }
        }
        return INSTANCE;
    }


    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

}
