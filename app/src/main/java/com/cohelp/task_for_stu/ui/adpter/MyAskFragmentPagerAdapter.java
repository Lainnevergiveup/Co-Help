package com.cohelp.task_for_stu.ui.adpter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

//fragment的适配器
public class MyAskFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List <Fragment> fragmentList= new ArrayList<>();

    public MyAskFragmentPagerAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle, List<Fragment> list) {
        super(fm);
        fragmentList=list;


    }

    public MyAskFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }
}

