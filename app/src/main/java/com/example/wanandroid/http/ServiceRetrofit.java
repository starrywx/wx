package com.example.wanandroid.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRetrofit {
    public final String BASE_URL = "https://www.wanandroid.com/";
    private static ServiceRetrofit serviceRetrofit;
    private Retrofit retrofit;
    private Service service;

    private ServiceRetrofit(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())   //自动解析为json对象类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //使其支持RxJava
                .client(okHttpClient.build())
                .build();
        service = retrofit.create(Service.class);
    }

    public static ServiceRetrofit getInstance(){
        if(serviceRetrofit == null){
            synchronized (Object.class){
                if(serviceRetrofit == null){
                    serviceRetrofit = new ServiceRetrofit();
                }
            }
        }
        return serviceRetrofit;
    }

    public Service getService(){
        return service;
    }
}
