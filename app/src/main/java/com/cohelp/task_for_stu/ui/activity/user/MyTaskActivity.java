package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
//    private NewsListEditAdapter mAdapter;

    TaskAdapter taskAdapter;
    CardViewListAdapter cardViewListAdapter;

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


        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //判断是否在刷新
                System.out.println("isrefreshing");
                Toast.makeText(MyTaskActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
                        ,Toast.LENGTH_SHORT).show();
                refreshCollectListData();

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

        cardViewListAdapter.setOnItemClickListener(new CardViewListAdapter.OnItemListenter(){
            @Override
            public void onItemClick(View view, int postion) {
                System.out.println("lisetn in act");
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
        });
//        //todo 展示接受过和发布过的任务，需要重新写业务方法
//        taskSolved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startLoadingProgress();
//                taskBiz.getAllMyTaskSolved(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Task>>() {
//                    @Override
//                    public void onError(Exception e) {
//                        stopLoadingProgress();
//                        T.showToast(e.getMessage());
//                    }
//
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onSuccess(List<Task> response) {
//                        stopLoadingProgress();
//                        T.showToast("查询任务数据成功！");
//                        updateList(response);
//                    }
//                });
//            }
//        });
//
//        taskPosted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startLoadingProgress();
//                taskBiz.getAllMyTaskPosted(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Task>>() {
//                    @Override
//                    public void onError(Exception e) {
//                        stopLoadingProgress();
//                        T.showToast(e.getMessage());
//                    }
//
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onSuccess(List<Task> response) {
//                        stopLoadingProgress();
//                        T.showToast("查询任务数据成功！");
//                        updateList(response);
//                    }
//                });
//            }
//        });
    }

    private void loadAll() {
        //TODO 查询所有问题
        startLoadingProgress();
        taskBiz.getAllMyTask(UserInfoHolder.getInstance().geteUser().getId(),new CommonCallback<List<Task>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Task> response) {
                stopLoadingProgress();
                T.showToast("更新任务数据成功！");
//                updateList(response);
            }
        });
    }

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
        getTaskList();
        cardViewListAdapter = new CardViewListAdapter(taskList);

        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);

        eRecyclerView = findViewById(R.id.id_recyclerview);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(cardViewListAdapter);


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