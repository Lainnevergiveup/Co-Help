package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.ACache;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.utils.StringUtils;
import com.xuexiang.xui.widget.alpha.XUIAlphaTextView;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * 登陆页控制类
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class LoginActivity extends BaseActivity {
    MaterialEditText password;
    MaterialEditText username;
    SuperButton login;
    XUIAlphaTextView toRegister;
    XUIAlphaTextView toUserFound;
    UserBiz userBiz;
    LoginRequest loginRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpToolBar();
        setTitle("登录");

        SessionUtils.deleteActivityPreference(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               toRegisterActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                login();
            }
        });

        toUserFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserFoundActivity();
            }
        });
    }

    private void login(){
        loginRequest = new LoginRequest();
        loginRequest.setUserAccount(username.getText().toString());
        loginRequest.setUserPassword( password.getText().toString());
        loginRequest.setUserAccount("1234567894");//debug
        loginRequest.setUserPassword( "1234567890");//debug

        if(StringUtils.isEmpty(loginRequest.getUserAccount()) || StringUtils.isEmpty(loginRequest.getUserPassword())){
            T.showToast("密码或账号不能为空哦~");
            return;
        }
        else{
            Request request = OKHttp.buildPostRequest("http://43.143.90.226:9090/user/login", GSON.gson.toJson(loginRequest), 0);
            OKHttp.client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String res = null;
                    try {
                        ResponseBody body = response.body();
                        res = body.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Result<User> userResult = GSON.gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
                    if (userResult == null){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // 通过Toast 显示信息
                                Toast.makeText(MyCoHelp.getAppContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        user = userResult.getData();
                        //将当前登录用户的数据加入缓存
                        ACache currentLoginUser = ACache.get(MyCoHelp.getAppContext(), "loginUser");
                        currentLoginUser.put("loginUser",GSON.gson.toJson(user),15*ACache.TIME_DAY);
                        toBasicInfoActivity();
                    }
                }
            });
        }
    }
    private void toBasicInfoActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void toUserFoundActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initView() {
        password = findViewById(R.id.et_password);
        username = findViewById(R.id.et_account_number);
        login = findViewById(R.id.id_btn_login);
        toRegister = findViewById(R.id.id_tv_register);
        toUserFound = findViewById(R.id.id_tv_found);
        userBiz = new UserBiz();
    }
}