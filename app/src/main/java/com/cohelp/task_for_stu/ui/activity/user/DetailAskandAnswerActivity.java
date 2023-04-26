package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.vo.AnswerBankVO;
import com.cohelp.task_for_stu.net.model.vo.QuestionBankVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewAskAnswerListAdapter;
import com.google.gson.reflect.TypeToken;
import com.lzy.ninegrid.NineGridView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
@RequiresApi(api = Build.VERSION_CODES.O)
public class DetailAskandAnswerActivity extends BaseActivity {


    Intent intent;
    List<AnswerBankVO> answerBankVOList;
    QuestionBankVO questionBankVO;
    TextView content,hot,answer;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    NineGridView nineGridView;
    Integer tag = 1;
    CardViewAskAnswerListAdapter cardViewAskAnswerListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_askand_answer);
        initTools();
        initView();
        initEvent();
        setDetailData();
    }
    private void initTools(){
        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                questionBankVO = (QuestionBankVO) bundle.getSerializable("question");
            }
        }
    }


    private void setDetailData(){

        if (questionBankVO!=null){
            content.setText(questionBankVO.getContent());
        }
        getAnswerBank();

    }

    private void initView(){
        content = findViewById(R.id.content);
        answer = findViewById(R.id.tv_answer);
        nineGridView = findViewById(R.id.grid_item_image);
        linearLayout = findViewById(R.id.ll_answer);
        recyclerView = findViewById(R.id.id_recyclerview);
        cardViewAskAnswerListAdapter = new CardViewAskAnswerListAdapter();
    }

    private void initEvent(){
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag==1){
                    linearLayout.setVisibility(View.INVISIBLE);
                    tag = tag-1;
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                    tag = tag+1;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAnswerBank(){

        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/teach/listanswerbankbyquestionbankid/"+questionBankVO.getId(),  null, 300);
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
                Result<List<AnswerBankVO>> result = GSON.gson.fromJson(res, new TypeToken<Result<List<AnswerBankVO>>>(){}.getType());
                answerBankVOList =  result.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("anse"+answerBankVOList);
                        cardViewAskAnswerListAdapter.setAnswerVOList(answerBankVOList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DetailAskandAnswerActivity.this));
                        recyclerView.setAdapter(cardViewAskAnswerListAdapter);
                    }
                });

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}