package com.jobo.myrxmvpbaseproject.demoVIdeoRecord.SmallVideoLib2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoVIdeoRecord.NowPlayVideoActivity;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;

/**
 * Created by JoBo on 2018/8/23.
 */

public class SendSmallVideoActivity extends BaseActivity {
    @BindView(R.id.et_send_content)
    EditText mEtSendContent;
    @BindView(R.id.iv_video_screenshot)
    ImageView mIvVideoScreenShort;
    private String mVideoUri;
    private String mVideoScreenshot;
    private AlertDialog mAlertDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_send_small_video;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initBundleData() {
        Intent intent = getIntent();
        mVideoUri = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
        mVideoScreenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
        Bitmap bitmap = BitmapFactory.decodeFile(mVideoScreenshot);
        mIvVideoScreenShort.setImageBitmap(bitmap);
        mEtSendContent.setHint("您视频地址为:" + mVideoUri);
    }


    @OnClick({R.id.tv_cancel, R.id.tv_send, R.id.iv_video_screenshot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                hesitate();
                break;
            case R.id.tv_send:
                break;
            case R.id.iv_video_screenshot:
                Intent intent = new Intent(this, NowPlayVideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("videoPath", mVideoUri);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    private void hesitate() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.hint)
                    .setMessage(R.string.record_camera_exit_dialog_message)
                    .setNegativeButton(
                            R.string.record_camera_cancel_dialog_yes,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();

//                                    FileUtils.deleteDir(getIntent().getStringExtra(MediaRecorderActivity.OUTPUT_DIRECTORY));

                                }

                            })
                    .setPositiveButton(R.string.record_camera_cancel_dialog_no,
                            null).setCancelable(false).show();
        } else {
            mAlertDialog.show();
        }
    }

}
