package com.cohelp.task_for_stu;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    OKHttp okHttp  = new OKHttp();
    //    LoginRequest loginRequest = new LoginRequest();
    Gson gson = new GSON().gsonSetter();
    Activity activity;
    LoginRequest loginRequest = new LoginRequest();
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.ear.task_for_stu", appContext.getPackageName());
    }
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

//        User user = userResult.getData();
        User user = new User();
        user.setId(userResult.getData().getId());
        user.setSex(0);
//        user.setUserName("SuperMonitor");
//        user.setPhoneNumber("19117195203");
//        user.setAnimalSign("龙");
//        user.setUserName("Test");
        System.out.println(user);
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
}