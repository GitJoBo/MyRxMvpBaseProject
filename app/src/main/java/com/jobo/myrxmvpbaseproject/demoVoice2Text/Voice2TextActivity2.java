package com.jobo.myrxmvpbaseproject.demoVoice2Text;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.iflytek.cloud.*;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.MainActivity;
import com.jobo.myrxmvpbaseproject.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by JoBo on 2018/8/17.
 */

public class Voice2TextActivity2 extends Activity {
    private static final String APPID="5b768775";//"578c4896"; //讯飞语音ID
    private static String TAG = Voice2TextActivity2.class.getSimpleName();

    private SpeechRecognizer mSpeechRecognizer;
    //语音识别结果
    private String mRecognizerResult;
//    private SharedPreferences mSharedPreferences;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private TextView tvShowInfo;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"="+APPID);
        setContentView(R.layout.activity_vice2text);
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(Voice2TextActivity2.this, mInitListener);
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(this, null);
//        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
//                Activity.MODE_PRIVATE);
        tvShowInfo = (TextView) findViewById(R.id.tv_info);
        btn = (Button) findViewById(R.id.play);
        // 设置参数
        mSpeechRecognizer.startListening(mRecognizerListener);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGet(v);
            }
        });
    }
    int ret=0;
    public void doGet(View view) {
        tvShowInfo.setText(null);
        mIatResults.clear();
        // 设置参数
        setParam();
        mSpeechRecognizer.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            Log.d(TAG,"听写失败,错误码：" + ret);
        } else {
            Log.d(TAG,"vvvv===>");
        }
    }
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.d("TAG","xxx 初始化失败，错误码：" + code);
            }
        }
    };
    /**
     * 语音识别监听器
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
        }
        @Override
        public void onBeginOfSpeech() {
        }
        @Override
        public void onEndOfSpeech() {
        }
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.d(TAG, "xxxx  recognizerResult===> "+recognizerResult.getResultString());
            printResult(recognizerResult);
            if (b) {
                // TODO 最后的结果
            }
        }
        @Override
        public void onError(SpeechError speechError) {
            //mSpeechRecognizer.startListening(mRecognizerListener);
            speechError.getPlainDescription(true);
            Log.d("HHHHHH", "xxxxx error==>>>" + speechError);
        }
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
        }
    };
    private void printResult(RecognizerResult results) {
        String result = parseVoice(results.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mIatResults.put(sn, result);
        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        tvShowInfo.setText(resultBuffer.toString());
        tvShowInfo.setSelected(true);
    }
    /**
     * 初始化讯飞的服务 语音听写对象
     */
    private void setParam(){
//        1.创建语音听写对象   第二个参数与服务方式关联  本地服务的话传初始化监听器，云服务的话传null
//        if (mSpeechRecognizer == null) {
//            mSpeechRecognizer = SpeechRecognizer.createRecognizer(MainActivity.this, null);
//        }
        //2.设置参数
//       mSpeechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
//        mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//        mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
//        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
//        mSpeechRecognizer.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));
// 清空参数
        mSpeechRecognizer.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
//        String lag = mSharedPreferences.getString("iat_language_preference",
//                "mandarin");
//        if (lag.equals("en_us")) {
            // 设置语言
//            mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "en_us");
//        } else {
            // 设置语言
            mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
//        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_PTT,  "1");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSpeechRecognizer.cancel();
        mSpeechRecognizer.destroy();
    }


    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice2TextActivity.Voice voiceBean = gson.fromJson(resultString, Voice2TextActivity.Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice2TextActivity.Voice.WSBean> ws = voiceBean.ws;
        for (Voice2TextActivity.Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }
}
