package com.jobo.myrxmvpbaseproject.adapter;

import android.support.annotation.NonNull;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.entity.RecyclerBean;
import com.jobo.recycle.base.BaseSuperAdapter;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.List;

/**
 * Created by JoBo on 2018/6/1.
 */

public class RecycleAdapter01 extends BaseSuperAdapter<RecyclerBean> {

    public RecycleAdapter01(List<RecyclerBean> list, int layoutResId) {
        super(list, layoutResId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        RecyclerBean info = mList.get(position);
        holder.setText(R.id.tv_message_item, info.getMessage());
    }
}
