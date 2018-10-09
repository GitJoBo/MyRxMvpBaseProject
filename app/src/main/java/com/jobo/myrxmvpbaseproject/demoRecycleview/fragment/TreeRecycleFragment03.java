package com.jobo.myrxmvpbaseproject.demoRecycleview.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.OnClick;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.httplib.utils.LogUtils;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.demoRecycleview.treerecyclerview.MyNodeViewFactory;
import com.jobo.recycle.bean.TreeNode;
import com.jobo.recycle.view.TreeView;

import java.util.List;

/**
 * 多级列表,全选,展开,关闭
 * Created by JoBo on 2018/6/4.
 */

public class TreeRecycleFragment03 extends BaseFragment {
    @BindView(R.id.container)
    ViewGroup mContainer;
    private TreeNode root;
    private TreeView treeView;

    @Override
    protected void init() {
        root = TreeNode.root();
        buildTree();
        treeView = new TreeView(root, getContext(), new MyNodeViewFactory());
        View view = treeView.getView();
        treeView.setItemSelectable(false);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContainer.addView(view);
    }

    @OnClick(R.id.btn)
    protected void OnClick() {
        ToastUtils.showToast(getSelectedNodes());
        LogUtils.d("aaaaaaaaaaa" + getSelectedNodes());
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_tree_recycler03;
    }

    public static TreeRecycleFragment03 newInstance(String string) {

        Bundle args = new Bundle();

        TreeRecycleFragment03 fragment = new TreeRecycleFragment03();
        fragment.setArguments(args);
        return fragment;
    }

    private void buildTree() {
        for (int i = 0; i < 20; i++) {
            TreeNode treeNode = new TreeNode(new String("Parent  " + "No." + i));
            treeNode.setLevel(0);
            for (int j = 0; j < 10; j++) {
                TreeNode treeNode1 = new TreeNode(new String("Child " + "No." + j));
                treeNode1.setLevel(1);
                for (int k = 0; k < 5; k++) {
                    if (j % 2 == 0)
                        break;
                    TreeNode treeNode2 = new TreeNode(new String("Grand Child " + "No." + k));
                    treeNode2.setLevel(2);
                    treeNode1.addChild(treeNode2);
                }
                treeNode.addChild(treeNode1);
            }
            root.addChild(treeNode);
        }
    }

    private String getSelectedNodes() {
        StringBuilder stringBuilder = new StringBuilder("You have selected: ");
        List<TreeNode> selectedNodes = treeView.getSelectedNodes();
        for (int i = 0; i < selectedNodes.size(); i++) {
            if (i < 5) {
                stringBuilder.append(selectedNodes.get(i).getValue().toString() + ",");
            } else {
                stringBuilder.append("...and " + (selectedNodes.size() - 5) + " more.");
                break;
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("03", "onAttach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("03", "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("03", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("03", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("03", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("03", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("03", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("03", "onDetach");
    }

    @Override
    protected void userInvisible() {
        super.userInvisible();
        Log.d("03", "userInvisible");
    }

    @Override
    protected void userVisible() {
        super.userVisible();
        Log.d("03", "userVisible");
    }

    @Override
    protected void userFristVisible() {
        super.userFristVisible();
        Log.d("03", "userFristVisible");
    }
}
