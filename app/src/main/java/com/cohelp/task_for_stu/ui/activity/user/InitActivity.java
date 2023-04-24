package com.cohelp.task_for_stu.ui.activity.user;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.ACache;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登陆页控制类
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class InitActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ACache loginUser = ACache.get(MyCoHelp.getAppContext(), "loginUser");
        if(loginUser!=null&&loginUser.getAsString("loginUser")!=null){
            User currentUser = GSON.gson.fromJson(loginUser.getAsString("loginUser"), User.class);
            user = currentUser;
            if(MyCoHelp.isNetworkConnected()){
                refreshLoginUser();
            }
            toBasicInfoActivity();
        }else {
            toLoginActivity();
        }
    }
    private void toBasicInfoActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        finish();
    }
    private void toLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void refreshLoginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserAccount(user.getUserAccount());
        loginRequest.setUserPassword(user.getUserPassword());
        Request request = OKHttp.buildPostRequest("http://43.143.90.226:9090/user/login", GSON.gson.toJson(loginRequest), 0);
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // 通过Toast 显示信息
                        Toast.makeText(MyCoHelp.getAppContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = null;
                try {
                    res = response.body().string();
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
                    User newUser = userResult.getData();
                    if(newUser!=null){
                        //将当前登录用户的数据加入缓存
                        ACache currentLoginUser = ACache.get(MyCoHelp.getAppContext(), "loginUser");
                        currentLoginUser.clear();
                        currentLoginUser.put("loginUser",GSON.gson.toJson(user),15*ACache.TIME_DAY);
                        user = newUser;
                    }
                }
            }
        });
    }
}