package com.jobo.myrxmvpbaseproject.demoVIdeoRecord;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.*;
import android.support.annotation.RequiresApi;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义录制（MediaRecorder录制片段+isoviewer-1.0-RC-27.jar（mp4parser）合并片段）
 * 开始时有嘀声，暂时无法静音
 * Created by JoBo on 2018/8/21.
 */

public class VideoRecordCustomActivity extends BaseActivity {
    private static final String TAG = "VideoRecordCustomActivity";
    @BindView(R.id.record_surfaceView)
    SurfaceView mRecordSurfaceView;
    @BindView(R.id.record_control)
    ImageView mRecordControl;
    @BindView(R.id.record_pause)
    ImageView mRecordPause;
    @BindView(R.id.record_time)
    Chronometer mRecordTime;

    private AudioManager mAudioManager;
    private int mRingerModeOld;
    private SurfaceHolder mSurfaceHolder;
    //录像机状态标识
    private int mRecorderState;
    public static final int STATE_INIT = 0;
    public static final int STATE_RECORDING = 1;
    public static final int STATE_PAUSE = 2;
    //    private boolean isRecording;// 标记，判断当前是否正在录制
//    private boolean isPause; //暂停标识
    private long mPauseTime = 0;           //录制暂停时间间隔
    // 存储文件
    private File mVecordFile;
    private Camera mCamera;
    private MediaRecorder mediaRecorder;
    private String currentVideoFilePath;
    private String saveVideoPath = "";
    private MediaRecorder.OnErrorListener OnErrorListener = (mediaRecorder, what, extra) -> {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };


    private SurfaceHolder.Callback mSurfaceCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            initCamera();
        }
        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            if (mSurfaceHolder.getSurface() == null) {
                return;
            }
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            releaseCamera();
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_video_record_custom;
    }

    @Override
    protected void init() {
        mRecordPause.setEnabled(false);
        //配置SurfaceHolder
        mSurfaceHolder = mRecordSurfaceView.getHolder();
        // 设置Surface不需要维护自己的缓冲区
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 设置分辨率
        mSurfaceHolder.setFixedSize(320, 280);
        // 设置该组件不会让屏幕自动关闭
        mSurfaceHolder.setKeepScreenOn(true);
        //回调接口
        mSurfaceHolder.addCallback(mSurfaceCallBack);
    }

    @Override
    protected void initBundleData() {

    }

    @OnClick({R.id.record_control, R.id.record_pause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.record_control:
                if (mRecorderState == STATE_INIT) {
                    if (getSDPath(getApplicationContext()) == null)
                        return;
                    //视频文件保存路径，configMediaRecorder方法中会设置
                    currentVideoFilePath = getSDPath(getApplicationContext()) + getVideoName();
                    LogUtils.d("currentVideoFilePath:" + currentVideoFilePath);
                    //开始录制视频
                    if (!startRecord())
                        return;
                    refreshControlUI();
                    mRecorderState = STATE_RECORDING;
                } else if (mRecorderState == STATE_RECORDING) {
                    //停止视频录制
                    stopRecord();
                    //先给Camera加锁后再释放相机
                    mCamera.lock();
                    releaseCamera();
                    refreshControlUI();
                    //判断是否进行视频合并
                    if ("".equals(saveVideoPath)) {
                        saveVideoPath = currentVideoFilePath;
                    } else {
                        mergeRecordVideoFile();
                    }
                    mRecorderState = STATE_INIT;
                    //延迟一秒跳转到播放器，（确保视频合并完成后跳转） TODO 具体的逻辑可根据自己的使用场景跳转
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(VideoRecordCustomActivity.this, NowPlayVideoActivity.class);
                            Bundle bundle = new Bundle();
                            LogUtils.d(TAG+saveVideoPath);
                            bundle.putString("videoPath", saveVideoPath);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                } else if (mRecorderState == STATE_PAUSE) {
                    //代表视频暂停录制时，点击中心按钮
                    Intent intent = new Intent(VideoRecordCustomActivity.this, NowPlayVideoActivity.class);
                    Bundle bundle = new Bundle();
                    LogUtils.d(TAG+saveVideoPath);
                    bundle.putString("videoPath", saveVideoPath);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.record_pause:
                if (mRecorderState == STATE_RECORDING) {
                    //正在录制的视频，点击后暂停
                    //取消自动对焦
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            if (success)
                                VideoRecordCustomActivity.this.mCamera.cancelAutoFocus();
                        }
                    });
                    stopRecord();
                    refreshPauseUI();
                    //判断是否进行视频合并
                    if ("".equals(saveVideoPath)) {
                        saveVideoPath = currentVideoFilePath;
                    } else {
                        mergeRecordVideoFile();
                    }
                    mRecorderState = STATE_PAUSE;
                } else if (mRecorderState == STATE_PAUSE) {
                    if (getSDPath(getApplicationContext()) == null)
                        return;
                    //视频文件保存路径，configMediaRecorder方法中会设置
                    currentVideoFilePath = getSDPath(getApplicationContext()) + getVideoName();
                    LogUtils.d("currentVideoFilePath:" + currentVideoFilePath);
                    //继续视频录制
                    if (!startRecord()) {
                        return;
                    }
                    refreshPauseUI();
                    mRecorderState = STATE_RECORDING;
                }
                break;
        }
    }

    /**
     * 初始化摄像头
     */
    private void initCamera() {
        if (mCamera != null) {
            releaseCamera();
        }
        mCamera = Camera.open();
        if (mCamera == null) {
            Toast.makeText(this, "未能获取到相机！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //将相机与SurfaceHolder绑定
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //配置CameraParams
            configCameraParams();
            //启动相机预览
            mCamera.startPreview();
        } catch (IOException e) {
            //有的手机会因为兼容问题报错，这就需要开发者针对特定机型去做适配了
            LogUtils.e("Error starting camera preview: " + e.getMessage());
        }
    }

    /**
     * 释放摄像头资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 设置摄像头为竖屏
     */
    private void configCameraParams() {
        Camera.Parameters params = mCamera.getParameters();
        //设置相机的横竖屏(竖屏需要旋转90°)
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            params.set("orientation", "portrait");
            mCamera.setDisplayOrientation(90);
        } else {
            params.set("orientation", "landscape");
            mCamera.setDisplayOrientation(0);
        }
        //设置聚焦模式
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        //缩短Recording启动时间
        params.setRecordingHint(true);
        //影像稳定能力
        if (params.isVideoStabilizationSupported())
            params.setVideoStabilization(true);
        mCamera.setParameters(params);
    }

    /**
     * 开始录制视频
     */
    public boolean startRecord() {
        initCamera();
        //录制视频前必须先解锁Camera
        mCamera.unlock();
        configMediaRecorder();
        try {
            //开始录制
            mediaRecorder.prepare();
            mediaRecorder.start();
            //还原用户设置
//            mAudioManager.setRingerMode(mRingerModeOld);
        } catch (IOException e) {
            LogUtils.e("IOException" + e);
            return false;
        }
        return true;
    }

    /**
     * 停止录制视频
     */
    public void stopRecord() {
        // 设置后不会崩
        mediaRecorder.setOnErrorListener(null);
        mediaRecorder.setPreviewDisplay(null);
        //停止录制
        mediaRecorder.stop();
        mediaRecorder.reset();
        //释放资源
        mediaRecorder.release();
        mediaRecorder = null;
    }

    /**
     * 配置MediaRecorder()
     */
    private void configMediaRecorder() {
//        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        //记录用户声音模式
//        mRingerModeOld = mAudioManager.getRingerMode();
//        //更改为静音模式
//        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

//        ((AudioManager)getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM,true);

        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setOnErrorListener(OnErrorListener);
        //使用SurfaceView预览
        mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        //1.设置采集声音
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        //设置采集图像
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //2.设置视频，音频的输出格式 mp4
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //3.设置音频的编码格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //设置图像的编码格式
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        //设置立体声
//        mediaRecorder.setAudioChannels(2);
        //设置最大录像时间 单位：毫秒
        mediaRecorder.setMaxDuration(60 * 6 * 1000);
        //设置最大录制的大小 单位，字节
//        mediaRecorder.setMaxFileSize(1024 * 1024);
        //音频一秒钟包含多少数据位
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        mediaRecorder.setAudioEncodingBitRate(44100);
        if (mProfile.videoBitRate > 2 * 1024 * 1024) {
            mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
        } else {
            mediaRecorder.setVideoEncodingBitRate(1024 * 1024);
        }
        mediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);
        //设置选择角度，顺时针方向，因为默认是逆向90度的，这样图像就是正常显示了,这里设置的是观看保存后的视频的角度
        mediaRecorder.setOrientationHint(90);
        //设置录像的分辨率
//        mediaRecorder.setVideoSize(352, 288);
        mediaRecorder.setVideoSize(480, 360);
        //设置录像视频输出地址
        mediaRecorder.setOutputFile(currentVideoFilePath);
    }

    /**
     * 点击中间按钮，执行的UI更新操作
     */
    private void refreshControlUI() {
        if (mRecorderState == STATE_INIT) {
            //录像时间计时
            mRecordTime.setBase(SystemClock.elapsedRealtime());
            mRecordTime.start();
            mRecordControl.setImageResource(R.mipmap.recordvideo_stop);
            //1s后才能按停止录制按钮
            mRecordControl.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecordControl.setEnabled(true);
                }
            }, 1000);
            mRecordPause.setVisibility(View.VISIBLE);
            mRecordPause.setEnabled(true);
        } else if (mRecorderState == STATE_RECORDING) {
            mPauseTime = 0;
            mRecordTime.stop();
            mRecordControl.setImageResource(R.mipmap.recordvideo_start);
            mRecordPause.setVisibility(View.GONE);
            mRecordPause.setEnabled(false);
        }
    }

    /**
     * 合并录像视频方法
     */
    private void mergeRecordVideoFile() {
        new Thread(() -> {
            try {
                String[] str = new String[]{saveVideoPath, currentVideoFilePath};
                //将2个视频文件合并到 append.mp4文件下
                VideoUtils.appendVideo(VideoRecordCustomActivity.this, getSDPath(VideoRecordCustomActivity.this) + "append" +
                        ".mp4", str);
                File reName = new File(saveVideoPath);
                String filePath = getSDPath(VideoRecordCustomActivity.this);
                LogUtils.d("filePath:" + filePath);
                File f = new File(filePath + "append.mp4");
                //再将合成的append.mp4视频文件 移动到 saveVideoPath 路径下
                f.renameTo(reName);
                if (reName.exists()) {
                    f.delete();
                    new File(currentVideoFilePath).delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 创建视频文件保存路径
     */
    public static String getSDPath(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(context, "请查看您的SD卡是否存在！", Toast.LENGTH_SHORT).show();
            return null;
        }
        File sdDir = Environment.getExternalStorageDirectory();
        File eis = new File(sdDir.toString() + "/RecordVideo/");
        if (!eis.exists()) {
            eis.mkdir();
        }
        return sdDir.toString() + "/RecordVideo/";
    }

    private String getVideoName() {
        return "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("onResume" + mRecorderState);
//        if (mRecorderState == STATE_PAUSE) {
//            if (getSDPath(getApplicationContext()) == null)
//                return;
//            //视频文件保存路径，configMediaRecorder方法中会设置
//            currentVideoFilePath = getSDPath(getApplicationContext()) + getVideoName();
//            Log.d(TAG, "currentVideoFilePath:" + currentVideoFilePath);
//            //继续视频录制
//            if (!startRecord()) {
//                return;
//            }
//            refreshPauseUI();
//            mRecorderState = STATE_RECORDING;
//        }
    }

    /**
     * 点击暂停继续按钮，执行的UI更新操作
     */
    private void refreshPauseUI() {
        if (mRecorderState == STATE_RECORDING) {
            mRecordPause.setImageResource(R.mipmap.control_play);
            mPauseTime = SystemClock.elapsedRealtime();
            mRecordTime.stop();
        } else if (mRecorderState == STATE_PAUSE) {
            mRecordPause.setImageResource(R.mipmap.control_pause);

            if (mPauseTime == 0) {
                mRecordTime.setBase(SystemClock.elapsedRealtime());
            } else {
                mRecordTime.setBase(SystemClock.elapsedRealtime() - (mPauseTime - mRecordTime.getBase()));
            }
            mRecordTime.start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d("onPause" + mRecorderState);
        if (mRecorderState == STATE_RECORDING) {
            //取消自动对焦
            if (mCamera != null)
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success)
                            VideoRecordCustomActivity.this.mCamera.cancelAutoFocus();
                    }
                });
            stopRecord();
//            pauseRecord();
            refreshPauseUI();
            //判断是否进行视频合并
            if ("".equals(saveVideoPath)) {
                saveVideoPath = currentVideoFilePath;
            } else {
                mergeRecordVideoFile();
            }
            mRecorderState = STATE_PAUSE;
        }
    }

}
