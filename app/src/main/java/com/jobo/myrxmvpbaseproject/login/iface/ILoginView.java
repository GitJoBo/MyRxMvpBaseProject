package com.jobo.myrxmvpbaseproject.login.iface;

import com.jobo.httplib.base.IBaseLoadView;
import com.jobo.httplib.base.IBaseView;
import com.jobo.myrxmvpbaseproject.entity.UserBean;

/**
 * 登录view
 */

public interface ILoginView extends IBaseLoadView {

    //显示结果
    void showResult(UserBean bean);

}
