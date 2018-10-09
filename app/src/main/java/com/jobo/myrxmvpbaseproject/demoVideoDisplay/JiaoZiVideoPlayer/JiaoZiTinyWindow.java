package com.jobo.myrxmvpbaseproject.demoVideoDisplay.JiaoZiVideoPlayer;

import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;
import com.bumptech.glide.Glide;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;

/**
 * Created by JoBo on 2018/7/9.
 */

public class JiaoZiTinyWindow extends BaseActivity {
    @BindView(R.id.jz_video)
    JZVideoPlayerStandard mJzVideo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jiaozi__tiny_window;
    }

    @Override
    protected void init() {
//        String url = "http://192.168.0.116:8080//upload/0VID_20180821_141255.mp4";
        String url = "http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd" +
                "-5287d2089db37e62345123a1be272f8b.mp4";
        mJzVideo.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Glide.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(mJzVideo.thumbImageView);
    }

    @Override
    protected void initBundleData() {

    }

    @OnClick({R.id.tiny_window, R.id.auto_tiny_list_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tiny_window:
                mJzVideo.startWindowTiny();
                break;
            case R.id.auto_tiny_list_view:
                UIUtils.startActivity(this, JZTinyWindowRecycleViewNormalActivity.class);
                break;
        }
    }
}
