package com.example.wanandroid.net;

import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.BaseBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 网络接口
 */

public interface ApiService {
    //获取首页banner
    @GET("banner/json")
    Observable<BaseBean<List<BannerBean>>> getBanner();

}
