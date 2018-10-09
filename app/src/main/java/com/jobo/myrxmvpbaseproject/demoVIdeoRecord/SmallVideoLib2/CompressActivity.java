package com.jobo.myrxmvpbaseproject.demoVIdeoRecord.SmallVideoLib2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.mabeijianxi.smallvideorecord2.LocalMediaCompress;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.StringUtils;
import com.mabeijianxi.smallvideorecord2.model.*;

/**
 * Created by JoBo on 2018/8/28.
 */

public class CompressActivity extends BaseActivity {
    @BindView(R.id.rb_auto)
    RadioButton mRbAuto;
    @BindView(R.id.rb_vbr)
    RadioButton mRbVbr;
    @BindView(R.id.rb_cbr)
    RadioButton mRbCbr;
    @BindView(R.id.rg_mode)
    RadioGroup mRgMode;
    @BindView(R.id.et_crfSize)
    EditText mEtCrfSize;
    @BindView(R.id.ll_crf)
    LinearLayout mLlCrf;
    @BindView(R.id.tv_maxbitrate)
    TextView mTvMaxbitrate;
    @BindView(R.id.et_maxbitrate)
    EditText mEtMaxbitrate;
    @BindView(R.id.et_bitrate)
    EditText mEtBitrate;
    @BindView(R.id.ll_bitrate)
    LinearLayout mLlBitrate;
    @BindView(R.id.et_only_framerate)
    EditText mEtOnlyFramerate;
    @BindView(R.id.ll_only_framerate)
    LinearLayout mLlOnlyFramerate;
    @BindView(R.id.et_only_scale)
    EditText mEtOnlyScale;
    @BindView(R.id.ll_only_scale)
    LinearLayout mLlOnlyScale;
    @BindView(R.id.spinner_only_compress)
    Spinner mSpinnerOnlyCompress;
    @BindView(R.id.bt_choose)
    Button mBtChoose;
    @BindView(R.id.ll_only_compress)
    LinearLayout mLlOnlyCompress;
    private ProgressDialog mProgressDialog;
    private final int CHOOSE_CODE = 0x000520;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_compress;
    }

    @Override
    protected void init() {
        mRgMode.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_auto:
                    mLlCrf.setVisibility(View.VISIBLE);
                    mLlBitrate.setVisibility(View.GONE);
                    break;
                case R.id.rb_vbr:
                    mLlCrf.setVisibility(View.GONE);
                    mLlBitrate.setVisibility(View.VISIBLE);
                    mTvMaxbitrate.setVisibility(View.VISIBLE);
                    mEtMaxbitrate.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_cbr:
                    mLlCrf.setVisibility(View.GONE);
                    mLlBitrate.setVisibility(View.VISIBLE);
                    mTvMaxbitrate.setVisibility(View.GONE);
                    mEtMaxbitrate.setVisibility(View.GONE);
                    break;
            }
        });

    }

    @Override
    protected void initBundleData() {

    }

    @OnClick(R.id.bt_choose)
    public void onViewClicked() {
        Intent it = new Intent(Intent.ACTION_GET_CONTENT,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        it.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*");
        startActivityForResult(it, CHOOSE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_CODE) {
            //
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.MIME_TYPE};

                Cursor cursor = getContentResolver().query(uri, proj, null,
                        null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int _data_num = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    int mime_type_num = cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE);

                    String _data = cursor.getString(_data_num);
//                    String mime_type = cursor.getString(mime_type_num);
//                    if (!TextUtils.isEmpty(mime_type) && mime_type.contains("video") && !TextUtils.isEmpty(_data)) {
                    BaseMediaBitrateConfig compressMode = null;

                    int compressModeCheckedId = mRgMode.getCheckedRadioButtonId();

                    if (compressModeCheckedId == R.id.rb_cbr) {
                        String bitrate = mEtBitrate.getText().toString();
                        if (checkStrEmpty(bitrate, "请输入压缩额定码率")) {
                            return;
                        }
                        compressMode = new CBRMode(166, Integer.valueOf(bitrate));
                    } else if (compressModeCheckedId == R.id.rb_auto) {
                        String crfSize = mEtCrfSize.getText().toString();
                        if (TextUtils.isEmpty(crfSize)) {
                            compressMode = new AutoVBRMode();
                        } else {
                            compressMode = new AutoVBRMode(Integer.valueOf(crfSize));
                        }
                    } else if (compressModeCheckedId == R.id.rb_vbr) {
                        String maxBitrate = mEtMaxbitrate.getText().toString();
                        String bitrate = mEtOnlyScale.getText().toString();

                        if (checkStrEmpty(maxBitrate, "请输入压缩最大码率") || checkStrEmpty(bitrate, "请输入压缩额定码率")) {
                            return;
                        }
                        compressMode = new VBRMode(Integer.valueOf(maxBitrate), Integer.valueOf(bitrate));
                    } else {
                        compressMode = new AutoVBRMode();
                    }

                    if (!mSpinnerOnlyCompress.getSelectedItem().toString().equals("none")) {
                        compressMode.setVelocity(mSpinnerOnlyCompress.getSelectedItem().toString());
                    }

                    String sRate = mEtOnlyFramerate.getText().toString();
                    String scale = mEtOnlyScale.getText().toString();
                    int iRate = 0;
                    float fScale = 0;
                    if (!TextUtils.isEmpty(sRate)) {
                        iRate = Integer.valueOf(sRate);
                    }
                    if (!TextUtils.isEmpty(scale)) {
                        fScale = Float.valueOf(scale);
                    }
                    LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
                    final LocalMediaConfig config = buidler
                            .setVideoPath(_data)
                            .captureThumbnailsTime(1)
                            .doH264Compress(compressMode)
                            .setFramerate(iRate)
                            .setScale(fScale)
                            .build();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showProgress("", "压缩中...", -1);
                                }
                            });
                            OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config)
                                    .startCompress();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hideProgress();
                                }
                            });
                            Intent intent = new Intent(CompressActivity.this, SendSmallVideoActivity.class);
                            intent.putExtra(MediaRecorderActivity.VIDEO_URI, onlyCompressOverBean.getVideoPath());
                            intent.putExtra(MediaRecorderActivity.VIDEO_SCREENSHOT, onlyCompressOverBean
                                    .getPicPath());
                            startActivity(intent);
                        }
                    }).start();
//                    } else {
//                        Toast.makeText(this, "选择的不是视频或者地址错误,也可能是这种方式定制神机取不到！", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        }
    }

    private boolean checkStrEmpty(String str, String display) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, display, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void showProgress(String title, String message, int theme) {
        if (mProgressDialog == null) {
            if (theme > 0)
                mProgressDialog = new ProgressDialog(this, theme);
            else
                mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
        }
        if (!StringUtils.isEmpty(title))
            mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }


    private void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
