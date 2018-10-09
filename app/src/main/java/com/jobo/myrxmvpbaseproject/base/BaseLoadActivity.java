package com.jobo.myrxmvpbaseproject.base;

import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.base.IBaseLoadView;
import com.jobo.httplib.base.IBaseView;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.widget.dialog.WaitProgressDialog;

/**
 * 增加统一等待层
 * Created by JoBo on 2018/7/5.
 */

public abstract class BaseLoadActivity<P extends BasePresenter> extends BaseActivity implements IBaseLoadView {
    public WaitProgressDialog mWaitProgressDialog;
    protected P mPresenter;

    @Override
    protected void init() {
        initPresenter();
        mWaitProgressDialog = new WaitProgressDialog(this, R.style.CustomHttpWaitDialog, null);
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
    protected void onDestroy() {
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
