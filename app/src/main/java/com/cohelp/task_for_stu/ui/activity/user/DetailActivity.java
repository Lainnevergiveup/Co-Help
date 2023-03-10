package com.cohelp.task_for_stu.ui.activity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.RemarkRequest;
import com.cohelp.task_for_stu.net.model.entity.Collect;
import com.cohelp.task_for_stu.net.model.entity.RemarkActivity;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.net.model.vo.RemarkVO;
import com.cohelp.task_for_stu.ui.adpter.CommentAdapter;
import com.cohelp.task_for_stu.ui.adpter.CommentExpandableListAdapter;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xuexiang.xui.widget.textview.ExpandableTextView;
import com.xuexiang.xutil.tip.ToastUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class DetailActivity extends AppCompatActivity {

    Button returnButton;
    Button reportButton;
    TextView topicTitle;
    TextView avatorName;
    TextView topicTime;
    TextView topicDetail;
    AvatorImageView avatorPic;
    ImageButton likeButton;
    ImageButton collectButton;
    ImageButton commentButton;
    EditText commentText;
    Button commentCommitButton;

    View view;
    View view2;
    RecyclerView commentRecycleView;
    ExpandableListView commentListView;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog bottomSheetDialog2;

    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetBehavior bottomSheetBehavior2;

    CommentAdapter commentAdapter;
    CommentExpandableListAdapter commentExpandableListAdapter;
    OkHttpUtils okHttpUtils;
    Intent intent;

    SwipeRefreshLayout eSwipeRefreshLayout;


    DetailResponse detail;
    List<RemarkVO> remarkList;
    List<RemarkVO> firstList;
    List<List<RemarkVO>> orderRemarkVO;
    IdAndType idAndType;

    Integer commentRootType = 1;
    Integer commentTargetID;
    Integer commentTopID;
    Integer commentDetailResopnseID;

    Integer detailType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initTools();
        initView();
        initData();
        initEvent();



        setTitle("????????????");
    }

    private void initTools(){
        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                detail = (DetailResponse) bundle.getSerializable("detailResponse");
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
        idAndType = new IdAndType(detail.getActivityVO().getId(),1);
    }

    private void initView(){
        view = LayoutInflater.from(this).inflate(R.layout.view_comment_bottomsheet, null, false);
//        view2 = LayoutInflater.from(this).inflate(R.layout.view_comment_bottomsheet, null, false);
//
//        reportButton = (Button)findViewById(R.id.button_MutRelease);
//        returnButton = (Button) findViewById(R.id.button_Cancel);
//
//        avatorPic = (AvatorImageView) findViewById(R.id.image_UserIcon);
//
        likeButton = (ImageButton) findViewById(R.id.imageButton_Like);
        collectButton = (ImageButton) findViewById(R.id.imageButton_Collect);
        commentButton =  findViewById(R.id.imageButton_Comment);


//
//        topicTitle = (TextView) findViewById(R.id.text_MessageTitle);
//        topicTime = (TextView) findViewById(R.id.text_TopicCreateTime);
//        topicDetail = (TextView) findViewById(R.id.text_TopicDetail);
//        avatorName = (TextView) findViewById(R.id.text_UserId);
//
//        commentRecycleView = (RecyclerView) view.findViewById(R.id.dialog_bottomsheet_rv_lists);
        commentText = view.findViewById(R.id.edit_comment);
        commentCommitButton = view.findViewById(R.id.btn_send_comment);
        commentListView = (ExpandableListView) view.findViewById(R.id.comment_item_list);
        bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialogStyle1);
//
//        bottomSheetDialog2 = new BottomSheetDialog(this,R.style.BottomSheetDialog);


        setBottomSheet();
//        initCommentRecycleView();
//        ExpandableTextView mExpandableTextView = findViewById(R.id.expand_text_view);
//        mExpandableTextView.setText("getString(R.string.etv_content_demo1daawsdd\n\n\nnu\n\nde\n\nre");
//        mExpandableTextView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
//            @Override
//            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
//                ToastUtils.toast(isExpanded ? "Expanded" : "Collapsed");
//            }
//        });

    }

    private void initData(){

        getComment();
        orderRemarkVO = orderRemarkVO(remarkList);
        firstList = new ArrayList<>();
        for (List<RemarkVO> voList:orderRemarkVO){
            RemarkVO vo = voList.get(0);
            firstList.add(vo);
            voList.remove(0);
        }
        detailType = detail.getType();

        if (detail.getIsLiked() == 0) {
            likeButton.setImageResource(R.drawable.icon_dianzan_undo);
        } else {
            likeButton.setImageResource(R.drawable.icon_dianzan_success);
        }

        if (detail.getIsCollected()==1){
            collectButton.setImageResource(R.drawable.icon_collect_success);
        }
        else {
            collectButton.setImageResource(R.drawable.icon_collect_undo);
        }

    }
    private void initEvent(){



//        returnButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        reportButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer type = detail.getType();
                new Thread(()->{
                    okHttpUtils.remark(type,detail.getIdByType(type));
                }).start();
                detail.setIsLiked(detail.getIsLiked()==1?0:1);
                if (detail.getIsLiked()==1){
                    likeButton.setImageResource(R.drawable.icon_dianzan_success);
                }
                else {
                    likeButton.setImageResource(R.drawable.icon_dianzan_undo);
                }
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(2);
                if (bottomSheetDialog!=null){
                    initCommentListView();
                    System.out.println("1"+remarkList);
                    bottomSheetDialog.show();
//                    bottomSheetDialog2.show();
                }
            }
        });
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collect collect = new Collect();
                Integer type = detail.getType();
                collect.setTopicId(detail.getIdByType(type));
                collect.setTopicType(type);
                detail.setIsCollected(detail.getIsCollected()==1?0:1);
                if (detail.getIsCollected()==1){
                    collectButton.setImageResource(R.drawable.icon_collect_success);
                }
                else {
                    collectButton.setImageResource(R.drawable.icon_collect_undo);
                }
                new Thread(()->{
                    okHttpUtils.insertCollection(collect);
                }).start();

            }

        });
        commentCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemarkActivity remarkActivity = remarkActivityBuilder();
                RemarkRequest remarkRequest = new RemarkRequest();
                remarkRequest.setRemarkActivity(remarkActivity);
                remarkRequest.setType(detail.getType());
                sendRemark(remarkRequest);
                commentText.setText("");
            }
        });
//        commentListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
//                commentTopID = firstList.get(groupPosition).getId();
//                commentTargetID = detail.getIdByType(detailType);
//
//
//                return true;
//            }
//        });
        commentListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                commentTopID = firstList.get(groupPosition).getId();
                commentTargetID = orderRemarkVO.get(groupPosition).get(childPosition).getId();
                commentRootType = 0;
                System.out.println(commentTopID);
                commentText.requestFocus();
                return true;
            }
        });
    }

    private void setBottomSheet(){
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.getWindow().setDimAmount(0f);
        bottomSheetDialog.setContentView(view);
        //????????????
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        //dialog?????????
        bottomSheetBehavior.setPeekHeight(getWindowHeight());
    }

    private void initCommentListView(){


        commentExpandableListAdapter = new CommentExpandableListAdapter(orderRemarkVO,firstList,this);
        commentListView.setAdapter(commentExpandableListAdapter);

    }
    /**
     * ????????????(?????????????????????????????????)
     *
     * @return
     */
    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        //????????????????????????????????????3/4
        return  heightPixels-heightPixels /4;
    }
    private synchronized void getComment(){

        try {
            Thread t1 = new Thread(()->{
            remarkList = okHttpUtils.getCommentList(idAndType);
                System.out.println(remarkList);
            });
            t1.start();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<List<RemarkVO>> orderRemarkVO(List<RemarkVO> data){
        if(data==null){
            return null;
        }
        System.out.println("length="+data.size());
        Stack<RemarkVO> stackA = new Stack<>();
        Stack<RemarkVO> stackB = new Stack<>();
        ArrayList<RemarkVO> topRemark = new ArrayList<>();
        List<List<RemarkVO>> arrayLists = new ArrayList<>();
        //?????????????????????
        Iterator<RemarkVO> iter = data.iterator();
        while(iter.hasNext()){
            RemarkVO remarkVO = iter.next();
            if(remarkVO.getTargetIsTopic().equals(1)){
                topRemark.add(remarkVO);
                iter.remove();
            }
        }
        //????????????????????????????????????????????????List
        for(RemarkVO remarkTop:topRemark){
            ArrayList<RemarkVO> remarkVOS = new ArrayList<>();
            stackA.push(remarkTop);
            while(!stackA.isEmpty()){
                RemarkVO peek = stackA.peek();
                Integer peekRemarkId = peek.getId();
                iter = data.iterator();
                while(iter.hasNext()){
                    RemarkVO remark = iter.next();
                    if(remark.getRemarkTargetId().equals(peekRemarkId)){
                        stackA.push(remark);
                        iter.remove();
                    }
                }
                if(stackA.peek().equals(peek)){
                    RemarkVO pop = stackA.pop();
                    //?????????????????????????????????
                    if (!stackA.isEmpty()){
                        pop.setRemarkTargetName(stackA.peek().getRemarkOwnerName());
                    }else {
                        pop.setRemarkTargetName(null);
                    }
                    stackB.push(pop);
                }
            }
            while(!stackB.isEmpty()){
                remarkVOS.add(stackB.pop());
            }
            arrayLists.add(remarkVOS);
        }




        return arrayLists;
    }
    private void changeLocalState(){
        List<DetailResponse> detailResponses = SessionUtils.getActivityPreference(DetailActivity.this);
    }
    private RemarkActivity remarkActivityBuilder(){
        RemarkActivity remarkActivity = new RemarkActivity();
        remarkActivity.setRemarkTime(new Date());
        remarkActivity.setRemarkContent(commentText.getText().toString());
        remarkActivity.setRemarkLike(0);
        remarkActivity.setTargetIsActivity(1);
        remarkActivity.setRemarkActivityId(detail.getActivityVO().getId());
        remarkActivity.setRemarkTargetId(detail.getActivityVO().getId());
        remarkActivity.setTopId(0);
        return remarkActivity;
    }
    private synchronized void sendRemark(RemarkRequest remarkRequest){
        Thread thread = new Thread(() -> {
            okHttpUtils.sendComment(remarkRequest);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}