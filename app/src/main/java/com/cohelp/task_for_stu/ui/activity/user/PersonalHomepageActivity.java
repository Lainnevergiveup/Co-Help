package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

public class PersonalHomepageActivity extends BaseActivity {

    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    TextView tv_sex,tv_team;
    EditText et_nick,et_age,et_tel;
    RoundButton btn_save;
    String[] s_sex = new String[2];
    String[] s_team = new String[20];
    User user;
    int sexSelectOption = 0;
    int teamSelectOption = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_homepage);
        setUpToolBar();
        initView();
        initEvent();
        setTitle("个人主页");
    }


    private void initEvent(){

        tv_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexPickerView();
            }
        });

        tv_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTeamPickerView();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = et_nick.getText().toString();
                String age = et_age.getText().toString();
                String tel = et_tel.getText().toString();
                String sex = tv_sex.getText().toString();
                String team = tv_team.getText().toString();
//                user = new User("","",nick,)
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
    }




    private void initView() {
        s_sex[0] = "男";
        s_sex[1] = "女";
        s_team[0] = "上理";
        s_team[1] = "华理";

        btn_save = findViewById(R.id.id_btn_save);
        tv_sex = findViewById(R.id.id_tv_sex);
        tv_team = findViewById(R.id.id_tv_team);
        et_age = findViewById(R.id.id_et_age);
        et_nick = findViewById(R.id.id_et_nickname);
        et_tel = findViewById(R.id.id_et_tel);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);

    }

    private void showSexPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (v, options1, options2, options3) -> {
            tv_sex.setText(s_sex[options1]);
            sexSelectOption = options1;
            return false;
        })
                .setTitleText("性别选择")
                .setSelectOptions(sexSelectOption)
                .build();
        pvOptions.setPicker(s_sex);
        pvOptions.show();
    }

    private void showTeamPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (v, options1, options2, options3) -> {
            tv_team.setText(s_team[options1]);
            teamSelectOption = options1;
            return false;
        })
                .setTitleText("组织选择")
                .setSelectOptions(teamSelectOption)
                .build();
        pvOptions.setPicker(s_team);
        pvOptions.show();
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