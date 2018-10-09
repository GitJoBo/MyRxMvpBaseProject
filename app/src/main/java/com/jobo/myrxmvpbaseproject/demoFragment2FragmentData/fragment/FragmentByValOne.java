package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.myrxmvpbaseproject.R;

/**
 * Created by JoBo on 2018/9/25.
 */

public class FragmentByValOne extends BaseByValFragment {
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.button)
    Button mButton;
    public static final String INTERFACE = FragmentByValOne.class.getName() + "NPNR";

    @Override
    protected void init() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_byval_one;
    }

    public static FragmentByValOne newInstance() {
        Bundle args = new Bundle();
        FragmentByValOne fragment = new FragmentByValOne();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mFunctionManager.invokeFunction(INTERFACE);
    }
}
