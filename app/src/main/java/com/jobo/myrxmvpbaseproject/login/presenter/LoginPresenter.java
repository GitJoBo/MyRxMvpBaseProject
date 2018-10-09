package com.jobo.myrxmvpbaseproject.login.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.http.helper.ParseHelper;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.http.retrofit.HttpRequest;
import com.jobo.httplib.http.retrofit.HttpResponse;
import com.jobo.httplib.http.retrofit.RetrofitUtils;
import com.jobo.httplib.utils.GsonUtil;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.entity.UserBean;
import com.jobo.myrxmvpbaseproject.http.ApiService;
import com.jobo.myrxmvpbaseproject.login.activity.LoginActivity;
import com.jobo.myrxmvpbaseproject.login.iface.ILoginView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * Created by JoBo on 2018/6/11.
 */

public class LoginPresenter extends BasePresenter<ILoginView, LoginActivity> {
    private final String TAG = LoginPresenter.class.getSimpleName();

    public LoginPresenter(ILoginView view, LoginActivity activity) {
        super(view, activity);
    }

    /**
     * 注解构建请求参数
     * 优选,个人理解(优点:快,接口,参数清晰,响应参数解析灵活      缺点:被他人拿到ApiXXXX不安全)
     *
     * @param userName
     * @param password
     */
    public void login(String userName, String password) {
        getView().showLoading();
        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "login") {
            @Override
            public void onSuccess(HttpResponse httpResponse) {
                if (getView() != null) {
                    getView().closeLoading();
                    UserBean userBean = GsonUtil.getJson(httpResponse.getResult(), UserBean.class);
                    LogUtils.d("登陆成功,userBean=" + userBean.toString());
                }

            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
                    getView().closeLoading();
                    ToastUtils.showToast(desc);
                }
            }
        };
        add(RetrofitUtils.Builder().retrofit().create(ApiService.class).mobileLogin(userName, password), getActivity(),
                httpRxCallback);
    }

    /**
     * 方法构建请求参数
     *
     * @param userName
     * @param password
     */
    public void login2(String userName, String password) {
        if (getView() != null) {
            getView().showLoading();
            HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "login") {
                @Override
                public void onSuccess(HttpResponse httpResponse) {
                    getView().closeLoading();
                    UserBean userBean = GsonUtil.getJson(httpResponse.getResult(), UserBean.class);
                    getView().showResult(userBean);
                }

                @Override
                public void onError(int code, String desc) {
                    if (getView() != null) {
                        getView().closeLoading();
                        ToastUtils.showToast(desc);
                    }
                }
            };

//            new UserBiz().login(userName, password, getActivity(), httpRxCallback);//为了presenter复用,参数构建与请求写在单独类里,
// p层只做处理响应
            login(userName, password, getActivity(), httpRxCallback);//减少类数,与XXXBiz思想冲突
            /**
             * ******此处代码为了测试取消请求,不是规范代码*****
             */
//            try {
//                Thread.sleep(50);
//                //取消请求
//                if (!httpRxCallback.isDisposed()) {
//                    httpRxCallback.cancel();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     * 方法构建,将UserBiz的代码移到LoginPresenter,省去多余的类,Android的MVP模式在中小型项目中用VP
     */
    private final String API_LOGIN = "appLogin/mobileLogin";

    public void login(String name, String password, LifecycleProvider lifecycleProvider, HttpRxCallback callback) {
        //构建请求参数
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("acconut", name);
        request.put("passWord", password);
//        File file = new File("");
//        request.put("file",file);
        request.put(HttpRequest.API_URL, API_LOGIN);

        //解析数据
        callback.setParseHelper(jsonElement -> {
            UserBean userBean = new Gson().fromJson(jsonElement, UserBean.class);
            Object[] objects = new Object[1];
            objects[0] = userBean;
            return objects;
        });

        //发送请求
        getRequest().request(HttpRequest.Method.POST, request, lifecycleProvider, callback);
    }
}
