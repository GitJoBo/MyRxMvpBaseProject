package com.jobo.myrxmvpbaseproject.demoRecycleview.fragment;

import android.os.Bundle;
import butterknife.BindView;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.widget.view.ErrorLayout;

/**
 * Created by JoBo on 2018/7/25.
 */

public class TestErrorLayoutFragment extends BaseFragment {
    @BindView(R.id.errorLayout)
    ErrorLayout mErrorLayout;
    @Override
    protected void init() {
        mErrorLayout.setErrorlayout(null,R.string.is_null,0,0,R.mipmap.b1);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_test_error_layout;
    }

    public static TestErrorLayoutFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TestErrorLayoutFragment fragment = new TestErrorLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
