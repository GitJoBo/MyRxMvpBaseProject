package com.jobo.httplib.base;

/**
 * Created by JoBo on 2018/7/5.
 */

public interface IBaseShowErrorView extends IBaseLoadView {
    void showError(int type);
    void showEmpty();
    void initLoadData();
    void hideErrorLayout();
}
