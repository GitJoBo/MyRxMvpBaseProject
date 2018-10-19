package com.jobo.myrxmvpbaseproject.demoMaterialDesign;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoBo on 2018/10/16.
 */

public class MaterialDesignActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.coordinator_context)
    CoordinatorLayout mCoordinatorContext;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.md_drawer_layout)
    DrawerLayout mMdDrawerLayout;

    private ArrayList<String> mTitles;
    private ArrayList<TypeFragment> mFragments;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_material_design;
    }

    @Override
    protected void init() {
        setUpDrawer();
        initNavigationView();
        mFragments = new ArrayList<>();
        mFragments.add(TypeFragment.newInstance("热点"));
        mFragments.add(TypeFragment.newInstance("军事"));
        mFragments.add(TypeFragment.newInstance("科技"));
        mFragments.add(TypeFragment.newInstance("娱乐"));
        mTitles = new ArrayList<>();
        mTitles.add("热点");
        mTitles.add("军事");
        mTitles.add("科技");
        mTitles.add("娱乐");
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tabPagerAdapter.setArguments(mFragments,mTitles);
        mViewPager.setAdapter(tabPagerAdapter);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);//MODE_SCROLLABLE
        //两个参数分别对应Tab未选中的文字颜色和选中的文字颜色
        mTablayout.setTabTextColors(Color.WHITE,Color.RED);
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //设置TabLayout的选择监听
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            //重复点击时回调
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                scrollToTop(mFragments.get(tab.getPosition()).mTypeList);
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorContext, "Snackbar哦！", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void initBundleData() {

    }

    private void setUpDrawer() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mMdDrawerLayout, mToolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        //设置左上角显示三道横线
        toggle.syncState();
        mToolbar.setTitle("MdView");
//        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMdDrawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
    }

    private void initNavigationView() {
        //初始化NavigationView顶部head的icon和name
        ImageView imageView = mNavigationView.getHeaderView(0).findViewById(R.id.nav_head_icon);
        imageView.setImageResource(R.mipmap.ic_launcher);
        TextView textView = mNavigationView.getHeaderView(0).findViewById(R.id.nav_head_name);
        textView.setText("VipOthershe");
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_item1:
                    ToastUtils.showToast("我的圈子");
                    break;
                case R.id.nav_item2:
                    ToastUtils.showToast("我的收藏");
                    break;
                case R.id.nav_set:
                    ToastUtils.showToast("设置");
                    break;
                case R.id.menu_share:
                    ToastUtils.showToast("分享");
                    break;
                case R.id.nav_about:
                    ToastUtils.showToast("关于");
                    break;
            }
            //隐藏NavigationView
            mMdDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_share:
                ToastUtils.showToast("分享");
                break;
            case R.id.menu_search:
                ToastUtils.showToast("搜索");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 滚动到列表顶部
     *
     * @param recyclerView
     */
    private void scrollToTop(RecyclerView recyclerView) {
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        int[] lastPositions = manager.findLastVisibleItemPositions(null);
        if (lastPositions[0] < 15) {
            recyclerView.smoothScrollToPosition(0);
        } else {
            recyclerView.scrollToPosition(0);
        }
    }

    public class TabPagerAdapter extends FragmentPagerAdapter{
        private List<TypeFragment> fragments;
        private List<String> titles;
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setArguments(List<TypeFragment> fragments, List<String> titles) {
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments!=null?fragments.size():0;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


}
