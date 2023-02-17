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
import com.cohelp.task_for_stu.biz.QuestionBiz;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.QuestionAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.ui.vo.Question;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;

import java.util.ArrayList;
import java.util.List;

public class MyQuestionActivity extends BaseActivity {
    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;

    RecyclerView eRecyclerView;
    SwipeRefreshLayout eSwipeRefreshLayout;

    CardViewListAdapter cardViewListAdapter;
    OkHttpUtils okHttpUtils;
    List<DetailResponse> involvedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        setUpToolBar();
        setTitle("我的参与");
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

        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toQuestionCenterActivity();
            }
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
                Intent intent = new Intent(MyQuestionActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailResponse",involvedList.get(postion));
                intent.putExtras(bundle);
                IdAndType idAndType = new IdAndType(involvedList.get(postion).getActivityVO().getId(),1);
                new Thread(()->{
                    System.out.println(okHttpUtils.getDetail(idAndType));
                }).start();
                startActivity(intent);
            }
        });
        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //判断是否在刷新
                System.out.println("isrefreshing");
                Toast.makeText(MyQuestionActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
                        ,Toast.LENGTH_SHORT).show();
                refreshInvolvedListData();
            }
        });

    }



    private void initView() {
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);

        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);

        getInvolvedList();
        if (involvedList!=null){
            cardViewListAdapter = new CardViewListAdapter(involvedList);
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

    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toQuestionCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
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
    private synchronized void refreshInvolvedListData(){
        involvedList = okHttpUtils.getInvolvedList();
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

}