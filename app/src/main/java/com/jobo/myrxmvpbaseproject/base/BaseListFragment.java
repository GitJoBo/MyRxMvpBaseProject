package com.jobo.myrxmvpbaseproject.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.OnLongClick;
import com.jobo.httplib.base.BasePresenter;
import com.jobo.httplib.base.IBaseListView;
import com.jobo.httplib.listener.OnLoadListViewListener;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.recycle.base.BaseSuperAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Created by JoBo on 2018/7/5.
 */

public abstract class BaseListFragment<P extends BasePresenter> extends BaseLoadErrorFragment<P> implements
        IBaseListView, OnRefreshListener, OnLoadMoreListener,OnLoadListViewListener {
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected RecyclerView mRecyclerView;
    private BaseSuperAdapter mAdapter;
//    //翻页
//    private int mFlipCode = 1;//当前页码
//    //刷新加载状态
//    private int mPagingState = 1;//1刷新,2加载更多
//    //总页数
    private int mPages = 1;
    @Override
    protected void init() {
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getlayoutManager());
        mAdapter = getAdapter();
        if (mAdapter!=null){
            mRecyclerView.setAdapter(mAdapter);
        }
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPages = 1;
        onLoad(mPages);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPages++;
        onLoad(mPages);
    }

    @Override
    public void refresh(List data, boolean hasmore) {
        hideErrorLayout();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.setNoMoreData(false);//恢复可加载更多数据
        mAdapter.refresh(data);
    }

    @Override
    public void loadMore(List data, boolean hasmore) {
        mSmartRefreshLayout.finishLoadMore();
        if (!hasmore){
            mSmartRefreshLayout.finishLoadMore(600,true,true);
        }
        mAdapter.addAllAotify(data);
    }

    @Override
    public void clearLoad() {
        mSmartRefreshLayout.finishRefresh(false);
        mSmartRefreshLayout.finishLoadMore(false);
    }

    public void initLoadData() {
        if (mSmartRefreshLayout != null) {
            boolean b = mSmartRefreshLayout.autoRefresh();
            LogUtils.d("aaaaaaaaaa,b="+b);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_load_list;
    }

    public abstract BaseSuperAdapter getAdapter();

    protected abstract void initData();

    protected RecyclerView.LayoutManager getlayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
