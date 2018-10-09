package com.jobo.myrxmvpbaseproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jobo.myrxmvpbaseproject.R;

/**
 * banner demo listview适配器
 * Created by JoBo on 2018/7/4.
 */

public class BannerListViewAdapter extends BaseAdapter {
    private String[] mDemoList;
    private Context mContext;

    public BannerListViewAdapter(String[] demoList, Context context) {
        mDemoList = demoList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDemoList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_text,null);
        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(mDemoList[position]);
        if (position%2 == 0){
            textView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        }else {
            textView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }
}
