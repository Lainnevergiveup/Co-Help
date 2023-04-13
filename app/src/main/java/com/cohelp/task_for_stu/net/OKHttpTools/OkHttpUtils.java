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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
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


    //构造方法
    @RequiresApi(api = Build.VERSION_CODES.O)
    public OkHttpUtils() {
        okHttp = new OKHttp();
        gson = new GSON().gsonSetter();
    }

    /**
     *Activity相关接口
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void activityPublish(Activity activity, Map<String,String> nameAndPath){

        String activityJson = gson.toJson(activity);
    Response response = okHttp.sendPostRequest(baseURL + "/activity/publish", "activity", activityJson, nameAndPath, cookie, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void activityUpdate(Activity activity){
        String act = gson.toJson(activity);
        Response response = okHttp.sendPostRequest(baseURL + "/activity/update", "activity", act, null, cookie, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DetailResponse> activityList(Integer conditionType){

        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(conditionType.toString());
        map.put("conditionType",list);

        //定义响应类型
        Type responseType = new TypeToken<Result<List<DetailResponse>>>() {}.getType();

        //发请求
        Response response = okHttp.sendGetRequest(baseURL + "/activity/list/1/90", cookie, map, 300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //转换数据
        Result<List<DetailResponse>> result = gson.fromJson(res, responseType);
        System.out.println(result);
        return result.getData();
    }

    /**
     *Help相关接口
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void helpPublish(Help help, Map<String,String> nameAndPath){
        String act = gson.toJson(help);
        System.out.println(act);
        Response response = okHttp.sendPostRequest(baseURL + "/help/publish", "help", act, nameAndPath, cookie, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void helpUpdate(Help help)  {
        String act = gson.toJson(help);
        Response response = okHttp.sendPostRequest(baseURL + "/help/update", "help", act, null, cookie, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DetailResponse> helpList(Integer conditionType){
        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(conditionType.toString());
        map.put("conditionType",list);
        Response response = okHttp.sendGetRequest(baseURL + "/help/list/1/20", cookie,map, 300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<DetailResponse>> result = gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DetailResponse> helpListByTag(String tag){
        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(tag);
        map.put("tag",list);

        Response response = okHttp.sendGetRequest(baseURL+"/help/list/tag/1/20",cookie,map,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<DetailResponse>> result = gson.fromJson(res, new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return result.getData();
    }

    /**
     *UserBase相关接口
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public User userLogin(String account, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserAccount(account);//debug
        loginRequest.setUserPassword(password);//debug
        String loginMessage = ToJsonString.toJson(loginRequest);
        Response response = okHttp.sendPostRequest(baseURL+"user/login",loginMessage,null,0);
        cookie = response.header("Set-Cookie");

        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result<User> userResult = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
        return userResult.getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changeUserInfo(User user) throws IOException{
        String act = gson.toJson(user);
        Response response = okHttp.sendPostRequest(baseURL+"/user/changeuserinfo",act,cookie,0);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public User getUser(){
        Response response = okHttp.sendGetRequest(baseURL+"/user/current",cookie,null,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<User> result = gson.fromJson(res, new TypeToken<Result<User>>(){}.getType());
        return result.getData();
    }


    /**
     *image相关接口
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> imageGet(int id, int type) {
        IdAndType idAndType = new IdAndType(id,type);
        Response response = okHttp.sendPostRequest(baseURL+"/image/getimagelist",gson.toJson(idAndType),cookie,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<String>> result = gson.fromJson(res, new TypeToken<Result<List<String>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> imageGetWithHistory(int id, int type) {
        IdAndType idAndType = new IdAndType(id,type);
        Response response = okHttp.sendPostRequest(baseURL+"/image/getallimage",gson.toJson(idAndType),cookie,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<String>> result = gson.fromJson(res, new TypeToken<Result<List<String>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getImageById(int id){
        Response response = okHttp.sendGetRequest(baseURL+"/image/getimagebyid?imageId="+id,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
        return result.getCode();
    }

    /**
     *General
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DetailResponse> search(String key, Integer type){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setKey(key);
        List<Integer> list = new ArrayList<>();
        list.add(type);
        searchRequest.setTypes(list);
        String searchMessage = ToJsonString.toJson(searchRequest);

        Response response = okHttp.sendPostRequest(baseURL+"/general/search/1/5",searchMessage,cookie,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<DetailResponse>> userResult = gson.fromJson(res,new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return userResult.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public DetailResponse getDetail(IdAndType idAndType){
        String idAndTypeJson = gson.toJson(idAndType);
        Response response = okHttp.sendPostRequest(baseURL+"/general/getdetail",idAndTypeJson,cookie,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<DetailResponse> userResult = gson.fromJson(res,new TypeToken<Result<DetailResponse>>(){}.getType());
        return userResult.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<RemarkVO> getCommentList(IdAndType idAndType){
        String idAndTypeJson = gson.toJson(idAndType);
        Response response = okHttp.sendPostRequest(baseURL+"/general/getremarklist",idAndTypeJson,cookie,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<RemarkVO>> userResult = gson.fromJson(res,new TypeToken<Result<List<RemarkVO>>>(){}.getType());
        return userResult.getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendComment(RemarkRequest remarkRequest){
        String json = gson.toJson(remarkRequest);
        Response response = okHttp.sendPostRequest(baseURL+"/general/insertremark",json,cookie,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *Remark
     */
    //点赞评论
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void remark(int type, int id){
        Response response = okHttp.sendGetRequest(baseURL+"/topic/like/"+type+"/"+id,cookie,null,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *Collect
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertCollection(Collect collect){
        String collectRequset = gson.toJson(collect);
        Response response = okHttp.sendPostRequest(baseURL+"/collect/insertcollectrecord",collectRequset,cookie,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ResultVO> getCollectList(){
        Response response = okHttp.sendGetRequest(baseURL+"/collect/getcollectlist/1/5",cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<ResultVO>> userResult = gson.fromJson(res,new TypeToken<Result<List<ResultVO>>>(){}.getType());
        return userResult.getData();
    }


    /**
     * Publish
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DetailResponse> searchPublic(){
        Response response = okHttp.sendGetRequest(baseURL+"/user/searchpub/1/20",cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<DetailResponse>> userResult = gson.fromJson(res,new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return userResult.getData();
    }
    /**
     * 获取我的参与
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DetailResponse> getInvolvedList(){
        Response response = okHttp.sendGetRequest(baseURL+"/history/getinvolvedlist",cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<DetailResponse>> userResult = gson.fromJson(res,new TypeToken<Result<List<DetailResponse>>>(){}.getType());
        return userResult.getData();
    }

    /**
     * 获取历史记录
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ResultVO> getHistoryList(){
        Response response = okHttp.sendGetRequest(baseURL+"/history/gethistorylist/1/10",cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<ResultVO>> userResult = gson.fromJson(res,new TypeToken<Result<List<ResultVO>>>(){}.getType());
        return userResult.getData();
    }

    /**
     * 删除历史记录
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String deleteHistoryList(List<Integer> ids){
        String json = gson.toJson(ids);
        Response response = okHttp.sendPostRequest(baseURL+"/history/deletehistoryrecords",json,cookie,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<Object> userResult = gson.fromJson(res,new TypeToken<Result<Object>>(){}.getType());
        return userResult.getMessage();
    }
    /**
     *Team相关接口
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Team> searchTeam(String teamName){
        Response response = okHttp.sendGetRequest(baseURL+"/team/query?teamName="+teamName,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Team>> result = gson.fromJson(res,new TypeToken<Result<List<Team>>>(){}.getType());
        return  result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changeTeam(Integer teamID){
        TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest();
        teamUpdateRequest.setTeamId(teamID);
        teamUpdateRequest.setConditionType(0);
        String json = gson.toJson(teamUpdateRequest);

        Response response = okHttp.sendPostRequest(baseURL+"/team/change",json,cookie,0);
    }
    /**
     *UserTeam
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTeamChangeState(){
        Response response = okHttp.sendGetRequest(baseURL+"/userteam/getchangeteam",cookie,null,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res,new TypeToken<Result<String>>(){}.getType());
        return  result.getMessage();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<CourseVO> getCourseList(String semester){
        Response response = okHttp.sendGetRequest(baseURL+"/course/list/"+semester,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<CourseVO>> result = gson.fromJson(res,new TypeToken<Result<List<CourseVO>>>(){}.getType());
        return result.getData();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getSemesterList(){
        Response response = okHttp.sendGetRequest(baseURL+"/user/semester",cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<String>> result = gson.fromJson(res,new TypeToken<Result<List<String>>>(){}.getType());
        return result.getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String delCollectList(List<Integer> list){
        String json = gson.toJson(list);
        Response response = okHttp.sendPostRequest(baseURL+"/collect/deletecollectrecords",json,cookie,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String delPublishList(List<PublishDeleteRequest> list){
        String json = gson.toJson(list);
        Response response = okHttp.sendPostRequest(baseURL+"/collect/deletepubs",json,cookie,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<AskVO > getAskList(Integer courseId, String semester){

        Response response = okHttp.sendGetRequest(baseURL+"/course/list/ask/1/5/"+courseId+"/"+semester+"/2",cookie,null,120);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AskVO>> result = gson.fromJson(res, new TypeToken<Result<List<AskVO>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<CourseVO> getTeacherCourseList(){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listcourse",cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<CourseVO>> result = gson.fromJson(res, new TypeToken<Result<List<CourseVO>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<AnswerVO> getTeacherAnswerList(Integer page, Integer limit, Integer askID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listanswer/"+page+'/'+limit+'/'+askID,cookie,null,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AnswerVO>> result = gson.fromJson(res, new TypeToken<Result<List<AnswerVO>>>(){}.getType());
        return result.getData();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<QuestionBankVO> getTeacherQuestionList(Integer page, Integer limit, Integer courseID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listquestion/"+page+'/'+limit+'/'+courseID,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<QuestionBankVO>> result = gson.fromJson(res, new TypeToken<Result<List<QuestionBankVO>>>(){}.getType());
        return result.getData();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String teacherAddQuestion(Integer askID, Integer level){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/addquestion/"+askID+'/'+level,cookie,null,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getMessage();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String teacherAddAnswer(Integer answerID, Integer recommendedDegree){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/addanswer/"+answerID+'/'+recommendedDegree,cookie,null,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getMessage();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
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
        Response response = okHttp.sendGetRequest(baseURL+"/teach/publishquestion/"+s,cookie,null,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getMessage();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<AnswerVO> teacherGetAnswerFromBank(Integer askID, Integer page, Integer limit){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listanswerfrombank/"+askID+'/'+page+'/'+limit,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AnswerVO>> result = gson.fromJson(res, new TypeToken<Result<List<AnswerVO>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<AnswerVO> teacherGetAnswerListByQuestionID(Integer questionID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listanswerbankbyquestionbankid/"+questionID,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AnswerVO>> result = gson.fromJson(res, new TypeToken<Result<List<AnswerVO>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ScoreVO> teacherGetScoreByCourseID(Integer courseID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listscore/"+courseID,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<ScoreVO>> result = gson.fromJson(res, new TypeToken<Result<List<ScoreVO>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<AskVO> getQuestionsBySemesterAndCourse(Integer page, Integer limit, Integer courseID, Integer semester, Integer condition){
        Response response = okHttp.sendGetRequest(baseURL+"/course/list/ask/"+page+"/"+limit+"/"+courseID+"/"+semester+"/"+condition,cookie,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AskVO>> result = gson.fromJson(res, new TypeToken<Result<List<AskVO>>>(){}.getType());
        return result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean answerPublish(Answer answer, Map<String,String> nameAndPath){
        String ans = gson.toJson(answer);
        Response response = okHttp.sendPostRequest(baseURL+"/course/answer","help",ans,nameAndPath,cookie,0);
        String res = response.toString();
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getData()==null?false:true;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean askPublish(Ask ask, Map<String,String> nameAndPath){
        String askJson = gson.toJson(ask);
        Response response = okHttp.sendPostRequest(baseURL+"/course/ask","help",askJson,nameAndPath,cookie,0);
        String res = response.toString();
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getData()==null?false:true;
    }
}