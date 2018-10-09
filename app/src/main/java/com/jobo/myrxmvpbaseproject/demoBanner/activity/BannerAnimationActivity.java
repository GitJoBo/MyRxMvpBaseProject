package com.jobo.myrxmvpbaseproject.demoBanner.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.BannerListViewAdapter;
import com.jobo.myrxmvpbaseproject.demoBanner.Loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * banner动画演示
 * Created by JoBo on 2018/7/4.
 */

public class BannerAnimationActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnBannerListener {

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.listview)
    ListView mListview;
    private List<Class<? extends ViewPager.PageTransformer>> mPageTransformer = new ArrayList<>();
    public void initDate(){
        mPageTransformer.add(DefaultTransformer.class);
        mPageTransformer.add(AccordionTransformer.class);
        mPageTransformer.add(BackgroundToForegroundTransformer.class);
        mPageTransformer.add(ForegroundToBackgroundTransformer.class);
        mPageTransformer.add(CubeInTransformer.class);//兼容问题，慎用
        mPageTransformer.add(CubeOutTransformer.class);
        mPageTransformer.add(DepthPageTransformer.class);
        mPageTransformer.add(FlipHorizontalTransformer.class);
        mPageTransformer.add(FlipVerticalTransformer.class);
        mPageTransformer.add(RotateDownTransformer.class);
        mPageTransformer.add(RotateUpTransformer.class);
        mPageTransformer.add(ScaleInOutTransformer.class);
        mPageTransformer.add(StackTransformer.class);
        mPageTransformer.add(TabletTransformer.class);
        mPageTransformer.add(ZoomInTransformer.class);
        mPageTransformer.add(ZoomOutTranformer.class);
        mPageTransformer.add(ZoomOutSlideTransformer.class);
        mPageTransformer.add(GallyPageTransformer.class);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_banner_animation;
    }

    @Override
    protected void init() {
        initDate();
        String[] anim = getResources().getStringArray(R.array.anim);
        String[] images = getResources().getStringArray(R.array.url);
        mListview.setAdapter(new BannerListViewAdapter(anim,this));
        mListview.setOnItemClickListener(this);
        mBanner.setImages( Arrays.asList(images))
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();

    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mBanner.setBannerAnimation(mPageTransformer.get(position));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast("点击了"+position);
    }
}
