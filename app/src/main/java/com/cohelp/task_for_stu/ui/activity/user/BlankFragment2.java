package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.ui.adpter.CardViewAskListAdapter;
import com.cohelp.task_for_stu.ui.view.MyListViewForScrollView;
import com.cohelp.task_for_stu.ui.view.MyRecyclerView;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.List;

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
    CardViewAskListAdapter myAskAdapter;
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR =3;



    private void initTools(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(getActivity()));
    }

    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    askList = (List<AskVO>) msg.obj;
                    myAskAdapter.setAskVOList(askList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(myAskAdapter);
                    System.out.println("ask_list"+askList);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(getContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(getContext(),"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.blankfragment2, container, false);
        }
        initTools();
        initView();
        initEvent();
        return root;
    }

    private void initView(){
        recyclerView = root.findViewById(R.id.recyclerView);

    }

    private void initEvent(){
        getAskList(id,semester);
        mRecyclerView = root.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myAskAdapter = new CardViewAskListAdapter(askList,getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(myAskAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
        @Override
        public boolean canScrollVertically() {
            return false;
        }
    };

    private  void getAskList(Integer id , String semester){
        Thread t1 = new Thread(()->{
            System.out.println("idgetasklist"+id);
            System.out.println("semester"+semester);
            askList = okHttpUtils.getAskList(id, semester);
            Message msg = Message.obtain();
            msg.obj = askList;
            msg.what = GET_DATA_SUCCESS;
            handler.sendMessage(msg);
        });
        System.out.println("ask_list"+askList);
        t1.start();

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
