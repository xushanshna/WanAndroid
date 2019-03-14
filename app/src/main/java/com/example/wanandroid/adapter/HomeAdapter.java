package com.example.wanandroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.base.BaseRvAdapter;
import com.example.wanandroid.adapter.base.BaseViewHolder;
import com.example.wanandroid.bean.HomeArticle;

import java.util.List;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 首页列表适配器
 */

public class HomeAdapter extends BaseRvAdapter<HomeArticle> {


    public HomeAdapter(Context context, List<HomeArticle> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void bindEvent(final BaseViewHolder holder) {
        super.bindEvent(holder);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner != null) {
                    onItemClickListner.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    protected void bindData(BaseViewHolder holder, HomeArticle data, int position) {
        TextView textView = holder.getView(R.id.home_item_tv_title);
        textView.setText(data.getTitle());
    }

}
