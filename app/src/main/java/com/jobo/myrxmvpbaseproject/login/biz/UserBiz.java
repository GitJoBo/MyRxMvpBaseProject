package com.jobo.myrxmvpbaseproject.login.biz;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jobo.httplib.base.BaseBiz;
import com.jobo.httplib.http.helper.ParseHelper;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.http.retrofit.HttpRequest;
import com.jobo.myrxmvpbaseproject.entity.UserBean;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.TreeMap;

/**
 * 可直接在LoginPresent实现
 * Created by JoBo on 2018/5/31.
 */

public class UserBiz extends BaseBiz {
    private final String API_LOGIN = "appLogin/mobileLogin";
    public void login(String name, String password, LifecycleProvider lifecycleProvider, HttpRxCallback callback){
        //构建请求参数
        TreeMap<String,Object> request = new TreeMap<>();
        request.put("acconut",name);
        request.put("passWord",password);
//        File file = new File("");
//        request.put("file",file);
        request.put(HttpRequest.API_URL, API_LOGIN);

        //解析数据
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                UserBean userBean = new Gson().fromJson(jsonElement, UserBean.class);
                Object[] objects = new Object[1];
                objects[0] = userBean;
                return objects;
            }
        });

        //发送请求
        getRequest().request(HttpRequest.Method.POST,request,lifecycleProvider,callback);
    }
}
