package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.listener.ClickListener;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.HoleListRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Hole;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.HoleVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.ActivityAdapter;
import com.cohelp.task_for_stu.ui.adpter.HoleAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
    活动
 */
public class TaskCenterActivity extends BaseActivity {
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout HoleCenter;
    LinearLayout UserCenter;
    LinearLayout SearchHot;
    LinearLayout SearchTime;

    EditText searchedContent;
    ImageView searchBtn;
    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;
    RelativeLayout SearchBox;
    Switch aSwitch;
    TaskAdapter taskAdapter;
    List<Task> taskList;
    TaskBiz taskBiz;
    List<DetailResponse> activityVOList = new ArrayList<>();
    User user;
    OkHttpUtils okHttpUtils;
    Intent intent;

    ActivityAdapter activityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_center);
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
        initView();
        initEvent();
        setTitle("首页");
    }

    private void initEvent() {
        setToolbar(R.drawable.common_add, new ClickListener() {
            @Override
            public void click() {
                toCreateNewTaskActivity();
            }
        });

        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHelpCenterActivity();
            }
        });

//        TaskCenter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toTaskCenterActivity();
//            }
//        });

        UserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserCenterActivity();
            }
        });
        HoleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toHoleCenterActivity();}
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch.isChecked()){
//
                    SearchBox.setVisibility(buttonView.VISIBLE);
                }else {
//
                    SearchBox.setVisibility(buttonView.GONE);
                }

            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 从服务端搜索
                String s = searchedContent.getText().toString();
                if(!StringUtils.isEmpty(s)){
                    T.showToast("查询的标题不能为空哦~");
                }
                startLoadingProgress();
                taskBiz.searchByTitle(s, new CommonCallback<List<Task>>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(List<Task> response) {
                        stopLoadingProgress();
                        T.showToast("查询成功！");
                        updateList(response);
                    }
                });
            }
        });

        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //判断是否在刷新
                System.out.println("isrefreshing");
                Toast.makeText(TaskCenterActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
                        ,Toast.LENGTH_SHORT).show();
                getActivityList();
//                activityAdapter = new ActivityAdapter(activityVOList);
//                activityAdapter.setOnItemClickListener(new ActivityAdapter.OnItemListenter(){
//                    @Override
//                    public void onItemClick(View view, int postion) {
//                        System.out.println("lisetn in act");
//                        Intent intent = new Intent(TaskCenterActivity.this,DetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("detailResponse",activityVOList.get(postion));
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
                activityAdapter.setActivityList(activityVOList);
                eRecyclerView.setAdapter(activityAdapter);
                eSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //关闭刷新
                        eSwipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });

//        eSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
//            @Override
//            public void onPullUpRefresh() {
//                loadAll();
//            }
//        });
    }

    private void toCreateNewTaskActivity() {
        //TODO 创建新任务
        Intent intent = new Intent(this,CreateNewTaskActivity.class);
        startActivityForResult(intent,1001);
    }

    private void loadAll() {
        //TODO 查询所有问题
        startLoadingProgress();
        getActivityList();
        ActivityAdapter activityAdapter = new ActivityAdapter(this,activityVOList);
//        System.out.println("holeList="+holeList);
//        HoleAdapter holeAdapter = new HoleAdapter(holeList);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eRecyclerView.setAdapter(activityAdapter);
        stopLoadingProgress();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList(List<Task> response) {
        taskList.clear();
        taskList.addAll(response);
        taskAdapter.notifyDataSetChanged();
        eSwipeRefreshLayout.setRefreshing(false);
        eSwipeRefreshLayout.setPullUpRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadAll();
    }

    private void initView() {

        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
//        act = findViewById(R.id.id_tv_activity);
//        help = findViewById(R.id.id_tv_help);
//        tree = findViewById(R.id.id_tv_treehole);
        searchedContent = findViewById(R.id.id_et_search);
        searchBtn = findViewById(R.id.id_iv_search);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        SearchBox = findViewById(R.id.id_rl_search);
        SearchHot = findViewById(R.id.id_ll_search_hot);
        SearchTime = findViewById(R.id.id_ll_search_time);
        aSwitch = findViewById(R.id.id_sw_check);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
//        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //判断是否在刷新
//                System.out.println("isrefreshing");
//                Toast.makeText(TaskCenterActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
//                        ,Toast.LENGTH_SHORT).show();
//                getActivityList();
//                eSwipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //关闭刷新
//                        eSwipeRefreshLayout.setRefreshing(false);
//                    }
//                },1000);
//            }
//        });
        taskBiz = new TaskBiz();
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this,taskList);
        getActivityList();
        activityAdapter = new ActivityAdapter(this,activityVOList);
//        holeList.add(new Hole("强奸","wow", 0,0,0,"friend"));
        activityAdapter.setOnItemClickListener(new ActivityAdapter.OnItemListenter(){
            @Override
            public void onItemClick(View view, int postion) {
                System.out.println("lisetn in act");
                Intent intent = new Intent(TaskCenterActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailResponse",activityVOList.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eRecyclerView.setAdapter(activityAdapter);
//        loadAll();
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

    }
    private synchronized void getActivityList(){

        Thread t1 = new Thread(()->{
            activityVOList = okHttpUtils.activityList();
//            holeList = okHttpUtils.holeList();
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}