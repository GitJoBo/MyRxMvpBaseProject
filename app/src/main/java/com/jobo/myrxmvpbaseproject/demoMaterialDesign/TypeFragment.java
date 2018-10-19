package com.jobo.myrxmvpbaseproject.demoMaterialDesign;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.myrxmvpbaseproject.R;

import java.util.ArrayList;

/**
 * Created by JoBo on 2018/10/16.
 */

public class TypeFragment extends BaseFragment {
    @BindView(R.id.type_list)
    RecyclerView mTypeList;

    private String mType;
    private Context mContext;
    private ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void init() {
        initBundleData();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager
                .VERTICAL);
        mTypeList.setLayoutManager(layoutManager);
        TypeListAdapter typeListAdapter = new TypeListAdapter(datas, R.layout.item_type_layout,getActivity());
        mTypeList.setAdapter(typeListAdapter);
    }

    private void initBundleData() {
        if (getArguments() != null) {
            mType = getArguments().getString("type");
            for (int i = 0; i < 35; i++) {
                datas.add(mType + (i + 1));
            }
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_type;
    }

    public static TypeFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        TypeFragment fragment = new TypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }
}
