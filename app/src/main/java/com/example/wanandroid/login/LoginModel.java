package com.example.wanandroid.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.wanandroid.bean.Login;
import com.example.wanandroid.http.ServiceRetrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements LoginContract.LoginModel {

    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    @Override
    public Observable<Login> login(Context context,String username, String password) {
        return ServiceRetrofit.getInstance()
                .getService()
                .login(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveData(Context context,String username, String password,boolean isAuto,boolean isRemember) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
        if(isAuto){
            editor.putBoolean("remember",true);
            editor.putString("username",username);
            editor.putString("password",password);
        }else if (isRemember){
            editor.putBoolean("auto",true);
            editor.putString("username",username);
            editor.putString("password",password);
        }else {
            editor.clear();
        }
        editor.apply();
    }

    @Override
    public String readUsername() {
        return pref.getString("username", "");
    }

    @Override
    public String readPassword() {
        return pref.getString("password", "");
    }
}
