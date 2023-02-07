package com.cohelp.task_for_stu.ui.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior bottomSheetBehavior;

    OkHttpUtils okHttpUtils;
    Intent intent;

    DetailResponse detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initTools();
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
    }

    private void initView(){
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


    }

    private void initEvent(){



    }


}