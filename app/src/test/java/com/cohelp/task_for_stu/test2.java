package com.cohelp.task_for_stu;

import com.alibaba.fastjson.JSON;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.HistoryAndCollectRequest;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.IdAndTypeList;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.PublishDeleteRequest;
import com.cohelp.task_for_stu.net.model.domain.RemarkRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.domain.SearchRequest;
import com.cohelp.task_for_stu.net.model.domain.TeamUpdateRequest;
import com.cohelp.task_for_stu.net.model.entity.Collect;
import com.cohelp.task_for_stu.net.model.entity.History;
import com.cohelp.task_for_stu.net.model.entity.RemarkActivity;
import com.cohelp.task_for_stu.net.model.entity.Team;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class test2 {
    OKHttp okHttp  = new OKHttp();
    LoginRequest loginRequest = new LoginRequest();
    IdAndType idAndType = new IdAndType();
    SearchRequest searchRequest = new SearchRequest();
    RemarkRequest remarkRequest = new RemarkRequest();
    RemarkActivity remarkActivity = new RemarkActivity();
    HistoryAndCollectRequest historyAndCollectRequest = new HistoryAndCollectRequest();
    History history = new History();
    Collect collect = new Collect();
    PublishDeleteRequest publishDeleteRequest = new PublishDeleteRequest();
    TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest();


    @Test
    public void getHistory(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        historyAndCollectRequest.setUserId(1);
        historyAndCollectRequest.setPageNum(1);
        historyAndCollectRequest.setRecordMaxNum(1);
        String getcollect = ToJsonString.toJson(historyAndCollectRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/history/gethistorylist",getcollect,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<List<DetailResponse>>().getClass());
        System.out.println(parseObject);

//        System.out.println("data:"+userResult);
    }


    @Test
    public void getCollect(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        historyAndCollectRequest.setUserId(1);
        historyAndCollectRequest.setPageNum(1);
        historyAndCollectRequest.setRecordMaxNum(1);
        String getcollect = ToJsonString.toJson(historyAndCollectRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/collect/getcollectlist",getcollect,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<List<Collect>> userResult = gson.fromJson(res,new TypeToken<Result<List<Collect>>>(){}.getType());
        Result result = JSON.parseObject(res, new Result<List<DetailResponse>>().getClass());
        System.out.println("data:"+result);
    }


    @Test
    public void search(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        searchRequest.setKey("不删测试");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        searchRequest.setTypes(list);
        String searchMessage = ToJsonString.toJson(searchRequest);
        System.out.println(searchMessage);
        okHttp.sendRequest("http://43.143.90.226:9090/general/search",searchMessage);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        //这个类型写啥？list有1,2,3这里知识查了activity的
//        Result<IdAndTypeList> userResult = gson.fromJson(res,new TypeToken<Result<IdAndTypeList>>(){}.getType());
        Result<IdAndTypeList> parseObject = JSON.parseObject(res, new Result<List<DetailResponse>>().getClass());
        System.out.println("data:"+parseObject);


    }


    @Test
    public void getinlvoved(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        LocalDateTime localDateTime = LocalDateTime.now();
//        history.setId(null);
//        history.setTopicId(1);
//        history.setTopicType(2);
//        history.setUserId(1);
//        history.setViewTime(localDateTime);

        String inserthistory = JSON.toJSONStringWithDateFormat(history, "yyyy-MM-dd HH:mm:ss");
//        String inserthistory = ToJsonString.toJson(history);
        okHttp.sendGetRequest("http://43.143.90.226:9090/history/getinvolvedlist",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<List<DetailResponse>>().getClass());
        System.out.println(parseObject);

    }

    @Test
    public void changeAvatar(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

    }

    @Test
    public void queryTeam(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);


//        String teamUpdate = ToJsonString.toJson(teamUpdateRequest);
//        String teamUpdateRequest = ToJsonString.toJson(teamUpdateRequest);
        okHttp.sendGetRequest("http://43.143.90.226:9090/team/query",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<List<Team>>().getClass());
        System.out.println(parseObject);
    }


    @Test
    public void changeTeam(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);

        teamUpdateRequest.setTeamId(2);
        teamUpdateRequest.setConditionType(1);

        String teamUpdate = ToJsonString.toJson(teamUpdateRequest);
//        String teamUpdateRequest = ToJsonString.toJson(teamUpdateRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/team/change",teamUpdate,cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<Boolean>().getClass());
        System.out.println(parseObject);
    }


    @Test
    public void team(){
        loginRequest.setUserAccount("1234567890");
        loginRequest.setUserPassword("1234567890");
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookieval = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookieval);
        Team team = new Team();
        okHttp.sendGetRequest("http://43.143.90.226:9090/team/1",cookieval);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
            System.out.println("res:"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        Result<History> userResult = gson.fromJson(res,new TypeToken<Result<History>>(){}.getType());
        Result parseObject = JSON.parseObject(res, new Result<Team>().getClass());
        System.out.println(parseObject);
    }
}
