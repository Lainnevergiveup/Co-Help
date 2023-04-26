package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyFragmentPagerAdapter;
import com.cohelp.task_for_stu.ui.adpter.NewsListEditAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MyTaskActivity extends BaseActivity implements View.OnClickListener{
    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    LinearLayout ll_all,ll_ac,ll_help,ll_dis,ll_current;

    TextView text_size,title;

    RecyclerView eRecyclerView;
    SwipeRefreshLayout eSwipeRefreshLayout;
    Button delbutton;
    EasyIndicator mEasyIndicator;
    ViewPager2 mViewPager;

    private TextView mTvSwitch;
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private NewsListEditAdapter mAdapter;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    TaskAdapter taskAdapter;
    CardViewListAdapter cardViewListAdapter;
    Button btn_delete;
    List<DetailResponse> involvedList;
    OkHttpUtils okHttpUtils = new OkHttpUtils();

    private List<ViewPagerFragment> list = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        setUpToolBar();
        initTools();
        initView();
        initEvent();
        initPager();
        title.setText("我的参与");

    }


    private void initTools(){
        
    }
    @SuppressLint("ResourceType")
    private void initEvent() {

        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHelpCenterActivity();
            }
        });
        HoleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toHoleCenterActivity();}
        });

        TaskCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTaskCenterActivity();
            }
        });




//        for (int i = 0; i < 4; i++) {
//
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//            ViewPagerFragment viewpager_fragment = new ViewPagerFragment();
//
//
//            Bundle bundle = new Bundle();
//            String json = okHttpUtils.getGson().toJson(taskList);
//            System.out.println(taskList);
//            System.out.println("11"+json);
//            bundle.putString("datailResponse",json);
//            viewpager_fragment.setArguments(bundle);
//            list.add(viewpager_fragment);
////            recyclerView.setLayoutManager(new LinearLayoutManager());
////        recyclerView.setAdapter(mAdapter = new NewsListEditAdapter(isSelectAll -> {
////            if (scbSelectAll != null) {
////                scbSelectAll.setCheckedSilent(isSelectAll);
////            }
////        },json));
////            recyclerView.setAdapter(new CardViewListAdapter(taskList));
//            fragmentTransaction.add(R.id.view_pager,viewpager_fragment);
//            fragmentTransaction.commit();
////            Bundle bundle = new Bundle();
////            bundle.putString("name","第"+(i+1)+"页");
////            viewpager_fragment.setArguments(bundle);
//
//
//
//
//        }
//        System.out.println("list"+list);
//
//
//
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
//        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),list) {
//            @NonNull
//            @Override
//            public Fragment getItem(int position) {
//                return list.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//        });
//
//        // 设置指示器
//        mEasyIndicator.setTabTitles(ContentPage.getPageNames());
//        mEasyIndicator.setViewPager(mViewPager, mPagerAdapter);
//        mViewPager.setOffscreenPageLimit(ContentPage.size() - 1);
//        mViewPager.setCurrentItem(0);

    }
    private void initView() {
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        flEdit = findViewById(R.id.fl_edit);
        scbSelectAll = findViewById(R.id.scb_select_all);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);
        btn_delete = findViewById(R.id.btn_delete);
        mTvSwitch = findViewById(R.id.id_tv_manager);
        mViewPager = findViewById(R.id.view_pager);
        title = findViewById(R.id.tv_title);
        ll_all = findViewById(R.id.ll_all);
        ll_ac = findViewById(R.id.ll_tab_activity);
        ll_help = findViewById(R.id.ll_tab_help);
        ll_dis =findViewById(R.id.ll_tab_dis);
        ll_all.setOnClickListener(this);
        ll_ac.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_dis.setOnClickListener(this);
        ll_all.setSelected(true);
        ll_current = ll_ac;

        getInvolvedList();

    }




    private void initPager(){
        mViewPager = findViewById(R.id.id_viewpaper);
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new BlankFragment1(this));
        list.add(new BlankFragment1(this));
        list.add(new BlankFragment1(this));
        list.add(new BlankFragment1(this));
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),list);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTap(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }
    //设置左滑右滑
    private void  changeTap(int positon){
        ll_current.setSelected(false);
        switch (positon){
            case 0:
                ll_all.setSelected(true);
                ll_current = ll_all;
                text_size = findViewById(R.id.id_tv_all);
                text_size.setTextSize(20);
                text_size = findViewById(R.id.id_tv_activity);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_help);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_dis);
                text_size.setTextSize(16);
                System.out.println("ll_all");
                break;
            case 1:
                ll_ac.setSelected(true);
                ll_current = ll_ac;
                text_size = findViewById(R.id.id_tv_all);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_activity);
                text_size.setTextSize(20);
                text_size = findViewById(R.id.id_tv_help);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_dis);
                text_size.setTextSize(16);
                System.out.println("ll_ac");
                break;
            case 2:
                ll_help.setSelected(true);
                text_size = findViewById(R.id.id_tv_all);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_activity);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_help);
                text_size.setTextSize(20);
                text_size = findViewById(R.id.id_tv_dis);
                text_size.setTextSize(16);
                ll_current = ll_help;
                break;
            case 3:
                ll_dis.setSelected(true);
                text_size = findViewById(R.id.id_tv_all);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_activity);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_help);
                text_size.setTextSize(16);
                text_size = findViewById(R.id.id_tv_dis);
                text_size.setTextSize(20);
                ll_current = ll_dis;
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_all:
                mViewPager.setCurrentItem(0);

                break;
            case R.id.ll_tab_activity:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_tab_help:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.ll_tab_dis:
                mViewPager.setCurrentItem(3);
        }
    }

    private void refreshManageMode() {
        if (mTvSwitch != null) {
            mTvSwitch.setText(mAdapter.isManageMode() ? "退出" : "管理");
            mTvSwitch.getCompoundPaddingLeft();

        }
        ViewUtils.setVisibility(flEdit, mAdapter.isManageMode());
    }

    private void toUserCenterActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        finish();
    }

    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHoleCenterActivity(){
        Intent intent = new Intent(this,HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toDetailActivity(int postion){
        Intent intent = new Intent(MyTaskActivity.this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",involvedList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(involvedList.get(postion).getActivityVO().getId(),1);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }
    private synchronized void getInvolvedList(){
        Thread t1 = new Thread(()->{
            involvedList = okHttpUtils.getInvolvedList();
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void refreshCollectListData(){
        getInvolvedList();
        cardViewListAdapter.setDetailResponseListList(involvedList);
        eRecyclerView.setAdapter(cardViewListAdapter);
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }


    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);

    //fragment触摸事件分发，将触摸事件分发给每个能够响应的fragment
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            if(listener != null) {
                listener.onTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }




}