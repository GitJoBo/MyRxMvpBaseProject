package com.jobo.myrxmvpbaseproject.demoUpDownload;

import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.http.retrofit.HttpResponse;
import com.jobo.httplib.http.retrofit.RetrofitUtils;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.http.ApiService;
import com.jobo.myrxmvpbaseproject.demoUpDownload.activity.UpDownloadActivity;
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

public class UpDownloadPresenter extends BasePresenter<IUpDownloadView, UpDownloadActivity> {
    private final String API_UP = "appProject/upLoad";

    public UpDownloadPresenter(IUpDownloadView view, UpDownloadActivity activity) {
        super(view, activity);
    }

    private final String TAG = UpDownloadPresenter.class.getSimpleName();

    /**
     * @param file
     * @param progressListener
     * @param type             请求参数添加模式,1代码构建,2注解构建
     */
    public void upFile(File file, ProgressListener progressListener, int type) {
        if (getView() != null) {
//            getView().showProgressBar();
            HttpRxCallback httpRxCallback = new HttpRxCallback(TAG) {

                @Override
                public void onSuccess(HttpResponse httpResponse) {
                    getView().showResult(httpResponse.toString());
                }

                @Override
                public void onError(int code, String desc) {
//                    getActivity().closeLoading();
                    if (getView() != null)
                        getView().showResult("code:" + code + ",desc" + desc);
                }
            };
            //null 目的程序转后台,页面暂停等时不暂停请求
            upFile(file, progressListener, null, httpRxCallback, type);
        }

    }


    //单个文件上传 参数配置 进度监听
    public void upFile(File file, ProgressListener progressListener, LifecycleProvider lifecycleProvider,
                       HttpRxCallback callback, int type) {
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("image/*"), file);
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(requestBody, progressListener);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("file", file.getName(), uploadFileRequestBody);
//        builder.addFormDataPart("file", file.getName(), uploadFileRequestBody);
//        builder.addFormDataPart("file", file.getName(), uploadFileRequestBody);
        builder.addFormDataPart("name", "name");
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        try {
            LogUtils.d("aaaaaaaaaaa," + multipartBody.contentLength());//获取多个RequestBody大小
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (type == 1) {
            callback.setParseHelper(jsonElement -> new Object[0]);

            getRequest().requestMultipartBody(API_UP, multipartBody, lifecycleProvider, callback);
        } else {
            //后台会报500
//            add(RetrofitUtils.Builder().retrofit().create(ApiService.class).file(multipartBody), lifecycleProvider,
//                    callback);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), uploadFileRequestBody);
            add(RetrofitUtils.Builder().retrofit().create(ApiService.class).file(part), lifecycleProvider, callback);
        }
    }
}
