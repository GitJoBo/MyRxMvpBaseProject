package com.jobo.myrxmvpbaseproject.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import com.jobo.httplib.base.IBaseShowErrorView;
import com.jobo.httplib.http.exception.ExceptionEngine;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.application.MyApplication;
import com.jobo.myrxmvpbaseproject.widget.view.ErrorLayout;

/**
 * Created by JoBo on 2018/7/5.
 */

public class MyViewUtil {


    public static ErrorLayout getErrorlayout(View viewRoot) {
        if (viewRoot == null) return null;
        ViewStub vs = (ViewStub) viewRoot.findViewById(R.id.vs_error);
        ErrorLayout errorLayout = (ErrorLayout) vs.inflate().findViewById(R.id.errorLayout);
        return errorLayout;
    }

    public static void setViewVisiable(View v) {
        if (v != null && v.getVisibility() == View.GONE) v.setVisibility(View.VISIBLE);
    }

    public static void setViewGone(View v) {
        if (v != null && v.getVisibility() == View.VISIBLE) {
            v.setVisibility(View.GONE);
        }
    }

    public static void setErrorlayout(ErrorLayout errorLayout, IBaseShowErrorView view, int type, Context context) {
        switch (type) {
            case ExceptionEngine.ERROR:
//                view.showEmpty();
//                view.showError(type);
                setErrorlayout(errorLayout,null, R.string.s404, 0,0, R.mipmap.default_ptr_wodfan_frame1);
                break;
            case ExceptionEngine.EMPTY:
                view.showEmpty();
                break;
            case ExceptionEngine.NET_ERROR:
                setErrorlayout(errorLayout, v -> {
                    // 跳转到系统的网络设置界面 解决飞行模式的无网络
                    Intent intent = null;
                    // 先判断当前系统版本
                    if (Build.VERSION.SDK_INT > 10) {  // 3.0以上
                        //ACTION_AIRPLANE_MODE_SETTINGS,ACTION_WIRELESS_SETTINGS
                        intent = new Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    } else {
                        intent = new Intent();
                        intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                    }
                    if (context != null)
                        context.startActivity(intent);
                }, R.string.net_no_available, R.string.net_no_availablehit, R.string.set_net, R.mipmap.ic_net_error);
                break;
            default:
                break;
        }
    }

    public static void setErrorlayout(ErrorLayout errorLayout, View.OnClickListener listener, int title, int desc,
                                      int btnId, int imgId) {
        if (errorLayout == null) return;
        errorLayout.setTvErrorTitle(title);
        if (desc == 0) {
            errorLayout.setTvDescGone();
        } else {
            errorLayout.setTvDesc(desc);
        }
        errorLayout.setErrorImage(imgId);
        if (listener == null) {
            errorLayout.setBtnVisibility(View.GONE);
        } else {
            errorLayout.setBtnVisibility(View.VISIBLE);
            errorLayout.setBtnText(btnId);
            errorLayout.setBtnListen(listener);
        }
    }

    public static ViewGroup.LayoutParams getWLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public final static int getColor(@ColorRes int resID) {
        return ContextCompat.getColor(MyApplication.sContext, resID);
    }
    /**
     * dip转px像素
     *
     * @return
     */
    public static int dip2px(float dps) {
//        final float scale = getScreenDensity(context);
        return Math.round(MyApplication.sContext.getResources().getDisplayMetrics().density * dps);
    }
}
