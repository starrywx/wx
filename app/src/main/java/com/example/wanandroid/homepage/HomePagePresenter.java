package com.example.wanandroid.homepage;

import android.content.Context;
import com.example.wanandroid.bean.EssayInfo;
import com.example.wanandroid.bean.Login;
import com.example.wanandroid.bean.banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class HomePagePresenter implements HomePageContract.HomePagePresenter {
    private HomePageContract.HomePageView homePageView;
    private HomePageContract.HomePageModel homePageModel= new HomePageModel();
    private int page;
    private List<String> bannerImageList = new ArrayList<>();
    private List<String> bannerTitleList = new ArrayList<>();
    private int number;


    @Override
    public void initDatas(Context context,int page) {
        homePageModel.getEssayInfo(context,page).subscribe(new Consumer<EssayInfo>() {
            @Override
            public void accept(EssayInfo essayInfo) throws Exception {
                homePageView.showList(essayInfo.getData().getDatas());
            }
        });
    }

    @Override
    public void initBanner(Context context) {
        homePageModel.getBanner(context).subscribe(new Consumer<banner>() {
            @Override
            public void accept(banner banner) throws Exception {
                number = 0;
                while(number<banner.getData().size()) {
                    bannerImageList.add(banner.getData().get(number).getImagePath());
                    bannerTitleList.add(banner.getData().get(number).getTitle());
                    number=number+1;
                }
                homePageView.showBanner(banner.getData(), bannerImageList, bannerTitleList);
            }
        });
    }

    @Override
    public void refreshDatas(Context context) {
        homePageModel.getEssayInfo(context,0).subscribe(new Consumer<EssayInfo>() {
            @Override
            public void accept(EssayInfo essayInfo) throws Exception {
                homePageView.showList(essayInfo.getData().getDatas());
            }
        });
    }

    @Override
    public void logout(Context context) {
        homePageModel.logout(context).subscribe(new Consumer<Login>() {
            @Override
            public void accept(Login login) throws Exception {
            }
        });
    }


    //绑定view和presenter
    @Override
    public void attach(HomePageContract.HomePageView view) {
        this.homePageView = view;
    }

    @Override
    public void detachView(HomePageContract.HomePageView view) {
        homePageModel=null;
        homePageView=null;
    }
}
