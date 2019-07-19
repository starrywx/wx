package com.example.wanandroid.http;

import com.example.wanandroid.bean.EssayInfo;
import com.example.wanandroid.bean.Login;
import com.example.wanandroid.bean.banner;

import java.lang.annotation.Target;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {

    @GET("article/list/{page}/json")
    Observable<EssayInfo> getEssayInfo(@Path("page") int page);

    @GET("banner/json")
    Observable<banner> getBanner();

    @FormUrlEncoded
    @POST("user/login")
    Observable<Login> login(@Field("username")String username,@Field("password")String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<Login> register(@Field("username")String username,@Field("password")String password,@Field("repassword")String repassword);

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<EssayInfo> query(@Path("page")int page,@Field("k")String keyword);

    @GET("user/logout/json")
    Observable<Login> logout();
}
