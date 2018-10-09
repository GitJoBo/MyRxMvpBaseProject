package com.jobo.myrxmvpbaseproject.demoRecycleview.treerecyclerview;

import android.view.View;
import com.jobo.recycle.base.tree.BaseNodeViewBinder;
import com.jobo.recycle.base.tree.BaseNodeViewFactory;

public class MyNodeViewFactory extends BaseNodeViewFactory {

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewBinder(view);
            case 1:
                return new SecondLevelNodeViewBinder(view);
            case 2:
                return new ThirdLevelNodeViewBinder(view);
            default:
                return null;
        }
    }
}
