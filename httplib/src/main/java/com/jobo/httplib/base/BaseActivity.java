package com.jobo.httplib.base;

import android.content.Context;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jobo.httplib.listener.LifeCycleListener;
import com.jobo.httplib.manager.ActivityStackManager;
import com.jobo.httplib.utils.LogUtils;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.List;


/**
 * 基类Activity
 * 备注:所有的Activity都继承自此Activity
 * 1.规范团队开发
 * 2.统一处理Activity所需配置,初始化
 */
public abstract class BaseActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks {

//    protected Context mContext;
    protected Unbinder unBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        ActivityStackManager.getManager().push(this);
        setContentView(getContentViewId());
//        mContext = this;
        unBinder = ButterKnife.bind(this);
        try {
            initBundleData();
        }catch (Exception e){
            LogUtils.e("e:"+e);
        }
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        if (mListener != null) {
            mListener.onDestroy();
        }
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityStackManager.getManager().remove(this);
        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 具备权限 直接进行操作
     * @param requestCode
     * @param list
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    /**
     * 权限拒绝 申请权限
     * @param requestCode
     * @param list
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }

    /**
     * 获取显示view的xml文件ID
     */
    protected abstract int getContentViewId();

    /**
     * 初始化应用程序，设置一些初始化数据,获取数据等操作
     */
    protected abstract void init();

    /**
     * 获取上一个界面传送过来的数据
     */
    protected abstract void initBundleData();


    /**
     * 回调函数
     */
    public LifeCycleListener mListener;

    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }


}
