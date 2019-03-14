package com.example.wanandroid.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.ui.activity.ArticleActivity;
import com.example.wanandroid.adapter.WxAdapter;
import com.example.wanandroid.adapter.base.BaseRvAdapter;
import com.example.wanandroid.bean.WxBean;
import com.example.wanandroid.net.ApiLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;


public class WeChatFragment extends Fragment {
    private static final String KEY_MSG = "msg";
    private String msg;
    private Unbinder unbinder;
    private Activity activity;
    private List<WxBean> wxBeanList = new ArrayList<>();
    private WxAdapter wxAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public WeChatFragment() {
    }

    public static WeChatFragment newInstance(String param1) {
        WeChatFragment fragment = new WeChatFragment();
        Bundle args = new Bundle();
        args.putString(KEY_MSG, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString(KEY_MSG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity = getActivity();
        initRv();
        getWxData();
        return view;
    }


    private void initRv() {
        wxAdapter = new WxAdapter(activity, wxBeanList, R.layout.wx_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(wxAdapter);
        wxAdapter.setOnItemClickListner(new BaseRvAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(activity, ArticleActivity.class);
                intent.putExtra("id", wxBeanList.get(position).getId());
                intent.putExtra("name", wxBeanList.get(position).getName());
                activity.startActivity(intent);
            }
        });
    }

    private void getWxData() {
        ApiLoader apiLoader = new ApiLoader();
        apiLoader.getWx().subscribe(new Action1<List<WxBean>>() {
            @Override
            public void call(List<WxBean> wxBeans) {
                wxBeanList.clear();
                wxBeanList.addAll(wxBeans);
                wxAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
