package com.jobo.myrxmvpbaseproject.http;

import com.jobo.httplib.http.retrofit.HttpResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.*;

import java.io.File;
import java.util.Map;

/**
 * Created by JoBo on 2018/6/11.
 */

public interface ApiService {
    //用户登录
    @FormUrlEncoded
    @POST("appLogin/mobileLogin")
    Observable<HttpResponse> mobileLogin(
            @Field("acconut") String acconut,
            @Field("passWord") String passWord
    );

    //appProject/upLoad
    @Multipart
    @POST("appProject/upLoad")
    Observable<HttpResponse> file(
//            @Part("file") MultipartBody file
            //@Part annotation must supply a name or use MultipartBody.Part parameter type. (parameter #1)
            //不提供("file") 则用MultipartBody.Part
            @Part MultipartBody.Part file
    );

    //appProject/upLoad
    @Multipart
    @POST("appProject/upLoad")
    Observable<HttpResponse> file(
            @Part("file") MultipartBody file
            //@Part annotation must supply a name or use MultipartBody.Part parameter type. (parameter #1)
            //不提供("file") 则用MultipartBody.Part
//            @Part MultipartBody.Part file
    );

    //具体项目库列表Object
//    @FormUrlEncoded
    @POST("appProject/allGvoernmentPros")
    Observable<HttpResponse> gylxmDataGridsObject(
            @Body Map<String, Object> defaultParams
    );
}
