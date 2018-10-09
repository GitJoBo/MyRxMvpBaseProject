package com.jobo.myrxmvpbaseproject.demoVideoDisplay;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.VideoDisplayAdapter;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoBo on 2018/7/6.
 */

public class VideoDisplayActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private VideoDisplayAdapter mVideoDisplayAdapter;
    private List<String> mList;

    @Override
    protected int getContentViewId() {
        return R.layout.include_recycle;
    }

    @Override
    protected void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mList.add("MediaPlayer+SurfaceView");
        mList.add("VideoView");
        mList.add("JieCaoVideoPlayer");//有bug,未解决
        mList.add("JiaoZiVideoPlayer");//JieCaoVideoPlayer升级,修复bug
        mList.add("Vitamio框架");
        mList.add("UniversalMusicPlayer");
        mRecyclerView.setAdapter(mVideoDisplayAdapter = new VideoDisplayAdapter(this, mList, R.layout.item_text));

    }

    @Override
    protected void initBundleData() {

    }
}
