package com.jobo.httplib.base;

import java.util.List;

/**
 * Created by JoBo on 2018/7/5.
 */

public interface IBaseListView<T> extends IBaseShowErrorView {
    void refresh(List<T> data, boolean hasmore);
    void loadMore(List<T> data,boolean hasmore);
//    void onLoad(int page);
    void clearLoad();
}
