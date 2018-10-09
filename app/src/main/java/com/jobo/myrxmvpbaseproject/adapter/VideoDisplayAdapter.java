package com.jobo.myrxmvpbaseproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoVideoDisplay.JiaoZiVideoPlayer.JiaoZiActivity;
import com.jobo.myrxmvpbaseproject.demoVideoDisplay.JieCaoVideoPlayer.JieCaoActivity;
import com.jobo.myrxmvpbaseproject.demoVideoDisplay.MediaPlayerActivity;
import com.jobo.myrxmvpbaseproject.demoVideoDisplay.VideoDisplayActivity;
import com.jobo.myrxmvpbaseproject.demoVideoDisplay.VideoViewActivity;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;
import com.jobo.recycle.base.BaseSuperAdapter;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.List;

/**
 * Created by JoBo on 2018/7/6.
 */

public class VideoDisplayAdapter extends BaseSuperAdapter<String> {
    private static final String TAG = "VideoDisplayAdapter";
    private Context mContext;
    public VideoDisplayAdapter(Context context, List<String> list, int layoutResId) {
        super(list, layoutResId);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setText(R.id.text,mList.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d(TAG+v+"postion="+position);
                switch (position) {
                    case 0:
                        UIUtils.startActivity(mContext,MediaPlayerActivity.class);
                        break;
                    case 1:
                        UIUtils.startActivity(mContext, VideoViewActivity.class);
                        break;
                    case 2:
                        UIUtils.startActivity(mContext, JieCaoActivity.class);
                        break;
                    case 3:
                        UIUtils.startActivity(mContext, JiaoZiActivity.class);
                        break;
                }
            }
        });
    }
}
