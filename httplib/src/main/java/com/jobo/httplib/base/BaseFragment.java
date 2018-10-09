package com.jobo.httplib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.logging.Logger;

public abstract class BaseFragment extends RxFragment {
    //BackHandledFragment   BackHandledInterface
    protected BaseActivity mActivity;

    protected boolean isFristVisible = true;
    private Unbinder mBind;
    private boolean isFristData = true;
    protected View mContentView;
    //    private boolean isFirstInvisible;
    protected boolean isVisibleToUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LogUtils.d("oncreat");
//        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
//            LogUtils.d("onCreateView");
            if (getContentViewLayoutID() != 0) {
//                mContentView = inflater.inflate(getContentViewLayoutID(),null);
                mContentView = inflater.inflate(getContentViewLayoutID(), container,false);
//                mContentView = LayoutInflater.from(this.getContext()).inflate(getContentViewLayoutID(),container,false);
                mBind = ButterKnife.bind(this, mContentView);//使用viewPager时view会NullPointerException
            }
        } else {
            ViewParent parent = mContentView.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(mContentView);
            }
        }
//        mBind = ButterKnife.bind(this, mContentView);

        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isFristVisible) {
                isFristVisible = false;
                userFristVisible();
            }
            userVisible();
        } else {
            if (!isFristVisible) {//不加判断,首次加载会执行
                userInvisible();
            }

        }
    }

    protected void userInvisible() {
//        LogUtils.d("userInvisible");
    }


    protected void userVisible() {
//        LogUtils.d("userVisible");
    }

    protected void userFristVisible() {
//        LogUtils.d("userFristVisible");
    }


    protected View findViewById(int viewId) {
        return mContentView.findViewById(viewId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        LogUtils.d("onActivityCreated");
        if (isFristData) {
            isFristData = false;
            initLoad();
            init();
        }
    }

    protected void initLoad() {

    }

    protected FragmentManager getSupportFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    protected View getContentView() {
        return mContentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
            mBind = null;
        }
        if(mActivity != null){
            mActivity = null;
        }
    }

    protected abstract void init();

//    public void showErrorInfo(String msg) {
//        ToastUtils.showToast(msg);
//    }

    protected abstract int getContentViewLayoutID();

}
