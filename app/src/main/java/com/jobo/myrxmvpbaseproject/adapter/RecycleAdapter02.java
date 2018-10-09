package com.jobo.myrxmvpbaseproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.entity.RecyclerBean;
import com.jobo.recycle.base.BaseSuperAdapter;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.List;

/**
 * 多条目
 * Created by JoBo on 2018/6/1.
 */

public class RecycleAdapter02 extends BaseSuperAdapter<RecyclerBean> {
    private final int typeOne = 1;
    private final int typeTwo = 2;
    private final int typeThree = 3;
    private final int typeFour = 4;

    public RecycleAdapter02(List<RecyclerBean> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new OneViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
                return new TwoViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new OneViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        RecyclerBean info = mList.get(position);
        if (holder instanceof OneViewHolder){
            holder.setText(R.id.tv_message_item,"One"+info.getMessage());
        }else if (holder instanceof TwoViewHolder){
            holder.setText(R.id.tv_message_item2,"Two"+info.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0){
            return typeOne;
        }else {
            return typeTwo;
        }
    }

    class OneViewHolder extends BaseViewHolder{
        public OneViewHolder(View itemView) {
            super(itemView);
        }
    }

    class TwoViewHolder extends BaseViewHolder{
        public TwoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
