package com.jobo.myrxmvpbaseproject.demoRecycleview.treerecyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.recycle.base.tree.CheckableNodeViewBinder;
import com.jobo.recycle.bean.TreeNode;

public class SecondLevelNodeViewBinder extends CheckableNodeViewBinder {

    TextView textView;
    ImageView imageView;
    public SecondLevelNodeViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_second_level;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        if (!treeNode.hasChild()){
            imageView.setVisibility(View.INVISIBLE);
        }else {
            imageView.setVisibility(View.VISIBLE);
        }
        textView.setText(treeNode.getValue().toString());
        imageView.setRotation(treeNode.isExpanded() ? 90 : 0);
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (!treeNode.hasChild()){
            imageView.setVisibility(View.INVISIBLE);
        }else {
            imageView.setVisibility(View.VISIBLE);
        }
        if (expand) {
            imageView.animate().rotation(90).setDuration(200).start();
        } else {
            imageView.animate().rotation(0).setDuration(200).start();
        }
    }
}
