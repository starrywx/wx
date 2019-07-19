package com.example.wanandroid.base;


public interface BasePresenter<R extends BaseView,T extends BaseModel>{
    //绑定
    void attach(R view);

    //解除绑定
    void detachView(R view);

}
