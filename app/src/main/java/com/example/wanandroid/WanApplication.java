package com.example.wanandroid;

import android.app.Application;
import android.support.annotation.Nullable;

import com.example.wanandroid.utils.Constant;
import com.example.wanandroid.utils.callback.CrashHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import rx.plugins.RxJavaPlugins;

/**
 * Created by Administrator on 2019/3/13 0013.
 */

public class WanApplication extends Application {
    private static final String TAG = "WanApplication";
    private static WanApplication instance;

    public static WanApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initBugly();
        initLogger();
        initCrash();
        handleRxJavaError();
    }

    //处理rxjava2上报的异常，不处理会崩溃
    private void handleRxJavaError() {

    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(),
                Constant.BUGLY_APP_ID, Constant.DEBUG);
    }

    //为应用设置异常处理，然后程序才可以获取未处理的异常
    private void initCrash() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return Constant.DEBUG;
            }
        });
    }
}
