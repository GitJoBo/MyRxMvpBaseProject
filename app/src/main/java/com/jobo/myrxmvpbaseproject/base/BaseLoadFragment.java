package com.jobo.myrxmvpbaseproject.base;

import com.jobo.httplib.base.BaseFragment;
import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.base.IBaseLoadView;
import com.jobo.httplib.base.IBaseView;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.widget.dialog.WaitProgressDialog;

/**
 * Created by JoBo on 2018/7/5.
 */

public abstract class BaseLoadFragment<P extends BasePresenter> extends BaseFragment implements IBaseLoadView {
    protected WaitProgressDialog mWaitProgressDialog;
    protected P mPresenter;

    @Override
    protected void initLoad() {
        super.initLoad();
        initPresenter();
        mWaitProgressDialog = new WaitProgressDialog(getActivity(), R.style.CustomHttpWaitDialog, null);
        mWaitProgressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 创建p
     */
    protected void initPresenter() {
        mPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void showLoading() {
        if (mWaitProgressDialog != null && !mWaitProgressDialog.isShowing()) {
            mWaitProgressDialog.show();
        }
    }

    @Override
    public void closeLoading() {
        if (mWaitProgressDialog != null && mWaitProgressDialog.isShowing()) {
            mWaitProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWaitProgressDialog != null) {
            if (mWaitProgressDialog.isShowing()) {
                mWaitProgressDialog.dismiss();
            }
            mWaitProgressDialog = null;
        }

        if (mPresenter != null){
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }
}
