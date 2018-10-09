package com.jobo.myrxmvpbaseproject.demoFragment2FragmentData;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.TabLayoutActivityAdapter;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.activity.BaseByValActivity;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.fragment.*;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function.FunctionManager;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function.FunctionNoParamNoResault;
import com.jobo.myrxmvpbaseproject.demoFragment2FragmentData.function.FunctionWithParamAndResult;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment与fragment传值，欲打造万能接口
 * Created by JoBo on 2018/9/25.
 */

public class FragmentByValActivity extends BaseByValActivity {
    private static final String TAG = "FragmentByValActivity";
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    private int[] mTabImgs = new int[]{R.mipmap.home, R.mipmap.location, R.mipmap.like, R.mipmap.person};
    private TabLayoutActivityAdapter mTabLayoutActivityAdapter;
    private List<String> mTabList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fragment_byval;
    }

    @Override
    protected void init() {
        initTabList();
        mTabLayoutActivityAdapter = new TabLayoutActivityAdapter(getSupportFragmentManager(), mTabList, this,
                mFragments, mTabImgs);
        mViewPager.setAdapter(mTabLayoutActivityAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setCurrentItem(0);
        mTablayout.setupWithViewPager(mViewPager);
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
    public void setFunctionForFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        BaseByValFragment fragment = (BaseByValFragment) fm.findFragmentByTag(tag);
        FunctionManager functionManager = FunctionManager.getInstance();
        fragment.setFunctionManager(functionManager.addFunction(new FunctionNoParamNoResault(FragmentByValOne
                .INTERFACE) {
            @Override
            public void function() {
                ToastUtils.showToast(TAG + "哈哈,无参无返回值回调");
            }
        }).addFunction(new FunctionWithParamAndResult<String, String>(FragmentByValTwo.INTERFACE_PARAM_RESULT) {

            @Override
            public String function(String param) {
                LogUtils.d(TAG + ",param:" + param);
                ToastUtils.showToast(TAG + param);
                return "Result返回给fragment" + param;
            }
        }));

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
        mFragments.add(FragmentByValOne.newInstance());
        mFragments.add(FragmentByValTwo.newInstance());
        mFragments.add(FragmentByValThree.newInstance());
        mFragments.add(FragmentByValFour.newInstance());
    }

    private void resetTabIcon() {
        mTablayout.getTabAt(0).setIcon(R.mipmap.home);
        mTablayout.getTabAt(1).setIcon(R.mipmap.location);
        mTablayout.getTabAt(2).setIcon(R.mipmap.like);
        mTablayout.getTabAt(3).setIcon(R.mipmap.person);
    }
}
