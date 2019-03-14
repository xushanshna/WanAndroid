package com.example.wanandroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.base.BaseRvAdapter;
import com.example.wanandroid.adapter.base.BaseViewHolder;
import com.example.wanandroid.bean.WxBean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/14 0014.
 */

public class WxAdapter extends BaseRvAdapter<WxBean> {

    public WxAdapter(Context context, List<WxBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, WxBean data, final int position) {
        TextView name = holder.getView(R.id.wx_item_tv_name);
        name.setText(data.getName());
    }


    @Override
    protected void bindEvent(final BaseViewHolder holder) {
        super.bindEvent(holder);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (onItemClickListner != null) {
                    onItemClickListner.onItemClick(v, position);
                }
            }
        });
    }


}
