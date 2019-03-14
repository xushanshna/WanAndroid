package com.example.wanandroid.net;

import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.BaseArticle;
import com.example.wanandroid.bean.BaseBean;
import com.example.wanandroid.bean.HomeArticle;
import com.example.wanandroid.bean.WxBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
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
    Observable<BaseBean<BaseArticle<List<HomeArticle>>>> getHomeList(
            @Path("page") int page);

    //公众号列表
    @GET("wxarticle/chapters/json")
    Observable<BaseBean<List<WxBean>>> getWx();
}
