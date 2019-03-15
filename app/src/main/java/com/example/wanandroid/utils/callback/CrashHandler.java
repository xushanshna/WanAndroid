package com.example.wanandroid.utils.callback;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import com.example.wanandroid.utils.SdCardUtil;
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/15 0015.
 * 获取应用crash信息
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    // 崩溃日志的存储路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath()
            + "/WanAndroid/log";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;

    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 当程序中有未捕获的异常时，系统自动调用该方法
     *
     * @param thread 出现未捕获异常的线程
     * @param ex     未捕获的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        //导出异常信息到SD卡中
/*
        dumpExceptionToSDCard(ex);
*/
        //异常信息上传到服务器
        uploadExceptionToServer();

        ex.printStackTrace();
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }

    }


    private void dumpExceptionToSDCard(Throwable ex) {
        if (!SdCardUtil.isExist()) {
            Logger.w("sdcard unmounted,skip dump exception");
            return;
        }
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(current));
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("dump crach info failed");
        }
    }

    //获取设备信息
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(String.valueOf(mContext.getPackageManager()),
                PackageManager.GET_ACTIVITIES);
        pw.println("App Version：" + pi.versionName + "_" + pi.versionCode);
        pw.println("OS Version：" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        pw.println("Vendor：" + Build.MANUFACTURER);
        pw.println("Model：" + Build.MODEL);
        pw.println("CPU ABI：" + Build.CPU_ABI);
    }

    private void uploadExceptionToServer() {
        //TODO 异常信息上传到服务器
    }

}
