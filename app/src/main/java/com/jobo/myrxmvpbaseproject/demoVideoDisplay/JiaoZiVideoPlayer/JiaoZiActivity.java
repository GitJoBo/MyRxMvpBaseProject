package com.jobo.myrxmvpbaseproject.demoVideoDisplay.JiaoZiVideoPlayer;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.*;
import com.bumptech.glide.Glide;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoVideoDisplay.JiaoZiVideoPlayer.CustomView.MyJZVideoPlayerStandard;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;

/**
 * Created by JoBo on 2018/7/9.
 */

public class JiaoZiActivity extends BaseActivity {
    @BindView(R.id.jz_video)
    MyJZVideoPlayerStandard mJzVideo;
    @BindView(R.id.btn_tiny_window)
    Button mTinyWindow;
    @BindView(R.id.btn_webview)
    Button mWebview;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jiaozi;
    }

    @Override
    protected void init() {
        mJzVideo.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd" +
                "-5287d2089db37e62345123a1be272f8b.mp4", JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "标题标题");
        Glide.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(mJzVideo.thumbImageView);
        JZVideoPlayer.setJzUserAction(new MyUserActionStandard());
    }

    @Override
    protected void initBundleData() {

    }

    @OnClick({R.id.btn_tiny_window, R.id.btn_webview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tiny_window:
                UIUtils.startActivity(this, JiaoZiTinyWindow.class);
                break;
            case R.id.btn_webview:
                UIUtils.startActivity(this, JiaoZiWebActivity.class);
                break;
        }
    }


    /**
     * 这只是给埋点统计用户数据用的，不能写和播放相关的逻辑，监听事件请参考MyJZVideoPlayerStandard，复写函数取得相应事件
     */
    class MyUserActionStandard implements JZUserActionStandard {

        @Override
        public void onEvent(int type, Object url, int screen, Object... objects) {
            switch (type) {
                case JZUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) +
                            " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0])
                            + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0])
                            + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0])
                            + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? ""
                            : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JZUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" :
                            objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) +
                            " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        JZVideoPlayer.setJzUserAction(null);
        //结束当前的播放状态
        JZVideoPlayerManager.completeAll();
        super.onDestroy();
    }
}
