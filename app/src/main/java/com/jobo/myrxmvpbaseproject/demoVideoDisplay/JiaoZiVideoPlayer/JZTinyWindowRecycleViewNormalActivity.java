package com.jobo.myrxmvpbaseproject.demoVideoDisplay.JiaoZiVideoPlayer;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.JZTinyWindowAdapter;

/**
 * Created by JoBo on 2018/7/9.
 */

public class JZTinyWindowRecycleViewNormalActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private JZTinyWindowAdapter mJZTinyWindowAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jiaozi_recycleview_normal_auto_tiny;
    }

    @Override
    protected void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mJZTinyWindowAdapter = new JZTinyWindowAdapter(R.layout.item_videoview,
                VideoConstant.videoUrls[0],
                VideoConstant.videoTitles[0],
                VideoConstant.videoThumbs[0]));
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                //滑动出屏幕时,小窗口播放
                JZVideoPlayer.onChildViewAttachedToWindow(view, R.id.videoplayer);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer.onChildViewDetachedFromWindow(view);
            }
        });
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void onDestroy() {
        JZVideoPlayer.setJzUserAction(null);
        //结束当前的播放状态
        JZVideoPlayerManager.completeAll();
        super.onDestroy();
    }
}
