package com.jobo.myrxmvpbaseproject.demoBigBitmap;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jobo.httplib.base.BaseActivity;
import com.jobo.myrxmvpbaseproject.R;
import com.sigseg.android.io.RandomAccessFileInputStream;
import com.sigseg.android.map.ImageSurfaceView;

import java.io.InputStream;

/**
 * Created by JoBo on 2018/8/20.
 */
//TODO 未实现（Droid4X可以，小米6不行）
public class BigBitmapActivity extends BaseActivity {
    @BindView(R.id.worldview)
    ImageSurfaceView mImageSurfaceView;
//    @BindView(R.id.big_imageview)
//    BigImageView mBigImageView;

    private static final String TAG = "BigBitmapActivity";
    private static final String KEY_X = "X";
    private static final String KEY_Y = "Y";
    private static final String KEY_FN = "FN";
    private String filename = null;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_big_bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_X) && savedInstanceState.containsKey(KEY_Y)) {
            Log.d(TAG, "restoring state");
            int x = (Integer) savedInstanceState.get(KEY_X);
            int y = (Integer) savedInstanceState.get(KEY_Y);

            String fn = null;
            if (savedInstanceState.containsKey(KEY_FN))
                fn = (String) savedInstanceState.get(KEY_FN);

            try {
                if (fn == null || fn.length()==0) {
                    mImageSurfaceView.setInputStream(getAssets().open("world.jpg"));
//                    mBigImageView.setInputStream(getAssets().open("world.jpg"));
                } else {
                    mImageSurfaceView.setInputStream(new RandomAccessFileInputStream(fn));
//                    mBigImageView.setInputStream(new RandomAccessFileInputStream(fn));
                }
                mImageSurfaceView.setViewport(new Point(x, y));
            } catch (java.io.IOException e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            // Centering the map to start
            Intent intent = getIntent();
            try {
                Uri uri = null;
                if (intent!=null)
                    uri = getIntent().getData();

                InputStream is;
                if (uri != null) {
                    filename = uri.getPath();
                    is = new RandomAccessFileInputStream(uri.getPath());
                } else {
                    is = getAssets().open("world.jpg");
                }

                mImageSurfaceView.setInputStream(is);
//                mBigImageView.setInputStream(is);
            } catch (java.io.IOException e) {
                Log.e(TAG, e.getMessage());
            }
            mImageSurfaceView.setViewportCenter();
        }
    }

    @Override
    protected void init() {


    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mImageSurfaceView.setViewport(new Point(mImageSurfaceView.getWidth()/2, mImageSurfaceView.getHeight()/2));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Point p = new Point();
        mImageSurfaceView.getViewport(p);
        outState.putInt(KEY_X, p.x);
        outState.putInt(KEY_Y, p.y);
        if (filename!=null)
            outState.putString(KEY_FN, filename);
        super.onSaveInstanceState(outState);
    }
}
