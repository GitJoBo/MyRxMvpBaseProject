package com.jobo.myrxmvpbaseproject.application;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import com.jobo.httplib.HttplibApp;
import com.jobo.httplib.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by JoBo on 2018/5/31.
 */

public class MyApplication extends Application {
    public static Context sContext;
    public static int H, W;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        HttplibApp.init(sContext);
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        getScreen(this);

    }

    /**
     * 获取手机屏幕宽高
     * H,W单位px
     * @param aty
     */
    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H=dm.heightPixels;
        W=dm.widthPixels;
        LogUtils.d("aaaaaaaH="+H+",W="+W);
    }
}
