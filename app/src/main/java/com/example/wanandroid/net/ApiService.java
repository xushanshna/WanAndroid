package com.example.wanandroid.net;

import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.base.BaseArticle;
import com.example.wanandroid.base.BaseBean;
import com.example.wanandroid.bean.TabBean;
import com.example.wanandroid.bean.WxBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 网络接口
 */

public interface ApiService {
    //获取首页banner
    @GET("banner/json")
    Observable<BaseBean<List<BannerBean>>> getBanner();

    //获取首页文章列表
    @GET("article/list/{page}/json")
    Observable<BaseBean<BaseArticle<List<ArticleBean>>>> getHomeList(
            @Path("page") int page
    );

    //公众号列表
    @GET("wxarticle/chapters/json")
    Observable<BaseBean<List<WxBean>>> getWx();

    //公众号文章列表
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseBean<BaseArticle<List<ArticleBean>>>> getArticleList(
            @Path("id") int id,
            @Path("page") int page
    );

    //获取项目分类
    @GET("project/tree/json")
    Observable<BaseBean<List<TabBean>>> getTab();

    //获取项目列表
    @GET("project/list/{page}/json")
    Observable<BaseBean<BaseArticle<List<ArticleBean>>>> getProject(
            @Path("page") int page,
            @Query("cid") int cid
    );

}
