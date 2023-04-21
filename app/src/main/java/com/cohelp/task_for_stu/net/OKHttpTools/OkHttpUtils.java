package com.cohelp.task_for_stu.net.OKHttpTools;

import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.LoginRequest;
import com.cohelp.task_for_stu.net.model.domain.PublishDeleteRequest;
import com.cohelp.task_for_stu.net.model.domain.RemarkRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.domain.TeamUpdateRequest;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.Answer;
import com.cohelp.task_for_stu.net.model.entity.Ask;
import com.cohelp.task_for_stu.net.model.entity.Collect;
import com.cohelp.task_for_stu.net.model.entity.Help;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class OkHttpUtils {
    public static OKHttp okHttp;
    public static Gson gson;
    public static String baseURL = "http://43.143.90.226:9090";

    static {
        okHttp = new OKHttp();
        gson = GSON.gson;
    }

    public Gson getGson(){
        return gson;
    }

    //构造方法
    @RequiresApi(api = Build.VERSION_CODES.O)
    public OkHttpUtils() {

    }

    /**
     *Activity相关接口
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void activityPublish(Activity activity, Map<String,String> nameAndPath){

        String activityJson = gson.toJson(activity);
        Response response = okHttp.sendPostRequest(baseURL + "/activity/publish", "activity", activityJson, nameAndPath, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void activityUpdate(Activity activity){
        String act = gson.toJson(activity);
        Response response = okHttp.sendPostRequest(baseURL + "/activity/update", "activity", act, null, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<DetailResponse> activityList(Integer conditionType){

        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(conditionType.toString());
        map.put("conditionType",list);

        //定义响应类型
        Type responseType = new TypeToken<Result<List<DetailResponse>>>() {}.getType();

        //发请求
        Response response = okHttp.sendGetRequest(baseURL + "/activity/list/1/90", map, 300);
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
    public static void helpPublish(Help help, Map<String,String> nameAndPath){
        String act = gson.toJson(help);
        System.out.println(act);
        Response response = okHttp.sendPostRequest(baseURL + "/help/publish", "help", act, nameAndPath, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void helpUpdate(Help help)  {
        String act = gson.toJson(help);
        Response response = okHttp.sendPostRequest(baseURL + "/help/update", "help", act, null, 0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<String> result = gson.fromJson(res, new TypeToken<Result<String>>(){}.getType());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<DetailResponse> helpList(Integer conditionType){
        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(conditionType.toString());
        map.put("conditionType",list);
        Response response = okHttp.sendGetRequest(baseURL + "/help/list/1/20",map, 300);
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
    public static List<DetailResponse> helpListByTag(String tag){
        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(tag);
        map.put("tag",list);

        Response response = okHttp.sendGetRequest(baseURL+"/help/list/tag/1/20",map,300);
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
    public static User userLogin(String account, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserAccount(account);//debug
        loginRequest.setUserPassword(password);//debug
        String loginMessage = ToJsonString.toJson(loginRequest);
        Response response = okHttp.sendPostRequest(baseURL+"user/login",loginMessage,0);

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
    public static void changeUserInfo(User user) throws IOException{
        String act = gson.toJson(user);
        Response response = okHttp.sendPostRequest(baseURL+"/user/changeuserinfo",act,0);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static User getUser(){
        Response response = okHttp.sendGetRequest(baseURL+"/user/current",null,0);
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
    public static List<String> imageGet(int id, int type) {
        IdAndType idAndType = new IdAndType(id,type);
        Response response = okHttp.sendPostRequest(baseURL+"/image/getimagelist",gson.toJson(idAndType),300);
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
    public static List<String> imageGetWithHistory(int id, int type) {
        IdAndType idAndType = new IdAndType(id,type);
        Response response = okHttp.sendPostRequest(baseURL+"/image/getallimage",gson.toJson(idAndType),300);
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
    public static String getImageById(int id){
        Response response = okHttp.sendGetRequest(baseURL+"/image/getimagebyid?imageId="+id,null,300);
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
    public static List<DetailResponse> search(String key, Integer type){
        //get请求参数预处理
        Map<String, List<String>> map = new HashMap<>();
        List<String> keyStr = new ArrayList<>();keyStr.add(key);map.put("key",keyStr);
        List<String> types = new ArrayList<>();types.add("1");map.put("types",types);

        Response response = OKHttp.sendGetRequest(OkHttpUtils.baseURL + "/general/search/1/5", map, 300);

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
    public static DetailResponse getDetail(IdAndType idAndType){
        String idAndTypeJson = gson.toJson(idAndType);
        Response response = okHttp.sendPostRequest(baseURL+"/general/getdetail",idAndTypeJson,0);
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
    public static List<RemarkVO> getCommentList(IdAndType idAndType){
        String idAndTypeJson = gson.toJson(idAndType);
        Response response = okHttp.sendPostRequest(baseURL+"/general/getremarklist",idAndTypeJson,0);
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
    public static void sendComment(RemarkRequest remarkRequest){
        String json = gson.toJson(remarkRequest);
        Response response = okHttp.sendPostRequest(baseURL+"/general/insertremark",json,0);
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
    public static void remark(int type, int id){
        Response response = okHttp.sendGetRequest(baseURL+"/topic/like/"+type+"/"+id,null,0);
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
    public static void insertCollection(Collect collect){
        String collectRequset = gson.toJson(collect);
        Response response = okHttp.sendPostRequest(baseURL+"/collect/insertcollectrecord",collectRequset,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<ResultVO> getCollectList(){
        Response response = okHttp.sendGetRequest(baseURL+"/collect/getcollectlist/1/5",null,300);
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
    public static List<DetailResponse> searchPublic(){
        Response response = okHttp.sendGetRequest(baseURL+"/user/searchpub/1/20",null,300);
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
    public static List<DetailResponse> getInvolvedList(){
        Response response = okHttp.sendGetRequest(baseURL+"/history/getinvolvedlist",null,300);
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
    public static List<ResultVO> getHistoryList(){
        Response response = okHttp.sendGetRequest(baseURL+"/history/gethistorylist/1/10",null,300);
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
    public static String deleteHistoryList(List<Integer> ids){
        String json = gson.toJson(ids);
        Response response = okHttp.sendPostRequest(baseURL+"/history/deletehistoryrecords",json,0);
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
    public static List<Team> searchTeam(String teamName){
        Response response = okHttp.sendGetRequest(baseURL+"/team/query?teamName="+teamName,null,300);
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
    public static void changeTeam(Integer teamID){
        TeamUpdateRequest teamUpdateRequest = new TeamUpdateRequest();
        teamUpdateRequest.setTeamId(teamID);
        teamUpdateRequest.setConditionType(0);
        String json = gson.toJson(teamUpdateRequest);

        Response response = okHttp.sendPostRequest(baseURL+"/team/change",json,0);
    }
    /**
     *UserTeam
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTeamChangeState(){
        Response response = okHttp.sendGetRequest(baseURL+"/userteam/getchangeteam",null,0);
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
    public static List<CourseVO> getCourseList(String semester){
        Response response = okHttp.sendGetRequest(baseURL+"/course/list/"+semester,null,300);
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
    public static List<String> getSemesterList(){
        Response response = okHttp.sendGetRequest(baseURL+"/user/semester",null,0);
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
    public static String delCollectList(List<Integer> list){
        String json = gson.toJson(list);
        Response response = okHttp.sendPostRequest(baseURL+"/collect/deletecollectrecords",json,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String delPublishList(List<PublishDeleteRequest> list){
        String json = gson.toJson(list);
        Response response = okHttp.sendPostRequest(baseURL+"/collect/deletepubs",json,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<AskVO > getAskList(Integer courseId, String semester){

        Response response = okHttp.sendGetRequest(baseURL+"/course/list/ask/1/5/"+courseId+"/"+semester+"/2",null,120);
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
    public static List<CourseVO> getTeacherCourseList(){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listcourse",null,300);
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
    public static List<AnswerVO> getAnswerList(Integer page, Integer limit, Integer askID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listanswer/"+page+'/'+limit+'/'+askID,null,0);
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
    public static List<QuestionBankVO> getTeacherQuestionList(Integer page, Integer limit, Integer courseID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listquestion/"+page+'/'+limit+'/'+courseID,null,300);
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
    public static String teacherAddQuestion(Integer askID, Integer level){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/addquestion/"+askID+'/'+level,null,0);
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
    public static String teacherAddAnswer(Integer answerID, Integer recommendedDegree){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/addanswer/"+answerID+'/'+recommendedDegree,null,0);
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
    public static String teacherPublishQuestion(List<Integer> questionBankIds){
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
        Response response = okHttp.sendGetRequest(baseURL+"/teach/publishquestion/"+s,null,0);
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
    public static List<AnswerVO> teacherGetAnswerFromBank(Integer askID, Integer page, Integer limit){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listanswerfrombank/"+askID+'/'+page+'/'+limit,null,300);
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
    public static List<AnswerVO> teacherGetAnswerListByQuestionID(Integer questionID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listanswerbankbyquestionbankid/"+questionID,null,300);
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
    public static List<ScoreVO> teacherGetScoreByCourseID(Integer courseID){
        Response response = okHttp.sendGetRequest(baseURL+"/teach/listscore/"+courseID,null,300);
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
    public static List<AskVO> getQuestionsBySemesterAndCourse(Integer page, Integer limit, Integer courseID, Integer semester, Integer condition){
        Response response = okHttp.sendGetRequest(baseURL+"/course/list/ask/"+page+"/"+limit+"/"+courseID+"/"+semester+"/"+condition,null,300);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<AskVO>> result = gson.fromJson(res, new TypeToken<Result<List<AskVO>>>(){}.getType());
        return result.getData();
    }

    public static Boolean answerPublish(Answer answer, Map<String,String> nameAndPath) {
        String ans = gson.toJson(answer);
        Response response = okHttp.sendPostRequest(baseURL+"/course/answer","answer",ans,nameAndPath,0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<Boolean> result = gson.fromJson(res, new TypeToken<Result<Boolean>>(){}.getType());
        return result.getData()==null?false:true;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean askPublish(Ask ask, Map<String,String> nameAndPath){
        String askJson = gson.toJson(ask);
        Response response = okHttp.sendPostRequest(baseURL+"/course/ask","ask",askJson,nameAndPath,0);

        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<List<Object>> result = gson.fromJson(res, new TypeToken<Result<List<Object>>>(){}.getType());
        return result.getData()==null?false:true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean askLike(Integer type, Integer id){
        Response response = okHttp.sendPostRequest(baseURL+"/course/like/"+type+"/"+id,"",0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<Boolean> result = gson.fromJson(res, new TypeToken<Result<Boolean>>(){}.getType());
        return result.getData()==null?false:(Boolean) result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean deleteAsk(Integer id){
        Response response = okHttp.sendPostRequest(baseURL+"/course/lideleteAsk/"+id,"",0);
        String res = null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result<Boolean> result = gson.fromJson(res, new TypeToken<Result<Boolean>>(){}.getType());
        return result.getData()==null?false:(Boolean) result.getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void collectAsk(Integer id){
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/course/collect/"+id, null, 0);
        OKHttp.client.newCall(request).enqueue(new Callback() {
       @Override
       public void onFailure(@NonNull Call call, @NonNull IOException e) {

       }
       @Override
       public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

       }
   });
    }

    public static void collectTeacherAsk(Integer id,Integer level){
        System.out.println("levelokhttp"+level);
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/teach/addquestion/"+id+"/"+level, null, 0);
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String string = response.body().string();
                Result<String> json = GSON.gson.fromJson(string, new TypeToken<Result<List<String>>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    public void run() {
//                        Toast.makeText(MyCoHelp.getAppContext(), json.getData(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }




}