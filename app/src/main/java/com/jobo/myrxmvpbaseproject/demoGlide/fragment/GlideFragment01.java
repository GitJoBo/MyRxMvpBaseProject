package com.jobo.myrxmvpbaseproject.demoGlide.fragment;

import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.NineImageViewEventAdapter;
import com.jobo.myrxmvpbaseproject.demoGlide.model.ImageModel;
import com.jobo.myrxmvpbaseproject.demoGlide.model.ModelUtil;
import com.jobo.myrxmvpbaseproject.widget.view.NineImageView.ImageAttr;
import com.jobo.myrxmvpbaseproject.widget.view.NineImageView.NineImageViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoBo on 2018/6/14.
 */

public class GlideFragment01 extends BaseFragment {
    @BindView(R.id.nine_image_viewgroup)
    NineImageViewGroup mNineImageViewGroup;

    @Override
    protected void init() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_glide01;
    }

    public static GlideFragment01 newInstance() {

        Bundle args = new Bundle();

        GlideFragment01 fragment = new GlideFragment01();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.btn_load})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                List<ImageModel> imageModels = ModelUtil.getImages();
                List<ImageAttr> imageAttrs = new ArrayList<>();
                for (int i = 0; i < imageModels.size(); i++) {
                    for (String url : imageModels.get(i).getImages()) {
                        ImageAttr attr = new ImageAttr();
                        attr.url = url;
                        imageAttrs.add(attr);
                    }
                }
                mNineImageViewGroup.setAdapter(new NineImageViewEventAdapter(this.getContext(),imageAttrs));
//                RequestOptions requestOptions = new RequestOptions();
//                requestOptions.placeholder(R.mipmap.ic_launcher);//添加占位符
//                Glide.with(this)
//                        .load("http://i4.bvimg.com/641641/c45df0cdd00d804e.jpg")
//                        .apply(requestOptions)
//                        .into(mImageView);
                break;
        }
    }
}
