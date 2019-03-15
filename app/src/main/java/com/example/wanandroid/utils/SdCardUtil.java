package com.example.wanandroid.utils;

import android.os.Environment;

/**
 * Created by Administrator on 2019/3/15 0015.
 */

public class SdCardUtil {


    /**
     * 判断SD卡是否存在
     *
     * @return true存在
     */
    public static boolean isExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
