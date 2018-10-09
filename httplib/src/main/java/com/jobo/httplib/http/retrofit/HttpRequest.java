package com.jobo.httplib.http.retrofit;

import com.jobo.httplib.http.Api.Api;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.http.observer.HttpRxObservable;
import com.jobo.httplib.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

import java.util.TreeMap;

/**
 * Http请求类
 */
public class HttpRequest {

    /**
     * ApiUrl key 开发者可自定义
     */
    public static final String API_URL = "API_URL";

    private final String appKey = "1889b37351288";
    private final String k_key = "key";

    public enum Method {
        GET,
        POST
    }


    /**
     * 发送请求
     * 备注:不管理生命周期
     *
     * @param method   请求方式
     * @param prams    参数集合
     * @param callback 回调
     */
    public void request(Method method, TreeMap<String, Object> prams, HttpRxCallback callback) {

        Observable<HttpResponse> apiObservable = handleRequest(method, prams);

        HttpRxObservable.getObservable(apiObservable).subscribe(callback);

    }


    /**
     * 发送请求
     * 备注:自动管理生命周期
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     *                  不管理生命周期的表现方式为页面暂停,销毁请求继续,导致浪费资源,容易内存泄漏
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, TreeMap<String, Object> prams, LifecycleProvider lifecycle, HttpRxCallback
            callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, prams);
        LogUtils.d("prams:" + prams.toString());
        HttpRxObservable.getObservable(apiObservable, lifecycle).subscribe(callback);
    }


    /**
     * 发送请求
     * 备注:手动指定生命周期-Activity
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, TreeMap<String, Object> prams, LifecycleProvider<ActivityEvent> lifecycle,
                        ActivityEvent event, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event).subscribe(callback);
    }


    /**
     * 发送请求
     * 备注:手动指定生命周期-Fragment
     *
     * @param method    请求方式
     * @param lifecycle 实现RxFragment
     * @param event     指定生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(Method method, TreeMap<String, Object> prams, LifecycleProvider<FragmentEvent> lifecycle,
                        FragmentEvent event, HttpRxCallback callback) {
        Observable<HttpResponse> apiObservable = handleRequest(method, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event).subscribe(callback);
    }

    /**
     * 预处理请求(TreeMap<String, Object>)
     *
     * @param method 请求方法
     * @param prams  参数集合
     * @return
     */
    private Observable<HttpResponse> handleRequest(Method method, TreeMap<String, Object> prams) {

        //获取基础参数
        TreeMap<String, Object> request = getBaseRequest();
        //添加业务参数
        request.putAll(prams);
        //获取apiUrl
        String apiUrl = "";
        if (request.containsKey(API_URL)) {
            apiUrl = String.valueOf(request.get(API_URL));
            //移除apiUrl参数（此参数不纳入业务参数）
            request.remove(API_URL);
        }

        Observable<HttpResponse> apiObservable;
        switch (method) {
            case GET:
                apiObservable = RetrofitUtils.Builder().retrofit().create(Api.class).get(apiUrl, request);
                break;
            case POST://post表单请求
                apiObservable = RetrofitUtils.Builder().retrofit().create(Api.class).post(apiUrl, request);
                break;
            default:
                apiObservable = RetrofitUtils.Builder().retrofit().create(Api.class).post(apiUrl, request);
                break;
        }
        return apiObservable;
    }

    /**
     * 发送请求 + 预处理请求(MultipartBody)
     * 备注:自动管理生命周期
     *
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param url       请求链接
     * @param prams     参数集合
     * @param callback  回调
     */
    public void requestMultipartBody(String url, MultipartBody prams, LifecycleProvider lifecycle, HttpRxCallback
            callback) {
        Observable<HttpResponse> apiObservable = RetrofitUtils.Builder().retrofit().create(Api.class).postMultipartBody
                (url, prams);
        LogUtils.d("prams:" + prams.toString());
        HttpRxObservable.getObservable(apiObservable, lifecycle).subscribe(callback);
    }

    /**
     * 发送请求 + 预处理请求(MultipartBody)
     * 备注:自动管理生命周期
     *
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param url       请求链接
     * @param prams     参数集合
     * @param callback  回调
     */
//    public void requestFlexible(String url, MultipartBody prams, LifecycleProvider lifecycle, HttpRxCallback
//            callback) {
//        Observable<HttpResponse> apiObservable = RetrofitUtils.Builder().retrofit().create(Api.class).postMultipartBody
//                (url, prams);
//        LogUtils.d("prams:" + prams.toString());
//        HttpRxObservable.getObservable(apiObservable, lifecycle).subscribe(callback);
//    }

    /**
     * 预处理请求(MultipartBody)
     *
     * @param prams  参数集合
     * @return
     */
//    private Observable<HttpResponse> handleMultipartBody(String url, MultipartBody prams) {
//        String apiUrl = url;
//        Observable<HttpResponse> apiObservable = RetrofitUtils.Builder().retrofit().create(Api.class).postMultipartBody
// (apiUrl, prams);
//        return apiObservable;
//    }

    /**
     * 获取基础request参数
     */
    private TreeMap<String, Object> getBaseRequest() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put(k_key, appKey);
        return map;
    }




}
