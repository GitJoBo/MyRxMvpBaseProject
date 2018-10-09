package com.jobo.myrxmvpbaseproject.demoUpDownload.utils;

/**
 * 进度监听接口
 * Created by JoBo on 2018/6/7.
 */

public interface ProgressListener {
    /**
     *
     * @param hasWrittenLen 当前进度
     * @param totalLen      总进度
     * @param hasFinish
     */
    void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
}
