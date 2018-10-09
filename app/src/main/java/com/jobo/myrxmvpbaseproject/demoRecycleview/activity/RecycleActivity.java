package com.jobo.myrxmvpbaseproject.demoRecycleview.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.TabLayoutActivityAdapter;
import com.jobo.myrxmvpbaseproject.demoRecycleview.fragment.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by JoBo on 2018/5/31.
 */

public class RecycleActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    private int[] mTabImgs = new int[]{R.mipmap.home, R.mipmap.location, R.mipmap.like, R.mipmap.person};
    private TabLayoutActivityAdapter mTabLayoutActivityAdapter;
    private List<String> mTabList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_recycle;
    }

    @Override
    protected void init() {
        initTabList();
        mTabLayoutActivityAdapter = new TabLayoutActivityAdapter(getSupportFragmentManager(), mTabList, this,
                mFragments, mTabImgs);
        mViewpager.setAdapter(mTabLayoutActivityAdapter);
        mViewpager.setOffscreenPageLimit(1);//ViewPage缓存页面数
        mViewpager.setCurrentItem(0);
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        mTablayout.getTabAt(0).setIcon(R.mipmap.home_fill);
        mTablayout.getTabAt(1).setIcon(R.mipmap.location);
        mTablayout.getTabAt(2).setIcon(R.mipmap.like);
        mTablayout.getTabAt(3).setIcon(R.mipmap.person);

    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initFragmentList();
    }

    /**
     * init the tab list.
     */
    private void initTabList() {
        mTabList.clear();
        mTabList.add(getString(R.string.item_home));
        mTabList.add(getString(R.string.item_location));
        mTabList.add(getString(R.string.item_like));
        mTabList.add(getString(R.string.item_person));
    }

    /**
     * add Fragment
     */
    public void initFragmentList() {
        mFragments.clear();
//        mFragments.add(RecycleFragment01.newInstance(getString(R.string.item_home)));
        mFragments.add(RecycleFragment001.newInstance());
//        mFragments.add(TestErrorLayoutFragment.newInstance());
        mFragments.add(RecycleFragment02.newInstance(getString(R.string.item_location)));
        mFragments.add(TreeRecycleFragment03.newInstance(getString(R.string.item_like)));
        mFragments.add(RecycleFragment04.newInstance(getString(R.string.item_person)));
    }

    private void resetTabIcon() {
        mTablayout.getTabAt(0).setIcon(R.mipmap.home);
        mTablayout.getTabAt(1).setIcon(R.mipmap.location);
        mTablayout.getTabAt(2).setIcon(R.mipmap.like);
        mTablayout.getTabAt(3).setIcon(R.mipmap.person);
    }

}
