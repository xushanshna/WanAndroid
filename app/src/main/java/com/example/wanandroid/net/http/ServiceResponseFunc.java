package com.example.wanandroid.net.http;

import com.example.wanandroid.base.BaseBean;
import com.example.wanandroid.net.http.exception.ServerException;

import rx.functions.Func1;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 从服务器响应结果中剥离数据data部分，处理服务器返回的异常
 */

public class ServiceResponseFunc<T> implements Func1<BaseBean<T>, T> {

    /**
     * 处理服务器返回异常
     *
     * @param tBaseBean 服务器返回的数据
     * @return data部分的数据
     */
    @Override
    public T call(BaseBean<T> tBaseBean) {
        /*Logger.d("服务器返回码：%s    服务器返回信息：%s",
                tBaseBean.getErrorCode(), tBaseBean.getErrorMsg());*/
        //对返回码进行判断，如果不是0，则证明服务器端返回错误信息了，
        // 根据跟服务器约定好的错误码去解析异常
        if (tBaseBean.getErrorCode() != 0) {
            if (tBaseBean.getErrorCode() == -1001) {
                //TODO 登录失效，需重新登录
            } else {
                //抛出自定义异常，由ServerException捕获并统一处理
                throw new ServerException(tBaseBean.getErrorCode(), tBaseBean.getErrorMsg());
            }
        }
        return tBaseBean.getData();
    }
}
