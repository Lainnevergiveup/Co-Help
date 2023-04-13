package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
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
import com.cohelp.task_for_stu.net.model.vo.ResultVO;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.NewsListEditAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyRecordActivity extends BasicInfoActivity {


    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;

    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;

    private TextView mTvSwitch;
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private NewsListEditAdapter mAdapter;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    TaskAdapter taskAdapter;
    Button btn_delete;



    CardViewListAdapter cardViewListAdapter;
    List<ResultVO> collectList;
    String delCollectList;
    OkHttpUtils okHttpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
    }


    private void initTools(){
//        intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
    }
    private void initEvent(){

//        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //判断是否在刷新
//                System.out.println("isrefreshing");
//                Toast.makeText(MyCollectActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
//                        ,Toast.LENGTH_SHORT).show();
//                refreshCollectListData();
//
//            }
//        });

        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
//        cardViewListAdapter.setOnItemClickListener(new CardViewListAdapter.OnItemListenter(){
//            @Override
//            public void onItemClick(View view, int postion) {
//                System.out.println("lisetn in act");
//                Intent intent = new Intent(MyCollectActivity.this,DetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("detailResponse",collectList.get(postion));
//                intent.putExtras(bundle);
//                IdAndType idAndType = new IdAndType(collectList.get(postion).getActivityVO().getId(),1);
//                new Thread(()->{
//                    System.out.println(okHttpUtils.getDetail(idAndType));
//                }).start();
//                startActivity(intent);
//            }
//        });

        mTvSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.switchManageMode();
                refreshManageMode();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleConfirmDialog();
            }
        });

        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        scbSelectAll.setOnCheckedChangeListener((checkBox, isChecked) -> mAdapter.setSelectAll(isChecked));

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            getRecordList();
            mAdapter.refresh(collectList);
            refreshLayout.finishRefresh();
        }, 300));
        //上拉加载
//        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
//            mAdapter.loadMore(collectList);
//            refreshLayout.finishLoadMore();
//        }, 1000));
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

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

    private void initView() {
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter =findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        flEdit = findViewById(R.id.fl_edit);
        scbSelectAll = findViewById(R.id.scb_select_all);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);
        btn_delete = findViewById(R.id.btn_delete);
        mTvSwitch = findViewById(R.id.id_tv_manager);

        mAdapter = new NewsListEditAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        },collectList);
        getRecordList();
        System.out.println("collect"+collectList);
//        cardViewListAdapter = new CardViewListAdapter(collectList);

//        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
//        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
//        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
//
//        eRecyclerView = findViewById(R.id.id_recyclerview);
//        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        eRecyclerView.setAdapter(cardViewListAdapter);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }
    private void toDetailActivity(int postion){
        Intent intent = new Intent(MyRecordActivity.this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",collectList.get(postion).getDetailResponse());
        intent.putExtras(bundle);

        ResultVO resultVO = collectList.get(postion);
        DetailResponse detailResponse = resultVO.getDetailResponse();
        Integer type = detailResponse.getType();
        IdAndType idAndType = new IdAndType(detailResponse.getIdByType(type),type);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }
    private void toHoleCenterActivity() {
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }
    private synchronized void getRecordList(){
        Thread t1 = new Thread(()->{
            collectList = okHttpUtils.getHistoryList();
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private synchronized void delRecordList(){
        Thread t1 = new Thread(()->{
            List<ResultVO> selectedDetailResponseList = mAdapter.getSelectedDetailResponseList();
            List<Integer> deleteIdList=new ArrayList<>();
            for (ResultVO i:selectedDetailResponseList){

                deleteIdList.add(i.getId());
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
    private synchronized void refreshCollectListData(){
        getRecordList();
        cardViewListAdapter.setDetailResponseListList(collectList.stream().map(i->i.getDetailResponse()).collect(Collectors.toList()));
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
        new MaterialDialog.Builder(MyRecordActivity.this)
                .content("是否确认删除")
                .positiveText(R.string.lab_yes)
                .negativeText(R.string.lab_no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        XToastUtils.toast("sssssss");
                        System.out.println("List"+mAdapter.getSelectedIndexList());
                        delRecordList();
                        getRecordList();
                        mAdapter.refresh(collectList);
                        refreshLayout.finishRefresh();

                        mAdapter.switchManageMode();
                        refreshManageMode();

                    }
                })
                .show();
    }
}