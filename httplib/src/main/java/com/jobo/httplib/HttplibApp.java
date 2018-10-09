package com.jobo.httplib;

import android.content.Context;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by JoBo on 2018/5/31.
 */

public class HttplibApp {
    public static Context sContext;

    public static void init(Context context) {
        sContext = context;
        Logger.addLogAdapter(new AndroidLogAdapter());//初始化Logger
    }
}
