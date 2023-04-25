package com.cohelp.task_for_stu.ui.activity.user;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.vo.CourseVO;
import com.cohelp.task_for_stu.net.model.vo.ScoreVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewScoreListAdapter;
import com.google.gson.reflect.TypeToken;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScoreActivity extends BaseActivity {

    List<CourseVO> courseList;
    List<ScoreVO> scoreVOList;
    OkHttpUtils okHttpUtils;
    CourseVO item;
    RecyclerView recyclerView;
    NiceSpinner niceSpinner;
    TextView textView;
    Toolbar toolbar;
    CardViewScoreListAdapter cardViewScoreListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getCourseList();
        setUpToolBar();
        initView();
        initEvent();

    }
    protected void setUpToolBar() {
        toolbar = findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );
    }
    private void initView(){
        toolbar = findViewById(R.id.id_toolbar);
        recyclerView =  findViewById(R.id.id_recyclerview);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        niceSpinner.attachDataSource(courseList);
        niceSpinner.setBackgroundResource(R.drawable.shape_for_custom_spinner);
        cardViewScoreListAdapter = new CardViewScoreListAdapter();
        textView = findViewById(R.id.id_tv_manager);
        textView.setVisibility(View.GONE);
        getScorelist(1);
    }

    private void initEvent(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );

        niceSpinner.setTextColor(0xFFFFFFFF);
        niceSpinner.setArrowTintColor(0xFFFFFFFF);
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                item = (CourseVO) niceSpinner.getItemAtPosition(position);
//                scoreVOList.clear();
                getScorelist(item.getId());
            }
        });

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

    public void getScorelist(Integer courseId){
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/teach/listscore/" + courseId, null, 300);
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
                Result<List<ScoreVO>> result = GSON.gson.fromJson(res, new TypeToken<Result<List<ScoreVO>>>(){}.getType());
                scoreVOList = result.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在这里操作UI
                        cardViewScoreListAdapter.setScoreVOList(scoreVOList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ScoreActivity.this));
                        recyclerView.setAdapter(cardViewScoreListAdapter);
                        System.out.println("scorelist"+scoreVOList);
                    }
                });
            }
        });
    }
}