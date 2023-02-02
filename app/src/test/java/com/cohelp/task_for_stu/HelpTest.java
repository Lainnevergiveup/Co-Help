package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.ActivityListRequest;
import com.cohelp.task_for_stu.net.model.domain.HelpListRequest;
import com.cohelp.task_for_stu.net.model.domain.HelpTagRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class HelpTest {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new GSON().gsonSetter();
    Help help;
    @Test
    public void helpPublish(){
        String cookie = new UserBaseTest().getUserBase();
        help = new Help("0","0",0,0,0,0,"happy");
        String act = gson.toJson(help);
        System.out.println(act);
        okHttp.sendMediaRequest("http://43.143.90.226:9090/help/publish","help",act,null,cookie);
        String res = okHttp.getResponse().toString();
        System.out.println(res);
    }
    @Test
    public void helpUpdate() throws IOException {
        String cookie = new UserBaseTest().getUserBase();
        help = new Help(7,1,"1","情", 0,0,0,0,"friend");
        String act = gson.toJson(help);
        okHttp.sendMediaRequest("http://43.143.90.226:9090/help/update","help",act,null,cookie);
        String res = okHttp.getResponse().body().string();
        Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
        System.out.println(result.getMessage());
        System.out.println(res);
    }
    @Test
    public void helpList(){
        String cookie = new UserBaseTest().getUserBase();
        HelpListRequest helpListRequest = new HelpListRequest();
        helpListRequest.setConditionType(1);
        String req = gson.toJson(helpListRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/help/list",req,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);

    }
    @Test
    public void helpListTag() throws IOException {
        String cookie = new UserBaseTest().getUserBase();
        HelpTagRequest helpTagRequest = new HelpTagRequest();
        helpTagRequest.setTag("学习");
        String req = gson.toJson(helpTagRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/help/list/tag",req,cookie);
        String res = okHttp.getResponse().toString();
        System.out.println(okHttp.getResponse().body().string());
        System.out.println(res);

    }
}
