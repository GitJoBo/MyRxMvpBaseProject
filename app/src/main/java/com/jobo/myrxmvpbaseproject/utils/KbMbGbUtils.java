package com.jobo.myrxmvpbaseproject.utils;

/**
 * 字节,MB,GB工具类
 * Created by JoBo on 2018/6/8.
 */

public class KbMbGbUtils {
    public static StringBuffer getProgress(long k) {
        StringBuffer sb = new StringBuffer();
        long l = 0;
        long m = k / 1024;
        if (m < 102400) {
            return sb.append(m).append(",").append("MB");
        }
        long g = m / 1024;
        if (g < 102400)
            return sb.append(g).append(",").append("GB");
        long t = g / 1024;
        return sb.append(t).append(",").append("TB");
    }
}
