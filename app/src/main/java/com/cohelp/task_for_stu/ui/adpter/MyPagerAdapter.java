package com.cohelp.task_for_stu.ui.adpter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.cohelp.task_for_stu.ui.activity.user.MultiPage;

import java.util.HashMap;
import java.util.Map;


public class MyPagerAdapter extends PagerAdapter {


    private MultiPage mDestPage = MultiPage.组团招人;

    private Map<MultiPage, View> mPageMap = new HashMap<>();

    private RecyclerView recyclerView;
    public RecyclerView getPrimaryItem() {
        return this.recyclerView;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
