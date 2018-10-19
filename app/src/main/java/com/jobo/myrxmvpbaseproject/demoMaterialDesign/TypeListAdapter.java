package com.jobo.myrxmvpbaseproject.demoMaterialDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.utils.UIUtils;
import com.jobo.recycle.base.BaseSuperAdapter;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.List;

/**
 * Created by JoBo on 2018/10/16.
 */

public class TypeListAdapter extends BaseSuperAdapter<String> {
//    private ArrayList<String> mDatas;
//    private List<Integer> mHeights;
    private Context mContext;

    public TypeListAdapter(List<String> list, int layoutResId, Context context) {
        super(list, layoutResId);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setText(R.id.item_tv,mList.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.startActivity(mContext,DetailActivity.class);
            }
        });
    }
}
