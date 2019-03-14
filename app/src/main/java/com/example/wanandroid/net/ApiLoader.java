package com.example.wanandroid.net;

import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.BaseArticle;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.TabBean;
import com.example.wanandroid.bean.WxBean;
import com.example.wanandroid.net.base.ApiServiceManager;
import com.example.wanandroid.net.base.TargetData;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 剥离数据
 */

public class ApiLoader {
    private ApiService service;

    public ApiLoader() {
        service = ApiServiceManager.getInstance().create(ApiService.class);
    }

    //处理线程切换
    private <T> Observable<T> getObservable(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<BannerBean>> getBanner() {
        return getObservable(service.getBanner())
                .map(new TargetData<List<BannerBean>>());
    }

    public Observable<BaseArticle<List<ArticleBean>>> getHomeList(int page) {
        return getObservable(service.getHomeList(page)
                .map(new TargetData<BaseArticle<List<ArticleBean>>>()));
    }

    public Observable<List<WxBean>> getWx() {
        return getObservable(service.getWx().map(new TargetData<List<WxBean>>()));
    }

    public Observable<BaseArticle<List<ArticleBean>>> getArticleList(int id, int page) {
        return getObservable(service.getArticleList(id, page))
                .map(new TargetData<BaseArticle<List<ArticleBean>>>());
    }

    public Observable<List<TabBean>> getTab() {
        return getObservable(service.getTab()).map(new TargetData<List<TabBean>>());
    }
}
