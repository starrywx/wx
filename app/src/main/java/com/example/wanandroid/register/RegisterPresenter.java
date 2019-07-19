package com.example.wanandroid.register;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.wanandroid.bean.Login;
import com.example.wanandroid.homepage.MainActivity;
import com.example.wanandroid.login.LoginActivity;

import io.reactivex.functions.Consumer;

public class RegisterPresenter implements RegisterContract.RegisterPresenter {

    private RegisterContract.RegisterView registerView;
    private RegisterContract.RegisterModel registerModel;

    @Override
    public void register(final Context context, String username, String password, String rePassword) {
        registerModel.register(context, username,password,rePassword).subscribe(new Consumer<Login>() {
            @Override
            public void accept(Login login) throws Exception {
                if(login.getErrorCode()!=0){
                    registerView.showToast(login.getErrorMsg());
                }else {
                    registerView.showToast("注册成功！请登录");
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);

                }
            }
        });
    }

    @Override
    public void attach(RegisterContract.RegisterView view) {
        registerModel=new RegisterModel();
        this.registerView =view;
    }

    @Override
    public void detachView(RegisterContract.RegisterView view) {
        registerView = null;
        registerModel = null;
    }
}
