package com.example.wanandroid.adapter.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2019/3/14 0014.
 * ViewHolder基类
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    //    SparseArray以key,value的形式保存数据，只需指定value的类型
    private SparseArray<View> views;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    public View getRootView() {
        return itemView;
    }

}
