package com.example.wanandroid.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.bean.EssayInfo;
import com.example.wanandroid.bean.banner;
import com.example.wanandroid.essayDetail.WebActivity;
import com.example.wanandroid.homepage.adapter.EssayAdapter;
import com.example.wanandroid.homepage.adapter.OnItemClickListener;
import com.example.wanandroid.login.LoginActivity;
import com.example.wanandroid.search.SearchActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.PUT;

public class MainActivity extends BaseActivity implements HomePageContract.HomePageView {

    ActionBar actionBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_to_search)
    Button btnToSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_essay_title)
    SwipeRefreshLayout swipeRefreshEssayTitle;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.dwl_menu)
    DrawerLayout dwlMenu;
    @BindView(R.id.banner)
    Banner banner;

    private HomePageContract.HomePagePresenter homePagePresenter;
    private EssayAdapter essayAdapter;
    private List<EssayInfo.DataBean.DatasBean> essayList = new ArrayList<>();
    private List<banner.DataBean> bannerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homePagePresenter = new HomePagePresenter();
        homePagePresenter.attach(this);
        setActionBar();//设置标题栏
        setHomeAsUp();//设置导航按钮
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        essayAdapter = new EssayAdapter(essayList);
        recyclerView.setAdapter(essayAdapter);
        homePagePresenter.initDatas(MainActivity.this, 0);
        homePagePresenter.initBanner(MainActivity.this);
        swipeRefreshEssayTitle.setColorSchemeResources(R.color.colorGray);

        //侧滑菜单中点击事件逻辑处理
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.im_login:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        menuItem.setVisible(true);
                        break;
                    case R.id.im_exit:
                        homePagePresenter.logout(MainActivity.this);
                        Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        menuItem.setVisible(false);
                }
                return true;
            }
        });

        //点击搜索
        btnToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //点击跳转至文章内容
        essayAdapter.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("link", essayList.get(position).getLink());
                intent.putExtra("title", essayList.get(position).getTitle());
                startActivity(intent);
            }
        });

        //下拉刷新
        swipeRefreshEssayTitle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshEssayTitle.setRefreshing(false);
                homePagePresenter.refreshDatas(MainActivity.this);

            }
        });

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("link", bannerList.get(position).getUrl());
                intent.putExtra("title", bannerList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }


    @Override //点击导航按钮打开侧滑菜单
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dwlMenu.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    //将Toolbar设置为标题栏
    private void setActionBar() {
        toolbar.setTitle("");          //将默认标题设为空
        setSupportActionBar(toolbar);
    }

    //设置导航按钮用于展示侧滑菜单
    private void setHomeAsUp() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
    }


    @Override
    public void showList(List<EssayInfo.DataBean.DatasBean> list) {
        this.essayList.addAll(list);
        essayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        homePagePresenter.detachView(this);
        super.onDestroy();
    }

    @Override
    public void showBanner(List<banner.DataBean> list, List<String> bannerImageList, List<String> bannerTitleList) {
        this.bannerList.addAll(list);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)  //显示圆形指示器和标题（水平显示）
                .setImageLoader(new MyLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(3000)
                .isAutoPlay(true)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setImages(bannerImageList)
                .setBannerTitles(bannerTitleList)
                .start();
    }


    private class MyLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView){
            Glide.with(context.getApplicationContext())
                    .load((String)path)
                    .into(imageView);
        }
    }



}
