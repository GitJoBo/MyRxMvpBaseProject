package com.jobo.httplib.utils;

import android.text.TextUtils;
import android.widget.Toast;
import com.jobo.httplib.HttplibApp;

/**
 * toast工具类
 */
public class ToastUtils {

    private static Toast toast;

    /**
     * 显示提示信息
     */
    public static void showToast(String text) {
        LogUtils.d("aaaaaaa,text="+text);
        if (TextUtils.isEmpty(text)) return;
        if (toast == null) {
            toast = Toast.makeText(HttplibApp.sContext, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 显示提示信息
     */
    public static void showToast(int rId) {
        if (toast == null) {
            toast = Toast.makeText(HttplibApp.sContext, rId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(rId);
        }
        toast.show();

    }

    /**
     * 显示提示信息(时间较长)
     */
    public static void showLongToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(HttplibApp.sContext, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.show();

    }

    /**
     * 显示提示信息(时间较长)
     */
    public static void showLongToast(int rId) {
        if (toast == null) {
            toast = Toast.makeText(HttplibApp.sContext, rId, Toast.LENGTH_LONG);
        } else {
            toast.setText(rId);
        }
        toast.show();

    }

}
