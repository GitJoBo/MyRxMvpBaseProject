package com.jobo.myrxmvpbaseproject.demoRecycleview.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.Unbinder;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.RecycleAdapter01;
import com.jobo.myrxmvpbaseproject.entity.RecyclerBean;
import com.jobo.myrxmvpbaseproject.utils.DynamicTimeFormat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by JoBo on 2018/6/4.
 */

public class RecycleFragment01 extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private ClassicsHeader mClassicsHeader;
    private List<RecyclerBean> mBeanList = new ArrayList<>();
    private RecycleAdapter01 mRecycleAdapter01;

    @Override
    protected void init() {
        int date = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis() - date));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setTimeFormat(new DynamicTimeFormat("更新于%s"));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        for (int i = 0; i < 10; i++) {
            mBeanList.add(new RecyclerBean(i + "", "第" + i + "个"));
        }
        mRecyclerView.setAdapter(mRecycleAdapter01 = new RecycleAdapter01(mBeanList, R.layout.item));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int count = mBeanList.size();
                if (count>=40){
                    mRefreshLayout.finishLoadMore(2000,true,true);
                    return;
                }
                mRefreshLayout.finishLoadMore(2000);
                List<RecyclerBean> list = new ArrayList<>();
                for (int i = count; i < count + 10; i++) {
                    list.add(new RecyclerBean(i + "", "LoadMore第" + i + "个"));
                }
                mRecycleAdapter01.addAllAotify(list);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishRefresh(2000);
                mRefreshLayout.setNoMoreData(false);
                List<RecyclerBean> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new RecyclerBean(i + "", "Refresh第" + i + "个"));
                }
                mRecycleAdapter01.refresh(list);
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_recycle;
    }

    public static RecycleFragment01 newInstance(String string) {

        Bundle args = new Bundle();

        RecycleFragment01 fragment = new RecycleFragment01();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("01","onAttach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("01","onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("01","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("01","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("01","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("01","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("01","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("01","onDetach");
    }
}
