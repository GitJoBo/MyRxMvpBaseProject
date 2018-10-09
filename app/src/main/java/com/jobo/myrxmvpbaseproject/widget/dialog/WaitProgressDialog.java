package com.jobo.myrxmvpbaseproject.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.jobo.myrxmvpbaseproject.R;

/**
 * Created by JoBo on 2018/5/31.
 */

public class WaitProgressDialog extends Dialog {
    private Context context;
    private String msg; //跟随Dialog 一起显示的message 信息！
    public WaitProgressDialog(@NonNull Context context, int themeResId, String msg) {
        super(context, themeResId);
        this.msg = msg;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view;
        TextView textView;
        view = View.inflate(context, R.layout.custom_wait_dialog,null);
        textView = view.findViewById(R.id.tv_MyDialog);
        if (!TextUtils.isEmpty(msg)){
            textView.setText(msg);
        }else {
            textView.setVisibility(View.GONE);
        }
        setContentView(view);
    }
}
