package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;
import com.cohelp.task_for_stu.utils.ACache;
import com.squareup.picasso.Picasso;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;

/**
 * 普通用户的基本信息展示页
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class BasicInfoActivity extends BaseActivity {
    NetRadiusImageView icon;
    TextView nickname;
    TextView team;
    RoundButton bt_logOut,bt_switch;
    LinearLayout Setting;
    LinearLayout Personal_homepage;
    LinearLayout myTask;
    LinearLayout myQuestion;
    LinearLayout myCollect;
    LinearLayout myRecord;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    LinearLayout HelpCenter;
    LinearLayout HoleCenter;
    LinearLayout ScoreList;
    com.cohelp.task_for_stu.net.model.entity.User transferUser;


    Intent intent;
    String userIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        initTools();
        initView();
        initEvent();
        setTitle("个人中心");
    }
    private void initView() {
        getUser();
        getUserIcon();

        icon = findViewById(R.id.id_iv_icon);
        nickname = findViewById(R.id.id_tv_nickname);
        team = findViewById(R.id.id_tv_userTeam);
        bt_logOut = findViewById(R.id.id_btn_logout);
        bt_switch = findViewById(R.id.id_btn_switch);
        myTask = findViewById(R.id.id_ll_myTask);
        myQuestion = findViewById(R.id.id_ll_myquestion);
        myCollect = findViewById(R.id.id_ll_myCollect);
        myRecord = findViewById(R.id.id_ll_myRecord);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        ScoreList = findViewById(R.id.id_ll_score);
        Setting = findViewById(R.id.id_ll_setting);
        Personal_homepage = findViewById(R.id.id_ll_personal_homepage);


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.tuku)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(MyCoHelp.getAppContext())
                .load(userIcon)
                .apply(options)
                .into(icon);
        nickname.setText(transferUser.getUserName());
        team.setText(transferUser.getTeamName());
        //Thread thread = new
//        Picasso.get()
//                .load(R.drawable.question)
//                .placeholder(R.drawable.pictures_no)
//                .transform(new CircleTransform())
//                .into(qs);
//        Picasso.get()
//                .load(R.drawable.task)
//                .placeholder(R.drawable.pictures_no)
//                .transform(new CircleTransform())
//                .into(tk);
//        startLoadingProgress();
//        updateUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUser();
    }

    private void updateUser() {
        startLoadingProgress();

        Picasso.get()
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(icon);
        nickname.setText(user.getUserName());
        team.setText(user.getId() + "");
    }

    private void initEvent() {


        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHelpCenterActivity();
            }
        });
        HoleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toHoleCenterActivity();}
        });
        TaskCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTaskCenterActivity();
            }
        });

        bt_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ACache cache = ACache.get(MyCoHelp.getAppContext(), "loginUser");
                cache.clear();
                OKHttp.cookieJar.getCookieStore().removeAll();
                toLoginActivity();
            }
        });

        myTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyTaskActivity();
            }
        });

        myCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMyCollectActivity();
            }
        });

        myQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyQuestionActivity();
            }
        });
        myRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyRecordActivity();
            }
        });
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSettingActivity();
            }
        });
        ScoreList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toScoreListActivity();
            }
        });

        Personal_homepage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) { toPersonalHomepageActivity();}
        });
//        Setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { toSettingActivity();}
//        });
//        Publish_manage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { toPublishManageActivity();}
//        });
//        Collect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {toMyCollectActivity();}
//        });
    }

    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }

    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }
    private void toHoleCenterActivity(){
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }

    private void toMyQuestionActivity() {
        Intent intent = new Intent(this,MyQuestionActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
    }

    private void toMyTaskActivity() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            intent = new Intent(this, MyTaskActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
    }

    private void toMyCollectActivity(){
        Intent intent = new Intent(this,MyCollectActivity.class);
        startActivity(intent);
    }
    private void toMyRecordActivity(){
        Intent intent = new Intent(this,MyRecordActivity.class);
        startActivity(intent);
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toSettingUserActivity() {
        Intent intent = new Intent(this,SettingUserActivity.class);
        startActivity(intent);
    }

    private void toSettingActivity(){
        Intent intent = new Intent(this,QuestionStoreActivity.class);
        startActivity(intent);
    }
    private void toScoreListActivity(){
        Intent intent = new Intent(this,ScoreActivity.class);
        startActivity(intent);
    }

    private void toPersonalHomepageActivity(){
        Intent intent = new Intent(this,PersonalHomepageActivity.class);
        startActivity(intent);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void initTools(){
        intent = getIntent();
    }
    private void getUser(){
        transferUser = user;
    }
    private void getUserIcon(){
        userIcon = user.getAvatarUrl();
    }
}