package com.jobo.myrxmvpbaseproject.demoVideoDisplay;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;

/**
 * Created by JoBo on 2018/7/6.
 */

public class VideoViewActivity extends BaseActivity {
    @BindView(R.id.et1)
    EditText mEt1;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.video)
    VideoView mVideo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_videoview;
    }

    @Override
    protected void init() {
        mBtn.setOnClickListener(v -> {
            String path = Environment.getExternalStorageDirectory().getPath() + "/Download/" + mEt1.getText()
                    .toString();
            Uri uri = Uri.parse(path);//将路劲转换成uri
            mVideo.setVideoURI(uri);//为视频播放器设置路径
            mVideo.setMediaController(new MediaController(this));//显示控制栏
            mVideo.setOnPreparedListener(mp -> mVideo.start());//开始播放视频
        });
    }

    @Override
    protected void initBundleData() {

    }

}
