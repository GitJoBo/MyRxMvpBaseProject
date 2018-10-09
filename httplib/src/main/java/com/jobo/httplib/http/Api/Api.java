package com.jobo.httplib.http.Api;

import com.jobo.httplib.http.retrofit.HttpResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.List;
import java.util.TreeMap;

/**
 * Api接口
 */
public interface Api {


    /**
     * GET请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @GET
    Observable<HttpResponse> get(@Url String url, @QueryMap TreeMap<String, Object> request);

    /**
     * POST请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<HttpResponse> post(@Url String url, @FieldMap TreeMap<String, Object> request);

    @POST
    Observable<HttpResponse> postMultipartBody(@Url String url,@Body MultipartBody request);

}
