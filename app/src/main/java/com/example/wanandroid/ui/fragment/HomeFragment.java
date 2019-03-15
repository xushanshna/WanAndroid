package com.example.wanandroid.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.wanandroid.base.BaseArticle;
import com.example.wanandroid.base.BaseRvAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.net.http.BaseObserver;
import com.example.wanandroid.net.ApiLoader;
import com.example.wanandroid.ui.activity.WebActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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
    private List<ArticleBean> articleList = new ArrayList<>();//文章列表
    private int currPage = -1;//当前页数

    @BindView(R.id.home_banner)
    Banner banner;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

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
        homeAdapter = new HomeAdapter(activity, articleList, R.layout.home_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setItemAnimator(null);//关闭动画效果
        recyclerView.setHasFixedSize(true);

        homeAdapter.setOnItemClickListner(new BaseRvAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("link", articleList.get(position).getLink());
                intent.putExtra("title", articleList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    private void getBannerData() {
        apiLoader.getBanner().subscribe(new BaseObserver<List<BannerBean>>() {
            @Override
            public void onCompleted() {
                startBanner();
            }

            @Override
            public void onNext(List<BannerBean> bannerBeans) {
                bannerBeanList.clear();
                images.clear();
                titles.clear();
                bannerBeanList.addAll(bannerBeans);
                for (BannerBean bannerBean : bannerBeans) {
                    images.add(bannerBean.getImagePath());
                    titles.add(bannerBean.getTitle());
                }
            }
        });
    }

    private void getHomeListData() {
        apiLoader.getHomeList(++currPage).subscribe(new BaseObserver<BaseArticle<List<ArticleBean>>>() {
            @Override
            public void onCompleted() {
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNext(BaseArticle<List<ArticleBean>> listBaseArticle) {
                articleList.clear();
                articleList.addAll(listBaseArticle.getDatas());
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
