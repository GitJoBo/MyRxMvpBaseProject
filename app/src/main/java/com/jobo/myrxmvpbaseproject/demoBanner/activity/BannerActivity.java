package com.jobo.myrxmvpbaseproject.demoBanner.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.BannerListViewAdapter;
import com.jobo.myrxmvpbaseproject.application.MyApplication;
import com.jobo.myrxmvpbaseproject.demoBanner.Loader.GlideImageLoader;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by JoBo on 2018/7/3.
 */

public class BannerActivity extends BaseActivity implements OnBannerListener, AdapterView.OnItemClickListener {
    @BindView(R.id.listview)
    ListView mListView;
    private Banner mBanner;
    private List<?> images;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_banner;
    }

    @Override
    protected void init() {
        View heardBanner = LayoutInflater.from(this).inflate(R.layout.heard_banner, null);
        mBanner = heardBanner.findViewById(R.id.banner);
        mBanner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.H / 4));
        mListView.addHeaderView(mBanner);
        String[] demoList = getResources().getStringArray(R.array.demo_list);
        mListView.setAdapter(new BannerListViewAdapter(demoList, this));
        mListView.setOnItemClickListener(this);
        String[] urls = getResources().getStringArray(R.array.url);
        images = Arrays.asList(urls);
        mBanner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void OnBannerClick(int position) {
        LogUtils.d("aaaaaa点击了"+position);
        ToastUtils.showToast("你点击了" + position);
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.d("点击了" + position);
        switch (position) {
            case 1:
                UIUtils.startActivity(this, BannerAnimationActivity.class);
                break;
            case 2:
                UIUtils.startActivity(this, BannerStyleActivity.class);
                break;
            case 3:
                UIUtils.startActivity(this,BannerCustomActivity.class);
                break;
            case 4:
                UIUtils.startActivity(this,BannerLocalActivity.class);
                break;
        }
    }
}
