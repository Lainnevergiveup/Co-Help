package com.cohelp.task_for_stu.net.OKHttpTools;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.ActivityListRequest;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.HelpListRequest;
import com.cohelp.task_for_stu.net.model.domain.HoleListRequest;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.IdAndTypeList;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.domain.SearchRequest;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.cohelp.task_for_stu.net.model.entity.Hole;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.HoleVO;
import com.cohelp.task_for_stu.utils.GsonUtil;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {
    OKHttp okHttp;
    Gson gson;
    private String baseURL = "http://43.143.90.226:9090";
    private String cookie;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /*
        构造方法
         */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public OkHttpUtils() {
        okHttp = new OKHttp();
        gson = new GSON().gsonSetter();
//        help = new Help();
    }

    /*
    Activity相关接口
     */
    public void activityPublish(Activity activity, Map<String,String> nameAndPath){
        String act = gson.toJson(activity);
        okHttp.sendMediaRequest(baseURL+"/activity/publish","activity",act,nameAndPath,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);

    }

    public void activityUpdate(Activity activity){
        String act = gson.toJson(activity);
        okHttp.sendMediaRequest(baseURL+"/activity/update","activity",act,null,cookie);
        String res = okHttp.getResponse().toString();
        System.out.println(res);
    }


    public List<DetailResponse> activityList(Integer conditionType){
        ActivityListRequest activityListRequest = new ActivityListRequest();
        activityListRequest.setConditionType(conditionType);
//        activityListRequest.setDayNum(2);
        String req = gson.toJson(activityListRequest);
        okHttp.sendRequest(baseURL+"/activity/list",req,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(res);
        Result<List<DetailResponse>> result = gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>(){}.getType());
//        List<ActivityVO> activityVOList = new ArrayList<>();
//        for (DetailResponse d : result.getData()){
//            activityVOList.add(d.getActivityVO());
//        }
        System.out.println(result);
        return result.getData();
    }
    /*
    Hole相关接口
     */
    public void holePublish(Hole hole){
        String act = gson.toJson(hole);
        System.out.println(act);
        okHttp.sendMediaRequest(baseURL+"/hole/publish","hole",act,null,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
    public void holeUpdate(Hole hole) {
        String act = gson.toJson(hole);
        okHttp.sendMediaRequest(baseURL+"/hole/update","hole",act,null,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
    public List<DetailResponse> holeList(Integer conditionType){
        HoleListRequest holeListRequest = new HoleListRequest();
        holeListRequest.setConditionType(1);
        String req = gson.toJson(holeListRequest);
        okHttp.sendRequest(baseURL+"/hole/list",req,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        Result<List<DetailResponse>> result = gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return result.getData();
    }
    /*
    Help相关接口
     */
    public void helpPublish(Help help){
        String act = gson.toJson(help);
        System.out.println(act);
        okHttp.sendMediaRequest(baseURL+"/help/publish","help",act,null,cookie);
        String res = okHttp.getResponse().toString();
        System.out.println(res);
    }
    public void helpUpdate(Help help)  {
        String act = gson.toJson(help);
        okHttp.sendMediaRequest(baseURL+"/help/update","help",act,null,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
        System.out.println(result.getMessage());
        System.out.println(res);
    }
    public List<DetailResponse> helpList(Integer conditionType){
        HelpListRequest helpListRequest = new HelpListRequest();
        helpListRequest.setConditionType(conditionType);
        String req = gson.toJson(helpListRequest);
        okHttp.sendRequest(baseURL+"/help/list",req,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        Result<List<DetailResponse>> result = gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        System.out.println(result);
        return result.getData();
    }


    /*
    UserBase相关接口
     */
    public User userLogin(String account,String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserAccount(account);//debug
        loginRequest.setUserPassword(password);//debug
        String loginMessage = ToJsonString.toJson(loginRequest);

        okHttp.sendRequest(baseURL+"user/login",loginMessage);
        cookie = okHttp.getResponse().header("Set-Cookie");
        System.out.println(cookie);
        okHttp.sendGetRequest(baseURL+"/user/current",cookie);

        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
        System.out.println(userResult.getData());
        return userResult.getData();
    }

    public void changeUserInfo(User user) throws IOException{
        String act = gson.toJson(user);
        System.out.println(act);
        okHttp.sendRequest(baseURL+"/user/changeuserinfo",act,cookie);
        System.out.println(okHttp.getResponse());
        System.out.println(okHttp.getResponse().body().string());
    }

    /*
    image相关接口
     */
    public List<String> imageGet(int id,int type) {
        IdAndType idAndType = new IdAndType(id,type);
        okHttp.sendRequest(baseURL+"/image/getimagelist",gson.toJson(idAndType),cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<String>> result = gson.fromJson(res, new TypeToken<Result<List<String>>>(){}.getType());
        return result.getData();
    }
    public List<String> imageGetWithHistory(int id,int type) {
        IdAndType idAndType = new IdAndType(id,type);
        okHttp.sendRequest(baseURL+"/image/getallimage",gson.toJson(idAndType),cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<String>> result = gson.fromJson(res, new TypeToken<Result<List<String>>>(){}.getType());
        return result.getData();
    }

    /*

     */
    public List<DetailResponse> search(String key,Integer type){
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.setKey(key);
        List<Integer> list = new ArrayList<>();
        list.add(type);
        searchRequest.setTypes(list);
        String searchMessage = ToJsonString.toJson(searchRequest);

        okHttp.sendRequest(baseURL+"/general/search",searchMessage,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        //这个类型写啥？list有1,2,3这里知识查了activity的
        Result<List<DetailResponse>> userResult = gson.fromJson(res,new TypeToken<Result<List<DetailResponse>>>(){}.getType());
//        Result<IdAndTypeList> parseObject = JSON.parseObject(res, new Result<IdAndTypeList>().getClass());
//        System.out.println("data:"+parseObject);
        System.out.println(userResult);
        return userResult.getData();
    }

}
