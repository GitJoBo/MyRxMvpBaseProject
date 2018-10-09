package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.activity;

import com.jobo.httplib.base.BaseActivity;

/**
 * Created by JoBo on 2018/9/25.
 */

public abstract class BaseByValActivity extends BaseActivity {
    /**
     * 添加接口并实现接口中的回调
     * @param tag   Fragment标记
     */
    public abstract void setFunctionForFragment(String tag);
}
