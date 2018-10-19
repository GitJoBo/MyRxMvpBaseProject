package com.jobo.myrxmvpbaseproject.demoMaterialDesign;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.design.widget.*;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;

/**
 * Created by JoBo on 2018/10/16.
 */

public class DetailActivity extends BaseActivity {
    @BindView(R.id.detail_img)
    ImageView mDetailImg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.detail_content)
    CoordinatorLayout mDetailContent;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_detail;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void init() {
        //使mToolbar取代原来的ActionBar
        setSupportActionBar(mToolbar);
        //设置mToolbar左上角显示返回图标
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        //设置返回图标的点击事件
        mToolbar.setNavigationOnClickListener(v->finish());
        //设置还没有收缩时状态下字体颜色
        mToolbarLayout.setExpandedTitleColor(Color.RED);
        //收缩后字体颜色
        mToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mDetailImg.setImageResource(R.mipmap.b1);
        mWebview.loadUrl("https://juejin.im/entry/5908235861ff4b0066dc924a#%E8%B7%91%E9%A9%AC%E7%81%AF");
        mWebview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
                mToolbarLayout.setTitle(title);
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mDetailContent, "Snackbar哦！", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initBundleData() {

    }
}
