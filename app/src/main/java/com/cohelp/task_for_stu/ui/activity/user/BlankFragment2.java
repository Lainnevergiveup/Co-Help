package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Context;
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
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.ui.adpter.MyAskAdapter;
import com.cohelp.task_for_stu.ui.view.MyListViewForScrollView;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BlankFragment2 extends Fragment implements View.OnClickListener{
    private Context context;
    private View root;




    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    FrameLayout flEdit;
    SmoothCheckBox scbSelectAll;
    Button btn_delete;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    List<AskVO> askList;
    Integer id;
    String semester;
    MyListViewForScrollView scrollView;
    private TextView mTvSwitch;
    MyAskAdapter myTaskAdapter = new MyAskAdapter();
    private void initTools(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(getActivity()));
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
        getAskList(id,semester);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.blankfragment1, container, false);
        }
        initTools();
        initView();

        initEvent();

        return root;
    }

    private void initView(){
        recyclerView = root.findViewById(R.id.recyclerView);
        refreshLayout = root.findViewById(R.id.refreshLayout);
        btn_delete = root.findViewById(R.id.btn_delete);
        mTvSwitch = root.findViewById(R.id.id_tv_manager);
        scrollView = root.findViewById(R.id.lv_task_list);

    }

    private void initEvent(){
        myTaskAdapter = new MyAskAdapter(getContext(),this,askList);
        scrollView.setAdapter(myTaskAdapter);
        scrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                toDetailActivity(position);
            }
        });
    }
    private synchronized void getAskList(Integer id , String semester){
        Thread t1 = new Thread(()->{
            askList = okHttpUtils.getAskList(id, semester);
        });
        System.out.println("ask_list"+askList);
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    private void toDetailActivity(int postion){
//        Intent intent = new Intent(HoleCenterActivity.this,DetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("detailResponse",holeList.get(postion));
//        intent.putExtras(bundle);
//        IdAndType idAndType = new IdAndType(holeList.get(postion).getIdByType(holeList.get(postion).getType()),1);
//        new Thread(()->{
//            System.out.println(okHttpUtils.getDetail(idAndType));
//        }).start();
//        startActivity(intent);
//    }
}
