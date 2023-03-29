package com.cohelp.task_for_stu;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Hole;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;

public class HoleTest {
    OKHttp okHttp  = new OKHttp();
    Gson gson = new GSON().gsonSetter();
    Hole hole;
    LoginRequest loginRequest = new LoginRequest();
    @Test
    public void changeUserInfo() throws IOException {

        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);

        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookie = okHttp.getResponse().header("Set-Cookie");
        okHttp.sendGetRequest("http://43.143.90.226:9090/user/current",cookie);

        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());

        User user = userResult.getData();
        user.setPhoneNumber("19117195203");
        String act = gson.toJson(user);
        System.out.println(act);
        okHttp.sendRequest("http://43.143.90.226:9090/user/changeuserinfo",act,cookie);
        System.out.println(okHttp.getResponse());
        System.out.println(okHttp.getResponse().body().string());
//        String res = null;
//        try {
//            res = okHttp.getResponse().body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(res);
    }
    @Test
    public void holePublish() throws IOException {
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookie = okHttp.getResponse().header("Set-Cookie");
        hole = new Hole("哈哈哈","wow", 0,0,0,"friend");
        String act = gson.toJson(hole);
        System.out.println(act);
        okHttp.sendMediaRequest("http://43.143.90.226:9090/hole/publish","hole",act,null,cookie);
        String res = okHttp.getResponse().body().string();
        System.out.println(res);
    }
    @Test
    public void holeUpdate() throws IOException {
        loginRequest.setUserAccount("1234567890");//debug
        loginRequest.setUserPassword( "1234567890");//debug
        String loginMessage = ToJsonString.toJson(loginRequest);
        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
        String cookie = okHttp.getResponse().header("Set-Cookie");
        hole = new Hole(7,1,"nice","wow", 0,0,0,"friend");
        String act = gson.toJson(hole);
        okHttp.sendMediaRequest("http://43.143.90.226:9090/hole/update","hole",act,null,cookie);
        String res = okHttp.getResponse().body().string();
        System.out.println(res);
    }
//    @Test
//    public void holeList(){
//        loginRequest.setUserAccount("1234567890");//debug
//        loginRequest.setUserPassword( "1234567890");//debug
//        String loginMessage = ToJsonString.toJson(loginRequest);
//        okHttp.sendRequest("http://43.143.90.226:9090/user/login",loginMessage);
//        String cookie = okHttp.getResponse().header("Set-Cookie");
//        HoleListRequest holeListRequest = new HoleListRequest();
//        holeListRequest.setConditionType(1);
//        String req = gson.toJson(holeListRequest);
//        okHttp.sendRequest("http://43.143.90.226:9090/hole/list",req,cookie);
//        String res = null;
//        try {
//            res = okHttp.getResponse().body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(res);
//        Result<List<DetailResponse>> result = gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>(){}.getType());
//        List<Hole> list = null;
//        if (result.getData()!=null){
//            list = new ArrayList<>();
//            for (DetailResponse d : result.getData()){
//                Hole t = new Hole();
//                HoleVO s = d.getHoleVO();
//                t.setId(s.getId());
//                t.setHoleCollect(s.getHoleCollect());
//                t.setHoleComment(s.getHoleComment());
//                t.setHoleLike(s.getHoleLike());
//                t.setHoleDetail(s.getHoleDetail());
//                t.setHoleLabel(s.getHoleLabel());
////                t.setHoleState(s.get);
//                t.setHoleTitle(s.getHoleTitle());
//                t.setHoleOwnerId(s.getHoleOwnerId());
//                t.setHoleCreateTime(s.getHoleCreateTime());
//                list.add(t);
//            }
//        }
//        System.out.println(list);
//    }
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
