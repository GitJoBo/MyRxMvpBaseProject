package com.jobo.myrxmvpbaseproject.demoBanner.activity;

import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoBanner.Loader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoBo on 2018/7/4.
 */

public class BannerLocalActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner mBanner;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_banner_local;
    }

    @Override
    protected void init() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        mBanner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    protected void initBundleData() {

    }
}
