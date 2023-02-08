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
import android.widget.ImageButton;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.entity.Collect;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.net.model.vo.RemarkVO;
import com.cohelp.task_for_stu.ui.adpter.CommentAdapter;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

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

    View view;
    View view2;
    RecyclerView commentRecycleView;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog bottomSheetDialog2;

    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetBehavior bottomSheetBehavior2;

    CommentAdapter commentAdapter;

    OkHttpUtils okHttpUtils;
    Intent intent;

    DetailResponse detail;
    List<RemarkVO> remarkList;
    IdAndType idAndType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initTools();
        getComment();
        initView();
        initEvent();

        setTitle("话题详情");
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
        view2 = LayoutInflater.from(this).inflate(R.layout.view_comment_bottomsheet, null, false);

        reportButton = (Button)findViewById(R.id.button_MutRelease);
        returnButton = (Button) findViewById(R.id.button_Cancel);

        avatorPic = (AvatorImageView) findViewById(R.id.image_UserIcon);

        likeButton = (ImageButton) findViewById(R.id.imageButton_Like);
        collectButton = (ImageButton) findViewById(R.id.imageButton_Collect);
        commentButton = (ImageButton) findViewById(R.id.imageButton_Comment);

        topicTitle = (TextView) findViewById(R.id.text_MessageTitle);
        topicTime = (TextView) findViewById(R.id.text_TopicCreateTime);
        topicDetail = (TextView) findViewById(R.id.text_TopicDetail);
        avatorName = (TextView) findViewById(R.id.text_UserId);

        commentRecycleView = (RecyclerView) view.findViewById(R.id.dialog_bottomsheet_rv_lists);
        bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialogStyle1);

        bottomSheetDialog2 = new BottomSheetDialog(this,R.style.BottomSheetDialog);

        setBottomSheet();
        initCommentRecycleView();


    }

    private void initEvent(){



        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(()->{
                    okHttpUtils.remark(1,detail.getActivityVO().getId());
                }).start();
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog!=null){
                    System.out.println(remarkList);
                    bottomSheetDialog.show();
                    bottomSheetDialog2.show();
                }
            }
        });
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collect collect = new Collect();
                collect.setTopicId(detail.getActivityVO().getId());
                collect.setTopicType(1);
                new Thread(()->{
                    okHttpUtils.insertCollection(collect);
                }).start();
            }
        });
    }

    private void setBottomSheet(){
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.getWindow().setDimAmount(0f);
        bottomSheetDialog.setContentView(view);
        //用户行为
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        //dialog的高度
        bottomSheetBehavior.setPeekHeight(getWindowHeight());

        bottomSheetDialog2.setCanceledOnTouchOutside(true);
        bottomSheetDialog2.getWindow().setDimAmount(0f);
        bottomSheetDialog2.setContentView(view2);
        //用户行为
        bottomSheetBehavior2 = BottomSheetBehavior.from((View) view2.getParent());
        //dialog的高度
        bottomSheetBehavior2.setPeekHeight(getWindowHeight()/2);
    }
    private void initCommentRecycleView(){

        commentRecycleView.setHasFixedSize(true);
        commentRecycleView.setLayoutManager(new LinearLayoutManager(this));
        commentRecycleView.setItemAnimator(new DefaultItemAnimator());
        if (commentAdapter==null){
            commentAdapter = new CommentAdapter(remarkList);
        }
        else{
            commentAdapter.setCommentList(remarkList);
        }
        commentRecycleView.setAdapter(commentAdapter);
    }
    /**
     * 计算高度(初始化可以设置默认高度)
     *
     * @return
     */
    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return heightPixels - heightPixels / 9;
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
}