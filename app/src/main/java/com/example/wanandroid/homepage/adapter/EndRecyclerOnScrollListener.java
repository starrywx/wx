package com.example.wanandroid.homepage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用于标记是否向上滑动
    private boolean isSlidingUp = false;

    //滑动状态被改变时调用
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //列表不滑动
        if(newState == RecyclerView.SCROLL_STATE_IDLE){
            //获取最后一个完全显示的item的position
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            //获取列表的长度
            int itemCount = manager.getItemCount();
            //是否滑到最后一个且向上滑动
            if(lastItemPosition == (itemCount-1)&&isSlidingUp){
                //加载的状态
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //向上滑动的时候dy是大于0的，向左滑动的时候dx是大于0的
        isSlidingUp = dy>0;
    }

    public abstract void onLoadMore();
}
