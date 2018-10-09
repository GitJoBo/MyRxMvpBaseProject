package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;

/**
 * Created by JoBo on 2018/9/25.
 */

public class FragmentByValTwo extends BaseByValFragment {
    private static final String TAG = "FragmentByValTwo";
    //定义一个接口标记
    public static final String INTERFACE_PARAM_RESULT = FragmentByValTwo.class.getName() + "PR";
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.textView2)
    TextView mTextView2;

    @Override
    protected void init() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_byval_two;
    }

    public static FragmentByValTwo newInstance() {
        Bundle args = new Bundle();
        FragmentByValTwo fragment = new FragmentByValTwo();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        //这里调用接口
        String resultStr = mFunctionManager.invokeFunction(INTERFACE_PARAM_RESULT, String.class, "我是传输的字符串:" + mEditText
                .getText());
        LogUtils.d(TAG+"我是从cativity返回的Result:"+resultStr);
        mTextView2.setText(resultStr);
    }
}
