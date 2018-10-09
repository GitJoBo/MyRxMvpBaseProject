package com.jobo.myrxmvpbaseproject.adapter;

import android.support.annotation.NonNull;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import com.bumptech.glide.Glide;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.recycle.base.BaseSuperAdapter;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.List;

/**
 * Created by JoBo on 2018/7/9.
 */

public class JZTinyWindowAdapter extends BaseSuperAdapter<String> {
    String[] mVideoUrls;
    String[] mVideoTitles;
    String[] mVideothumbs;

    public JZTinyWindowAdapter(int layoutResId, String[] videoUrls, String[] videoTitles, String[] videoThumbs) {
        super(layoutResId);
        mVideoUrls = videoUrls;
        mVideoTitles = videoTitles;
        mVideothumbs = videoThumbs;
    }

    public JZTinyWindowAdapter(List<String> list, int layoutResId, String[] videoUrls, String[] videoTitles, String[]
            videothumbs) {
        super(list, layoutResId);
        mVideoUrls = videoUrls;
        mVideoTitles = videoTitles;
        mVideothumbs = videothumbs;
    }

    @Override
    public int getItemCount() {
        return mVideoUrls == null?0:mVideoUrls.length;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        JZVideoPlayerStandard jzVideoPlayer = holder.getView(R.id.videoplayer);
        jzVideoPlayer.setUp(mVideoUrls[position], JZVideoPlayer.SCREEN_WINDOW_LIST,
                mVideoTitles[position]);
        Glide.with(holder.getContext())
                .load(mVideothumbs[position])
                .into(jzVideoPlayer.thumbImageView);
        jzVideoPlayer.positionInList = position;
    }
}
