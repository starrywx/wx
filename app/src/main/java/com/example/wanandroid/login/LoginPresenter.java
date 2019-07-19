package com.example.wanandroid.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.wanandroid.bean.Login;
import com.example.wanandroid.homepage.MainActivity;

import io.reactivex.functions.Consumer;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginContract.LoginModel loginModel;
    private LoginContract.LoginView loginView ;


    @Override
    public void attach(LoginContract.LoginView view) {
        this.loginView = view;
        loginModel = new LoginModel();
    }

    @Override
    public void detachView(LoginContract.LoginView view) {
        loginView= null;
        loginModel = null;
    }

    @Override
    public void login(final Context context, final String username, final String password, final boolean isAuto, final boolean isRemember) {
        loginModel.login(context,username,password).subscribe(new Consumer<Login>() {
            @Override
            //只对Next事件做出响应
            public void accept(Login login) throws Exception {
                if(login.getErrorCode()!=0){
                    loginView.showToast(login.getErrorMsg());
                }else {
                    loginModel.saveData(context,username,password,isAuto,isRemember);
                    Log.d("wx",username);
                    loginView.showToast("登录成功！");
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public String username() {
        return loginModel.readUsername();
    }

    @Override
    public String password() {
        return loginModel.readPassword();
    }
}
