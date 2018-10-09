package com.jobo.httplib.base;

/**
 * Created by JoBo on 2018/7/5.
 */

public interface IBaseLoadView extends IBaseView {

    //显示loading
    void showLoading();

    //关闭loading
    void closeLoading();
}
