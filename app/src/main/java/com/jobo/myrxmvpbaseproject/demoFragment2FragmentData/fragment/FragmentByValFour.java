package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.fragment;

import android.os.Bundle;
import com.jobo.myrxmvpbaseproject.R;

/**
 * Created by JoBo on 2018/9/25.
 */

public class FragmentByValFour extends BaseByValFragment {
    @Override
    protected void init() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_byval_one;
    }

    public static FragmentByValFour newInstance() {
        Bundle args = new Bundle();
        FragmentByValFour fragment = new FragmentByValFour();
        fragment.setArguments(args);
        return fragment;
    }
}
