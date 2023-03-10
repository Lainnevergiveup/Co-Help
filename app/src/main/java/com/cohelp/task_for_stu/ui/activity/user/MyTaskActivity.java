package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.NewsListEditAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.ui.widget.ContentPage;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MyTaskActivity extends BaseActivity {
    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    TextView all;
    TextView taskSolved;
    TextView taskPosted;
    RecyclerView eRecyclerView;
    SwipeRefreshLayout eSwipeRefreshLayout;
    Button delbutton;
    EasyIndicator mEasyIndicator;
    ViewPager mViewPager;

    LayoutInflater inflater;

    LinearLayout linearLayout;
    private TextView mTvSwitch;
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private NewsListEditAdapter mAdapter;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    TaskAdapter taskAdapter;
    CardViewListAdapter cardViewListAdapter;
    Button btn_delete;
    List<DetailResponse> taskList;
    OkHttpUtils okHttpUtils = new OkHttpUtils();

    private List<ViewPagerFragment> list = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        setUpToolBar();
        setTitle("????????????");
        initTools();
        initView();
        initEvent();



    }





    private void initTools(){
//        intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
    }
    @SuppressLint("ResourceType")
    private void initEvent() {

        mTvSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter == null) {
                    return;
                }
                mAdapter.switchManageMode();
                refreshManageMode();
            }
        });
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSimpleConfirmDialog();
//            }
//        });
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

        UserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserCenterActivity();
            }
        });
        System.out.println(list);

        for (int i = 0; i < 4; i++) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ViewPagerFragment viewpager_fragment = new ViewPagerFragment();

//            System.out.println(viewpager_fragment.getView());
//            recyclerView = viewpager_fragment.getRecyclerView();
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(new CardViewListAdapter(taskList));
            Bundle bundle = new Bundle();
            String json = okHttpUtils.getGson().toJson(taskList);
//            System.out.println(taskList);
//            System.out.println("11"+json);
            bundle.putString("datailResponse",json);
            viewpager_fragment.setArguments(bundle);
            list.add(viewpager_fragment);
//            recyclerView.setLayoutManager(new LinearLayoutManager());
//        recyclerView.setAdapter(mAdapter = new NewsListEditAdapter(isSelectAll -> {
//            if (scbSelectAll != null) {
//                scbSelectAll.setCheckedSilent(isSelectAll);
//            }
//        },json));
//            recyclerView.setAdapter(new CardViewListAdapter(taskList));
            fragmentTransaction.add(R.id.view_pager,viewpager_fragment);
            fragmentTransaction.commit();
//            Bundle bundle = new Bundle();
//            bundle.putString("name","???"+(i+1)+"???");
//            viewpager_fragment.setArguments(bundle);




        }
//        System.out.println("list"+list);



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),list));



        // ???????????????
        mEasyIndicator.setTabTitles(ContentPage.getPageNames());
        mEasyIndicator.setViewPager(mViewPager, mPagerAdapter);
        mViewPager.setOffscreenPageLimit(ContentPage.size() - 1);
        mViewPager.setCurrentItem(0);

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
        mEasyIndicator = findViewById(R.id.easy_indicator);
//        list1.add(LayoutInflater.from(this).inflate(R.layout.activity_act_summary,null));
        getTaskList();
//        System.out.println("list"+taskList);
        cardViewListAdapter = new CardViewListAdapter(taskList);
//        linearLayout  = findViewById(R.id.id_ll_cardview);


    }
//    private void refreshManageMode() {
//        if (mTvSwitch != null) {
//            mTvSwitch.setText(mAdapter.isManageMode() ? R.string.title_exit_manage_mode : R.string.title_enter_manage_mode);
//        }
//        ViewUtils.setVisibility(flEdit, mAdapter.isManageMode());
//    }


    private  void initCardView(){

        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new NewsListEditAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        },taskList));
        scbSelectAll.setOnCheckedChangeListener((checkBox, isChecked) -> mAdapter.setSelectAll(isChecked));

        //????????????
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.refresh(taskList);
            refreshLayout.finishRefresh();
        }, 1000));
        //????????????
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.loadMore(taskList);
            refreshLayout.finishLoadMore();
        }, 1000));
        refreshLayout.autoRefresh();//????????????????????????????????????????????????

        mAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (mAdapter.isManageMode()) {
                mAdapter.updateSelectStatus(position);
            } else {
                toDetailActivity(position);
//                Utils.goWeb(getContext(), item.getDetailUrl());
            }
        });
        mAdapter.setOnItemClickListener(new NewsListEditAdapter.OnItemListenter() {
            @Override
            public void onItemClick(View view, int postion) {
                toDetailActivity(postion);
            }
        });
        mAdapter.setOnItemLongClickListener((itemView, item, position) -> {
            if (!mAdapter.isManageMode()) {
                mAdapter.enterManageMode(position);
                refreshManageMode();
            }
        });
    }


    private void refreshManageMode() {
        if (mTvSwitch != null) {
            mTvSwitch.setText(mAdapter.isManageMode() ? "??????" : "??????");
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
        bundle.putSerializable("detailResponse",taskList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(taskList.get(postion).getActivityVO().getId(),1);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }

    private synchronized void getTaskList(){
        Thread t1 = new Thread(()->{
            taskList = okHttpUtils.searchPublic();
            System.out.println(taskList);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private synchronized void refreshCollectListData(){
        getTaskList();
        cardViewListAdapter.setDetailResponseListList(taskList);
        eRecyclerView.setAdapter(cardViewListAdapter);
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //????????????
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

    /**
     * ????????????????????????
     */
    private void showSimpleConfirmDialog() {
        new MaterialDialog.Builder(MyTaskActivity.this)
                .content("??????????????????")
                .positiveText(R.string.lab_yes)
                .negativeText(R.string.lab_no)
                .show();
    }


    /**
     *  ?????????
     */
    private Map<ContentPage, View> mPageMap = new HashMap<>();

    private PagerAdapter mPagerAdapter = new PagerAdapter( ) {
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return ContentPage.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            ContentPage page = ContentPage.getPage(position);
            System.out.println("getpage"+page.getPosition());
            View view = getPageView(page);
//            System.out.println(view);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    };

    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if(view == null){
            System.out.println("page"+page.getPosition());
            System.out.println(list.size());
            ViewPagerFragment viewPagerFragment = list.get(page.getPosition());
//            viewPagerFragment.getRecyclerView().setAdapter(new CardViewListAdapter(taskList));
//            System.out.println(viewPagerFragment);
            view = viewPagerFragment.getView();
            System.out.println("view"+view);
            mPageMap.put(page, view);
        }

        return view;
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        List<ViewPagerFragment> viewPagerFragmentList;
        public MyViewPagerAdapter(FragmentManager fm, List<ViewPagerFragment> fragment) {
            super(fm);
            this.viewPagerFragmentList = fragment;
        }

        @Override
        public int getCount() {
            return viewPagerFragmentList.size();
        }
        @Override
        public Fragment getItem(int arg0) {
            return viewPagerFragmentList.get(arg0);
        }
    }



}