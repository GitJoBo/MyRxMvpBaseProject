package com.jobo.myrxmvpbaseproject.demoBanner.activity;

import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoBanner.Loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.AccordionTransformer;
import com.youth.banner.transformer.CubeOutTransformer;
import com.youth.banner.transformer.ZoomInTransformer;

import java.util.Arrays;

/**
 * baaner 自定义属性
 * Created by JoBo on 2018/7/4.
 */

public class BannerCustomActivity extends BaseActivity {
    @BindView(R.id.banner1)
    Banner mBanner1;
    @BindView(R.id.banner2)
    Banner mBanner2;
    @BindView(R.id.banner3)
    Banner mBanner3;

    @Override
    protected int getContentViewId() {
        return R.layout.activity__banner_custom;
    }

    @Override
    protected void init() {
        String[] images = getResources().getStringArray(R.array.url);
        String[] titles = getResources().getStringArray(R.array.title);
        mBanner1.setImages(Arrays.asList(images))
                .setImageLoader(new GlideImageLoader())
                .setBannerAnimation(AccordionTransformer.class)
                .start();
        mBanner2.setImages(Arrays.asList(images))
                .setImageLoader(new GlideImageLoader())
                .setBannerTitles(Arrays.asList(titles))
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setBannerAnimation(CubeOutTransformer.class)
                .start();
        mBanner3.setImages(Arrays.asList(images))
                .setImageLoader(new GlideImageLoader())
                .setBannerAnimation(ZoomInTransformer.class)
                .start();
    }

    @Override
    protected void initBundleData() {

    }

}
