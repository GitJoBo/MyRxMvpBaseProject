package com.jobo.myrxmvpbaseproject.login.activity;

import android.text.TextUtils;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.base.BaseLoadActivity;
import com.jobo.myrxmvpbaseproject.entity.UserBean;
import com.jobo.myrxmvpbaseproject.login.iface.ILoginView;
import com.jobo.myrxmvpbaseproject.login.presenter.LoginPresenter;

/**
 * Created by JoBo on 2018/5/31.
 */

public class LoginActivity extends BaseLoadActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
//    private LoginPresenter mLoginPresenter = new LoginPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, this);
    }

    @Override
    protected void initBundleData() {

    }


    @OnClick(R.id.login)
    public void onViewClicked() {
        String userName = mEtUserName.getText().toString();
        String password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            return;
        }

//        mLoginPresenter.login(userName, password);
//        mLoginPresenter.login2(userName, password);
        mPresenter.login2(userName, password);
    }

    @OnLongClick(R.id.login)
    public boolean onViewLongClick(){
        mWaitProgressDialog.show();
        return true;
    }

    @Override
    public void showResult(UserBean bean) {
        if (bean == null) {
            return;
        }
        ToastUtils.showToast(bean.getUserAcconut());
    }
}
