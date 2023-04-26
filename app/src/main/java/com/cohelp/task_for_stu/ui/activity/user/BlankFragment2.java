package com.cohelp.task_for_stu.ui.activity.user;

import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.cohelp.task_for_stu.ui.view.MyRecyclerView;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
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
public class BlankFragment2 extends Fragment implements View.OnClickListener{
    private Context context;
    private View root;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    SmartRefreshLayout refreshLayout;
    MyRecyclerView recyclerView;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    Button btn_delete;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    List<AskVO> askList;
    Integer id;
    String semester;
    MyListViewForScrollView scrollView;
    private TextView mTvSwitch;
    MyAskAdapter myAskAdapter;
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR =3;
    SwipeRefreshLayout swipeRefreshLayout;


    private void initTools(){
    }

    public BlankFragment2(Context context ,Integer id , String semester){
        this.context = context;
        this.id = id;
        this.semester = semester;
    }
    public BlankFragment2(){

    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public static BlankFragment2 newInstance(String tabname) {

        Bundle args = new Bundle();

        BlankFragment2 fragment = new BlankFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.blankfragment1, container, false);
        }
        initTools();
        initView();
        initAskList(id,semester);
        return root;
    }

    private void initView(){
        recyclerView = root.findViewById(R.id.recyclerview);
        swipeRefreshLayout = root.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setMode(SwipeRefresh.Mode.PULL_FROM_START);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("讨论界面");

                initAskList(id,semester);
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //关闭刷新
                        swipeRefreshLayout.setRefreshing(false);
                        System.out.println("刷新中");
                    }
                },1000);
            }
        });


    }
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
        @Override
        public boolean canScrollVertically() {
            return false;
        }
    };

    private void initAskList(Integer id , String semester){

        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/course/list/ask/1/10/" + id + "/" + semester + "/2", null, 30);
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
                        System.out.println("讨论1111");
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
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }
}
