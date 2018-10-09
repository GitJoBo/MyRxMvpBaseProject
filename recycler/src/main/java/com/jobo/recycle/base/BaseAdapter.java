package com.jobo.recycle.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class  BaseAdapter<VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {


    @Override
    public VH onCreateViewHolder(ViewGroup parent, final int viewType) {
        final VH holder = onCreate(parent, viewType);
        return holder;
    }

    protected abstract VH onCreate(ViewGroup parent, int viewType);
}
