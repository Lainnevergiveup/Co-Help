package com.cohelp.task_for_stu.net.OKHttpTools;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.ActivityListRequest;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.HelpListRequest;
import com.cohelp.task_for_stu.net.model.domain.HelpTagRequest;
import com.cohelp.task_for_stu.net.model.domain.HoleListRequest;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.PublishDeleteRequest;
import com.cohelp.task_for_stu.net.model.domain.RemarkRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.domain.SearchRequest;
import com.cohelp.task_for_stu.net.model.domain.TeamUpdateRequest;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.Answer;
import com.cohelp.task_for_stu.net.model.entity.Ask;
import com.cohelp.task_for_stu.net.model.entity.Collect;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.cohelp.task_for_stu.net.model.entity.Hole;
import com.cohelp.task_for_stu.net.model.entity.Team;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.net.model.vo.AnswerVO;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.net.model.vo.CourseVO;
import com.cohelp.task_for_stu.net.model.vo.QuestionBankVO;
import com.cohelp.task_for_stu.net.model.vo.RemarkVO;
import com.cohelp.task_for_stu.net.model.vo.ResultVO;
import com.cohelp.task_for_stu.net.model.vo.ScoreVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class OkHttpUtils {
    OKHttp okHttp;
    Gson gson;
    private String baseURL = "http://43.143.90.226:9090";
    private String cookie;

    public Gson getGson() {
        return gson;
    }

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
//        cookie = SessionUtils.getCookiePreference(this);
//        help = new Help();
    }

    /*
    Activity相关接口
     */
    public void activityPublish(Activity activity, Map<String,String> nameAndPath){
        String act = gson.toJson(activity);
        okHttp.sendMediaRequest(baseURL+"/activity/publish","activity",act,nameAndPath,cookie);
        System.out.println(cookie);
        String res = null;

        try {
            ResponseBody body = okHttp.getResponse().body();
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
        okHttp.sendRequest(baseURL+"/activity/list/1/90",req,cookie);
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
    public void holePublish(Hole hole,Map<String,String> nameAndPath){
        String act = gson.toJson(hole);
        System.out.println(act);
        okHttp.sendMediaRequest(baseURL+"/hole/publish","hole",act,nameAndPath,cookie);
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
    public void helpPublish(Help help,Map<String,String> nameAndPath){
        String act = gson.toJson(help);
        System.out.println(act);
        okHttp.sendMediaRequest(baseURL+"/help/publish","help",act,nameAndPath,cookie);
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
        okHttp.sendRequest(baseURL+"/help/list/1/20",req,cookie);
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
    public List<DetailResponse> helpListByTag(String tag){
        HelpTagRequest helpTagRequest = new HelpTagRequest();
        helpTagRequest.setTag(tag);
        String req = gson.toJson(helpTagRequest);
        okHttp.sendRequest(baseURL+"/help/list/tag/1/20",req,cookie);
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
    public User getUser(){
        okHttp.sendGetRequest(baseURL+"/user/current",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<User> result = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
        return result.getData();
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
    public String getImageById(int id){
        okHttp.sendGetRequest(baseURL+"/image/getimagebyid?imageId="+id,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
        return result.getCode();
    }

    /*
    General
     */
    public List<DetailResponse> search(String key,Integer type){
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.setKey(key);
        List<Integer> list = new ArrayList<>();
        list.add(type);
        searchRequest.setTypes(list);
        String searchMessage = ToJsonString.toJson(searchRequest);

        okHttp.sendRequest(baseURL+"/general/search/1/5",searchMessage,cookie);
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
    public DetailResponse getDetail(IdAndType idAndType){
        String idAndTypeJson = gson.toJson(idAndType);
        okHttp.sendRequest(baseURL+"/general/getdetail",idAndTypeJson,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<DetailResponse> userResult = gson.fromJson(res,new TypeToken<Result<DetailResponse>>(){}.getType());
        return userResult.getData();
    }
    public List<RemarkVO> getCommentList(IdAndType idAndType){
        String idAndTypeJson = gson.toJson(idAndType);
        okHttp.sendRequest(baseURL+"/general/getremarklist",idAndTypeJson,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<RemarkVO>> userResult = gson.fromJson(res,new TypeToken<Result<List<RemarkVO>>>(){}.getType());
        return userResult.getData();
    }

    public void sendComment(RemarkRequest remarkRequest){
        String json = gson.toJson(remarkRequest);
        okHttp.sendRequest(baseURL+"/general/insertremark",json,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;

    }
    /*
    Remark
     */
    //点赞评论
    public void remark(int type,int id){
        okHttp.sendRequest(baseURL+"/topic/like/"+type+"/"+id,"",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }

    /*
    Collect
     */
    public void insertCollection(Collect collect){
        String collectRequset = gson.toJson(collect);
        okHttp.sendRequest(baseURL+"/collect/insertcollectrecord",collectRequset,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
    public List<ResultVO> getCollectList(){
        okHttp.sendGetRequest(baseURL+"/collect/getcollectlist/1/5",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        //这个类型写啥？list有1,2,3这里知识查了activity的
        Result<List<ResultVO>> userResult = gson.fromJson(res,new TypeToken<Result<List<ResultVO>>>(){}.getType());
        return userResult.getData();
    }


    /*
    Publish
     */
    public List<DetailResponse> searchPublic(){
        okHttp.sendGetRequest(baseURL+"/user/searchpub/1/20",cookie);
        System.out.println(cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        //这个类型写啥？list有1,2,3这里知识查了activity的
        Result<List<DetailResponse>> userResult = gson.fromJson(res,new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return userResult.getData();
    }


    /*
    History
     */
    public List<DetailResponse> getInvolvedList(){
        okHttp.sendRequest(baseURL+"/history/getinvolvedlist","",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        //这个类型写啥？list有1,2,3这里知识查了activity的
        Result<List<DetailResponse>> userResult = gson.fromJson(res,new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return userResult.getData();
    }
    /*
    Team相关接口
     */
    public List<Team> searchTeam(String teamName){
        okHttp.sendGetRequest(baseURL+"/team/query?teamName="+teamName,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Team>> result = gson.fromJson(res,new TypeToken<Result<List<Team>>>(){}.getType());
        return  result.getData();
    }
    public void changeTeam(Integer teamID){
        TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest();

        teamUpdateRequest.setTeamId(teamID);

        teamUpdateRequest.setConditionType(0);

        String json = gson.toJson(teamUpdateRequest);

        okHttp.sendRequest(baseURL+"/team/change",json,cookie);

        return;
    }
    /*
    UserTeam
     */
    public String getTeamChangeState(){

        okHttp.sendGetRequest(baseURL+"/userteam/getchangeteam",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res,new TypeToken<Result<String>>(){}.getType());
        return  result.getMessage();

    }

    public List<CourseVO> getCourseList(String semester){
        okHttp.sendGetRequest(baseURL+"/course/list/"+semester,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<CourseVO>> result = gson.fromJson(res,new TypeToken<Result<List<CourseVO>>>(){}.getType());
        return result.getData();

    }
    public List<String> getSemesterList(){
        okHttp.sendGetRequest(baseURL+"/user/semester",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<String>> result = gson.fromJson(res,new TypeToken<Result<List<String>>>(){}.getType());
        return result.getData();
    }

    public String delCollectList(List<Integer> list){
        String json = gson.toJson(list);
        okHttp.sendRequest(baseURL+"/collect/deletecollectrecords",json,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        return res;
    }
    public String delPublishList(List<PublishDeleteRequest> list){
        String json = gson.toJson(list);
        okHttp.sendRequest(baseURL+"/collect/deletepubs",json,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        return res;
    }

    public List<AskVO > getAskList(Integer courseId,String semester){

        okHttp.sendGetRequest(baseURL+"/course/list/ask/1/5/"+courseId+"/"+semester+"/2",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AskVO>> result = gson.fromJson(res, new TypeToken<Result<List<AskVO>>>(){}.getType());
        return result.getData();
    }
    public List<CourseVO> getTeacherCourseList(){
        okHttp.sendGetRequest(baseURL+"/teach/listcourse",cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<CourseVO>> result = gson.fromJson(res, new TypeToken<Result<List<CourseVO>>>(){}.getType());
        return result.getData();
    }
    public List<AnswerVO> getTeacherAnswerList(Integer page,Integer limit,Integer askID){
        okHttp.sendGetRequest(baseURL+"/teach/listanswer/"+page+'/'+limit+'/'+askID,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AnswerVO>> result = gson.fromJson(res, new TypeToken<Result<List<AnswerVO>>>(){}.getType());
        return result.getData();

    }
    public List<QuestionBankVO> getTeacherQuestionList(Integer page,Integer limit,Integer courseID){
        okHttp.sendGetRequest(baseURL+"/teach/listquestion/"+page+'/'+limit+'/'+courseID,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<QuestionBankVO>> result = gson.fromJson(res, new TypeToken<Result<List<QuestionBankVO>>>(){}.getType());
        return result.getData();

    }
    public String teacherAddQuestion(Integer askID,Integer level){
        okHttp.sendGetRequest(baseURL+"/teach/addquestion/"+askID+'/'+level,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getMessage();
    }
    public String teacherAddAnswer(Integer answerID,Integer recommendedDegree){
        okHttp.sendGetRequest(baseURL+"/teach/addanswer/"+answerID+'/'+recommendedDegree,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getMessage();
    }
    public String teacherPublishQuestion(List<Integer> questionBankIds){
        String s = "";
        boolean isFirst = false;
        for (Integer c : questionBankIds){
            if (isFirst){
                s+=","+c.toString();
            }
            else {
                s+=c.toString();
                isFirst = true;
            }
        }
        okHttp.sendGetRequest(baseURL+"/teach/publishquestion/"+s,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getMessage();
    }
    public List<AnswerVO> teacherGetAnswerFromBank(Integer askID,Integer page,Integer limit){
        okHttp.sendGetRequest(baseURL+"/teach/listanswerfrombank/"+askID+'/'+page+'/'+limit,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AnswerVO>> result = gson.fromJson(res, new TypeToken<Result<List<AnswerVO>>>(){}.getType());
        return result.getData();
    }
    public List<AnswerVO> teacherGetAnswerListByQuestionID(Integer questionID){
        okHttp.sendGetRequest(baseURL+"/teach/listanswerbankbyquestionbankid/"+questionID,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AnswerVO>> result = gson.fromJson(res, new TypeToken<Result<List<AnswerVO>>>(){}.getType());
        return result.getData();
    }
    public List<ScoreVO> teacherGetScoreByCourseID(Integer courseID){
        okHttp.sendGetRequest(baseURL+"/teach/listscore/"+courseID,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<ScoreVO>> result = gson.fromJson(res, new TypeToken<Result<List<ScoreVO>>>(){}.getType());
        return result.getData();
    }
    public List<AskVO> getQuestionsBySemesterAndCourse(Integer page,Integer limit,Integer courseID,Integer semester,Integer condition){
        okHttp.sendGetRequest(baseURL+"/course/list/ask/"+page+"/"+limit+"/"+courseID+"/"+semester+"/"+condition,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AskVO>> result = gson.fromJson(res, new TypeToken<Result<List<AskVO>>>(){}.getType());
        return result.getData();
    }
    public Boolean answerPublish(Answer answer,Map<String,String> nameAndPath){
        String ans = gson.toJson(answer);
        okHttp.sendMediaRequest(baseURL+"/course/answer","answer",ans,nameAndPath,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<Boolean> result = gson.fromJson(res, new TypeToken<Result<Boolean>>(){}.getType());
        return result.getData()==null?false:true;
    }
    public Boolean askPublish(Ask ask, Map<String,String> nameAndPath){
        String askJson = gson.toJson(ask);
        okHttp.sendMediaRequest(baseURL+"/course/ask","ask",askJson,nameAndPath,cookie);
        String res = null;
        try {
            res = okHttp.getResponse().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        Result<?> result = gson.fromJson(res, new TypeToken<Result<?>>(){}.getType());
        return result.getData()==null?false:true;
    }

}