package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

public class Test {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new GSON().gsonSetter();
    Activity activity;
    LoginRequest loginRequest = new LoginRequest();
    @org.junit.Test
    public String getImageById(){
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);

        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);
        okHttp.sendGetRequest("http://43.143.90.226:9090/image/getimagebyid?imageId=1",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
        System.out.println(result);
        return result.getData();
    }
}
