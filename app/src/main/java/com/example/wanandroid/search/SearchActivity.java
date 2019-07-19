package com.example.wanandroid.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseActivity;
import com.example.wanandroid.bean.EssayInfo;
import com.example.wanandroid.essayDetail.WebActivity;
import com.example.wanandroid.homepage.MainActivity;
import com.example.wanandroid.homepage.adapter.EssayAdapter;
import com.example.wanandroid.homepage.adapter.OnItemClickListener;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchContract.SearchView {

    private List<EssayInfo.DataBean.DatasBean> resultList = new ArrayList<>();
    private SearchContract.SearchPresenter searchPresenter;
    private static InputMethodManager manager;
    private EssayAdapter essayAdapter;
    @BindView(R.id.btn_search)
    Button searchBtn;
    @BindView(R.id.edt_search)
    EditText searchEdt;
    @BindView(R.id.imv_delete)
    ImageView deleteImv;
    @BindView(R.id.recv_result)
    RecyclerView resultRecv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        searchPresenter = new SearchPresenter();
        searchPresenter.attach(this);
        searchBtn.setSelected(true);
        searchEdt.requestFocus();//获取焦点
        deleteImv.setVisibility(View.GONE);//隐藏删除图标
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        resultRecv.setLayoutManager(layoutManager);
        essayAdapter= new EssayAdapter(resultList);
        resultRecv.setAdapter(essayAdapter);
        //监听输入框
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            //文本改变前执行
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            //文本改变时执行
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    deleteImv.setVisibility(View.GONE);//隐藏删除图标
                    resultRecv.setVisibility(View.GONE);//将列表隐藏
                } else {
                    deleteImv.setVisibility(View.VISIBLE);//显示删除图标
                    showResult();//显示搜索结果
                }
            }
            @Override
            //文本改变后执行
            public void afterTextChanged(Editable s) {
            }
        });
        //删除点击事件
        deleteImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdt.setText("");//将输入框置为空
                resultRecv.setVisibility(View.GONE);//将列表隐藏
            }
        });
        //搜索点击事件
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchEdt.getText().toString())) {
                    Toast.makeText(getBaseContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    deleteImv.setVisibility(View.VISIBLE);//显示删除图标
                    showResult();//显示搜索结果
                    //软键盘相关设置
                    if (manager == null) {
                        manager = ((InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
                    }
                    manager.hideSoftInputFromWindow(( SearchActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        //点击跳转至文章内容
        essayAdapter.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                intent.putExtra("link",resultList.get(position).getLink());
                intent.putExtra("title",resultList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    private void showResult(){
        resultRecv.setVisibility(View.VISIBLE);
        String keyword = searchEdt.getText().toString().trim();
        searchPresenter.getResult(SearchActivity.this,0,keyword);
    }

    @Override
    public void showList(List<EssayInfo.DataBean.DatasBean> list) {
        resultList.clear();
        resultList.addAll(list);
    }

    @Override
    protected void onDestroy() {
        searchPresenter.detachView(this);
        super.onDestroy();
    }
}
