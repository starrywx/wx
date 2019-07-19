package com.example.wanandroid.login;

import android.content.Context;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.base.BaseView;
import com.example.wanandroid.bean.Login;

import io.reactivex.Observable;

public interface LoginContract {

    interface LoginView extends BaseView{
        void showToast(String msg);
        String getUsername();
        String getPassword();
        void setEdt(String username,String password);
    }

    interface LoginModel extends BaseModel{
        Observable<Login> login(Context context,String username,String password);

        void saveData(Context context, String username,String password,boolean isAuto,boolean isRemember);

        String readUsername();
        String readPassword();

    }

    interface LoginPresenter extends BasePresenter<LoginView,LoginModel>{
        void login(Context context,String username,String password,boolean isAuto,boolean isRemember);
        String  username();
        String password();
    }
}
