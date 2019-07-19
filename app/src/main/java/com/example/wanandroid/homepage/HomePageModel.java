package com.example.wanandroid.homepage;

import android.content.Context;

import com.example.wanandroid.bean.EssayInfo;
import com.example.wanandroid.bean.Login;
import com.example.wanandroid.bean.banner;
import com.example.wanandroid.http.ServiceRetrofit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePageModel implements HomePageContract.HomePageModel {
    @Override
    public Observable<EssayInfo> getEssayInfo(Context context, int page) {
        //返回一个观察者（包含所需数据）
        return ServiceRetrofit.getInstance()
                .getService()
                .getEssayInfo(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<banner> getBanner(Context context) {
        return ServiceRetrofit.getInstance()
                .getService()
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Login> logout(Context context) {
        return ServiceRetrofit.getInstance()
                .getService()
                .logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
