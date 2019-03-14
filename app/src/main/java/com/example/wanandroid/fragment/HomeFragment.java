package com.example.wanandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.adapter.HomeAdapter;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.BaseArticle;
import com.example.wanandroid.bean.HomeArticle;
import com.example.wanandroid.net.ApiLoader;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;


public class HomeFragment extends Fragment {
    private static final String KEY_MSG = "msg";
    private String msg;
    private Activity activity;
    private Unbinder unbinder;
    private ApiLoader apiLoader;

    private List<BannerBean> bannerBeanList = new ArrayList<>();//banner
    private List<String> images = new ArrayList<>();//banner图片
    private List<String> titles = new ArrayList<>();//banner文字

    private HomeAdapter homeAdapter;
    private List<HomeArticle> articleList = new ArrayList<>();//文章列表
    private int currPage = -1;//当前页数

    @BindView(R.id.home_banner)
    Banner banner;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    public HomeFragment() {

    }

    public static HomeFragment newInstance(String msg) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(KEY_MSG, msg);
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        unbinder = ButterKnife.bind(this, view);
        apiLoader = new ApiLoader();
        initBanner();
        getBannerData();//获取banner数据
        initRv();
        getHomeListData();//获取列表数据

        return view;
    }

    private void initBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new GlideImageLoader());//设置图片加载器
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(activity, bannerBeanList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startBanner() {
        banner.setImages(images);//设置图片集合
        banner.setBannerTitles(titles);
        banner.start();//banner设置方法全部调用完毕时最后调用
    }

    private void initRv() {
        homeAdapter = new HomeAdapter(activity, articleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setItemAnimator(null);//关闭动画效果
        recyclerView.setHasFixedSize(true);
        homeAdapter.setListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(activity, "点击：" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getBannerData() {
        apiLoader.getBanner()
                .subscribe(new Action1<List<BannerBean>>() {
                    @Override
                    public void call(List<BannerBean> bannerBeans) {
                        bannerBeanList.clear();
                        images.clear();
                        titles.clear();
                        bannerBeanList.addAll(bannerBeans);
                        for (BannerBean bannerBean : bannerBeans) {
                            images.add(bannerBean.getImagePath());
                            titles.add(bannerBean.getTitle());
                        }
                        startBanner();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.d(throwable.getMessage());
                    }
                });
    }

    private void getHomeListData() {
        apiLoader.getHomeList(++currPage)
                .subscribe(new Action1<BaseArticle<List<HomeArticle>>>() {
                    @Override
                    public void call(BaseArticle<List<HomeArticle>> listBaseArticle) {
                        articleList.clear();
                        articleList.addAll(listBaseArticle.getDatas());
                        homeAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
