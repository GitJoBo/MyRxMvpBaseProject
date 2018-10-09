package com.jobo.myrxmvpbaseproject.demoRecycleview.presenter;

import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.http.observer.HttpRxCallback;
import com.jobo.httplib.http.retrofit.HttpResponse;
import com.jobo.httplib.http.retrofit.RetrofitUtils;
import com.jobo.httplib.utils.GsonUtil;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.demoRecycleview.IRecycleVew;
import com.jobo.myrxmvpbaseproject.demoRecycleview.Model.ProjectLibListModel2;
import com.jobo.myrxmvpbaseproject.demoRecycleview.activity.RecycleActivity;
import com.jobo.myrxmvpbaseproject.http.ApiService;
import com.jobo.myrxmvpbaseproject.login.presenter.LoginPresenter;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JoBo on 2018/7/5.
 */

public class RecyclePresenter extends BasePresenter<IRecycleVew, RecycleActivity> {
    private final String TAG = LoginPresenter.class.getSimpleName();

    public RecyclePresenter(IRecycleVew view, RecycleActivity activity) {
        super(view, activity);
    }

    public void gylxmDataGridsObject(Map<String, Object> map,int page) {
//        getView().showLoading();
        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "login") {
            @Override
            public void onSuccess(HttpResponse httpResponse) {
                if (getView() != null) {
//                    getView().closeLoading();
                }
                ProjectLibListModel2 model = GsonUtil.getJson(httpResponse.getResult(), ProjectLibListModel2.class);
                int isEnd = 0;
                if (model.getTotal() == 0){
                    isEnd = 1;
                }else {
                    if (model.getTotal()%10==0){
                        if (page == model.getTotal()/10){
                            isEnd = 1;
                        }
                    }else {
                        if (page - 1 == model.getTotal()/10){
                            isEnd = 1;
                        }
                    }
                }
                UIUtils.setListLoad(mView, page, model.getList(),isEnd);
//                List<ProjectLibListModel2.ListBean> listBeans = new ArrayList<>();
//                UIUtils.setListLoad(mView, page, listBeans,isEnd);
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
//                    getView().closeLoading();
                    ToastUtils.showToast(desc);
                    UIUtils.setListLoad(mView, page, null,0);
//                    UIUtils.setListLoad(mView);
//                    getView().showEmpty();
                }
            }
        };
        add(RetrofitUtils.Builder().retrofit().create(ApiService.class).gylxmDataGridsObject(map),
                getActivity(), httpRxCallback);
    }


}
