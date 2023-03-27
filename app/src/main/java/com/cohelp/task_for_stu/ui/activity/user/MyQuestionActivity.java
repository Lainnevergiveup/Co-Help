package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyTaskListEditAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MyQuestionActivity extends BaseActivity {
    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    TextView title;
    RecyclerView eRecyclerView;
    SwipeRefreshLayout eSwipeRefreshLayout;

    CardViewListAdapter cardViewListAdapter;
    OkHttpUtils okHttpUtils;
    List<DetailResponse> taskList;


    private TextView mTvSwitch;
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private MyTaskListEditAdapter mAdapter;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    TaskAdapter taskAdapter;
    Button btn_delete;

    String delCollectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);

        initTools();
        initView();
        initEvent();
        title.setText("我的发布");
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

        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                toQuestionCenterActivity();
            }
        });

        TaskCenter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                Intent intent = new Intent(MyQuestionActivity.this,DetailActivity.class);
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
//        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //判断是否在刷新
//                System.out.println("isrefreshing");
//                Toast.makeText(MyQuestionActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
//                        ,Toast.LENGTH_SHORT).show();
//                refreshTaskListData();
//            }
//        });

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


        mAdapter = new MyTaskListEditAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        },taskList);



        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);


        if (taskList!=null){
            cardViewListAdapter = new CardViewListAdapter(taskList);
        }
        else {
            cardViewListAdapter = new CardViewListAdapter();
        }

        eRecyclerView = findViewById(R.id.id_recyclerview);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(cardViewListAdapter);
    }

    private void toUserCenterActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toQuestionCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }


    private synchronized void refreshtaskListData(){
        taskList = okHttpUtils.getInvolvedList();
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

    private synchronized void delCollectList(){
        Thread t1 = new Thread(()->{
            List<DetailResponse> selectedDetailResponseList = mAdapter.getSelectedDetailResponseList();
            List<Integer> deleteIdList=new ArrayList<>();
            for (DetailResponse i:selectedDetailResponseList){

                deleteIdList.add(i.getType());
            }


            System.out.println(deleteIdList);
            delCollectList = okHttpUtils.delCollectList(deleteIdList);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private synchronized void refreshTaskListData(){
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

    /**
     * 简单的确认对话框
     */
    private void showSimpleConfirmDialog() {
        new MaterialDialog.Builder(MyQuestionActivity.this)
                .content("是否确认删除")
                .positiveText(R.string.lab_yes)
                .negativeText(R.string.lab_no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        XToastUtils.toast("sssssss");
                        System.out.println("List"+mAdapter.getSelectedIndexList());
                        delCollectList();

//                        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
//
//                        }, 1000));
                        getTaskList();
                        mAdapter.refresh(taskList);
                        refreshLayout.finishRefresh();
//                        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
//                            mAdapter.refresh(collectList);
//                            refreshLayout.finishRefresh();
//                        }, 1000));
//                        refreshLayout.autoRefresh();
//                        recyclerView.setAdapter(new NewsListEditAdapter(isSelectAll -> {
//                            if (scbSelectAll != null) {
//                                scbSelectAll.setCheckedSilent(isSelectAll);
//                            }
//                        },collectList));
                        mAdapter.switchManageMode();
                        refreshManageMode();
//                        refreshCollectListData();
//                        System.out.println(delCollectList);
                    }
                })
                .show();
    }

    private void refreshManageMode() {
        if (mTvSwitch != null) {
            mTvSwitch.setText(mAdapter.isManageMode() ? "退出" : "管理");
            mTvSwitch.getCompoundPaddingLeft();

        }
        ViewUtils.setVisibility(flEdit, mAdapter.isManageMode());
    }
}