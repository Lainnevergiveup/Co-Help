package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;

public class SettingActivity extends BaseActivity {

    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();
        initEvent();
        title.setText("我的设置");
    }

    private void initEvent(){

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
    }




    private void initView() {
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter =findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        title = findViewById(R.id.tv_title);
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
    private void toHoleCenterActivity() {
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }






}