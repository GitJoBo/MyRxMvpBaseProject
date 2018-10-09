package com.jobo.myrxmvpbaseproject.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.jobo.myrxmvpbaseproject.R;

/**
 * Created by JoBo on 2018/3/22.
 */

public class DialogUtil {
    //退出确定
    public final static AlertDialog getCustomTipDialog(Activity context, String msg,
                                                       String cancelStr, String okStr,
                                                       final View.OnClickListener okListen,
                                                       final View.OnClickListener nListen) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = View.inflate(context, R.layout.dialog_tip2, null);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_tip, null);
        builder.setView(view);

        TextView tv = (TextView) view.findViewById(R.id.dialog_text);
        tv.setText(msg);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        TextView tvOk = (TextView) view.findViewById(R.id.dialog_ok);
        if (!TextUtils.isEmpty(okStr)) tvOk.setText(okStr);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (okListen != null)
                    okListen.onClick(v);
            }
        });
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel);
        if (!TextUtils.isEmpty(cancelStr)) tvCancel.setText(cancelStr);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (nListen != null)
                    nListen.onClick(v);
            }
        });

        dialog.setView(view);
        dialog.show();
//        dialog.getWindow().setDimAmount(0f);//核心代码 解决了无法去除遮罩
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背景，加这句代码
        WindowManager m = context.getWindowManager();
//        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        DisplayMetrics dm = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (dm.heightPixels * 0.28);   //高度设置为屏幕的0.3
        p.width = (int) (dm.widthPixels * 0.6);    //宽度设置为屏幕的0.5
        dialog.getWindow().setAttributes(p);     //设置生效
//        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        return dialog;
    }
}
