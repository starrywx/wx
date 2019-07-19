package com.example.wanandroid.homepage.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.EssayInfo;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class EssayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<EssayInfo.DataBean.DatasBean> mEssayList;
    protected OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View titleView;
        TextView title;
        TextView author;
        TextView niceDate;
        TextView superChapterName;

        //传入参数view为RecyclerView子项的最外层布局
        public ViewHolder(View view) {
            super(view);
            titleView = view;
            title = view.findViewById(R.id.tv_title);
            author=view.findViewById(R.id.tv_author);
            niceDate=view.findViewById(R.id.tv_niceDate);
            superChapterName=view.findViewById(R.id.tv_superChapterName);
        }
    }



    public EssayAdapter(List<EssayInfo.DataBean.DatasBean> essayList) {
        mEssayList = essayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int viewType) {
        //普通的item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.title, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    if (onItemClickListener != null) {
                        viewHolder.title.setTextColor(Color.parseColor("#9E9E9E"));
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        return viewHolder;


    }

    @Override
    //对子项的数据进行赋值
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < mEssayList.size()){
            EssayInfo.DataBean.DatasBean essay=mEssayList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.title.setText(Html.fromHtml(essay.getTitle()));
            viewHolder.author.setText(essay.getAuthor());
            viewHolder.niceDate.setText(essay.getNiceDate());
            viewHolder.superChapterName.setText(essay.getSuperChapterName());

        }

}

    @Override
    //子项的数量
    public int getItemCount() {
        return mEssayList.size();
    }

    @Override
    public int getItemViewType(int position){
       return position;
    }



    public void seOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }



}
