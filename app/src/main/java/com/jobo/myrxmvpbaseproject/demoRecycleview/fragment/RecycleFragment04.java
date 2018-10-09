package com.jobo.myrxmvpbaseproject.demoRecycleview.fragment;

import android.app.Activity;
import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import com.jobo.httplib.base.BaseFragment;
import com.jobo.httplib.utils.ToastUtils;
import com.jobo.myrxmvpbaseproject.R;
import com.jobo.myrxmvpbaseproject.adapter.RecycleAdapter01;
import com.jobo.myrxmvpbaseproject.entity.RecyclerBean;
import com.jobo.myrxmvpbaseproject.listener.OnRecyclerItemClickListener;
import com.jobo.recycle.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JoBo on 2018/6/4.
 */

public class RecycleFragment04 extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecycleAdapter01 mRecycleAdapter01;
    private List<RecyclerBean> mBeanList = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for (int i = 0; i < 10; i++) {
            mBeanList.add(new RecyclerBean(i + "", "第" + i + "个"));
        }
        mRecyclerView.setAdapter(mRecycleAdapter01 = new RecycleAdapter01(mBeanList, R.layout.item));
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(BaseViewHolder vh) {
                ToastUtils.showToast(vh.getLayoutPosition() + "");
            }

            @Override
            public void onItemLongClick(BaseViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
                if (vh.getLayoutPosition() != 0 && vh.getLayoutPosition() != 1) {
                    mItemTouchHelper.startDrag(vh);

                    //获取系统震动服务
                    Vibrator vib = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
                    vib.vibrate(70);//震动70毫秒

                }
            }
        });
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向
             * 如果是列表类型的RecyclerView的只存在UP和DOWN，
             * 如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
//                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView
                    .ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mBeanList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mBeanList, i, i - 1);
                    }
                }
                mRecycleAdapter01.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                myAdapter.notifyItemRemoved(position);
//                datas.remove(position);
            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_recycle04;
    }

    public static RecycleFragment04 newInstance(String string) {

        Bundle args = new Bundle();

        RecycleFragment04 fragment = new RecycleFragment04();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("04","onAttach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("04","onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("04","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("04","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("04","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("04","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("04","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("04","onDetach");
    }

    @Override
    protected void userInvisible() {
        super.userInvisible();
        Log.d("04","userInvisible");
    }

    @Override
    protected void userVisible() {
        super.userVisible();
        Log.d("04","userVisible");
    }

    @Override
    protected void userFristVisible() {
        super.userFristVisible();
        Log.d("04","userFristVisible");
    }
}
