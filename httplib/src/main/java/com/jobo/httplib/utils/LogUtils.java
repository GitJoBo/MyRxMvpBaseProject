package com.jobo.httplib.utils;

import android.content.pm.ApplicationInfo;
import com.jobo.httplib.HttplibApp;
import com.orhanobut.logger.Logger;

/**
 * LOG工具类
 */
public class LogUtils {

//    private static final String TAG = "Rx-Mvp";
    public static boolean isDebug = isApkDebugable();

    private LogUtils() {
    }

    public static void d(String content) {
        if (!isDebug)
            return;
        Logger.d(content);
    }

    public static void e(String content) {
        if (!isDebug)
            return;
        Logger.e(content);
    }

    public static void i(String content) {
        if (!isDebug)
            return;
        Logger.i(content);
    }

    public static void v(String content) {
        if (!isDebug)
            return;
        Logger.v(content);
    }

    public static void w(String content) {
        if (!isDebug)
            return;
        Logger.w(content);
    }

    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = HttplibApp.sContext.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

}
