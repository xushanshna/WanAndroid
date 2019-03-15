package com.example.wanandroid.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2019/3/14 0014.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<T> datas;
    private int layoutId;
    public OnItemClickListner onItemClickListner;

    public BaseRvAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        bindEvent(holder);
        return holder;
    }

    /**
     * 绑定点击事件
     *
     * @param holder
     */
    protected void bindEvent(BaseViewHolder holder) {
        //绑定点击事件
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        bindData(baseViewHolder, datas.get(i), i);
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param data     数据bean
     * @param position
     */
    protected abstract void bindData(BaseViewHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClick(View v, int position);
    }
}
