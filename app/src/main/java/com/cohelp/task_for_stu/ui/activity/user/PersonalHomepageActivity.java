package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.entity.Team;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;

import java.io.IOException;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PersonalHomepageActivity extends BaseActivity {

    LinearLayout HoleCenter;
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;
    LinearLayout teamChangeTerminalLayout;
    TextView teamChangeTerminal;
    TextView tv_sex,tv_team,tv_account;
    EditText et_nick,et_age,et_tel;
    RoundButton btn_save;
    NetRadiusImageView iv_icon;
    String[] s_sex = new String[2];
    String[] s_team = new String[20];
    List<Team> teamList;
    User user;
    Intent intent;
    String userIcon;
    String teamChangeState;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    int sexSelectOption = 0;
    int teamSelectOption = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_homepage);

        setUpToolBar();
        initTools();
        initView();
        initEvent();
        setTitle("");
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
                String age_st = et_age.getText().toString();

                String tel = et_tel.getText().toString();
                String sex_st = tv_sex.getText().toString();
                Integer sex;
                Integer age=0;
                String team = tv_team.getText().toString();
                System.out.println(age_st + tel + team);

                if(age_st.length()>2){
                    Toast.makeText(PersonalHomepageActivity.this,"您输入的年龄有误！",Toast.LENGTH_LONG).show();
                }else {
                     age = Integer.parseInt(age_st);
                }
                if(tel.length() != 11 ){
                    Toast.makeText(PersonalHomepageActivity.this,"您输入的电话有误！",Toast.LENGTH_LONG).show();
                }
                if(sex_st=="男"){
                    sex = 0;
                }else{
                    sex = 1;
                }
                user.setUserName(nick);
                user.setSex(sex);
                user.setAge(age);
                user.setPhoneNumber(tel);
//                user.setTeamName(team);
                new Thread(()->{
                    try {
                        okHttpUtils.changeUserInfo(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                if (teamSelectOption!=user.getTeamId()){
                    new  Thread(()->{
                        okHttpUtils.changeTeam(teamSelectOption);
                    }).start();
                    //显示确认框


                }

                toUserCenterActivity();


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

        getUser();
        getUserIcon();
        getTeamList();
        getTeamChangeState();
        btn_save = findViewById(R.id.id_btn_save);
        iv_icon = findViewById(R.id.id_iv_icon);
        tv_account = findViewById(R.id.id_tv_account);
        tv_sex = findViewById(R.id.id_tv_sex);
        tv_team = findViewById(R.id.id_tv_team);
        et_age = findViewById(R.id.id_et_age);
        et_nick = findViewById(R.id.id_et_nickname);
        et_tel = findViewById(R.id.id_et_tel);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
//
//        LinearLayout linearLayout = findViewById(R.id.test_hideLayout);
//        linearLayout.setVisibility(View.GONE);
        teamChangeTerminalLayout = findViewById(R.id.id_ll_teamChangeTerminal);
        teamChangeTerminal = findViewById(R.id.id_tv_teamTerminal);
        System.out.println("1="+teamChangeState);

        refreshUser();

    }

    private void showSexPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (v, options1, options2, options3) -> {
            tv_sex.setText(s_sex[options1]);
            sexSelectOption = options1;
//            tv_sex.setText(teamList.get(options1).getTeamName());
//            sexSelectOption = teamList.get(options1).getId();
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
//            tv_team.setText(s_team[options1]);
//            teamSelectOption = options1;
            tv_team.setText(teamList.get(options1).getTeamName());
            teamSelectOption = teamList.get(options1).getId();
//            System.out.println(teamList.get(options1).getTeamName());
//            System.out.println(teamList.get(options1).getId());
            return false;
        })
                .setTitleText("组织选择")
                .setSelectOptions(teamSelectOption)
                .build();
        pvOptions.setPicker(teamList);
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

    private void initTools(){
        intent = getIntent();

        
    }

    private synchronized void getUser(){
        Thread t1 = new Thread(()->{
            user=OkHttpUtils.getUser();
            System.out.println(user);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void getUserIcon(){
        Thread t1 = new Thread(()->{
            userIcon = okHttpUtils.getImageById(user.getAvatar());
            System.out.println(userIcon);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void  refreshUser(){

        tv_account.setText(user.getUserAccount());
        et_nick.setText(user.getUserName());
        if(user.getSex()==0){
            tv_sex.setText("男");
        }else
        {
            tv_sex.setText("女");
        }
//        tv_sex.setText(user.getSex());
        System.out.println(user.getAge());
        et_age.setText(user.getAge().toString());
        et_tel.setText(user.getPhoneNumber());
        tv_team.setText(user.getTeamName());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.tuku)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(MyCoHelp.getAppContext())
                .load(userIcon)
                .apply(options)
                .into(iv_icon);



        if (teamChangeState==null||teamChangeState.isEmpty()||teamChangeState.equals(user.getTeamName())){
            teamChangeTerminalLayout.setVisibility(View.GONE);
        }
        else {
            teamChangeTerminal.setText(user.getTeamName()+"->"+teamChangeState+"(审核中)");
        }
        System.out.println(user.getTeamName());
    }

    private synchronized void getTeamList(){
        Thread t1 = new Thread(()->{
            teamList = okHttpUtils.searchTeam("");
            System.out.println(teamList);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private synchronized void getTeamChangeState(){
        Thread t1 = new Thread(()->{
            teamChangeState = okHttpUtils.getTeamChangeState();
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}