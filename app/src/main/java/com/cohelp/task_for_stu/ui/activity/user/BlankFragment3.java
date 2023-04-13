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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.adpter.MyTaskAdapter;
import com.cohelp.task_for_stu.ui.view.MyListViewForScrollView;
import com.cohelp.task_for_stu.ui.view.MyRecyclerView;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.O)
public class BlankFragment3 extends Fragment implements View.OnClickListener{
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
    List<DetailResponse> helplist;
    String tag;
    String semester;
    MyListViewForScrollView scrollView;
    private TextView mTvSwitch;
    MyTaskAdapter myTaskAdapter;
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR =3;



    private void initTools(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(getActivity()));
    }

//    private Handler handler = new Handler() {
//        @SuppressLint("HandlerLeak")
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case GET_DATA_SUCCESS:
//                    helplist = (List<AskVO>) msg.obj;
////                    myAskAdapter.setAskVOList(helplist);
//                    mRecyclerView.setLayoutManager(mLayoutManager);
//                    mRecyclerView.setAdapter(myAskAdapter);
//                    System.out.println("ask_list"+helplist);
//                    break;
//                case NETWORK_ERROR:
//                    Toast.makeText(getContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
//                    break;
//                case SERVER_ERROR:
//                    Toast.makeText(getContext(),"服务器发生错误",Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };

    public BlankFragment3(Context context , String tag){
        this.context = context;
        this.tag = tag;
    }
    public BlankFragment3(){

    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public static BlankFragment3 newInstance(String tabname) {

        Bundle args = new Bundle();

        BlankFragment3 fragment = new BlankFragment3();
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
        try {
            initEvent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void initView(){
        recyclerView = root.findViewById(R.id.recyclerview);
        scrollView = root.findViewById(R.id.lv_task_list);
    }

    private void initEvent() throws InterruptedException {
        gethelplist(tag);
        myTaskAdapter= new MyTaskAdapter(getContext(),this,helplist);
        scrollView.setAdapter(myTaskAdapter);
        scrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toDetailHelp(position);
            }
        });

    }
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
        @Override
        public boolean canScrollVertically() {
            return false;
        }
    };

    private synchronized void gethelplist(String tag) throws InterruptedException {
        Thread t1 = new Thread(()->{

            System.out.println("TAG"+tag);
            helplist = okHttpUtils.helpListByTag(tag);
//            Message msg = Message.obtain();
//            msg.obj = helplist;
//            msg.what = GET_DATA_SUCCESS;
//            handler.sendMessage(msg);
        });
        System.out.println("ask_list"+helplist);
        t1.start();
        t1.join();

    }

    private void toDetailHelp(int postion){
        Intent intent = new Intent(context,DetailAskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",helplist.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(helplist.get(postion).getType(),1);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }

}