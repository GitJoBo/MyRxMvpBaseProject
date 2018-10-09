package com.jobo.myrxmvpbaseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jobo.httplib.base.IBaseListView;
import com.jobo.httplib.http.exception.ApiException;
import com.jobo.httplib.http.exception.ExceptionEngine;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.manager.ActivityStackManager;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.application.MyApplication;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;


/**
 * Created by sks on 2015/9/16.
 */
public class UIUtils {

    public static final void setBottomDialog(android.app.Dialog dialog) {
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    /**
     * 兼容Android7.0安装APP方法
     *
     * @param context
     */
    public static void install(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, getFileProvider(), file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clz) {
        context.startActivity(new Intent(context, clz));
    }

    public static void startActivity(Context context, Class clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clz, String key, String value) {
        Intent intent = new Intent(context, clz);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clz, String key, int value) {
        Intent intent = new Intent(context, clz);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clz, String key, boolean value) {
        Intent intent = new Intent(context, clz);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clz, int rcode) {
        context.startActivity(new Intent(context, clz));
    }

    public static void startActivityForResult(Activity a, Class clz, int rcode) {
        a.startActivityForResult(new Intent(a, clz), rcode);
    }

    public static void startActivityForResult(Activity a, Class clz, int rcode, Bundle bundle) {
        Intent intent = new Intent(a, clz);
        intent.putExtras(bundle);
        a.startActivityForResult(intent, rcode);
    }

    private static long lastClickTime;

    public synchronized static final boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    public static Uri getOutputMediaFileUri() {
        File mediaFile = getImgFile();
        if (mediaFile == null) return null;
//        if(mediaFile.exists())mediaFile.delete();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri imageUri = FileProvider.getUriForFile(MyApplication.sContext, getFileProvider(), mediaFile);
            //通过FileProvider创建一个content类型的Uri
            return imageUri;
        }
        return Uri.fromFile(mediaFile);
    }

    public static Uri getOutputMediaFileUri(File mediaFile) {
        if (mediaFile == null) return null;
//        if(mediaFile.exists())mediaFile.delete();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri imageUri = FileProvider.getUriForFile(MyApplication.sContext, getFileProvider(), mediaFile);
            //通过FileProvider创建一个content类型的Uri
            return imageUri;
        }

        return Uri.fromFile(mediaFile);
    }

    @Nullable
    public static File getImgFile() {
        File mediaStorageDir = new File(
                CacheUtil.getSaveDirPath());
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        long timeMillis = System.currentTimeMillis();
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + timeMillis + CacheUtil.HEAD_IMG);
//        if(mediaFile.exists())mediaFile.delete();
        return mediaFile;
    }

    public static String getFileProvider() {
        return MyApplication.sContext.getPackageName() + ".fileprovider";
    }

    public static void exit(Context context) {
//        MySP.clearInfo();
        //结束所有activity
        ActivityStackManager.getManager().finishAllActivity();
        //退出程序
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
        ActivityStackManager.getManager().exitApp(context);
    }

    public final static void setListLoad(IBaseListView mView, int page, List datas, int isEnd) {
        if (page == MySpUtil.LOAD_INIT_CODE) {
            mView.refresh(datas, isEnd == MySpUtil.LOAD_GO);
            if (datas == null || datas.size() == 0) {
                mView.showError(ExceptionEngine.EMPTY);
            }
        } else {
            boolean b = isEnd == MySpUtil.LOAD_GO;
            if (datas != null)
                System.out.println(datas.size() + "" + b);
            mView.loadMore(datas, isEnd == MySpUtil.LOAD_GO);
        }
    }

    public final static void setListLoad(IBaseListView mView, int page, List datas) {
        if (page == MySpUtil.LOAD_INIT_CODE) {
            mView.refresh(datas, true);
            if (datas == null || datas.size() == 0) {
                mView.showError(ExceptionEngine.EMPTY);
            }
        } else {
            mView.loadMore(datas, datas != null && datas.size() != 0);
        }
    }

    public final static void setListLoad(IBaseListView mView) {
        mView.clearLoad();
    }

    public final static MultipartBody.Part getMultipartBodyPart(String fileName, File file1) {
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("image/jpg"), file1);
        MultipartBody.Part part =
                MultipartBody.Part.createFormData(fileName, file1.getName(), requestBody);
        return part;
    }
}
