package com.example.wanandroid.homepage;

import android.content.Context;

import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.base.BaseView;
import com.example.wanandroid.bean.EssayInfo;
import com.example.wanandroid.bean.Login;
import com.example.wanandroid.bean.banner;

import java.util.List;

import io.reactivex.Observable;

public interface HomePageContract {

    interface HomePageView extends BaseView{

        //显示文章列表
        void showList(List<EssayInfo.DataBean.DatasBean> list);

        void showBanner(List<banner.DataBean> list, List<String> bannerImageList, List<String> bannerTitleList);

    }

    interface HomePageModel extends BaseModel{

        Observable<EssayInfo> getEssayInfo(Context context, int page);

        Observable<banner> getBanner(Context context);

        Observable<Login> logout(Context context);
    }

    interface HomePagePresenter extends BasePresenter<HomePageView,HomePageModel>{

        //初始化文章列表数据
        void initDatas(Context context, int page);

        void initBanner(Context context);

        //处理下拉刷新数据
        void refreshDatas(Context context);

        void logout(Context context);
    }
}
