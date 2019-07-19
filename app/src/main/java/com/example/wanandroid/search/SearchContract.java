package com.example.wanandroid.search;

import android.content.Context;

import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.base.BaseView;
import com.example.wanandroid.bean.EssayInfo;

import java.util.List;

import io.reactivex.Observable;

public interface SearchContract {
    interface SearchView extends BaseView{
        void showList(List<EssayInfo.DataBean.DatasBean> list);
    }

    interface SearchModel extends BaseModel{
        Observable<EssayInfo> query(Context context, int page, String keyword);
    }

    interface SearchPresenter extends BasePresenter<SearchView,SearchModel>{
        void getResult(Context context, int page, String keyword);
    }
}
