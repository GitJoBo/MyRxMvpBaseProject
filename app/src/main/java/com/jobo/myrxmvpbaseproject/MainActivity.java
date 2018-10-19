package com.jobo.myrxmvpbaseproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.demoBanner.activity.BannerActivity;
import com.jobo.myrxmvpbaseproject.demoBigBitmap.BigBitmapActivity;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.FragmentByValActivity;
import com.jobo.myrxmvpbaseproject.demoGlide.activity.GlideActivity;
import com.jobo.myrxmvpbaseproject.demoMaterialDesign.MaterialDesignActivity;
import com.jobo.myrxmvpbaseproject.demoRecycleview.activity.RecycleActivity;
import com.jobo.myrxmvpbaseproject.demoUpDownload.activity.UpDownloadActivity;
import com.jobo.myrxmvpbaseproject.demoVIdeoRecord.VideoRecordActivity;
import com.jobo.myrxmvpbaseproject.demoVideoDisplay.VideoDisplayActivity;
import com.jobo.myrxmvpbaseproject.demoVoice2Text.Voice2TextActivity;
import com.jobo.myrxmvpbaseproject.login.activity.LoginActivity;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;
import com.sigseg.android.map.ImageViewerActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.btn_recyclerView)
    Button mPhoneAddress;
    @BindView(R.id.btn_up_download)
    Button mUpDownload;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initBundleData() {

    }

    @OnClick({R.id.login, R.id.btn_recyclerView, R.id.btn_up_download, R.id.btn_glide, R.id.btn_banner,
            R.id.btn_video_display, R.id.btn_big_bitmap, R.id.btn_voice2text, R.id.btn_video_record,
            R.id.btn_fragment_byval,R.id.btn_material_design})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                UIUtils.startActivity(this, LoginActivity.class);
                break;
            case R.id.btn_recyclerView:
                UIUtils.startActivity(this, RecycleActivity.class);
                break;
            case R.id.btn_up_download:
                UIUtils.startActivity(this, UpDownloadActivity.class);
                break;
            case R.id.btn_glide:
                UIUtils.startActivity(this, GlideActivity.class);
                break;
            case R.id.btn_banner:
                UIUtils.startActivity(this, BannerActivity.class);
                break;
            case R.id.btn_video_display:
                UIUtils.startActivity(this, VideoDisplayActivity.class);
                break;
            case R.id.btn_big_bitmap:
                UIUtils.startActivity(this, BigBitmapActivity.class);
                break;
            case R.id.btn_voice2text:
                UIUtils.startActivity(this, Voice2TextActivity.class);
//                UIUtils.startActivity(this, Voice2TextActivity2.class);
                break;
            case R.id.btn_video_record:
                // 申请权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager
                        .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
                }
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager
                                .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            3);
                }
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager
                        .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 3);
                }
//                NotificationManager notificationManager = (NotificationManager) MyApplication.sContext
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//                        && !notificationManager.isNotificationPolicyAccessGranted()) {
//                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//                    getApplicationContext().startActivity(intent);
//                    return;
//                }
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                        .PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat
                        .checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                        .PERMISSION_GRANTED) {
                    UIUtils.startActivity(this, VideoRecordActivity.class);
                } else {
                    ToastUtils.showToast("权限不足");
                }
                break;
            case R.id.btn_material_design:
                UIUtils.startActivity(this, MaterialDesignActivity.class);
                break;

            case R.id.btn_fragment_byval:
                UIUtils.startActivity(this, FragmentByValActivity.class);
                break;

        }
    }
}
