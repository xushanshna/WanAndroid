package com.example.wanandroid.net.http;

import android.content.Context;
import android.widget.Toast;

import com.example.wanandroid.net.http.exception.ApiException;
import com.example.wanandroid.net.http.exception.ServerException;
import com.orhanobut.logger.Logger;

import rx.Observer;

/**
 * Created by Administrator on 2019/3/15 0015.
 * 处理异常
 */

public abstract class BaseObserver<T> implements Observer<T> {

    private Context context;

    public BaseObserver(Context context) {
        this.context = context;
    }

    public BaseObserver() {
    }

    @Override
    public void onError(Throwable e) {
//        showToast(e);
        handleError(e);
    }

    private void handleError(Throwable e) {
        if (e instanceof ApiException) {
            Logger.d(((ApiException) e).code + ((ApiException) e).message);
        } else if (e instanceof ServerException) {
            Logger.d(((ServerException) e).errorCode() + ((ServerException) e).errorMsg());
        } else {
            Logger.d(e.getMessage());
        }
    }


    private void showToast(Throwable e) {
        if (context == null) return;
        if (e instanceof ApiException) {
            Toast.makeText(context, ((ApiException) e).message, Toast.LENGTH_SHORT).show();
        } else if (e instanceof ServerException) {
            Toast.makeText(context, ((ServerException) e).errorMsg(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
