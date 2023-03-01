package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.listener.ClickListener;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;

import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.ActivityAdapter;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;
import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;

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

    SwitchButton switchButton;
    List<DetailResponse> activityVOList;
    User user;
    OkHttpUtils okHttpUtils;
    Intent intent;

    ActivityAdapter activityAdapter;
    CardViewListAdapter cardViewListAdapter;
    Integer conditionState = 0;

    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    activityVOList = (List<DetailResponse>) msg.obj;
                    cardViewListAdapter.setDetailResponseListList(activityVOList);
                    eRecyclerView.setLayoutManager(new LinearLayoutManager(TaskCenterActivity.this));
                    eRecyclerView.setAdapter(cardViewListAdapter);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(TaskCenterActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(TaskCenterActivity.this,"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_center);
        activityVOList = SessionUtils.getActivityPreference(TaskCenterActivity.this);
        initTools();
        initView();
        initEvent();
        setTitle("活动");
    }
    private void initTools(){
        intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
    }


    private void initEvent() {
        setToolbar(R.drawable.common_add, new ClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchButton.isChecked()){
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
                if(StringUtils.isEmpty(s)){
                    T.showToast("查询的标题不能为空哦~");
                }
                else {
                    startLoadingProgress();
                    searchActivity(s);
//                    activityAdapter.setActivityList(activityVOList);
                    cardViewListAdapter.setDetailResponseListList(activityVOList);
//                    eRecyclerView.setAdapter(activityAdapter);
                    eRecyclerView.setAdapter(cardViewListAdapter);
                    eSwipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //关闭刷新
                            eSwipeRefreshLayout.setRefreshing(false);
                        }
                    },1000);
                }
                stopLoadingProgress();
            }
        });

        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //判断是否在刷新
                System.out.println("isrefreshing");
                Toast.makeText(TaskCenterActivity.this,eSwipeRefreshLayout.isRefreshing()?"正在刷新":"刷新完成"
                        ,Toast.LENGTH_SHORT).show();
                refreshActivityListData();

            }
        });

        SearchHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                conditionState = 0;
                refreshActivityListData();
                stopLoadingProgress();
            }
        });

        SearchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoadingProgress();
                conditionState = 1;
                refreshActivityListData();
                stopLoadingProgress();
            }
        });
        cardViewListAdapter.setOnItemClickListener(new CardViewListAdapter.OnItemListenter(){
            @Override
            public void onItemClick(View view, int postion) {
                System.out.println("lisetn in act");
                Intent intent = new Intent(TaskCenterActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailResponse",activityVOList.get(postion));
                intent.putExtras(bundle);
                IdAndType idAndType = new IdAndType(activityVOList.get(postion).getActivityVO().getId(),1);
                new Thread(()->{
                    System.out.println(okHttpUtils.getDetail(idAndType));
                }).start();
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toCreateNewTaskActivity() {
        //TODO 创建新任务
        Intent intent = new Intent(this,CreateNewTaskActivity.class);
        startActivity(intent);
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
        switchButton = findViewById(R.id.id_sb_check);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        cardViewListAdapter = new CardViewListAdapter();
        getActivityList(conditionState);
//        activityAdapter = new ActivityAdapter(this,activityVOList);
//        cardViewListAdapter = new CardViewListAdapter(activityVOList);
        cardViewListAdapter.setDetailResponseListList(activityVOList);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(TaskCenterActivity.this));
        eRecyclerView.setAdapter(cardViewListAdapter);
    }

    @Override
    protected void onPause() {
        SessionUtils.saveActivityPreference(TaskCenterActivity.this,okHttpUtils.getGson().toJson(activityVOList));
        super.onPause();
//        System.out.println("\n\n\n\n\n\nonPause!!!!!\n\n\n\n\n\n\n");
    }


    @Override
    protected void onDestroy() {
//        SessionUtils.deleteActivityPreference(TaskCenterActivity.this);
        super.onDestroy();
//        System.out.println("\n\n\n\n\n\nonDestroy!!!!!");
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
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }
    private  void getActivityList(Integer CconditionType){
        if (activityVOList!=null){
            System.out.println("is not empty");
            return;
        }
        Thread t1 = new Thread(()->{
            activityVOList = okHttpUtils.activityList(conditionState);
            Message msg = Message.obtain();
            msg.obj = activityVOList;
            msg.what = GET_DATA_SUCCESS;
            handler.sendMessage(msg);
        });
        t1.start();


    }
    private  void  searchActivity(String key){
        Thread t1 = new Thread(()->{
            activityVOList = okHttpUtils.search(key,1);
            Message msg = Message.obtain();
            msg.obj = activityVOList;
            msg.what = GET_DATA_SUCCESS;
            handler.sendMessage(msg);
        });
        t1.start();

    }
    private synchronized void refreshActivityListData(){
        getActivityList(conditionState);
        cardViewListAdapter.setDetailResponseListList(activityVOList);
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