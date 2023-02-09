package com.cohelp.task_for_stu.ui.callBack;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewSwipeCallback extends ItemTouchHelper.Callback {
//
//    private IMoveAndSwipeCallback iMoveAndSwipeCallback;
//
//    public void setiMoveAndSwipeCallback(IMoveAndSwipeCallback iMoveAndSwipeCallback) {
//        this.iMoveAndSwipeCallback = iMoveAndSwipeCallback;
//    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
