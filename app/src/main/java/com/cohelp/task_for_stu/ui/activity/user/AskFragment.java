package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.ui.adpter.CardViewAskListAdapter;
import com.cohelp.task_for_stu.utils.SessionUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class AskFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Integer id;
    private String semester;
    private View root;
    private Context context;
    List<AskVO> askList;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    CardViewAskListAdapter cardViewAskListAdapter;
    RecyclerView recyclerView;
    private void initTools(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(getActivity()));
    }
    public AskFragment() {
        // Required empty public constructor
    }
    public AskFragment(Context context) {
        this.context = context;
    }
    public AskFragment(Integer id,String semester){
        this.id = id;
        this.semester = semester;
    }


    public static AskFragment newInstance(String param1, String param2) {
        AskFragment fragment = new AskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.fragment_ask, container, false);
        }
        initView();
        initEvent();
        getAskList(this.id,this.semester);
        return root;

    }

    private void initView(){
        recyclerView = root.findViewById(R.id.rv_aks);

    }

    private void initEvent(){

      cardViewAskListAdapter = new CardViewAskListAdapter(askList,getContext(),this);
      recyclerView.setAdapter(cardViewAskListAdapter);
    }


    private synchronized void getAskList(Integer id , String semester){
        Thread t1 = new Thread(()->{
            askList = okHttpUtils.getAskList(id, semester);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}