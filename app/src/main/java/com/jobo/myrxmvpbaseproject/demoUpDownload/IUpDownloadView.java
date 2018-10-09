package com.jobo.myrxmvpbaseproject.demoUpDownload;

import com.jobo.httplib.base.IBaseLoadView;
import com.jobo.httplib.base.IBaseView;

/**
 * Created by JoBo on 2018/6/6.
 */

public interface IUpDownloadView extends IBaseLoadView {
    //显示结果
    void showResult(String bean);
    //显示进度
    void showProgressBar();
}
