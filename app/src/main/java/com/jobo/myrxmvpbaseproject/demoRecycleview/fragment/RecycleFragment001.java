package com.jobo.myrxmvpbaseproject.demoRecycleview.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.utils.HandlerUtil;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.base.BaseListFragment;
import com.jobo.myrxmvpbaseproject.demoRecycleview.IRecycleVew;
import com.jobo.myrxmvpbaseproject.demoRecycleview.ProjectLibListAdapter;
import com.jobo.myrxmvpbaseproject.demoRecycleview.activity.RecycleActivity;
import com.jobo.myrxmvpbaseproject.demoRecycleview.presenter.RecyclePresenter;
import com.jobo.myrxmvpbaseproject.utils.MySpUtil;
import com.jobo.recycle.base.BaseSuperAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JoBo on 2018/7/5.
 */

public class RecycleFragment001 extends BaseListFragment<RecyclePresenter> implements IRecycleVew,
        Runnable {
    @Override
    public void onLoad(int page) {
        if (mPresenter != null) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("projectState", 2);//0:协调调度  1:产业类 2:政府类,
            map1.put("pageNum", page);
            map1.put("pageSize", 10);
            map1.put("userOrgId", "7d3bc8ac3d5d11e8a5a7000c2982d30f");
            map1.put("userAcconut", "admin");
            map1.put("state", 4);//tab
            mPresenter.gylxmDataGridsObject(map1, page);
        }
    }

    @Override
    public void showEmpty() {
        LogUtils.d("aaaaaa,showEmpty()");
        setEmptyLayout(null, R.string.is_null, 0, 0, R.mipmap.default_ptr_wodfan_frame1);
    }

    @Override
    protected RecyclePresenter createPresenter() {
        return new RecyclePresenter(this, (RecycleActivity) this.getActivity());
    }

    @Override
    public BaseSuperAdapter getAdapter() {
        return new ProjectLibListAdapter(R.layout.item_not_in_service_layout, getActivity(), 2,
                4);
    }

    @Override
    protected void userVisible() {
        Log.d("001","userVisible");
        int time = isFristVisible ? MySpUtil.TIME : MySpUtil.TIME_LONG;
        HandlerUtil.postDelayed(this, time);
        super.userVisible();
    }

    @Override
    protected void userInvisible() {
        Log.d("001","userInvisible");
        HandlerUtil.remove(this);
        super.userInvisible();
    }

    @Override
    protected void initData() {

    }

    public static RecycleFragment001 newInstance() {
        Bundle args = new Bundle();
        RecycleFragment001 fragment = new RecycleFragment001();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void run() {
        initLoadData();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("001","onAttach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("001","onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("001","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("001","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("001","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("001","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("001","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("001","onDetach");
    }

    @Override
    public void userFristVisible() {
        super.userFristVisible();
        Log.d("001","userFristVisible");
    }



}
