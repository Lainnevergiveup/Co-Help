package com.cohelp.task_for_stu.ui.activity.user;

import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.ui.adpter.MyAskAdapter;
import com.cohelp.task_for_stu.ui.view.MyListViewForScrollView;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


@RequiresApi(api = Build.VERSION_CODES.O)
public class BlankFragment4 extends Fragment implements View.OnClickListener{

    private Context context;
    private View root;


    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    Button btn_delete;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    Integer id;
    List<AskVO> askList;

    MyListViewForScrollView scrollView;
    private TextView mTvSwitch;
    MyAskAdapter myAskAdapter;
    private void initTools(){

    }
    public BlankFragment4(Context context, Integer id){
        this.context = context;
        this.id = id;
    }
    public BlankFragment4(){

    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.blankfragment1, container, false);
        }
        initTools();
        initView();
        initAskList(id);

        return root;
    }



    private void initView(){
        recyclerView = root.findViewById(R.id.recyclerview);
        scrollView = root.findViewById(R.id.lv_task_list);
    }

    private void initEvent(){
        myAskAdapter = new MyAskAdapter(getContext(),this,askList);
        scrollView.setAdapter(myAskAdapter);
        scrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toDetailAsk(position);
            }
        });

    }
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
        @Override
        public boolean canScrollVertically() {
            return false;
        }
    };

    private void initAskList(Integer id){

        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/course/list/ask/1/30/"+id+"/2022-2023-2/2" , null, 30);
        //执行请求
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
                Result<List<AskVO>> result = GSON.gson.fromJson(res, new TypeToken<Result<List<AskVO>>>(){}.getType());
                askList =  result.getData();
                runOnUiThread(new Runnable() {
                    public void run() {
                        initEvent();
                    }
                });
            }
        });
    }



    private void toDetailAsk(int postion){
        Intent intent = new Intent(context,DetailAskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",askList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(askList.get(postion).getId(),1);
        System.out.println("详情");
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }
}