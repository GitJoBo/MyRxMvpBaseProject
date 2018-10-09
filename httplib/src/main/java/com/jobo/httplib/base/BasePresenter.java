package com.jobo.httplib.base;

import android.os.Bundle;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.http.observer.HttpRxObservable;
import com.jobo.httplib.http.retrofit.HttpRequest;
import com.jobo.httplib.http.retrofit.HttpResponse;
import com.jobo.httplib.listener.LifeCycleListener;
import com.jobo.httplib.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import io.reactivex.Observable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * BasePresenter
 * JAVA弱引用，管理View的引用，以及activity的引用，避免强引用导致资源无法释放而造成的内存溢出，
 * 写代码的时候想到了一个很巧妙的方式：Presenter中传如一个activity，同时实现Activity生命周期监听，
 * 在onDestroy中移除View和Activity的引用
 */

public class BasePresenter<V, T> implements LifeCycleListener {

    protected Reference<V> mViewRef;
    protected V mView;
    protected Reference<T> mActivityRef;
    protected T mActivity;

    /**
     * 将BasePresenter与BaseBiz结合,减少代码类,与XXXBiz,P层只做响应处理思想冲突
     */
    protected HttpRequest mHttpRequest;

    protected HttpRequest getRequest() {
        if (mHttpRequest == null) {
            mHttpRequest = new HttpRequest();
        }
        return mHttpRequest;
    }

    /**
     * @param apiObservable
     * @param lifecycle     生命周期绑定,实现暂停退出页面时取消请求  null时页面暂停,消失等请求还是会继续
     * @param callback
     */
    public void add(Observable<HttpResponse> apiObservable, LifecycleProvider lifecycle, HttpRxCallback<HttpResponse>
            callback) {
        HttpRxObservable.getObservable(apiObservable, lifecycle).subscribe(callback);
    }

    public void add(Observable<HttpResponse> apiObservable, HttpRxCallback<HttpResponse>
            callback) {
        HttpRxObservable.getObservable(apiObservable, null).subscribe(callback);
    }

    public void add(Observable<HttpResponse> apiObservable, HttpRxCallback<HttpResponse>
            callback, boolean showAnimation) {
        V view = getView();
        if (showAnimation && view != null && view instanceof IBaseLoadView) {
            ((IBaseLoadView) view).showLoading();
        }
        add(apiObservable, callback);
    }
//    public void addSubscription(Subscription subscriber){
//        CompositeSubscription compositeSub = getCompositeSub();
//        compositeSub.add(subscriber);
//    }
//    public void add(Observable o, Action1 subscriber){
//        getCompositeSub().add(o.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber));
//    }
//
//    public void add(Observable o, Subscriber s, boolean isLoadding) {
//        V view = getView();
//        if(isLoadding&&view!=null&&view instanceof ShowLoadView){
//            ((ShowLoadView) view).showLoading();
//        }
//        add(o, s);
//    }


    public BasePresenter(V view, T activity) {
        attachView(view);
        attachActivity(activity);
        setListener(activity);
//        mHttpRequest = new HttpRequest();
    }

    /**
     * 设置生命周期监听
     */
    private void setListener(T activity) {
        if (getActivity() != null) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) getActivity()).setOnLifeCycleListener(this);
            } /*else if (activity instanceof BaseFragmentActivity) {
                ((BaseFragmentActivity) getActivity()).setOnLifeCycleListener(this);
            }*/
        }
    }

    /**
     * 关联
     *
     * @param view
     */
    private void attachView(V view) {
        //   WeakReference表示一个对象的弱引用，WeakReference所引用的对象在发生GC时，如果该对象只被这个WeakReference所引用，
        // 那么不管当前内存使用情况如何都会释放其所占用的内存．
        //  SoftReference表示一个对象的软引用，SoftReference所引用的对象在发生GC时，如果该对象只被这个SoftReference
        // 所引用，那么在内存使用情况已经比较紧张的情况下会释放其所占用的内存，若内存比较充实，则不会释放其所占用的内存．
        // 比较常用于一些Cache的实现．
        mViewRef = new WeakReference<V>(view);
        mView = mViewRef.get();
    }

    /**
     * 关联
     *
     * @param activity
     */
    private void attachActivity(T activity) {
        mActivityRef = new WeakReference<T>(activity);
        mActivity = mActivityRef.get();
    }

    /**
     * 销毁
     */
    private void detachView() {
        if (isViewAttached() && mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
            mView = null;//手动清空
        }
    }

    /**
     * 销毁
     */
    private void detachActivity() {
        if (isActivityAttached() && mActivityRef != null) {
            mActivityRef.clear();
            mActivityRef = null;
            mActivity = null;//手动清空
        }
    }

    /**
     * 获取
     *
     * @return
     */
    public V getView() {
        if (mViewRef == null) {
            return null;
        }
        return mViewRef.get();
    }

    /**
     * 获取
     *
     * @return
     */
    public T getActivity() {
        if (mActivityRef == null) {
            return null;
        }
        return mActivityRef.get();
    }

    /**
     * 是否已经关联
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 是否已经关联
     *
     * @return
     */
    public boolean isActivityAttached() {
        return mActivityRef != null && mActivityRef.get() != null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        LogUtils.d("P销毁绑定开始");
        LogUtils.d("mView=" + mView);
        LogUtils.d("mActivity=" + mActivity);
        LogUtils.d("mViewRef" + mViewRef);
        LogUtils.d("mActivityRef=" + mActivityRef);
        detachView();
        detachActivity();
        //经多方面测试,弱引用无法达到GC时清空,原因不明
        LogUtils.d("P销毁绑定结束");
        LogUtils.d("mView=" + mView);
        LogUtils.d("mActivity=" + mActivity);
        LogUtils.d("mViewRef" + mViewRef);
        LogUtils.d("mActivityRef=" + mActivityRef);
    }
}
