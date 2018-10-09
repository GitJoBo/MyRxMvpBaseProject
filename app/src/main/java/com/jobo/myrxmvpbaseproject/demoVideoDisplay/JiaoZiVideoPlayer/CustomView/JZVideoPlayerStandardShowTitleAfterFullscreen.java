package com.jobo.myrxmvpbaseproject.demoVideoDisplay.JiaoZiVideoPlayer.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by JoBo on 2018/7/9.
 */
public class JZVideoPlayerStandardShowTitleAfterFullscreen extends JZVideoPlayerStandard {
    public JZVideoPlayerStandardShowTitleAfterFullscreen(Context context) {
        super(context);
    }

    public JZVideoPlayerStandardShowTitleAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            titleTextView.setVisibility(View.VISIBLE);
        } else {
            titleTextView.setVisibility(View.INVISIBLE);
        }
    }
}
