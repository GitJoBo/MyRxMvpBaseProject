package com.jobo.myrxmvpbaseproject.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jobo.myrxmvpbaseproject.application.MyApplication;

import java.lang.reflect.Field;

public class DensityUtil {
//    private static float appDensity;
//    private static float appScaledDensity;
//    private static DisplayMetrics appDisplayMetrics;
//    private static int barHeight;//状态栏高度
//    public final static String WIDTH = "width";
//    public final static String HEIGHT = "height";

    private static WindowManager windowManager;

    private static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    public static float dip2px(Context context, float dpValue) {
        if (context == null) {
            float scale = MyApplication.sContext.getResources().getDisplayMetrics().density;
            return dpValue * scale;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 屏幕宽度（像素）
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager(context).getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    // 屏幕高度（像素）
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager(context).getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

//    public static ViewGroup.LayoutParams getWLayoutParams() {
//        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//    }

    /**
     * 修改Tablayout的下划线长度, bug字会变小
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem()
                .getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem()
                .getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams
                    .MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static void setIndicator2(Context context, TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem()
                .getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem()
                .getDisplayMetrics());
        // 这里是为了通过 TextView 的 Paint 来测量文字所占的宽度
        TextView tv = new TextView(context);
        // 必须设置和tab文字一样的大小，因为不同大小字所占宽度不同
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            // 当前TAB上的文字
            String str = tabs.getTabAt(i).getText().toString();
            // 所占的宽度
            int width = (int) tv.getPaint().measureText(str);
            // 这里设置宽度，要稍微多一点，否则丑死了！
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width + 20, LinearLayout.LayoutParams
                    .MATCH_PARENT);
            params.leftMargin = left;//莫名的会卡顿
            params.rightMargin = right;//莫名的会卡顿
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

//    /**
//     * 今日头条适配法
//     * 思路：修改Density为统一值
//     */
//    public static void setAppDensity(@Nullable Application application){
//        //获取application的DisplayMetrics
//        appDisplayMetrics = application.getResources().getDisplayMetrics();
//        //获取状态栏高度
//        barHeight = getScreenHeight(application);
//        if (appDensity == 0){
//            //初始化的时候赋值
//            appDensity = appDisplayMetrics.density;
//            appScaledDensity = appDisplayMetrics.scaledDensity;
//            //添加字体变化的监听
//            application.registerComponentCallbacks(new ComponentCallbacks() {
//                @Override
//                public void onConfigurationChanged(Configuration newConfig) {
//                    //字体改变后,将appScaledDensity重新赋值
//                    if (newConfig != null && newConfig.fontScale > 0) {
//                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
//                    }
//                }
//                @Override
//                public void onLowMemory() {
//
//                }
//            });
//        }
//    }
//
//    /**
//     * 此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好)
//     * 在setContentView()之前设置
//     * @param activity
//     */
//    public static void setDefault(Activity activity) {
//        setAppOrientation(activity, WIDTH);
//    }
//
//    /**
//     * 此方法用于在某一个Activity里面更改适配的方向
//     * 在setContentView()之前设置
//     * @param activity
//     * @param orientation
//     */
//    public static void setOrientation(Activity activity, String orientation) {
//        setAppOrientation(activity, orientation);
//    }
//
//    /**
//     * targetDensity
//     * targetScaledDensity
//     * targetDensityDpi
//     * 这三个参数是统一修改过后的值
//     * orientation:方向值,传入width或height
//     */
//    private static void setAppOrientation(@Nullable Activity activity, String orientation) {
//        float targetDensity;
//        if (orientation.equals("height")) {
//            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / 667f;//设计图的高度 单位:dp
//        } else {
//            targetDensity = appDisplayMetrics.widthPixels / 360f;//设计图的宽度 单位:dp
//        }
//        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
//        int targetDensityDpi = (int) (160 * targetDensity);
//
//        //最后在这里将修改过后的值赋给系统参数,只修改Activity的density值
//        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
//        activityDisplayMetrics.density = targetDensity;
//        activityDisplayMetrics.scaledDensity = targetScaledDensity;
//        activityDisplayMetrics.densityDpi = targetDensityDpi;
//    }
//
//    /**
//     * 获取状态栏高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getStatusBarHeight(Context context) {
//        int result = 0;
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }

}