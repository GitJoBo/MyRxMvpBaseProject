package com.jobo.myrxmvpbaseproject.demoVIdeoRecord;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;

import java.io.File;

/**
 * Created by JoBo on 2018/8/21.
 */

public class NowPlayVideoActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, mMediaController
        .MediaPlayerControl {
    @BindView(R.id.videoView)
    MyVideoView mVideoView;
    private mMediaController controller;
    private String mVideoPath;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_now_play_video;
    }

    @Override
    protected void init() {
        File sourceVideoFile = new File(mVideoPath);
        int screenW = getWindowManager().getDefaultDisplay().getWidth();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mVideoView.getLayoutParams();
        params.width = screenW;
        params.height = screenW * 4 / 3;
        params.gravity = Gravity.TOP;
        mVideoView.setLayoutParams(params);

        mVideoView.setOnPreparedListener(this);
        controller = new mMediaController(this);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mVideoView.setVideoURI(Uri.fromFile(sourceVideoFile));
        }
    }

    @Override
    protected void initBundleData() {
        mVideoPath = getIntent().getExtras().getString("videoPath");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        controller.setAnchorView((ViewGroup) findViewById(R.id.fl_videoView_parent));
        controller.show();

    }

    @Override
    public void start() {
        if (mVideoView != null)
            mVideoView.start();
    }

    @Override
    public void pause() {
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    @Override
    public int getDuration() {
        if (mVideoView != null) {
            return mVideoView.getDuration();
        } else {
            return 0;
        }
    }

    @Override
    public int getCurrentPosition() {
        if (mVideoView != null) {
            return mVideoView.getCurrentPosition();
        } else {
            return 0;
        }
    }

    @Override
    public void seekTo(int pos) {
        mVideoView.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        if (mVideoView != null) {
            return mVideoView.isPlaying();
        }else {
            return false;
        }
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return mVideoView.canPause();
    }

    @Override
    public boolean canSeekBackward() {
        return mVideoView.canSeekBackward();
    }

    @Override
    public boolean canSeekForward() {
        return mVideoView.canSeekForward();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {

    }
}
