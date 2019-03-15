package com.example.wanandroid.net.http;


import com.example.wanandroid.net.http.exception.ExceptionEngine;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2019/3/15 0015.
 * 处理onerror事件的拦截器
 */

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
