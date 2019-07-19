package com.example.wanandroid.search;

import android.content.Context;

import com.example.wanandroid.bean.EssayInfo;
import com.example.wanandroid.http.ServiceRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchModel implements SearchContract.SearchModel {

    @Override
    public Observable<EssayInfo> query(Context context, int page, String keyword) {
        return ServiceRetrofit.getInstance()
                .getService()
                .query(page,keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
