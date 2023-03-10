package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.UserInfoHolder;
import com.cohelp.task_for_stu.bean.Comment;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.T;
import com.leon.lfilepickerlibrary.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.utils.L;

import java.util.Calendar;

public class TaskDetailActivity extends BaseActivity {
    private static final String KEY_TASK = "TASK";
    LinearLayout questionCenter;
    LinearLayout TaskCenter;
    LinearLayout UserCenter;

    TextView content;
    TextView nickname;
    TextView postTime;
    TextView timeLimit;
    TextView state;
    TextView loadMore;


    Button fun;
    ImageView icon;

    Task task;
    TaskBiz taskBiz;
    UserBiz userBiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        setUpToolBar();
        Intent intent = getIntent();
        if(intent != null){
            task = (Task) intent.getSerializableExtra(KEY_TASK);
            setTitle(task.getContext().getTitle());
        }
        if(task == null){
            T.showToast("??????????????????");
            finish();
            return;
        }
        initView();
        initEvent();
    }

    private void initEvent() {
        //???????????????
        fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("??????????????????????????????^^");

                if(UserInfoHolder.getInstance().geteUser().getId().equals(task.getPostedUser().getId())){
//                    if(!task.getContext().getCurrentState().equals(Config.TASK_DOING_SOLVE)){
//                        T.showToast("?????????????????????????????????????????????????????????~");
//                        return;
//                    }
                    //?????????????????????????????????
                    LinearLayout layout = new LinearLayout(builder.getContext());
                    LinearLayout layout1 = new LinearLayout(builder.getContext());
                    EditText editText = new EditText(builder.getContext());
                    TextView textView = new TextView(builder.getContext());
                    EditText editText1 = new EditText(builder.getContext());
                    //????????????
                    editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    //?????????
                    editText.setMaxLines(3);
                    editText.setHint("?????????????????????");
                    textView.setText("????????????????????????????????????^^");
                    layout.setVerticalGravity(Gravity.CENTER);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout1.setOrientation(LinearLayout.HORIZONTAL);
                    layout1.addView(textView);
                    layout1.addView(editText1);
                    layout.addView(editText);
                    layout.addView(layout1);
                    builder.setView(layout);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String s = editText1.getText().toString();
                            if(!StringUtils.isEmpty(s)){
                                Integer exReward = Integer.parseInt(s);
                                int finalReward = task.getPostedUser().getGrade() - exReward;
                                if(finalReward >= 0){
                                    User user = UserInfoHolder.getInstance().geteUser();
                                    user.setGrade(finalReward);
                                    task.getContext().setReward(task.getContext().getReward() + exReward);
                                    //??????????????????
                                    userBiz.updateUser(user, new CommonCallback<User>() {
                                        @Override
                                        public void onError(Exception e) {
                                            L.e(e.getMessage());
                                        }
                                        @Override
                                        public void onSuccess(User response) {
                                        }
                                    });
                                }else{
                                    T.showToast("???????????????^^");
                                }
                            }
                            String s1 = editText.getText().toString();
                            if(StringUtils.isEmpty(s1)){
                                T.showToast("???????????????????????????^^");
                                return;
                            }else{
                                Comment comment = new Comment();
                                comment.setContentDesc(s1);
                                comment.setPostDate(Calendar.getInstance().getTime());
                                comment.setPid(task.getDoneUser().getId());
                                comment.setDid(task.getPostedUser().getId());
                                comment.setRid(task.getContext().getId());
                                comment.setTitle(task.getContext().getTitle());
                                task.setComment(comment);
                            }
                            task.getContext().setCurrentState(Config.TASK_SOLVE);

                            //todo ???????????????????????????
                            startLoadingProgress();
                            taskBiz.finishTask(task, new CommonCallback<String>() {
                                @Override
                                public void onError(Exception e) {
                                    stopLoadingProgress();
                                    T.showToast(e.getMessage());
                                    toTaskCenterActivity();
                                }

                                @Override
                                public void onSuccess(String response) {
                                    stopLoadingProgress();
                                    T.showToast(response);
                                    toTaskCenterActivity();
                                }
                            });


                        }
                    });
                    builder.setNeutralButton("??????",null);
                    builder.show();
                }else{
                    startLoadingProgress();
                    //????????????????????????????????????????????????
                    if(task.getContext().getCurrentState().equals(Config.TASK_WAIT_SOLVE)){
                        //????????????????????????????????????????????????????????????????????????????????????????????????
                        task.getContext().setDuid(UserInfoHolder.getInstance().geteUser().getId());
                        task.getContext().setCurrentState(Config.TASK_DOING_SOLVE);
                        taskBiz.save(task, new CommonCallback<String>() {
                            @Override
                            public void onError(Exception e) {
                                stopLoadingProgress();
                                T.showToast(e.getMessage());
                                toTaskCenterActivity();
                                finish();
                            }

                            @Override
                            public void onSuccess(String response) {
                                stopLoadingProgress();
                                T.showToast(response);
                                toTaskCenterActivity();
                                finish();
                            }
                        });
                    }else{
                        stopLoadingProgress();
                        T.showToast("????????????????????????^^");
                    }
                }
            }
        });
        //??????????????????
        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = UserInfoHolder.getInstance().geteUser();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("????????????");
                //????????????????????????????????????????????????????????????????????????
                if(task.getContext().getCurrentState().equals(Config.TASK_WAIT_SOLVE)){
                    builder.setMessage("???????????????????????????????????????^^");
                }else{
                    //???????????????????????????????????????????????????????????????????????????????????????
                    if(user.getId().equals(task.getPostedUser().getId())){
                        builder.setMessage("?????????????????????"+task.getDoneUser().getRealName() + "\n" + "???????????????????????????" + task.getDoneUser().getPhone());
                        if(task.getComment() != null){
                            builder.setMessage("?????????????????????"+task.getDoneUser().getRealName() + "\n" + "???????????????????????????" + task.getDoneUser().getPhone()
                                    + "\n?????????" + task.getComment().getContentDesc()
                            );
                        }
                    }else if(user.getId().equals(task.getDoneUser().getId())){
                        builder.setMessage("??????????????????"+task.getPostedUser().getRealName() + "\n" + "????????????????????????" + task.getPostedUser().getPhone());
                        if(task.getComment() != null){
                            builder.setMessage("??????????????????"+task.getPostedUser().getRealName() + "\n" + "????????????????????????" + task.getPostedUser().getPhone()
                                    + "\n?????????" + task.getComment().getContentDesc()
                            );
                        }
                    }else{
                        builder.setMessage("?????????????????????,?????????????????????????????????^^");
                    }
                }
                builder.setPositiveButton("?????????",null);
                builder.show();
            }
        });

        questionCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toQuestionCenterActivity();
            }
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

    @SuppressLint("SetTextI18n")
    private void initView() {
        content = findViewById(R.id.id_tv_content);
        nickname = findViewById(R.id.id_tv_nickname);
        fun = findViewById(R.id.id_btn_fun);
        postTime = findViewById(R.id.id_tv_postTime);
        icon =  findViewById(R.id.id_iv_icon);
        questionCenter = findViewById(R.id.id_ll_questionCenter);
        TaskCenter = findViewById(R.id.id_ll_taskCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        timeLimit = findViewById(R.id.id_tv_timeLimit);
        state = findViewById(R.id.id_tv_state);
        loadMore = findViewById(R.id.id_tv_loadMore);

        userBiz = new UserBiz();
        taskBiz = new TaskBiz();

        timeLimit.setText("???????????????" + Config.dateFormat.format(task.getContext().getPostDate()) + "-" + Config.dateFormat.format(task.getContext().getEndDate()));
        state.setText(task.getContext().getCurrentState());
        nickname.setText(task.getPostedUser().getNickName());
        content.setText(task.getContext().getContentDesc());
        postTime.setText(Config.dateFormat.format(task.getContext().getPostDate()));
        Picasso.get()
                .load(Config.rsUrl + task.getPostedUser().getIcon())
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(icon);
        fun.setText(UserInfoHolder.getInstance().geteUser().getId().equals(task.getPostedUser().getId()) ? "??????" : "??????");
    }
    public static void launch(Context context, Task task) {
        Intent intent = new Intent(context,TaskDetailActivity.class);
        intent.putExtra(KEY_TASK,task);
        context.startActivity(intent);
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

    private void toQuestionCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }

}