package com.jobo.httplib.http.retrofit;

import com.jobo.httplib.utils.GsonUtil;
import com.jobo.httplib.utils.LogUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * RetrofitUtils工具类
 * 此工具类全局唯一(单例模式)
 */
public class RetrofitUtils {
    /**
     * 接口地址
     */
//    public static final String BASE_API = "http://60.168.131.135:65529/";
//    public static final String BASE_API = "http://192.168.0.104:65529/";
    public static final String BASE_API = "http://192.168.0.107:8080/";//晶日wifi
    public static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    public static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    public static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒
    private static RetrofitUtils mInstance = null;

    private RetrofitUtils() {
    }

    public static RetrofitUtils Builder() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置okHttp
     */
    private static OkHttpClient okHttpClient() {
        //开启Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> LogUtils.d("okHttp:" + message));
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return client;
    }

    /**
     * 获取Retrofit
     */
    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

}
