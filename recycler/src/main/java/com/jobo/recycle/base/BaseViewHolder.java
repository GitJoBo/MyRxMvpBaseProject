package com.jobo.recycle.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.*;

/**
 * Created by JoBo on 2018/6/1.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "BaseViewHolder";
    private SparseArray<View> mChildViews = null;
    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mChildViews = new SparseArray<>();
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T getView(int id) {
        View childView = mChildViews.get(id);
        if (childView == null) {
            childView = itemView.findViewById(id);
            if (childView != null)
                mChildViews.put(id, childView);
        }
        try {
            return (T) childView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setText("");
        } else {
            textView.setText(text);
        }
        return this;
    }

    public BaseViewHolder setText(int viewId, int text) {
        TextView textView = getView(viewId);

        textView.setText(text);

        return this;
    }

    public BaseViewHolder setMovementMethod(int viewId, MovementMethod method) {
        TextView textView = getView(viewId);
        textView.setMovementMethod(method);
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

//    public BaseViewHolder setImageUri(Context context, int viewId, String imageUri) {
//
//        return this;
//    }

    public BaseViewHolder setScaleType(int viewId, ImageView.ScaleType type) {
        ImageView view = getView(viewId);
        view.setScaleType(type);
        return this;
    }

    /**
     * Set adapter for AbsListView.
     *
     * @param viewId  id
     * @param adapter BaseAdapter
     * @return BaseViewHolder
     */
    public BaseViewHolder setAdapter(int viewId, BaseAdapter adapter) {
        View view = getView(viewId);
        if (view instanceof ListView) {
            ((ListView) view).setAdapter(adapter);
        } else if (view instanceof GridView) {
            ((GridView) view).setAdapter(adapter);
        }
        return this;
    }

    /**
     * Set adapter for RecyclerView.
     *
     * @param viewId  id
     * @param adapter RecyclerView.Adapter
     * @return BaseViewHolder
     */
    public BaseViewHolder setAdapter(int viewId, RecyclerView.Adapter adapter) {
        View view = getView(viewId);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(adapter);
        }
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int bgColor) {
        View view = getView(viewId);
        view.setBackgroundColor(bgColor);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setAlpha(int viewId, float value) {
        View view = getView(viewId);
        ViewCompat.setAlpha(view, value);
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }

    public BaseViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BaseViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
    public BaseViewHolder setOnClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
        return this;
    }
    public void update(int position,BaseViewHolder holder) {}

    @Override
    public void onClick(View v) {
        Log.d(TAG,"onClick");
        if(mlisten!=null){
            mlisten.onClick(v,mPosition);
        }
    }

    public interface OnClickListen{
        void onClick(View v, int position);
    }
    OnClickListen mlisten;
    int mPosition;
    public void setOnClickListen(int viewId,OnClickListen listen,int position){
        View view = getView(viewId);
        mlisten=listen;
        mPosition=position;
        view.setOnClickListener(this);
    }
    public void setOnClickListen(View view, OnClickListen listen, int position){
        mlisten=listen;
        mPosition=position;
        view.setOnClickListener(this);
    }

    public Context getContext(){
        return getItemView().getContext();
    }

    public Activity getActivity(){
        return (Activity) getContext();
    }
}
