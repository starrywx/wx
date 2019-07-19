package com.example.wanandroid.essayDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录
    private static final String LINK = "link";
    private static final String TITLE = "title";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wv_essay)
    WebView wvEssay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        //获取传递的数据
        Intent intent = getIntent();
        String link = intent.getStringExtra(LINK);
        String title = intent.getStringExtra(TITLE);
        setActionBar(title);
        setHomeAsUp();
        setWeb(); //设置缓存
        wvEssay.setWebViewClient(new WebViewClient());
        wvEssay.loadUrl(link);

    }

    private void setWeb(){
        WebSettings settings = wvEssay.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存模式
        settings.setDomStorageEnabled(true);//开启DOM storage API功能
        settings.setDatabaseEnabled(true);//开启database storage API功能
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        settings.setAppCachePath(cacheDirPath); // 设置Application caches缓存目录
        settings.setAppCacheEnabled(true); // 开启Application Cache功能
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //将Toolbar设置为标题栏
    private void setActionBar(String title){
        toolbar.setTitle("");
        tvTitle.setText(Html.fromHtml(title));//将默认标题设为空
        setSupportActionBar(toolbar);
    }

    //设置导航按钮用于展示侧滑菜单
    private void setHomeAsUp(){
        getSupportActionBar();
        if( getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
    }
}
