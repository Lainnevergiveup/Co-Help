package com.cohelp.task_for_stu.ui.activity.user;

import static com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils.baseURL;
import static com.cohelp.task_for_stu.net.gsonTools.GSON.gson;

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
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.vo.CourseVO;
import com.cohelp.task_for_stu.net.model.vo.QuestionBankVO;
import com.cohelp.task_for_stu.ui.adpter.NewsListEditQuestionAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.T;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class QuestionStoreActivity extends BasicInfoActivity {

    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;

    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;

    private TextView mTvSwitch;
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    private NewsListEditQuestionAdapter mAdapter;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    Button btn_delete;
    Button btn_publish;
    NiceSpinner niceSpinner;
    CourseVO item;
    List<QuestionBankVO> questionList;
    List<CourseVO> courseList;
    Integer currentCourse;
    String semeter = "";
    String delCollectList;
    OkHttpUtils okHttpUtils;
    Intent intent;
    TextView manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_store);
        setUpToolBar();
        initTools();
        initData();
        initView();
        initEvent();
        setTitle("");
    }

    private void initTools(){
//        intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");
        T.init(this);
    }
    private void initData(){
        getCourseList();
        getQuestionList();
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
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    toHelpCenterActivity();
                }
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
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPublishConfirmDialog();
            }
        });
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        scbSelectAll.setOnCheckedChangeListener((checkBox, isChecked) -> mAdapter.setSelectAll(isChecked));

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            getQuestionList();
            mAdapter.refresh(questionList);
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
        mAdapter.setOnItemLongClickListener((itemView, item, position) -> {
            if (!mAdapter.isManageMode()) {
                mAdapter.enterManageMode(position);
                refreshManageMode();
            }
        });

        niceSpinner.setBackgroundResource(R.drawable.shape_for_custom_spinner);
        niceSpinner.setTextColor(0xFFFFFFFF);
        niceSpinner.setArrowTintColor(0xFFFFFFFF);
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                item = (CourseVO) niceSpinner.getItemAtPosition(position);
                questionList.clear();
                refreshQuestionListData();
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
        btn_publish = findViewById(R.id.btn_publish);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        niceSpinner.attachDataSource(courseList);
        niceSpinner.setBackgroundResource(R.drawable.shape_for_custom_spinner);
        manager = findViewById(R.id.id_tv_manager);
        manager.setText("管理");
        mAdapter = new NewsListEditQuestionAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        },questionList);
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
        Intent intent = new Intent(this,QuestionStoreActivity.class);
        startActivity(intent);
        finish();
    }
    private void toDetailActivity(int postion){
        intent = new Intent(QuestionStoreActivity.this,DetailAskandAnswerActivity.class);
        Bundle bundle = new Bundle();
        QuestionBankVO questionBankVO = questionList.get(postion);
        bundle.putSerializable("question",questionBankVO);
        intent.putExtras(bundle);

//        new Thread(()->{
//            System.out.println(okHttpUtils.getDetail(idAndType));
//        }).start();
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
    private synchronized void getQuestionList(){
        Thread t1 = new Thread(()->{
            if (item!=null){
                questionList = okHttpUtils.getTeacherQuestionList(1,20,item.getId());
//                System.out.println("1"+questionList);
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void delQuestionList(){
        List<QuestionBankVO> selectedQuestionList = mAdapter.getSelectedQuestionList();
        for (QuestionBankVO q : selectedQuestionList){
            deleteQuestion(q.getId());
            questionList.remove(q);
        }
        mAdapter.refresh(questionList);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private synchronized void refreshQuestionListData(){
        getQuestionList();
        mAdapter.refresh(questionList);
//        cardViewListAdapter.setDetailResponseListList(collectList.stream().map(i->i.getDetailResponse()).collect(Collectors.toList()));
//        eRecyclerView.setAdapter(cardViewListAdapter);
//        eSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //关闭刷新
//                eSwipeRefreshLayout.setRefreshing(false);
//            }
//        },1000);
    }

    /**
     * 简单的确认对话框
     */
    private void showSimpleConfirmDialog() {
        new MaterialDialog.Builder(QuestionStoreActivity.this)
                .content("是否确认删除")
                .positiveText(R.string.lab_yes)
                .negativeText(R.string.lab_no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        XToastUtils.toast("sssssss");
//                        System.out.println("List"+mAdapter.getSelectedIndexList());
                        delQuestionList();
//                        getQuestionList();
                        mAdapter.refresh(questionList);
                        refreshLayout.finishRefresh();

                        mAdapter.switchManageMode();
                        refreshManageMode();

                    }
                })
                .show();
    }

    private synchronized void getCourseList(){
        Thread thread = new Thread(() -> {
            courseList = okHttpUtils.getTeacherCourseList();
            System.out.println("2"+courseList);
            item = courseList==null||courseList.isEmpty()?null:courseList.get(0);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void deleteQuestion(Integer id){
        Request request = OKHttp.buildGetRequest(baseURL + "/teach/removequestion/" + id, null, 0);
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }
    private void publishQuestion(List<Integer> ids){
        String s = "";
        Integer integer = ids.get(0);
        s+=String.valueOf(integer);
        ids.remove(integer);
        for (Integer i : ids){
            s =s+ ","+String.valueOf(i);
        }
        System.out.println(s);
        Request request = OKHttp.buildGetRequest(baseURL + "/teach/publishquestion/" + s, null, 0);
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
                T.showToast(result.getMessage());
            }
        });
    }
    private void doPublishQuestion(){
        List<QuestionBankVO> selectedQuestionList = mAdapter.getSelectedQuestionList();
        List<Integer> ids = new ArrayList<>();
//        Iterator<QuestionBankVO> iterator = selectedQuestionList.iterator();
//        String s = "";
//        while (iterator.hasNext()){
//            s+=iterator.
//        }
        for (QuestionBankVO questionBankVO:selectedQuestionList){
            ids.add(questionBankVO.getId());
        }
        publishQuestion(ids);
    }
    private void showPublishConfirmDialog(){
        new MaterialDialog.Builder(QuestionStoreActivity.this)
                .content("是否确认发布")
                .positiveText(R.string.lab_yes)
                .negativeText(R.string.lab_no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        XToastUtils.toast("sssssss");
//                        System.out.println("List"+mAdapter.getSelectedIndexList());
                        doPublishQuestion();
//                        getQuestionList();
//                        mAdapter.refresh(questionList);
//                        refreshLayout.finishRefresh();

                        mAdapter.switchManageMode();
                        refreshManageMode();

                    }
                })
                .show();
    }
}
