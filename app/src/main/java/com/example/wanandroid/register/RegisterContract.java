package com.example.wanandroid.register;

import android.content.Context;
import android.widget.EditText;

import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.base.BaseView;
import com.example.wanandroid.bean.Login;

import io.reactivex.Observable;

public interface RegisterContract {

    interface RegisterView extends BaseView{
        void showToast(String msg);
        String getEdt(EditText edt);

    }
     interface RegisterModel extends BaseModel{
        Observable<Login> register(Context context, String username, String password, String rePassword);
     }

    interface RegisterPresenter extends BasePresenter<RegisterView,RegisterModel>{
        void register(Context context, String username, String password, String rePassword);
    }
}
