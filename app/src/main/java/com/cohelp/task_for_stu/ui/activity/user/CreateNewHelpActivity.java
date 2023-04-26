package com.cohelp.task_for_stu.ui.activity.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.TimePickerView;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.FlowTagAdapter;
import com.cohelp.task_for_stu.ui.adpter.FlowTagPaidAdapter;
import com.cohelp.task_for_stu.ui.adpter.FullyGridLayoutManager;
import com.cohelp.task_for_stu.ui.adpter.GridImageAdapter;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.Request;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateNewHelpActivity extends BaseActivity{

    EditText title;
    EditText content;
    EditText startTime;
    EditText endTime;
    int Paid=-1;
    String Label;
    RadioButton lb1,lb2,lb3,lb4,lb5;
    EditText lb_diy;
    TextView title1;
    Button publish;
    TaskBiz taskBiz;
    Task task;
    Help help;
    BaseTask baseTask;
    TimePickerView pickerView;
    FlowtagLayout mSingleFlowTagLayout;
    FlowTagLayout mPaidFlowTagLayout;

    private static final int IMG_COUNT = 8;
    private static final String IMG_ADD_TAG = "a";
    private GridView gridView;
    //    private GridAdapter adapterr;
//    private GVAdapter adapter;
    private ImageView img;
    private List<String> list = new ArrayList<>();
    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private RecyclerView mRecyclerView;
    private PopupWindow pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_help);

        setUpToolBar();
        setTitle("创建互助");

        initView();
        title1.setText("互助发布");
        initEvent();
        initWidget();
    }
    private void initEvent() {



        publish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                String te = title.getText().toString();
                String ct = content.getText().toString();
                if(te.isEmpty() || Paid==-1 || Label.isEmpty()){
                    Toast.makeText(CreateNewHelpActivity.this,"您输入的信息不完整\n请再次检查!",Toast.LENGTH_LONG).show();
                    System.out.println("ssss");
                }else {
                    help = new Help(te,ct,Paid,null,null,null,Label);
                    getPath();
                    HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
                    for (int i =0;i<list.size()-1;i++){
                        stringStringHashMap.put(i+"",list.get(i));
                    }
                    String act = GSON.gson.toJson(help);
                    Request request = OKHttp.buildPostRequest(OkHttpUtils.baseURL + "/help/publish", "help", act, stringStringHashMap, 0);
                    OKHttp.client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String string = response.body().string();
                            Result<Boolean> result = GSON.gson.fromJson(string,new TypeToken<Result<Boolean>>(){}.getType());
                            if(result!=null&&result.getData()!=null&&result.getData()){
                                Toast.makeText(CreateNewHelpActivity.this, "发布成功~", Toast.LENGTH_LONG).show();
                                toHelpCenterActivity();
                            }else if(result!=null&&result.getMessage()!=null){
                                Toast.makeText(CreateNewHelpActivity.this,result.getMessage(),Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(CreateNewHelpActivity.this,"网络异常",Toast.LENGTH_LONG).show();
                            }
                            toHelpCenterActivity();
                        }
                    });

                }


            }
        });
    }

    private void initView() {
        title1 = findViewById(R.id.id_title2);
        title = findViewById(R.id.id_et_title);
        content = findViewById(R.id.id_et_content);
        publish = findViewById(R.id.id_btn_publish);
        startTime = findViewById(R.id.id_et_startDate);
        mPaidFlowTagLayout = findViewById(R.id.flowlayout_single_select_paid);
        initPaidFlowTagLayout();

        mSingleFlowTagLayout = findViewById(R.id.flowlayout_single_select);
        initLabelFlowTagLayout();

        mRecyclerView = findViewById(R.id.id_recycler_view);
        task = new Task();
        taskBiz = new TaskBiz();
        baseTask = new BaseTask();
        task.setContext(baseTask);
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(new Date());
        endDate.setTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 7));
        pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                EditText tv = (EditText) v;
                tv.setText(Config.dateFormat.format(date));
                if(tv == startTime){
                    task.getContext().setStartDate(date);
                }else if(tv == endTime){
                    task.getContext().setEndDate(date);
                }
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .setLabel(" 年", "月", "日", "时", "分", "")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();

    }
    private void upload()  {
        Bitmap bitmap;
        Bitmap bmpCompressed;
        for (int i = 0; i < list.size() - 1; i++) {
            System.out.println("i-----" + i);
            bitmap = BitmapFactory.decodeFile(list.get(i));
            bmpCompressed = Bitmap.createScaledBitmap(bitmap, 800, 800, false);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            System.out.println("data" + data);
        }
    }

    private void initPaidFlowTagLayout() {
        FlowTagPaidAdapter tagAdapter = new FlowTagPaidAdapter(CreateNewHelpActivity.this);
        mPaidFlowTagLayout.setAdapter(tagAdapter);
        mPaidFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mPaidFlowTagLayout.setOnTagSelectListener((parent, position, selectedList) -> {
            if (selectedList.size()>0){
                Paid = position;
            }
        });
        tagAdapter.addTags(ResUtils.getStringArray(CreateNewHelpActivity.this, R.array.tags_values_paid));

    }

    private void initLabelFlowTagLayout() {
        FlowTagAdapter tagAdapter = new FlowTagAdapter(CreateNewHelpActivity.this);
        mSingleFlowTagLayout.setAdapter(tagAdapter);
        mSingleFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mSingleFlowTagLayout.setOnTagSelectListener((parent, position, selectedList) -> {
            if (selectedList.size()>0){
                Label = (String)parent.getAdapter().getItem(position);
            }
            System.out.println("label"+Label);
        });
        tagAdapter.addTags(ResUtils.getStringArray(CreateNewHelpActivity.this, R.array.tags_values));

    }


    private void setSelectedText(FlowTagLayout parent, List<Integer> selectedList) {



    }

    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
//                            PictureSelector.create(CreateNewTaskActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(CreateNewHelpActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(CreateNewHelpActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(CreateNewHelpActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @SuppressLint("CheckResult")
        @Override
        public void onAddPicClick() {
            //获取写的权限
            RxPermissions rxPermission = new RxPermissions(CreateNewHelpActivity.this);
            rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) {
                            if (permission.granted) {// 用户已经同意该权限
                                //第一种方式，弹出选择和拍照的dialog
                                showPop();

                                //第二种方式，直接进入相册，但是 是有拍照得按钮的
//                                showAlbum();
                            } else {
                                Toast.makeText(CreateNewHelpActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    private void showAlbum() {
        //参数很多，根据需要添加
        PictureSelector.create(CreateNewHelpActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.selectionMedia(selectList)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(false) // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void showPop() {
        View bottomView = View.inflate(CreateNewHelpActivity.this, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
//        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.picker_view_slide_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(CreateNewHelpActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
//                    case R.id.tv_camera:
//                        //拍照
//                        System.out.println("22222");
//                        PictureSelector.create(CreateNewTaskActivity.this)
//                                .openCamera(PictureMimeType.ofImage())
//                                .forResult(PictureConfig.CHOOSE_REQUEST);
//                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
//        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    private void getPath(){
        for (int i = 0;i < selectList.size()-1;i++){
            list.add(selectList.get(i).getPath());
            System.out.println(list.get(i));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<LocalMedia> images;
        System.out.println("rc"+resultCode+requestCode);

        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                System.out.println("p1111");
                System.out.println(data);
//                final Uri uri = data.getData();
//                String path = ImageTool.getImageAbsolutePath(this,uri);
//                System.out.println(path);
                images = PictureSelector.obtainMultipleResult(data);
                System.out.println(images);
                selectList.addAll(images);
                System.out.println(selectList);
                //selectList = PictureSelector.obtainMultipleResult(data);

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }

    }



    private void toHelpCenterActivity(){
        Intent intent = new Intent(this,HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }

}