package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.NewsListEditAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.List;

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


    private TextView mTvSwitch;
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private NewsListEditAdapter mAdapter;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    TaskAdapter taskAdapter;
    CardViewListAdapter cardViewListAdapter;
    Button btn_submit;
    List<DetailResponse> taskList;
    OkHttpUtils okHttpUtils;
    Intent intent;
    TaskBiz taskBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        setUpToolBar();
        setTitle("我的发布");
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
    private void initEvent() {


//        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //判断是否在刷新
//                System.out.println("isrefreshing");
//                Toast.makeText(MyTaskActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
//                        ,Toast.LENGTH_SHORT).show();
//                refreshCollectListData();
//
//            }
//        });
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



        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new NewsListEditAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        },taskList));
        scbSelectAll.setOnCheckedChangeListener((checkBox, isChecked) -> mAdapter.setSelectAll(isChecked));

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.refresh(taskList);
            refreshLayout.finishRefresh();
        }, 1000));
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.loadMore(taskList);
            refreshLayout.finishLoadMore();
        }, 1000));
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        mAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (mAdapter.isManageMode()) {
                mAdapter.updateSelectStatus(position);
            } else {
//                Utils.goWeb(getContext(), item.getDetailUrl());
            }
        });
        mAdapter.setOnItemLongClickListener((itemView, item, position) -> {
            if (!mAdapter.isManageMode()) {
                mAdapter.enterManageMode(position);
                refreshManageMode();
            }
        });


    }

//    private void loadAll() {
//        //TODO 查询所有问题
//        startLoadingProgress();
//        taskBiz.getAllMyTask(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Task>>() {
//            @Override
//            public void onError(Exception e) {
//                stopLoadingProgress();
//                T.showToast(e.getMessage());
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onSuccess(List<Task> response) {
//                stopLoadingProgress();
//                T.showToast("更新任务数据成功！");
////                updateList(response);
//            }
//        });
//    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<DetailResponse> response) {
        taskList.clear();
        taskList.addAll(response);
        taskAdapter.notifyDataSetChanged();
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
        btn_submit = findViewById(R.id.btn_submit);
        mTvSwitch = findViewById(R.id.id_tv_manager);
        getTaskList();
        System.out.println("list"+taskList);
        cardViewListAdapter = new CardViewListAdapter(taskList);

//        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
//        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
//        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);

//        eRecyclerView = findViewById(R.id.id_recyclerview);
//        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        eRecyclerView.setAdapter(cardViewListAdapter);





    }
//    private void refreshManageMode() {
//        if (mTvSwitch != null) {
//            mTvSwitch.setText(mAdapter.isManageMode() ? R.string.title_exit_manage_mode : R.string.title_enter_manage_mode);
//        }
//        ViewUtils.setVisibility(flEdit, mAdapter.isManageMode());
//    }

    private void refreshManageMode() {
        if (mTvSwitch != null) {
            mTvSwitch.setText(mAdapter.isManageMode() ? "退出" : "管理");
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
    private synchronized void getTaskList(){
        Thread t1 = new Thread(()->{
            taskList = okHttpUtils.searchPublic();
//            System.out.println(taskList);
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
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }




}