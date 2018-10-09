package com.jobo.myrxmvpbaseproject.demoVoice2Text;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.iflytek.cloud.*;
import com.iflytek.cloud.thirdparty.bb;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.application.MyApplication;

import java.util.ArrayList;

/**
 * Created by JoBo on 2018/8/17.
 */

public class Voice2TextActivity extends BaseActivity {
    @BindView(R.id.play)
    Button mPlay;
    @BindView(R.id.tv_info)
    TextView mTvInfo;
    private StringBuilder mStringBuilder = new StringBuilder();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_vice2text;
    }

    @Override
    protected void init() {
        // 将“xxxxxxx”替换成您申请的 APPID
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b768775");
        // 申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 2);
        }
    }

    @Override
    protected void initBundleData() {

    }

    /**
     * 初始化语音识别
     */
    public void initSpeech(final Context context) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置试题编码类型
        mDialog.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mDialog.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mDialog.setParameter(SpeechConstant.VAD_BOS, "40000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mDialog.setParameter(SpeechConstant.VAD_EOS, "10000");

        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音
                    //返回的result为识别后的汉字,直接赋值到TextView上即可
                    String result = parseVoice(recognizerResult.getResultString());
                    LogUtils.d("result:" + result);
                    mStringBuilder.append(result);
                    mTvInfo.setText(mStringBuilder.toString());
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                LogUtils.d("speechError:" + speechError);
            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();
        //获取字体所在的控件，设置为"",隐藏字体，
        TextView txt = (TextView) mDialog.getWindow().getDecorView().findViewWithTag("textlink");
        if (txt != null){
            txt.setText("核心技术由讯飞提供");
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }

    @OnClick(R.id.play)
    public void onViewClicked() {
        initSpeech(this);
    }
}
