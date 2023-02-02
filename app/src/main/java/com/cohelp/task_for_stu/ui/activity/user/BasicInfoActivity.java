package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.listener.ClickListener;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.T;
import com.squareup.picasso.Picasso;

/**
 * 普通用户的基本信息展示页
 */
public class BasicInfoActivity extends BaseActivity {
    ImageView icon;
    TextView nickname;
    TextView grade;
    TextView logOut;
    TextView Browsing_history;
    TextView Personal_homepage;
    TextView Setting;
    TextView Publish_manage;
    TextView Collect;
    ImageView myTask;
    ImageView myQuestion;
    ImageView myCollect;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    LinearLayout HelpCenter;
    LinearLayout HoleCenter;
    User user;

    com.cohelp.task_for_stu.net.model.entity.User transferUser;
    UserBiz userBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        initView();
        initEvent();
        setTitle("个人中心");
    }
    private void initView() {
        icon = findViewById(R.id.id_iv_icon);
        nickname = findViewById(R.id.id_tv_nickname);
        grade = findViewById(R.id.id_tv_grade);
        logOut = findViewById(R.id.id_tv_logout);
        myTask = findViewById(R.id.id_mytask);
        myQuestion = findViewById(R.id.id_myQuestion);
        myCollect = findViewById(R.id.id_mycollect);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);

        Browsing_history = findViewById(R.id.id_tv_browsing_history);
        Personal_homepage = findViewById(R.id.id_tv_personal_homepage);
        userBiz = new UserBiz();
        Intent intent = getIntent();

        transferUser = (com.cohelp.task_for_stu.net.model.entity.User) intent.getSerializableExtra("user");
        nickname.setText(transferUser.getUserAccount());

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
        userBiz.userGet(UserInfoHolder.getInstance().geteUser().getId(), new CommonCallback<User>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
            }
            @Override
            public void onSuccess(User response) {
                stopLoadingProgress();
                UserInfoHolder.getInstance().setUser(response);
                user = response;
                Picasso.get()
                        .load(Config.rsUrl + user.getIcon())
                        .placeholder(R.drawable.pictures_no)
                        .transform(new CircleTransform())
                        .into(icon);
                nickname.setText(user.getNickName());
                grade.setText(user.getGrade() + "");
                T.showToast("用户数据更新完成！");
            }
        });
    }

    private void initEvent() {

        setToolbar(R.drawable.setting, new ClickListener() {
            @Override
            public void click() {
                toSettingUserActivity();
            }
        });

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

        UserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserCenterActivity();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginActivity();
            }
        });

        myTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyTaskActivity();
            }
        });

        myQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyQuestionActivity();
            }
        });
        Browsing_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHistoryActivity();
            }
        });
        Personal_homepage.setOnClickListener(new View.OnClickListener() {
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

    private void toUserCenterActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        finish();
    }

    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }
    private void toHoleCenterActivity(){
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toMyQuestionActivity() {
        Intent intent = new Intent(this,MyQuestionActivity.class);
        startActivity(intent);
    }

    private void toMyTaskActivity() {
        Intent intent = new Intent(this,MyTaskActivity.class);
        startActivity(intent);
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toSettingUserActivity() {
        Intent intent = new Intent(this,SettingUserActivity.class);
        startActivityForResult(intent,1001);
    }

    private void toHistoryActivity(){
        Intent intent = new Intent(this,HistoryActivity.class);
        startActivity(intent);
    }

    private void toPersonalHomepageActivity(){
        Intent intent = new Intent(this,PersonalHomepageActivity.class);
        startActivity(intent);
    }

    private void toSettingActivity(){
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

    private void toPublishManageActivity(){
        Intent intent = new Intent(this,PublishManageActivity.class);
        startActivity(intent);
    }

    private void toMyCollectActivity(){
        Intent intent = new Intent(this,MyCollectActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userBiz.onDestory();
    }
}