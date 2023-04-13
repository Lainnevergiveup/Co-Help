package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ImageTest {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new GSON().gsonSetter();
    Activity activity;
    LoginRequest loginRequest = new LoginRequest();
    @Test
    public void imageGet() throws IOException {
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookie = okHttp.getResponse().header("Set-Cookie");
        IdAndType idAndType = new IdAndType(2,0);
        okHttp.sendRequest("http://43.143.90.226:9090/image/getimagelist",gson.toJson(idAndType),cookie);
        String res = okHttp.getResponse().body().string();
        Result<List<String>> result = gson.fromJson(res, new TypeToken<Result<List<String>>>(){}.getType());
        System.out.println(result.getData());

    }
    @Test
    public void imageGetWithHistory() throws IOException {
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookie = okHttp.getResponse().header("Set-Cookie");
        IdAndType idAndType = new IdAndType(37,1);
        okHttp.sendRequest("http://43.143.90.226:9090/image/getallimage",gson.toJson(idAndType),cookie);
        String res = okHttp.getResponse().body().string();
        Result<List<String>> result = gson.fromJson(res, new TypeToken<Result<List<String>>>(){}.getType());
        System.out.println(result.getData());

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
