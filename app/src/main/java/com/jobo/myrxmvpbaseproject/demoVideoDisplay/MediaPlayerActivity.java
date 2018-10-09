package com.jobo.myrxmvpbaseproject.demoVideoDisplay;

import android.media.MediaPlayer;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * MediaPlayer+SurfaceVIew 实现视频播放
 * Created by JoBo on 2018/7/6.
 */

public class MediaPlayerActivity extends BaseActivity {
    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.sfv)//能够播放图像的控件
            SurfaceView mSfv;
    @BindView(R.id.sb)
    SeekBar mSb;
    @BindView(R.id.play)//播放按钮
            Button mPlay;
    @BindView(R.id.pause)
    Button mPause;
    private Timer mTimer;//定时器
    private TimerTask mTimerTask;//定时器任务
    private MediaPlayer mMediaPlayer;//媒体播放器
    private String path;//本地文件路径
    private SurfaceHolder holder;
    private int mPosition = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mediapalyer_surfaceview;
    }

    @Override
    protected void init() {
        holder = mSfv.getHolder();
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//过时
        mSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //当进度条停止拖动的时候，把媒体播放器的进度跳转到进度条对应的进度
                if (mMediaPlayer != null) {
                    mMediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //为了避免图像控件还没有创建成功，用户就开始播放视频，造成程序异常，所以在创建成功后才使播放按钮可点击
                LogUtils.d("surfaceCreated");
                mPlay.setEnabled(true);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LogUtils.d("surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //当程序没有退出，但不在前台运行时，因为surfaceview很耗费空间，所以会自动销毁，
                // 这样就会出现当你再次点击进程序的时候点击播放按钮，声音继续播放，却没有图像
                //为了避免这种不友好的问题，简单的解决方式就是只要surfaceview销毁，我就把媒体播放器等
                //都销毁掉，这样每次进来都会重新播放，当然更好的做法是在这里再记录一下当前的播放位置，
                //每次点击进来的时候把位置赋给媒体播放器，很简单加个全局变量就行了。
                LogUtils.d("surfaceDestroyed");
                if (mMediaPlayer != null) {
                    mPosition = mMediaPlayer.getCurrentPosition();
                    stop();
                }
            }
        });

    }

    @Override
    protected void initBundleData() {

    }

    private void play() {
        mPlay.setEnabled(false);//在播放时不允许再点击播放按钮

        if (isPause) {//如果是暂停状态下播放，直接start
            isPause = false;
            mMediaPlayer.start();
            return;
        }

        path = Environment.getExternalStorageDirectory().getPath() + "/Download/";
        path = path + mEt.getText().toString();//sdcard的路径加上文件名称是文件全路径
        File file = new File(path);
        if (!file.exists()) {//判断需要播放的文件路径是否存在，不存在退出播放流程
            ToastUtils.showToast("文件路径不存在");
            return;
        }

        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setDisplay(holder);//将影像播放控件与媒体播放控件关联起来

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {//视频播放完成后，释放资源
                    mPlay.setEnabled(true);
                    stop();
                }
            });

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //媒体播放器就绪后，设置进度条总长度，开启计时器不断更新进度条，播放视频
                    LogUtils.d("onPrepared");
                    mSb.setMax(mMediaPlayer.getDuration());
                    mTimer = new Timer();
                    mTimerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (mMediaPlayer != null) {
                                try {
                                    int time = mMediaPlayer.getCurrentPosition();
                                    mSb.setProgress(time);
                                } catch (Exception e) {
                                    mSb.setProgress(0);
                                    LogUtils.e("e:" + e);
                                }
                            }
                        }
                    };
                    mTimer.schedule(mTimerTask, 0, 500);
                    mSb.setProgress(mPosition);
                    mMediaPlayer.seekTo(mPosition);
                    mMediaPlayer.start();
                }
            });

            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(View v) {
        play();
        LogUtils.d(path);
    }

    private boolean isPause;

    private void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
            mPlay.setEnabled(true);
        }
    }

    public void pause(View v) {
        pause();
    }

    private void replay() {
        isPause = false;
        if (mMediaPlayer != null) {
            stop();
            play();
        }
    }

    public void replay(View v) {
        replay();
    }

    private void stop() {
        isPause = false;
        if (mMediaPlayer != null) {
            mSb.setProgress(0);
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            if (mTimer != null) {
                mTimer.cancel();
            }
            mPlay.setEnabled(true);
        }
    }

    public void stop(View v) {
        stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }
}
