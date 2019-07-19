package com.example.wanandroid.search;

import android.content.Context;
import com.example.wanandroid.bean.EssayInfo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import io.reactivex.functions.Consumer;

public class SearchPresenter implements SearchContract.SearchPresenter {

    private SearchContract.SearchView searchView;
    private SearchContract.SearchModel searchModel;

    @Override
    public void attach(SearchContract.SearchView view) {
        this.searchView = view;
        searchModel = new SearchModel();
    }

    @Override
    public void detachView(SearchContract.SearchView view) {
        searchModel = null;
        searchView = null;
    }



    @Override
    public void getResult(Context context, int page, String keyword) {
        searchModel.query(context, page, keyword).subscribe(new Consumer<EssayInfo>() {
            @Override
            public void accept(EssayInfo essayInfo) throws Exception {
                searchView.showList(essayInfo.getData().getDatas());
            }
        });
    }



}
