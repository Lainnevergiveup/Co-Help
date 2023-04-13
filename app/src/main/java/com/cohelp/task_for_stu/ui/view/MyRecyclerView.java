package com.cohelp.task_for_stu.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView extends RecyclerView {
    private boolean isIntercepted;
    private int mLastX;
    private int mLastY;
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                //如果是垂直滑动，则拦截
                if (Math.abs(deltaX) - Math.abs(deltaY) < 0) {
                    intercept = true;
                } else {
                    intercept = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        super.onInterceptTouchEvent(ev);//这一句一定不能漏掉，否则无法拦截事件
        return intercept;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 根据移动距离计算出需要滚动的距离
                float distanceX = event.getX() - mLastX;
                // ...
                break;
            case MotionEvent.ACTION_UP:
                // ...
                break;
        }
        return super.onTouchEvent(event);
    }
}
