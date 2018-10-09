package com.jobo.myrxmvpbaseproject.demoVideoDisplay.JiaoZiVideoPlayer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import butterknife.BindView;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import com.bumptech.glide.Glide;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;

/**
 * web与java交互
 * Created by JoBo on 2018/7/9.
 */

public class JiaoZiWebActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView mWebView;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_webview;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setBackgroundDrawable(null);
//        super.onCreate(savedInstanceState);
//    }

    @Override
    protected void init() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JZCallBack(),"jzvd");//注意参数二与js保持一致
        mWebView.loadUrl("file:///android_asset/jzvd.html");
    }

    @Override
    protected void initBundleData() {

    }

    public class JZCallBack{
        @JavascriptInterface
        public void adViewJiaoZiVideoPlayer(final int width, final int height, final int top, final int left, final int index) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (index == 0) {
                        JZVideoPlayerStandard webVieo = new JZVideoPlayerStandard(JiaoZiWebActivity.this);
                        webVieo.setUp(VideoConstant.videoUrlList[1],
                                JZVideoPlayer.SCREEN_WINDOW_LIST, "饺子骑大马");
                        Glide.with(JiaoZiWebActivity.this)
                                .load(VideoConstant.videoThumbList[1])
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JZUtils.dip2px(JiaoZiWebActivity.this, top);
                        layoutParams.x = JZUtils.dip2px(JiaoZiWebActivity.this, left);
                        layoutParams.height = JZUtils.dip2px(JiaoZiWebActivity.this, height);
                        layoutParams.width = JZUtils.dip2px(JiaoZiWebActivity.this, width);
                        mWebView.addView(webVieo, layoutParams);
                    } else {
                        JZVideoPlayerStandard webVieo = new JZVideoPlayerStandard(JiaoZiWebActivity.this);
                        webVieo.setUp(VideoConstant.videoUrlList[2],
                                JZVideoPlayer.SCREEN_WINDOW_LIST, "饺子失态了");
                        Glide.with(JiaoZiWebActivity.this)
                                .load(VideoConstant.videoThumbList[2])
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JZUtils.dip2px(JiaoZiWebActivity.this, top);
                        layoutParams.x = JZUtils.dip2px(JiaoZiWebActivity.this, left);
                        layoutParams.height = JZUtils.dip2px(JiaoZiWebActivity.this, height);
                        layoutParams.width = JZUtils.dip2px(JiaoZiWebActivity.this, width);
                        mWebView.addView(webVieo, layoutParams);
                    }

                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
