package com.jobo.myrxmvpbaseproject.demoBanner.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoBanner.Loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.Arrays;

/**
 * Created by JoBo on 2018/7/4.
 */

public class BannerStyleActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.spinnerStyle)
    Spinner mSpinnerStyle;
    @BindView(R.id.spinnerPosition)
    Spinner mSpinnerPosition;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_banner_style;
    }

    @Override
    protected void init() {
        mSpinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mBanner.updateBannerStyle(BannerConfig.NOT_INDICATOR);
                        break;
                    case 1:
                        mBanner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        break;
                    case 2:
                        mBanner.updateBannerStyle(BannerConfig.NUM_INDICATOR);
                        break;
                    case 3:
                        mBanner.updateBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
                        break;
                    case 4:
                        mBanner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                        break;
                    case 5:
                        mBanner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mBanner.setIndicatorGravity(BannerConfig.LEFT);
                        break;
                    case 1:
                        mBanner.setIndicatorGravity(BannerConfig.CENTER);
                        break;
                    case 2:
                        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
                        break;
                }
                mBanner.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] images = getResources().getStringArray(R.array.url);
        String[] titles = getResources().getStringArray(R.array.title);
        //默认是CIRCLE_INDICATOR
        mBanner.setImages(Arrays.asList(images))
                .setBannerTitles(Arrays.asList(titles))
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    protected void initBundleData() {

    }

}
