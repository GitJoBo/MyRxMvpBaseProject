package com.jobo.myrxmvpbaseproject.demoRecycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoRecycleview.Model.ProjectLibListModel2;
import com.jobo.recycle.base.BaseSuperAdapter;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.List;

/**
 * Created by JoBo on 2018/3/26.
 */

public class ProjectLibListAdapter extends BaseSuperAdapter<ProjectLibListModel2.ListBean> {
    private Context mContext;
    private int mProjectState;
    private int mProjectPhase;

    public ProjectLibListAdapter(List list, int layoutResId) {
        super(list, layoutResId);
    }

    public ProjectLibListAdapter(int layoutResId, Context context, int projectState, int projectPhase) {
        super(layoutResId);
        this.mContext = context;
        this.mProjectState = projectState;
        this.mProjectPhase = projectPhase;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (mList == null || mList.size() < 1) return;
        ProjectLibListModel2.ListBean info = mList.get(position);
        if (info == null) return;

        holder.setText(R.id.tv_title, info.getProjectName());
        switch (mProjectState) {
            case 0:
                holder.setText(R.id.tv_five, "总投资：" + info.getTotalCost() + "亿元");
                String temp = "未开工";
                switch (info.getStartState()) {
                    case "00":
                        temp = "未开工";
                        break;
                    case "01":
                        temp = "申请开工";
                        break;
                    case "02":
                        temp = "已开工";
                        break;
                    case "03":
                        temp = "申请竣工";
                        break;
                    case "04":
                        temp = "已竣工";
                        break;
                }
                holder.setText(R.id.tv_year_all_invest, "项目阶段：" + temp);
                break;
            case 1:
                holder.setText(R.id.tv_five, "总投资：" + info.getTotalInvestment() + "亿元");
                holder.setText(R.id.tv_year_all_invest, "年度计划投资：" + info.getYearInvestment() + "亿元");
                break;
            case 2:
                holder.setText(R.id.tv_five, "总投资：" + info.getTotalCost() + "亿元");
                holder.setText(R.id.tv_year_all_invest, "年度计划投资：" + info.getYearTotalCost() + "亿元");
                break;
            case 3:
                if (!TextUtils.isEmpty(info.getTotalCost())) {
                    holder.setText(R.id.tv_five, "总投资：" + info.getTotalCost() + "亿元");
                    holder.setText(R.id.tv_year_all_invest, "年度计划投资：" + info.getYearTotalCost() + "亿元");
                } else {
                    holder.setText(R.id.tv_five, "总投资：" + info.getTotalInvestment() + "亿元");
                    holder.setText(R.id.tv_year_all_invest, "年度计划投资：" + info.getYearInvestment() + "亿元");
                }

                break;
        }

        holder.setOnClickListener(v -> {

        });

    }
}
