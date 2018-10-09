package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.fragment;

import android.content.Context;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.activity.BaseByValActivity;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function.FunctionManager;

/**
 * Created by JoBo on 2018/9/25.
 */

public abstract class BaseByValFragment extends BaseFragment {
    protected FunctionManager mFunctionManager;
    protected BaseByValActivity mBaseActivityByVal;

    /**
     * 为要实现接口的Fragment添加FunctionManager
     * @param functionManager
     */
    public void setFunctionManager(FunctionManager functionManager){
        this.mFunctionManager = functionManager;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //确保context实现了fragment相应的接口
        if (context instanceof BaseByValActivity){
            mBaseActivityByVal = (BaseByValActivity) context;
            mBaseActivityByVal.setFunctionForFragment(getTag());
        }
    }
}
