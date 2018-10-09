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
import com.jobo.httplib.base.BaseFragment;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.RecycleAdapter02;
import com.jobo.myrxmvpbaseproject.entity.RecyclerBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.*;

/**
 * 官方主题刷新,多条目recycleview
 * Created by JoBo on 2018/6/4.
 */

public class RecycleFragment02 extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List<RecyclerBean> mBeanList = new ArrayList<>();
    private RecycleAdapter02 mRecycleAdapter02;

    @Override
    protected void init() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        for (int i = 0; i < 10; i++) {
            mBeanList.add(new RecyclerBean(i + "", "第" + i + "个"));
        }
        mRecyclerView.setAdapter(mRecycleAdapter02 = new RecycleAdapter02(mBeanList));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int count = mBeanList.size();
                mRefreshLayout.finishLoadMore(2000);
                List<RecyclerBean> list = new ArrayList<>();
                for (int i = count; i < count + 10; i++) {
                    list.add(new RecyclerBean(i + "", "LoadMore第" + i + "个"));
                }
                mRecycleAdapter02.addAllAotify(list);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishRefresh(2000);
                List<RecyclerBean> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new RecyclerBean(i + "", "Refresh第" + i + "个"));
                }
                mRecycleAdapter02.refresh(list);
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_recycle02;
    }

    public static RecycleFragment02 newInstance(String string) {

        Bundle args = new Bundle();

        RecycleFragment02 fragment = new RecycleFragment02();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("02","onAttach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("02","onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("02","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("02","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("02","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("02","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("02","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("02","onDetach");
    }

    @Override
    public void userFristVisible() {
        super.userFristVisible();
        Log.d("02","userFristVisible");
    }

    @Override
    protected void userVisible() {
        super.userVisible();
        Log.d("02","userVisible");
    }

    @Override
    protected void userInvisible() {
        super.userInvisible();
        Log.d("02","userInvisible");
    }
}
