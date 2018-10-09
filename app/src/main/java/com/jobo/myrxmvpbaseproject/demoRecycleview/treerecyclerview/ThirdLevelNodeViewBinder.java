package com.jobo.myrxmvpbaseproject.demoRecycleview.treerecyclerview;

import android.view.View;
import android.widget.TextView;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.recycle.base.tree.CheckableNodeViewBinder;
import com.jobo.recycle.bean.TreeNode;

public class ThirdLevelNodeViewBinder extends CheckableNodeViewBinder {
    TextView textView;
    public ThirdLevelNodeViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_third_level;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
    }
}
