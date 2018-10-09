package com.jobo.myrxmvpbaseproject.widget.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.utils.DensityUtil;

/**
 * Created by JoBo on 2018/7/5.
 */

public class ErrorLayout extends LinearLayout {
    private ImageView mImageView;
    private TextView mTvErrorTitle, mTvDesc;
    private Button mButton;

    public ErrorLayout(Context context) {
        this(context, null);
    }

    public ErrorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        mImageView = new ImageView(context);
//        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewGroup.LayoutParams wl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        addView(mImageView, wl);
        mTvErrorTitle = new TextView(context);
        mTvErrorTitle.setTextSize(16);
        mTvErrorTitle.setTextColor(Color.parseColor("#3b3b3b"));
        addView(mTvErrorTitle, wl);
        setMarginTop(context, mTvErrorTitle);
        mTvDesc = new TextView(context);
        addView(mTvDesc, wl);
        setMarginTop(context, mTvDesc);
        mButton = new Button(context);
        mButton.setTextColor(Color.parseColor("#999999"));
        mButton.setBackgroundResource(R.drawable.sp_corner_white);
        mButton.setVisibility(GONE);
        addView(mButton, new ViewGroup.LayoutParams((int) DensityUtil.dip2px(context, 100), (int) DensityUtil.dip2px
                (context, 30)));
        setMarginTop(context, mButton);
    }

    private void setMarginTop(Context context, View view) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.topMargin = (int) DensityUtil.dip2px(context, 8);
    }

    public void setTvErrorTitle(int title) {
        if (mTvErrorTitle != null) {
            mTvErrorTitle.setVisibility(VISIBLE);
            mTvErrorTitle.setText(title);
        }
    }

    public void setTvDesc(int desc) {
        if (mTvDesc != null) {
            mTvDesc.setVisibility(VISIBLE);
            mTvDesc.setText(desc);
        }
    }

    public void setTvDescGone() {
        if (mTvDesc != null) mTvDesc.setVisibility(View.GONE);
    }

    public void setErrorImage(int resId) {
//        tvErrorTitle.setVisibility(GONE);
//        tvDesc.setVisibility(GONE);
//        btn.setVisibility(GONE);
        if (mImageView != null) {
            mImageView.setImageResource(resId);
            mImageView.setVisibility(VISIBLE);
        }
    }

    public void setBtnText(int text) {
        if (mButton != null) mButton.setText(text);
    }

    public void setBtnVisibility(int visible) {
        if (mButton != null) mButton.setVisibility(visible);
    }

    public void setBtnListen(OnClickListener listen) {
        if (mButton != null)
            mButton.setOnClickListener(listen);
    }


    public void setErrorlayout(OnClickListener listener, @StringRes int title, @StringRes int desc, @StringRes int
            btnId, @DrawableRes int imgId) {
        if (title != 0) {
            setTvErrorTitle(title);
        } else {
            setTvDescGone();
        }
        if (desc == 0) {
            setTvDescGone();
        } else {
            setTvDesc(desc);
        }
        if (listener != null) {
            setBtnVisibility(VISIBLE);
            setBtnText(btnId);
            setBtnListen(listener);
        } else {
            setBtnVisibility(GONE);
        }
        setErrorImage(imgId);
    }

}










