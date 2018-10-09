package com.jobo.myrxmvpbaseproject.demoGlide.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.TabLayoutActivityAdapter;
import com.jobo.myrxmvpbaseproject.demoGlide.fragment.GlideFragment01;
import com.jobo.myrxmvpbaseproject.demoGlide.fragment.GlideFragment02;
import com.jobo.myrxmvpbaseproject.demoRecycleview.fragment.RecycleFragment04;
import com.jobo.myrxmvpbaseproject.demoRecycleview.fragment.TreeRecycleFragment03;
import com.jobo.myrxmvpbaseproject.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoBo on 2018/6/14.
 */

public class GlideActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    private int[] mTabImgs = new int[]{R.mipmap.home, R.mipmap.location, R.mipmap.like, R.mipmap.person};
    private List<String> mTabList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabLayoutActivityAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_glide;
    }

    @Override
    protected void init() {
//        StatusBarUtil.setStatusBarTranslucent(this, false);
//        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setTransparent(this);
        initTabList();
        mAdapter = new TabLayoutActivityAdapter(getSupportFragmentManager(), mTabList, this,
                mFragmentList, mTabImgs);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager,true);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetTabIcon();
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        tab.setIcon(R.mipmap.home_fill);
                        break;
                    case 1:
                        tab.setIcon(R.mipmap.location_fill);
                        break;
                    case 2:
                        tab.setIcon(R.mipmap.like_fill);
                        break;
                    case 3:
                        tab.setIcon(R.mipmap.person_fill);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.getTabAt(0).setIcon(R.mipmap.home_fill);
        mTabLayout.getTabAt(1).setIcon(R.mipmap.location);
        mTabLayout.getTabAt(2).setIcon(R.mipmap.like);
        mTabLayout.getTabAt(3).setIcon(R.mipmap.person);

    }

    private void initTabList() {
        mTabList.clear();
        mTabList.add("Home");
        mTabList.add("Location");
        mTabList.add("Like");
        mTabList.add("Me");
    }

    private void resetTabIcon() {
        mTabLayout.getTabAt(0).setIcon(R.mipmap.home);
        mTabLayout.getTabAt(1).setIcon(R.mipmap.location);
        mTabLayout.getTabAt(2).setIcon(R.mipmap.like);
        mTabLayout.getTabAt(3).setIcon(R.mipmap.person);
    }

    /**
     * add Fragment
     */
    public void initFragmentList() {
        mFragmentList.clear();
        mFragmentList.add(GlideFragment01.newInstance());
        mFragmentList.add(GlideFragment02.newInstance());
        mFragmentList.add(TreeRecycleFragment03.newInstance(getString(R.string.item_like)));
        mFragmentList.add(RecycleFragment04.newInstance(getString(R.string.item_person)));
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        initFragmentList();
    }
}
