package com.jobo.myrxmvpbaseproject.demoGlide.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.github.chrisbanes.photoview.PhotoView;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.ImagesAdapter;
import com.jobo.myrxmvpbaseproject.utils.ColorUtil;
import com.jobo.myrxmvpbaseproject.utils.StatusBarUtil;
import com.jobo.myrxmvpbaseproject.widget.view.FixedViewPager;
import com.jobo.myrxmvpbaseproject.widget.view.NineImageView.ImageAttr;
import com.sunfusheng.glideimageview.util.DisplayUtil;

import java.util.List;

/**
 * 图片放大显示
 * Created by JoBo on 2018/6/15.
 */

public class ImagesActivity extends BaseActivity implements ViewTreeObserver.OnPreDrawListener {
    public static final String IMAGE_ATTR = "image_attr";
    public static final String CUR_POSITION = "cur_position";
    public static final int ANIM_DURATION = 300; // ms
    @BindView(R.id.viewPager)
    FixedViewPager mViewPager;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.rootView)
    RelativeLayout mRootView;

    private ImagesAdapter mAdapter;
    private List<ImageAttr> imageAttrs;
    private boolean isAnimating;

    private int curPosition;
    private int screenWidth;
    private int screenHeight;
    private float scaleX;
    private float scaleY;
    private float translationX;
    private float translationY;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_images;
    }

    @Override
    protected void init() {
        StatusBarUtil.setTranslucent(this);
        screenWidth = DisplayUtil.getScreenWidth(this);
        screenHeight = DisplayUtil.getScreenHeight(this);

        mTvTip.setText(String.format(getString(R.string.image_index), (curPosition + 1), imageAttrs.size()));

        mAdapter = new ImagesAdapter(this, imageAttrs);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(curPosition);
        mViewPager.getViewTreeObserver().addOnPreDrawListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                mTvTip.setText(String.format(getString(R.string.image_index), (curPosition + 1), imageAttrs.size()));
            }
        });

    }

    @Override
    protected void initBundleData() {
        Intent intent = getIntent();
        imageAttrs = (List<ImageAttr>) intent.getSerializableExtra(IMAGE_ATTR);
        curPosition = intent.getIntExtra(CUR_POSITION, 0);
    }

    @Override
    public void onBackPressed() {
        finishWithAnim();
    }

    private void initImageAttr(PhotoView photoView, ImageAttr attr, boolean isFinish) {
        int originalWidth = attr.width;
        int originalHeight = attr.height;
        int originalCenterX = attr.left + originalWidth / 2;
        int originalCenterY = attr.top + originalHeight / 2;

        float widthRatio = screenWidth * 1.0f / attr.realWidth;
        float heightRatio = screenHeight * 1.0f / attr.realHeight;
        int finalHeight = (int) (attr.realHeight * widthRatio);
        int finalWidth = screenWidth; //imageAttrs.size() == 1 ? screenWidth : finalHeight;

        scaleX = originalWidth * 1.0f / finalWidth;
        scaleY = originalHeight * 1.0f / finalHeight;
        translationX = originalCenterX - screenWidth / 2;
        translationY = originalCenterY - screenHeight / 2;

        Log.d("--->", "(left, top): (" + attr.left + ", " + attr.top + ")");
        Log.d("--->", "originalWidth: " + originalWidth + " originalHeight: " + originalHeight);
        Log.d("--->", "finalWidth: " + finalWidth + " finalHeight: " + finalHeight);
        Log.d("--->", "scaleX: " + scaleX + " scaleY: " + scaleY);
        Log.d("--->", "translationX: " + translationX + " translationY: " + translationY);
        Log.d("--->", "" + attr.toString());
        Log.d("--->", "----------------------------------------------------------------");
    }

    @Override
    public boolean onPreDraw() {
        if (isAnimating) return true;
        mRootView.getViewTreeObserver().removeOnPreDrawListener(this);
        PhotoView photoView = mAdapter.getPhotoView(curPosition);
        ImageAttr attr = imageAttrs.get(curPosition);
        initImageAttr(photoView, attr, false);

        translateXAnim(photoView, translationX, 0);
        translateYAnim(photoView, translationY, 0);
        scaleXAnim(photoView, scaleX, 1);
        scaleYAnim(photoView, scaleY, 1);
        setBackgroundColor(0f, 1f, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        return true;
    }

    public void finishWithAnim() {
        if (isAnimating) return;
        PhotoView photoView = mAdapter.getPhotoView(curPosition);
        photoView.setScale(1f);
        ImageAttr attr = imageAttrs.get(curPosition);
        initImageAttr(photoView, attr, true);

        translateXAnim(photoView, 0, translationX);
        translateYAnim(photoView, 0, translationY);
        scaleXAnim(photoView, 1, scaleX);
        scaleYAnim(photoView, 1, scaleY);
        setBackgroundColor(1f, 0f, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void translateXAnim(PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(it -> photoView.setX((Float) it.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void translateYAnim(PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(it -> photoView.setY((Float) it.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleXAnim(PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(it -> photoView.setScaleX((Float) it.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleYAnim(PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(it -> photoView.setScaleY((Float) it.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void setBackgroundColor(float from, float to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(it -> mRootView.setBackgroundColor(ColorUtil.evaluate((Float) it.getAnimatedValue(),
                Color.TRANSPARENT, Color.BLACK)));
        anim.setDuration(ANIM_DURATION);
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.start();
    }

}
