package com.cohelp.task_for_stu.ui.activity.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.TimePickerView;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.FullyGridLayoutManager;
import com.cohelp.task_for_stu.ui.adpter.GridImageAdapter;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateNewTaskActivity extends BaseActivity  {
    EditText title;
    EditText content;
    EditText reward;
    EditText startTime;
    EditText endTime;
    Button publish;
    TaskBiz taskBiz;
    Task task;
    Activity activity;
    BaseTask baseTask;
    TimePickerView pickerView;
    OkHttpUtils okHttpUtils = new OkHttpUtils();


    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private RecyclerView mRecyclerView;
    private PopupWindow pop;



    private static final int IMG_COUNT = 8;
    private static final String IMG_ADD_TAG = "a";
    private GridView gridView;
    //    private GridAdapter adapterr;
//    private GVAdapter adapter;
    private ImageView img;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
        askPermissions();

        setUpToolBar();
        setTitle("????????????");
        initView();
        initEvent();
//        takePhoto();
        initWidget();
//        takePhoto();
    }
    private void initEvent() {
//        endTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickerView.show(endTime);
//            }
//        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerView.show(startTime);
            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String te = title.getText().toString();
                String ct = content.getText().toString();
                String pt = startTime.getText().toString();
//                String lb = label.getText().toString();
                System.out.println("te"+te);
                System.out.println("ct"+ct);
                if(te.isEmpty() || ct.isEmpty() || pt.isEmpty()) {
                    Toast.makeText(CreateNewTaskActivity.this, "???????????????????????????\n???????????????!", Toast.LENGTH_LONG).show();
                    System.out.println("ssss");
                }else {
                    getPath();
                    LocalDateTime dateTime=LocalDateTime.parse(pt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    activity = new Activity(null,null,te,ct,dateTime,null,null,null,null,null,null);
                    HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
                    for (int i =0;i<list.size()-1;i++){
                        stringStringHashMap.put(i+"",list.get(i));
                    }
                    new Thread(()->{
                        okHttpUtils.activityPublish(activity,stringStringHashMap);
                    }).start();

                    toTaskCenterActivity();
                }

            }
        });

//        initData();
    }

    private void initView() {
        title = findViewById(R.id.id_et_title);
        content = findViewById(R.id.id_et_content);
        publish = findViewById(R.id.id_btn_submit);
        startTime = findViewById(R.id.id_et_startDate);
//        endTime = findViewById(R.id.id_et_endDate);
        gridView = findViewById(R.id.gridview);
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
                .setLabel(" ???", "???", "???", "???", "???", "")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();

//        GridLayoutManager manager = new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(mAdapter = new ImageSelectGridAdapter(this, this));
//        mAdapter.setSelectList(mSelectList);
//        mAdapter.setSelectMax(8);
//        mAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(CreateNewTaskActivity.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));

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
                            // ???????????? ???????????????????????????
//                            PictureSelector.create(CreateNewTaskActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(CreateNewTaskActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // ????????????
                            PictureSelector.create(CreateNewTaskActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // ????????????
                            PictureSelector.create(CreateNewTaskActivity.this).externalPictureAudio(media.getPath());
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
            //??????????????????
            RxPermissions rxPermission = new RxPermissions(CreateNewTaskActivity.this);
            rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) {
                            if (permission.granted) {// ???????????????????????????
                                //??????????????????????????????????????????dialog
                                showPop();

                                //????????????????????????????????????????????? ????????????????????????
//                                showAlbum();
                            } else {
                                Toast.makeText(CreateNewTaskActivity.this, "??????", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    private void showAlbum() {
        //?????????????????????????????????
        PictureSelector.create(CreateNewTaskActivity.this)
                .openGallery(PictureMimeType.ofImage())// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                .maxSelectNum(maxSelectNum)// ????????????????????????
                .minSelectNum(1)// ??????????????????
                .imageSpanCount(4)// ??????????????????
                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ??????PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// ?????????????????????
                .isCamera(true)// ????????????????????????
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                //.setOutputCameraPath("/CustomPath")// ???????????????????????????
                .enableCrop(true)// ????????????
                .compress(true)// ????????????
                //.sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .glideOverride(160, 160)// glide ???????????????????????????????????????????????????????????????????????????????????????
                .withAspectRatio(1, 1)// ???????????? ???16:9 3:2 3:4 1:1 ????????????
                //.selectionMedia(selectList)// ????????????????????????
                //.previewEggs(false)// ??????????????? ????????????????????????????????????(???????????????????????????????????????????????????)
                //.cropCompressQuality(90)// ?????????????????? ??????100
                //.compressMaxKB()//???????????????kb compressGrade()???Luban.CUSTOM_GEAR??????
                //.compressWH() // ??????????????? compressGrade()???Luban.CUSTOM_GEAR??????
                //.cropWH()// ???????????????????????????????????????????????????????????????
                .rotateEnabled(false) // ???????????????????????????
                //.scaleEnabled()// ?????????????????????????????????
                //.recordVideoSecond()//?????????????????? ??????60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//????????????onActivityResult code
    }

    private void showPop() {
        View bottomView = View.inflate(CreateNewTaskActivity.this, R.layout.layout_bottom_dialog, null);
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
                        //??????
                        PictureSelector.create(CreateNewTaskActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
//                    case R.id.tv_camera:
//                        //??????
//                        System.out.println("22222");
//                        PictureSelector.create(CreateNewTaskActivity.this)
//                                .openCamera(PictureMimeType.ofImage())
//                                .forResult(PictureConfig.CHOOSE_REQUEST);
//                        break;
                    case R.id.tv_cancel:
                        //??????
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
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// ????????????????????????
                System.out.println("p1111");
                System.out.println(data);
                images = PictureSelector.obtainMultipleResult(data);
                System.out.println(images);
                selectList.addAll(images);
                System.out.println(selectList);
                //selectList = PictureSelector.obtainMultipleResult(data);

                // ?????? LocalMedia ??????????????????path
                // 1.media.getPath(); ?????????path
                // 2.media.getCutPath();????????????path????????????media.isCut();?????????true
                // 3.media.getCompressPath();????????????path????????????media.isCompressed();?????????true
                // ????????????????????????????????????????????????????????????????????????????????????
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }

    }





    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }


    private void askPermissions() {//?????????????????????
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,//??????????????????
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};//??????SD?????????
            //????????????????????????
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //????????????
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }

    }




}