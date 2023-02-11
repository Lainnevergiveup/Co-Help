package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;

public class GeneralTest {
    OKHttp okHttp  = new OKHttp();
//    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new GSON().gsonSetter();
    LoginRequest loginRequest;
    @Test
    public void testGetDetail(){

        IdAndType idAndType = new IdAndType(1,1);
        String checkMessage = gson.toJson(idAndType);
        System.out.println(checkMessage);
        okHttp.sendRequest("http://43.143.90.226:9090/general/getdetail",checkMessage);
        try {
            String res = okHttp.getResponse().body().string();

            System.out.println(res);
            Result<DetailResponse> result = gson.fromJson(res, new TypeToken<Result<DetailResponse>>(){}.getType());
            System.out.println(result.getData().getActivityVO());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
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
