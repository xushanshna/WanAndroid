package com.example.wanandroid.net.base;

import com.example.wanandroid.bean.BaseBean;
import com.orhanobut.logger.Logger;

import rx.functions.Func1;

/**
 * Created by Administrator on 2019/3/13 0013.
 * 从响应结果中剥离数据
 * T为响应结果的data部分
 */

public class TargetData<T> implements Func1<BaseBean<T>, T> {

    @Override
    public T call(BaseBean<T> tBaseBean) {
        Logger.d("返回码：%s    返回信息：%s",
                tBaseBean.getErrorCode(), tBaseBean.getErrorMsg());
        //返回失败，抛出异常
        if (tBaseBean.getErrorCode() != 0) {
            //TODO 抛出自定义异常
        }
        return tBaseBean.getData();
    }
}
