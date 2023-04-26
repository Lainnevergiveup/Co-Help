package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.User;
import com.cohelp.task_for_stu.biz.UserBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.CommonCallback;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.ToJsonString;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.RegisterRequest;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.ui.CircleTransform;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.utils.T;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.squareup.picasso.Picasso;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;

/**
 * 注册控制类
 */
public class RegisterActivity extends BaseActivity {

    ImageView icon;
    MaterialEditText username;
    MaterialEditText password;
    MaterialEditText repassword;

    MaterialEditText comfirmCode;
    MaterialEditText email;
    User user;
    UserBiz userBiz;
    SuperButton register;
    RoundButton getConfirmCode;
    RegisterRequest registerRequest ;
    OKHttp okHttp;
    String emailString;
    private CountDownButtonHelper mCountDownHelper;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        setUpToolBar();
        setTitle("注册");
        initView();
        initEvent();
    }

    private void initEvent() {
        /**
         * 准备注册
         */

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //TODO 发起来自biz的请求
                registerRequest.setUserAccount(username.getText().toString());
                registerRequest.setUserPassword(password.getText().toString());

                registerRequest.setUserConfirmPassword(repassword.getText().toString());
                registerRequest.setConfirmCode(comfirmCode.getText().toString());
                if(password.getText().toString().equals(repassword.getText().toString())){
                    sendRegistRequest();
                }else {
                   Toast.makeText(MyCoHelp.getAppContext(), "请检查密码是否一致", Toast.LENGTH_SHORT).show();
                }



            }
        });
        getConfirmCode.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public  void onClick(View view){
                System.out.println("验证码");
                mCountDownHelper.start();
                emailString = email.getText().toString();
                if (emailString!=null){
                    sendComfirmCodeRequest();
                }
                else T.showToast("请输入合法的邮箱！");
            }

        });
        /**
         * 选择图片
         */
//        icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startPickFile();
//            }
//        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendComfirmCodeRequest(){
        new Thread(()->{

            registerRequest.setUserEmail(email.getText().toString());
            emailString= registerRequest.getUserEmail();

            System.out.println(3);
            Response response = okHttp.sendGetRequest("http://43.143.90.226:9090/user/sendconfirmcode?userEmail=" + emailString, null,  0);

        }).start();


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendRegistRequest(){
        new Thread(()->{
            String registMessage = ToJsonString.toJson(registerRequest);

            Response response = okHttp.sendPostRequest("http://43.143.90.226:9090/user/register", registMessage, 0);
            String res = null;
            try {
                res = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            Result<Integer> json = GSON.gson.fromJson(res, new TypeToken<Result<Integer>>() {
            }.getType());
            String message = json.getMessage();
            if(message.equals("注册成功")){
                Toast.makeText(MyCoHelp.getAppContext(), "账号注册成功~", Toast.LENGTH_SHORT).show();
                toLoginActivity();
            }else{
                Toast.makeText(MyCoHelp.getAppContext(), message, Toast.LENGTH_SHORT).show();
            }
            System.out.println(message);

        }).start();


    }
    private void startPickFile() {
        new LFilePicker()
                .withActivity(this)
                .withRequestCode(1001)
                .withMutilyMode(false)
                .withTitle("上传个人头像")//标题文字
                .withFileFilter(new String[]{".jpg", ".png",".jpeg"})//支持的上传的文件类型
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                String fileName = data.getStringArrayListExtra(Constant.RESULT_INFO).get(0);
                File file = new File(fileName);
                if(file.exists()){
                    startLoadingProgress();
                    userBiz.uploadImg(file, new CommonCallback<String>() {
                        @Override
                        public void onError(Exception e) {
                            stopLoadingProgress();
                            T.showToast(e.getMessage());
                        }

                        @Override
                        public void onSuccess(String response) {
                            stopLoadingProgress();
                            user.setIcon(response);
                            Picasso.get()
                                    .load(Config.rsUrl + response)
                                    .placeholder(R.drawable.pictures_no)
                                    .transform(new CircleTransform())
                                    .into(icon);
                        }
                    });
                }else{
                    T.showToast("不能找到对应文件");
                }
            }
        }else if(resultCode == RESULT_CANCELED){
            T.showToast("返回登录页面~");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        okHttp = new OKHttp();
        icon = findViewById(R.id.id_iv_icon);
        username = findViewById(R.id.et_account_number);
        password = findViewById(R.id.et_password);
        repassword = findViewById(R.id.et_repassword);
        email = findViewById(R.id.et_mail_number);
        register = findViewById(R.id.btn_login);
        comfirmCode = findViewById(R.id.et_verify_code);
        user = new User();
        registerRequest = new RegisterRequest();
        getConfirmCode = findViewById(R.id.btn_get_verify_code);

        mCountDownHelper = new CountDownButtonHelper(getConfirmCode, 60,1);
//        Picasso.get()
//                .load(R.drawable.pictures_no)
//                .transform(new CircleTransform())
//                .into(icon);
    }
    /**
     * 获取验证码
     */
    private void getVerifyCode(String phoneNumber) {
        // TODO: 2019-11-18 这里只是界面演示而已
//        XToastUtils.warning("只是演示，验证码请随便输");
        mCountDownHelper.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}