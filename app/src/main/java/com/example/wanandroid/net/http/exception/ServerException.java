package com.example.wanandroid.net.http.exception;

/**
 * Created by Administrator on 2019/3/15 0015.
 * 服务器返回信息有异常
 */

public class ServerException extends RuntimeException {
    private final int errorCode;
    private final String errorMsg;

    public ServerException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int errorCode() {
        return errorCode;
    }

    public String errorMsg() {
        return errorMsg;
    }
}
