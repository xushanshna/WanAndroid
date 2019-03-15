package com.example.wanandroid.net.http.exception;

/**
 * Created by Administrator on 2019/3/15 0015.
 * 访问服务器异常
 */

public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

}
