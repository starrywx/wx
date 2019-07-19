package com.example.wanandroid.register;

import android.content.Context;

import com.example.wanandroid.bean.Login;
import com.example.wanandroid.http.ServiceRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterModel implements RegisterContract.RegisterModel {
    @Override
    public Observable<Login> register(Context context, String username, String password, String rePassword) {
        return ServiceRetrofit.getInstance()
                .getService()
                .register(username,password,rePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
