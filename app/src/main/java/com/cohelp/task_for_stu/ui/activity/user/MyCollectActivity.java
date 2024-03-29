package com.cohelp.task_for_stu.ui.activity.user;

import static com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils.baseURL;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.cohelp.task_for_stu.net.model.vo.ResultVO;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.NewsListEditAdapter;
import com.cohelp.task_for_stu.ui.adpter.TaskAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MyCollectActivity extends BasicInfoActivity {

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
    TextView title;
    Integer tag = 1;

    CardViewListAdapter cardViewListAdapter;
    List<ResultVO> collectList;
    String delCollectList;
    OkHttpUtils okHttpUtils;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        setUpToolBar();
        initTools();
        initView();
        setTitle("");
    }

    private void initTools(){
//        intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");
        
    }
    private void initEvent(){

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
            getCollectList();
            mAdapter.refresh(collectList);
            refreshLayout.finishRefresh();
        }, 100));

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
        title = findViewById(R.id.tv_title);
        title.setText("我的收藏");
        mAdapter = new NewsListEditAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        },collectList);
        getCollectList();
        System.out.println("collect"+collectList);

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

    private void toTaskCenterActivity() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            intent = new Intent(this, TaskCenterActivity.class);
        }
        startActivity(intent);
        finish();
    }
    private void toDetailActivity(int postion){
        Intent intent = new Intent(MyCollectActivity.this,DetailActivity.class);
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

    private void toHelpCenterActivity() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            intent = new Intent(this, HelpCenterActivity.class);
        }
        startActivity(intent);
        finish();
    }
    private void toHoleCenterActivity() {
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private synchronized void getCollectList(){

        //创建请求
        Request request = OKHttp.buildGetRequest(baseURL+"/collect/getcollectlist/1/5", null, 300);
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
                Result<List<ResultVO>> result = GSON.gson.fromJson(res, new TypeToken<Result<List<ResultVO>>>() {}.getType());
                if(result!=null){
                    collectList = result.getData();
                }else{
                    collectList = new ArrayList<>();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        initEvent();
                    }
                });

            }
        });


    }
    private synchronized void delCollectList(){
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
        getCollectList();
//        cardViewListAdapter.setDetailResponseListList(collectList.stream().map(i->i.getDetailResponse()).collect(Collectors.toList()));
//        eRecyclerView.setAdapter(cardViewListAdapter);
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
        new MaterialDialog.Builder(MyCollectActivity.this)
                .content("是否确认删除")
                .positiveText(R.string.lab_yes)
                .negativeText(R.string.lab_no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        XToastUtils.toast("sssssss");
                        System.out.println("List"+mAdapter.getSelectedIndexList());
                        delCollectList();
                        getCollectList();
                        mAdapter.refresh(collectList);
                        refreshLayout.finishRefresh();

                        mAdapter.switchManageMode();
                        refreshManageMode();

                    }
                })
                .show();
    }
}