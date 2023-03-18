package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.listener.ClickListener;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;
/*
问答中心
 */
public class HelpCenterActivity extends BaseActivity {
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout HoleCenter;
    LinearLayout UserCenter;
    TextView lb1,lb2,lb3,lb4,lb5;
    RelativeLayout SearchBox;
    SwitchButton aSwitch;
    TextView all;

    EditText searchedContent;
    ImageView searchBtn;
    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;
    Integer conditionState = 0;
    List<DetailResponse> helpList = new ArrayList<>();

//    HelpAdapter helpAdapter;
    CardViewListAdapter cardViewListAdapter;
    OkHttpUtils okHttpUtils;
    String labelType = "全部";

    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    helpList = (List<DetailResponse>) msg.obj;
                    cardViewListAdapter.setDetailResponseListList(helpList);
                    eRecyclerView.setLayoutManager(new LinearLayoutManager(HelpCenterActivity.this));
                    eRecyclerView.setAdapter(cardViewListAdapter);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(HelpCenterActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(HelpCenterActivity.this,"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        initTools();
        initView();
        initEvent();
        setTitle("互助");
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
        setToolbar(R.drawable.common_add, new ClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void click() {
                toCreateNewHelpActivity();
            }
        });

        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHelpCenterActivity();
            }
        });

        TaskCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTaskCenterActivity();
            }
        });

        HoleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHoleCenterActivity();
            }
        });

        UserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserCenterActivity();
            }
        });

        lb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingProgress();
                conditionState = 0;
                refreshHelpListData();
                stopLoadingProgress();
            }
        });

        lb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingProgress();
                conditionState = 1;
                refreshHelpListData();
                stopLoadingProgress();
            }
        });

        lb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingProgress();
                conditionState = 2;
                refreshHelpListData();
                stopLoadingProgress();
            }
        });

        lb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingProgress();
                conditionState = 3;
                refreshHelpListData();
                stopLoadingProgress();
            }
        });

        lb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingProgress();
                conditionState = 4;
                refreshHelpListData();
                stopLoadingProgress();
            }
        });





//        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(aSwitch.isChecked()){
////
//                    SearchBox.setVisibility(buttonView.VISIBLE);
//                }else {
////
//                    SearchBox.setVisibility(buttonView.GONE);
//                }
//
//            }
//        });

//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 从服务端搜索
//                String s = searchedContent.getText().toString();
//                if (StringUtils.isEmpty(s)) {
//                    T.showToast("查询的标题不能为空哦~");
//                } else {
//                    startLoadingProgress();
//                    refreshHelpListData();
//                    stopLoadingProgress();
//                }
//
//
//            }
//        });
        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHelpListData();
            }
        });

    }

    private synchronized void refreshHelpListData(){
        getHelpList(conditionState);
//        helpAdapter.setHelpList(helpList);
        cardViewListAdapter.setDetailResponseListList(helpList);
        eRecyclerView.setAdapter(cardViewListAdapter);
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        lb1 = findViewById(R.id.id_tv_lb1);
        lb2 = findViewById(R.id.id_tv_lb2);
        lb3 = findViewById(R.id.id_tv_lb3);
        lb4 = findViewById(R.id.id_tv_lb4);
        lb5 = findViewById(R.id.id_tv_lb5);

        searchedContent = findViewById(R.id.id_et_search);
        searchBtn = findViewById(R.id.id_iv_search);

        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eRecyclerView = findViewById(R.id.id_recyclerview);

        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        getHelpList(conditionState);
        cardViewListAdapter = new CardViewListAdapter(helpList);
        cardViewListAdapter.setOnItemClickListener(new CardViewListAdapter.OnItemListenter(){
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(HelpCenterActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailResponse",helpList.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(cardViewListAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toCreateNewHelpActivity() {
        Intent intent = new Intent(this,CreateNewHelpActivity.class);
        startActivity(intent);
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
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }
    private synchronized void getHelpList(Integer CconditionType){
        Thread t1 = new Thread(()->{
            helpList = okHttpUtils.activityList(conditionState);
            Message msg = Message.obtain();
            msg.obj = helpList;
            msg.what = GET_DATA_SUCCESS;
            handler.sendMessage(msg);
        });
        t1.start();
    }


}