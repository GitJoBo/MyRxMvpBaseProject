package com.jobo.myrxmvpbaseproject.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoUpDownload.utils.ProgressListener;

/**
 * Created by JoBo on 2018/5/31.
 */

public class UpProgressDialog2 extends Dialog implements ProgressListener {
    private Context context;
    private Activity mActivity;
    private String msg; //跟随Dialog 一起显示的message 信息！
    private TextView mTextView;
    private ProgressBar mProgressBar;
    public UpProgressDialog2(@NonNull Context context, int themeResId, String msg, Activity activity) {
        super(context, themeResId);
        this.msg = msg;
        this.context = context;
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view;
        view = View.inflate(context, R.layout.up_progress_dialog,null);
        mTextView = view.findViewById(R.id.tv_MyDialog);
        mProgressBar = view.findViewById(R.id.iv_wait);
        if (!TextUtils.isEmpty(msg)){
            mTextView.setText(msg);
        }else {
            mTextView.setVisibility(View.GONE);
        }
        setContentView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
        LogUtils.d("Dialog;;hasWrittenLen=" + hasWrittenLen + ",totalLen=" + totalLen + ",hasFinish=" + hasFinish);
//        mProgressBar.setMax((int) totalLen);
//        mProgressBar.setProgress((int) hasWrittenLen,true);
        int temp = (int) (hasWrittenLen/(totalLen/100));
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (hasFinish){
                    mTextView.setText("已完成");
                }else {
                    mTextView.setText(temp+"%");
                }
            }
        });
    }
}
