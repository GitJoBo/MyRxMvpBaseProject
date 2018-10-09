package com.jobo.myrxmvpbaseproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.demoGlide.activity.ImagesActivity;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;
import com.jobo.myrxmvpbaseproject.widget.view.NineImageView.ImageAttr;
import com.jobo.myrxmvpbaseproject.widget.view.NineImageView.NineImageViewGroup;
import com.jobo.myrxmvpbaseproject.widget.view.NineImageView.NineImageViewAdapter;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class NineImageViewEventAdapter extends NineImageViewAdapter {

    public NineImageViewEventAdapter(Context context, List<ImageAttr> imageAttr) {
        super(context, imageAttr);
    }

    @Override
    protected void onImageItemClick(Context context, NineImageViewGroup nineImageViewGroup, int index, List<ImageAttr> images) {
        for (int i = 0; i < images.size(); i++) {
            ImageAttr attr = images.get(i);
            View imageView = nineImageViewGroup.getChildAt(i);
            if (i >= nineImageViewGroup.getMaxSize()) {
                imageView = nineImageViewGroup.getChildAt(nineImageViewGroup.getMaxSize() - 1);
            }
            attr.width = imageView.getWidth();
            attr.height = imageView.getHeight();
            int[] points = new int[2];
            imageView.getLocationInWindow(points);
            attr.left = points[0];
            attr.top = points[1];
        }
        ToastUtils.showToast("点击了");

        Intent intent = new Intent(context, ImagesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagesActivity.IMAGE_ATTR, (Serializable) images);
        bundle.putInt(ImagesActivity.CUR_POSITION, index);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(0, 0);//取消Activity跳转动画
    }

}
