package com.jobo.myrxmvpbaseproject.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.base.IBaseShowErrorView;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.utils.MyViewUtil;
import com.jobo.myrxmvpbaseproject.widget.view.ErrorLayout;

/**
 * Created by JoBo on 2018/7/5.
 */

public abstract class BaseLoadErrorFragment<P extends BasePresenter> extends BaseLoadFragment<P>
        implements IBaseShowErrorView {
    private ErrorLayout mErrorLayout;

    @Override
    public void showError(int type) {
        try {
            if (mErrorLayout == null)
                mErrorLayout = MyViewUtil.getErrorlayout(getContentView());
            MyViewUtil.setViewVisiable(mErrorLayout);
            MyViewUtil.setErrorlayout(mErrorLayout, this, type, getActivity());
        } catch (Exception e) {
            LogUtils.e("e= " + e);
        }

    }

    public void hideErrorLayout() {
        try {
            if (mErrorLayout != null)
                MyViewUtil.setViewGone(mErrorLayout);
        } catch (Exception e) {
            LogUtils.e("e= " + e);
        }

    }

    protected void setEmptyLayout(View.OnClickListener listener, @StringRes int title, @StringRes
            int desc, @StringRes int btnId, @DrawableRes int imgId) {
        try {
            if (mErrorLayout != null)
                mErrorLayout.setErrorlayout(listener, title, desc, btnId, imgId);
        } catch (Exception e) {
            LogUtils.e("e=" + e);
        }
    }
}
