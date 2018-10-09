package com.jobo.myrxmvpbaseproject.demoUpDownload.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.*;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoUpDownload.IUpDownloadView;
import com.jobo.myrxmvpbaseproject.demoUpDownload.UpDownloadPresenter;
import com.jobo.myrxmvpbaseproject.demoUpDownload.utils.ProgressDownloader;
import com.jobo.myrxmvpbaseproject.demoUpDownload.utils.ProgressListener;
import com.jobo.myrxmvpbaseproject.demoUpDownload.utils.ProgressResponseBody;
import com.jobo.myrxmvpbaseproject.demoUpDownload.utils.UploadFileRequestBody;
import com.jobo.myrxmvpbaseproject.widget.dialog.UpProgressDialog;
import com.jobo.myrxmvpbaseproject.widget.dialog.WaitProgressDialog;
import com.jobo.myrxmvpbaseproject.widget.view.HorizontalProgressBar;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;

/**
 * Created by JoBo on 2018/6/6.
 */

public class UpDownloadActivity extends BaseActivity implements IUpDownloadView, ProgressListener,
        ProgressResponseBody.ProgressListener {
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    @BindView(R.id.et_file_path)
    EditText mEtFilePath;
    @BindView(R.id.btn_select)
    Button mBtnSelect;
    @BindView(R.id.btn_up)
    Button mBtnUp;
    @BindView(R.id.tv_re)
    TextView mTvRe;
    @BindView(R.id.horizontal_progressBar)
    HorizontalProgressBar mHorizontalProgressBar;
    private UpDownloadPresenter mUpDownloadPresenter = new UpDownloadPresenter(this, this);
    private WaitProgressDialog mWaitProgressDialog;
    private UpProgressDialog mUpProgressDialog;

    private long mBreakPoints;
    private ProgressDownloader downloader;
    private long mTotalBytes;
    private long mContentLength;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_up_download;
    }

    @Override
    protected void init() {
        mWaitProgressDialog = new WaitProgressDialog(this, R.style.CustomHttpWaitDialog, null);
        mWaitProgressDialog.setCanceledOnTouchOutside(false);
        // 申请并获得权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        mUpProgressDialog = new UpProgressDialog(this, R.style.CustomHttpWaitDialog, "请稍后...", this);
        mUpProgressDialog.setCanceledOnTouchOutside(false);
//        mUpProgressDialog.show();
    }

    @Override
    protected void initBundleData() {

    }

    @OnClick({R.id.btn_select, R.id.btn_up, R.id.btn_up2, R.id.btn_up3, R.id.btn_down,
            R.id.btn_down_pause, R.id.btn_down_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");//选择图片
                //intent.setType("audio/*"); //选择音频
                //intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
//                intent.setType("video/*;image/*");//同时选择视频和图片
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);

                break;
            case R.id.btn_up:
                fileUpLoad(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.d("aaaaaa,onFailure,e=" + e);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast("上传失败！");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.d("response=" + response.toString());
                                mTvRe.setText(response.toString());
                                ToastUtils.showToast("上传成功！");
                            }
                        });
                    }
                });
                break;
            case R.id.btn_up2:
                String path = mEtFilePath.getText().toString().trim();
                File file = new File(path);
                if (!file.exists()) {
                    ToastUtils.showToast("文件不存在");
                } else {
                    this.showProgressBar();
                    mUpDownloadPresenter.upFile(file, mUpProgressDialog, 1);
                }
                break;
            case R.id.btn_up3:
                String path3 = mEtFilePath.getText().toString().trim();
                File file3 = new File(path3);
                if (!file3.exists()) {
                    ToastUtils.showToast("文件不存在");
                } else {
                    mUpDownloadPresenter.upFile(file3, this, 2);
                }
                break;
            case R.id.btn_down:
                String url = mEtFilePath.getText().toString().trim();
                if (TextUtils.isEmpty(url)){
                    mEtFilePath.setText("http://shouji.360tpcdn.com/180809/e552e11e2437dcb3cc376c72639e358f/com.qihoo360.mobilesafe_261.apk");
                    url = "http://shouji.360tpcdn.com/180809/e552e11e2437dcb3cc376c72639e358f/com.qihoo360.mobilesafe_261.apk";
                }
// 新下载前清空断点信息
                mBreakPoints = 0L;
//                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//                        "sample.apk");
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"test.txt");
                downloader = new ProgressDownloader(url, null, this);
                downloader.download(0L);
                break;
            case R.id.btn_down_pause:
                downloader.pause();
                LogUtils.d("下载暂停");
                // 存储此时的contentLength，即断点位置。
                mBreakPoints = mContentLength;
                break;
            case R.id.btn_down_continue:
                downloader.download(mBreakPoints);
                break;
        }
    }

    @Override
    public void showLoading() {
        mWaitProgressDialog.show();
    }

    @Override
    public void closeLoading() {
        mWaitProgressDialog.hide();
    }

    String path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
                mEtFilePath.setText(path);
                Toast.makeText(this, path + "11111", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                mEtFilePath.setText(path);
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                mEtFilePath.setText(path);
                Toast.makeText(this, path + "222222", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0 || PackageManager.PERMISSION_GRANTED != grantResults[0]) {
            ToastUtils.showToast("你拒绝了权限");
        } else {
            //在这执行你创建文件的代码

        }
    }


    public void fileUpLoad(Callback callback) {
        // 获得输入框中的路径
        String path = mEtFilePath.getText().toString().trim();
        File file = new File(path);
        LogUtils.d("" + file.exists());
        OkHttpClient client = new OkHttpClient();
        // 上传文件使用MultipartBody.Builder
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "sunhaiyu") // 提交普通字段
                .addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file)) //
                // 提交图片，第一个参数是键（name="第一个参数"），第二个参数是文件名，第三个是一个RequestBody
                .build();
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(requestBody, this);
        // POST请求
        Request request = new Request.Builder()
                .url("http://192.168.0.107:8080/appProject/upLoad")//appProject/upLoad  //dservlet/upLoad
                .post(uploadFileRequestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void showResult(String bean) {
        if (mUpProgressDialog != null && mUpProgressDialog.isShowing()) {
            mUpProgressDialog.dismiss();
//            mUpProgressDialog = null;
//            mProgressBar.setProgress(0);
        }
    }

    @Override
    public void showProgressBar() {
        if (mUpProgressDialog == null) {
            mUpProgressDialog = new UpProgressDialog(this, R.style.CustomHttpWaitDialog, "请稍后...", this);
            mUpProgressDialog.setCanceledOnTouchOutside(false);
            mUpProgressDialog.show();
        } else {
            mUpProgressDialog.show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
        LogUtils.d("hasWrittenLen=" + hasWrittenLen + ",totalLen=" + totalLen + ",hasFinish=" + hasFinish);
        long progress = (hasWrittenLen / 100) / (totalLen / 10000);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvRe.setText(progress + "%");
                mHorizontalProgressBar.setProgressWithAnimation(progress);
//                mHorizontalProgressBar.setCurrentProgress(progress);
            }
        });
    }

    @Override
    public void onPreExecute(long totalBytes) {
        // 文件总长只需记录一次，要注意断点续传后的contentLength只是剩余部分的长度
        if (mTotalBytes == 0L) {
            mTotalBytes = totalBytes;
//            mPercentProgressbar.setMax((int) (mContentLength / 1024));
        }
    }
    long progress = 0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void update(long contentLength, boolean done) {
        LogUtils.d("contentLength=" + contentLength + ",done=" + done);
        // 注意加上断点的长度
        mContentLength = contentLength + mBreakPoints;
        LogUtils.d("mContentLength="+mContentLength);
        if (mTotalBytes > 100){
            progress = mContentLength / (mTotalBytes/100);
        }else {
            progress = mContentLength;
        }
        LogUtils.d("progress="+progress);
        LogUtils.d("mTotalBytes=" + mTotalBytes);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvRe.setText(progress + "%");
                mHorizontalProgressBar.setProgressWithAnimation(progress);
//                mHorizontalProgressBar.setCurrentProgress(progress);
            }
        });

        if (done) {
            // 切换到主线程
            Observable
                    .empty()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            ToastUtils.showToast("下载完成");
                            LogUtils.d("下载完成");
                        }
                    })
                    .subscribe();
        }
    }
}
