package com.jobo.myrxmvpbaseproject.demoUpDownload;

import com.google.gson.JsonElement;
import com.jobo.httplib.base.BaseBiz;
import com.jobo.httplib.http.helper.ParseHelper;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.demoUpDownload.utils.ProgressListener;
import com.jobo.myrxmvpbaseproject.demoUpDownload.utils.UploadFileRequestBody;
import com.trello.rxlifecycle2.LifecycleProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;

/**
 * Created by JoBo on 2018/6/6.
 */

public class UpDownloadBiz extends BaseBiz {
        private final String API_UP = "appProject/upLoad";
//    private final String API_UP = "dservlet/upLoad";

    public void upFile(File file, LifecycleProvider lifecycleProvider, HttpRxCallback callback) {
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("file", file.getName(), requestBody);
        builder.addFormDataPart("file", file.getName(), requestBody);
        builder.addFormDataPart("file", file.getName(), requestBody);
        builder.addFormDataPart("name","name");
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();


        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                return new Object[0];
            }
        });

        getRequest().requestMultipartBody(API_UP, multipartBody, lifecycleProvider, callback);
    }

    //上传进度监听
    public void upFile(File file, ProgressListener progressListener, LifecycleProvider lifecycleProvider, HttpRxCallback callback) {

        RequestBody requestBody =
                RequestBody.create(MediaType.parse("image/*"), file);
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(requestBody,progressListener);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("file", file.getName(), uploadFileRequestBody);
        builder.addFormDataPart("file", file.getName(), uploadFileRequestBody);
        builder.addFormDataPart("file", file.getName(), uploadFileRequestBody);
        builder.addFormDataPart("name","name");
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        try {
            LogUtils.d("aaaaaaaaaaa,"+multipartBody.contentLength());//获取多个RequestBody大小
        } catch (IOException e) {
            e.printStackTrace();
        }

        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                return new Object[0];
            }
        });

        getRequest().requestMultipartBody(API_UP, multipartBody, lifecycleProvider, callback);
    }
}
