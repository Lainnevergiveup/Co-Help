package com.cohelp.task_for_stu.ui.activity.user;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.adpter.MyTaskAdapter;
import com.cohelp.task_for_stu.ui.view.MyListViewForScrollView;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BlankFragment1 extends Fragment implements View.OnClickListener {


    private Context context;
    private View root;
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    Button btn_delete;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    List<DetailResponse> taskList;
    MyListViewForScrollView scrollView;
    private TextView mTvSwitch;
    MyTaskAdapter myTaskAdapter = new MyTaskAdapter();
    private void initTools(){
    }
    public BlankFragment1(Context context){
        this.context = context;
    }
    public BlankFragment1(){

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
        getTaskList();
        initEvent();

        return root;
    }

    private void initView(){
        flEdit = root.findViewById(R.id.fl_edit);
        scbSelectAll = root.findViewById(R.id.scb_select_all);
        recyclerView = root.findViewById(R.id.recyclerView);
        refreshLayout = root.findViewById(R.id.refreshLayout);
        btn_delete = root.findViewById(R.id.btn_delete);
        mTvSwitch = root.findViewById(R.id.id_tv_manager);
        scrollView = root.findViewById(R.id.lv_task_list);

    }

    private void initEvent(){

        myTaskAdapter = new MyTaskAdapter(getContext(),this,taskList);
        scrollView.setAdapter(myTaskAdapter);
        scrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toDetailActivity(position);
            }
        });

    }



    private synchronized void getTaskList(){
        Thread t1 = new Thread(()->{
            taskList = okHttpUtils.searchPublic();
            System.out.println(taskList);
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task_list111"+taskList);
    }

    private void toDetailActivity(int postion){
        Intent intent = new Intent(context,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",taskList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(taskList.get(postion).getActivityVO().getId(),1);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }
}
