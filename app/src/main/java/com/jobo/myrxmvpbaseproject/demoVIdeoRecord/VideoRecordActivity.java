package com.jobo.myrxmvpbaseproject.demoVIdeoRecord;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoVIdeoRecord.SmallVideoLib2.CompressActivity;
import com.jobo.myrxmvpbaseproject.demoVIdeoRecord.SmallVideoLib2.SendSmallVideoActivity;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;
import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JoBo on 2018/8/23.
 */

public class VideoRecordActivity extends BaseActivity {
    private static final String TAG = "VideoRecordActivity";
    public static final int RECORD_SYSTEM_VIDEO = 1;
    private String mTXFilePath;//系统录像文件保存路径

    @Override
    protected int getContentViewId() {
        return R.layout.activity_video_record;
    }

    @Override
    protected void init() {
        initSmallVideo();
    }

    @Override
    protected void initBundleData() {

    }

    @OnClick({R.id.btn_sys, R.id.btn_custom, R.id.btn_ffmpeg,R.id.btn_compress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sys://启动系统自带录制
                Uri fileUri;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    fileUri = Uri.fromFile(getOutputMediaFile("VIDXT_"));
                } else {
                    /**
                     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                     */
                    fileUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider",
                            getOutputMediaFile("VIDXT_"));
                }

                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);     //限制的录制时长 以秒为单位
//                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024);        //限制视频文件大小 以字节为单位
//                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);      //设置拍摄的质量0~1
//                intent.putExtra(MediaStore.EXTRA_FULL_SCREEN, false);        // 全屏设置
                startActivityForResult(intent, RECORD_SYSTEM_VIDEO);

                break;
            case R.id.btn_custom:
                UIUtils.startActivity(this, VideoRecordCustomActivity.class);
                break;
            case R.id.btn_ffmpeg:
                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                        .fullScreen(false)//true全屏录制
                        .smallVideoWidth(Integer.valueOf(480))
                        .smallVideoHeight(Integer.valueOf(360))
                        .recordTimeMax(Integer.valueOf(6 * 6 * 1000))
                        .recordTimeMin(Integer.valueOf(1500))
                        .maxFrameRate(Integer.valueOf(20))//最大帧率
                        .videoBitrate(Integer.valueOf(580000))//比特率
                        .captureThumbnailsTime(1)
                        .build();
                MediaRecorderActivity.goSmallVideoRecorder(this, SendSmallVideoActivity.class.getName(), config);
                break;
            case R.id.btn_compress:
                UIUtils.startActivity(this, CompressActivity.class);
                break;
        }
    }

    private File getOutputMediaFile(String nameType) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ToastUtils.showToast("请检查SDCard");
            return null;
        }
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
//                "MyCameraApp");
//        if (!mediaStorageDir.exists()) {
//            mediaStorageDir.mkdirs();
//        }
        mTXFilePath = getSDPath(getApplicationContext()) + nameType + new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date()) + ".mp4";
        File mediaFile = new File(mTXFilePath);
        return mediaFile;
    }

    public void initSmallVideo() {
        // 设置拍摄视频缓存路径
//        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File dcim = getOutputMediaFile("VIDFF_");
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(getSDPath(getApplicationContext()));
            } else {
                JianXiCamera.setVideoCachePath(getSDPath(getApplicationContext()).replace("/sdcard/",
                        "/sdcard-ext/")
                        + "");
            }
        } else {
            JianXiCamera.setVideoCachePath(getSDPath(getApplicationContext()));
        }
        // 初始化拍摄
        JianXiCamera.initialize(true, null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case RECORD_SYSTEM_VIDEO://系统录制完成
//                LogUtils.d(TAG+",getData():"+data.getData());
//                LogUtils.d(TAG+",getData().getPath():"+data.getData().getPath());
//                LogUtils.d(TAG+",getData().getEncodedPath():"+data.getData().getEncodedPath());
                Intent intent = new Intent(this, NowPlayVideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoPath", mTXFilePath);
                intent.putExtras(bundle);
                startActivity(intent);
//                finish();
                break;
        }
    }
}
