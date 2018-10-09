package com.jobo.myrxmvpbaseproject.demoGlide.fragment;

import android.app.IntentService;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.load.engine.GlideException;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.sunfusheng.glideimageview.GlideImageView;
import com.sunfusheng.glideimageview.progress.OnGlideImageViewListener;
import com.sunfusheng.glideimageview.progress.OnProgressListener;

/**
 * Created by JoBo on 2018/6/21.
 */

public class GlideFragment02 extends BaseFragment implements OnProgressListener {

    String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497688355699&di" +
            "=ea69a930b82ce88561c635089995e124&imgtype=0&src=http%3A%2F%2Fcms-bucket" +
            ".nosdn.127.net%2Ff84e566bcf654b3698363409fbd676ef20161119091503.jpg";
    String url2 = "http://img1.imgtn.bdimg.com/it/u=4027212837,1228313366&fm=23&gp=0.jpg";
    String gif1 = "http://img.zcool.cn/community/01e97857c929630000012e7e3c2acf.gif";
    String gif2 = "http://img.zcool.cn/community/01d6dd554b93f0000001bf72b4f6ec.jpg";
    @BindView(R.id.glideview_01)
    GlideImageView mGlideview01;
    @BindView(R.id.glideview_02)
    GlideImageView mGlideview02;
    @BindView(R.id.glideview03)
    GlideImageView mGlideview03;
    @BindView(R.id.glideview04)
    GlideImageView mGlideview04;
    @BindView(R.id.imageview)
    ImageView mImageView;
    @BindView(R.id.btn_load)
    Button mBtnLoad;

    @Override
    protected void init() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_glide02;
    }

    public static GlideFragment02 newInstance() {

        Bundle args = new Bundle();

        GlideFragment02 fragment = new GlideFragment02();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.btn_load)
    public void onViewClicked() {
        mGlideview01.setCircle(true);
        mGlideview01.setBorderWidth(5);
        mGlideview01.loadImage(url1,0).listener(new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException
                    exception) {
                LogUtils.d("aaaaimageUrl:"+imageUrl+"\n,bytrsRead:"+bytesRead+"\n,totaBytes:"+totalBytes+"\n,isDone:"+isDone);
            }
        });

        mGlideview02.loadImage(gif1,0);
        GlideImageLoader glideImageLoader = GlideImageLoader.create(mImageView);
//        glideImageLoader.setOnProgressListener(gif2,this);
        glideImageLoader.loadImage(gif2,0);

        mGlideview03.loadImage(gif2,0);

        mGlideview04.loadImage(url2,0).listener(new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                LogUtils.d("percent:"+percent+"\n,isDone:"+isDone+"\n,exception:"+exception);
            }
        });
    }

    @Override
    public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
        LogUtils.d("imageUrl:"+imageUrl+",bytrsRead:"+bytesRead+",totaBytes:"+totalBytes+",isDone:"+isDone);
    }
}
