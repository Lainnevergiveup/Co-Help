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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.ActivityAdapter;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.T;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.utils.StringUtils;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;
import com.xuexiang.xui.widget.button.switchbutton.SwitchButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/*
    活动
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskCenterActivity extends BaseActivity {
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout HoleCenter;
    LinearLayout UserCenter;

    TextView title;

    FloatingActionButton floatingActionButton;
    androidx.appcompat.widget.Toolbar toolbar;
    EditText searchedContent;
    ImageView searchBtn,search;
    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;
    RelativeLayout SearchBox;

    SwitchButton switchButton;
    List<DetailResponse> activityVOList;
    OkHttpUtils okHttpUtils;
    Intent intent;
    SearchFragment searchFragment = SearchFragment.newInstance();
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
//        activityVOList = SessionUtils.getActivityPreference(TaskCenterActivity.this);
        initTools();
        initView();
        initToolbar();
        initEvent();
        setTitle("活动");
    }
    private void initTools(){
        
    }

    private void initView() {
        toolbar = findViewById(R.id.tool_bar_2);
        title = findViewById(R.id.tv_title);
        floatingActionButton = findViewById(R.id.fab);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        search = findViewById(R.id.iv_search_search);
        searchedContent = findViewById(R.id.et_search_keyword);
        searchBtn = findViewById(R.id.id_iv_search);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.PULL_FROM_START);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        cardViewListAdapter = new CardViewListAdapter();
        refreshActivityList();

    }

    private void initToolbar(){
//        toolbar.setNavigationOnClickListener(onClickListener);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        title.setText("活动");
    }

    private void initEvent() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCreateNewTaskActivity();
            }
        });
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                //这里处理逻辑
                //TODO 从服务端搜索
                if(StringUtils.isEmpty(keyword)){
                    T.showToast("查询的标题不能为空哦~");
                }
                else {
                    searchActivity(keyword);
                    startLoadingProgress();
                    eSwipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //关闭刷新
                            eSwipeRefreshLayout.setRefreshing(false);
                        }
                    },1000);
                }
                stopLoadingProgress();


                Toast.makeText(TaskCenterActivity.this, keyword, Toast.LENGTH_SHORT).show();
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


        cardViewListAdapter.setOnItemClickListener(new CardViewListAdapter.OnItemListenter(){
            @Override
            public void onItemClick(View view, int postion) {
                System.out.println("lisetn in act");
                toDetailActivity(postion);
            }
        });

    }


    androidx.appcompat.widget.Toolbar.OnMenuItemClickListener menuItemClickListener = item -> {
        if (item.getItemId() == R.id.item_hot) {
            startLoadingProgress();
            conditionState = 0;
            refreshActivityListData();
            stopLoadingProgress();
        }else if(item.getItemId() == R.id.item_time){
            startLoadingProgress();
            conditionState = 1;
            refreshActivityListData();
            stopLoadingProgress();
        }else if(item.getItemId() == R.id.item_search){
            searchFragment.showFragment(getSupportFragmentManager(),SearchFragment.TAG);
        }

        return false;
    };
    private View.OnClickListener onClickListener = v -> toCreateNewTaskActivity();




    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toCreateNewTaskActivity() {
        //TODO 创建新任务
        Intent intent = new Intent(this,CreateNewTaskActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onPause() {
//        SessionUtils.saveActivityPreference(TaskCenterActivity.this,OkHttpUtils.gson.toJson(activityVOList));
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void toUserCenterActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }
    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }
    private void toHoleCenterActivity(){
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }
    private void toDetailActivity(int postion){
        Intent intent = new Intent(TaskCenterActivity.this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",activityVOList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(activityVOList.get(postion).getActivityVO().getId(),1);
        new Thread(()->{
            OkHttpUtils.getDetail(idAndType);
        }).start();
        startActivity(intent);
    }
    private  void refreshActivityList(){
        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();list.add(conditionState.toString());map.put("conditionType",list);
        //创建请求
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/activity/list/1/20", map, 300);

        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MyCoHelp.getAppContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //转换数据
                Result<List<DetailResponse>> result = GSON.gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>() {}.getType());
                if(result!=null){
                    activityVOList = result.getData();
                }else{
                    activityVOList = new ArrayList<>();
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        cardViewListAdapter.setDetailResponseListList(activityVOList);
                        eRecyclerView.setLayoutManager(new LinearLayoutManager(TaskCenterActivity.this));
                        eRecyclerView.setAdapter(cardViewListAdapter);
                    }
                });

            }
        });
    }
    private  void  searchActivity(String key){
        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> keyStr = new ArrayList<>();keyStr.add(key);map.put("key",keyStr);
        List<String> types = new ArrayList<>();types.add("1");map.put("types",types);

        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/general/search/1/5", map, 300);
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MyCoHelp.getAppContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //转换数据
                Result<List<DetailResponse>> result = GSON.gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>() {}.getType());
                activityVOList = result.getData();
                runOnUiThread(new Runnable() {
                    public void run() {
                        cardViewListAdapter.setDetailResponseListList(activityVOList);
                        eRecyclerView.setAdapter(cardViewListAdapter);
                    }
                });
            }
        });
    }
    private void refreshActivityListData(){
        refreshActivityList();
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }


}