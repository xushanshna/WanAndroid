package com.example.wanandroid.net;

import com.example.wanandroid.base.BaseArticle;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.TabBean;
import com.example.wanandroid.bean.WxBean;
import com.example.wanandroid.net.http.ApiServiceManager;
import com.example.wanandroid.net.http.HttpResponseFunc;
import com.example.wanandroid.net.http.ServiceResponseFunc;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 剥离数据，异常处理（服务器返回的错误+网络异常onerror事件）
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
                //拦截服务器返回的错误
                .map(new ServiceResponseFunc<List<BannerBean>>())
                //拦截onError事件
                .onErrorResumeNext(new HttpResponseFunc<List<BannerBean>>());
    }

    public Observable<BaseArticle<List<ArticleBean>>> getHomeList(int page) {
        return getObservable(service.getHomeList(page)
                .map(new ServiceResponseFunc<BaseArticle<List<ArticleBean>>>()));
    }

    public Observable<List<WxBean>> getWx() {
        return getObservable(service.getWx()
                .map(new ServiceResponseFunc<List<WxBean>>()))
                .onErrorResumeNext(new HttpResponseFunc<List<WxBean>>());
    }

    public Observable<BaseArticle<List<ArticleBean>>> getArticleList(int id, int page) {
        return getObservable(service.getArticleList(id, page))
                .map(new ServiceResponseFunc<BaseArticle<List<ArticleBean>>>());
    }

    public Observable<List<TabBean>> getTab() {
        return getObservable(service.getTab())
                .map(new ServiceResponseFunc<List<TabBean>>())
                .onErrorResumeNext(new HttpResponseFunc<List<TabBean>>());
    }

    public Observable<BaseArticle<List<ArticleBean>>> getProject(int page, int cid) {
        return getObservable(service.getProject(page, cid))
                .map(new ServiceResponseFunc<BaseArticle<List<ArticleBean>>>());
    }
}
