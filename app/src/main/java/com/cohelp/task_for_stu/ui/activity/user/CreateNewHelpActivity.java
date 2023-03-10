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
import android.widget.RadioGroup;
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
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.FlowTagAdapter;
import com.cohelp.task_for_stu.ui.adpter.FlowTagPaidAdapter;
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
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateNewHelpActivity extends BaseActivity{

    EditText title;
    EditText content;
    EditText startTime;
    EditText endTime;
    RadioButton lb_money;
    RadioButton lb_nomoney;
    RadioGroup paid,label;
    int Paid=-1;
    String Label;
    RadioButton lb1,lb2,lb3,lb4,lb5;
    EditText lb_diy;
    Button publish;
    TaskBiz taskBiz;
    Task task;
    Help help;
    BaseTask baseTask;
    TimePickerView pickerView;
    FlowTagLayout mSingleFlowTagLayout;
    FlowTagLayout mPaidFlowTagLayout;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
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
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
//        askPermissions();
        setUpToolBar();
        setTitle("????????????");
        initView();
        initEvent();
        initWidget();
    }
    private void initEvent() {

//        paid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.id_rb_paid:
//                        Paid = 1;
//                        break;
//                    case R.id.id_rb_nopaid:
//                        Paid = 0;
//                        break;
//                }
//            }
//        });

//        label.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.id_rb_1:
//                        Label = lb1.getText().toString();
//                        break;
//                    case R.id.id_rb_2:
//                        Label = lb2.getText().toString();
//                        break;
//                    case R.id.id_rb_3:
//                        Label = lb3.getText().toString();
//                        break;
//                    case R.id.id_rb_4:
//                        Label = lb4.getText().toString();
//                        break;
//                    case R.id.id_rb_5:
//                        Label = lb_diy.getText().toString();
//                        break;
//                }
//            }
//        });


        publish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                String te = title.getText().toString();
                String ct = content.getText().toString();
                if(te.isEmpty() || Paid==-1 || Label.isEmpty()){
                    Toast.makeText(CreateNewHelpActivity.this,"???????????????????????????\n???????????????!",Toast.LENGTH_LONG).show();
                    System.out.println("ssss");
                }else {
                    help = new Help(te,ct,Paid,null,null,null,Label);
                    getPath();
                    HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
                    for (int i =0;i<list.size()-1;i++){
                        stringStringHashMap.put(i+"",list.get(i));
                    }
                    new Thread(()->{
                        okHttpUtils.helpPublish(help,stringStringHashMap);
                    }).start();
                    toHelpCenterActivity();
                }


            }
        });
//        lb_diy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void initView() {
        title = findViewById(R.id.id_et_title);
        content = findViewById(R.id.id_et_content);
        publish = findViewById(R.id.id_btn_submit);
        startTime = findViewById(R.id.id_et_startDate);
        mPaidFlowTagLayout = findViewById(R.id.flowlayout_single_select_paid);
        initPaidFlowTagLayout();
//        endTime = findViewById(R.id.id_et_endDate);
//        lb_money = findViewById(R.id.id_rb_paid);
//        lb_nomoney = findViewById(R.id.id_rb_nopaid);
//        paid = findViewById(R.id.id_rg_paid);
        mSingleFlowTagLayout = findViewById(R.id.flowlayout_single_select);
        initLabelFlowTagLayout();
//        lb1 = findViewById(R.id.id_rb_1);
//        lb2 = findViewById(R.id.id_rb_2);
//        lb3 = findViewById(R.id.id_rb_3);
//        lb4 = findViewById(R.id.id_rb_4);
//        lb5 = findViewById(R.id.id_rb_5);
//        label = findViewById(R.id.id_rg_label);
//        lb_diy = findViewById(R.id.id_et_diy);
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
        mPaidFlowTagLayout.setOnTagSelectListener((parent, position, selectedList) -> XToastUtils.toast(getSelectedText(parent, selectedList)));
        tagAdapter.addTags(ResUtils.getStringArray(CreateNewHelpActivity.this, R.array.tags_values_paid));
//        tagAdapter.setSelectedPositions(null);

    }

    private void initLabelFlowTagLayout() {
        FlowTagAdapter tagAdapter = new FlowTagAdapter(CreateNewHelpActivity.this);
        mSingleFlowTagLayout.setAdapter(tagAdapter);
        mSingleFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mSingleFlowTagLayout.setOnTagSelectListener((parent, position, selectedList) -> XToastUtils.toast(getSelectedText(parent, selectedList)));
        tagAdapter.addTags(ResUtils.getStringArray(CreateNewHelpActivity.this, R.array.tags_values));
//        tagAdapter.setSelectedPositions(null);

    }


    private String getSelectedText(FlowTagLayout parent, List<Integer> selectedList) {
        StringBuilder sb = new StringBuilder("??????????????????\n");
        for (int index : selectedList) {
            sb.append(parent.getAdapter().getItem(index));
            sb.append(";");
        }
        return sb.toString();
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
                            PictureSelector.create(CreateNewHelpActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // ????????????
                            PictureSelector.create(CreateNewHelpActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // ????????????
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
            //??????????????????
            RxPermissions rxPermission = new RxPermissions(CreateNewHelpActivity.this);
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
                                Toast.makeText(CreateNewHelpActivity.this, "??????", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    private void showAlbum() {
        //?????????????????????????????????
        PictureSelector.create(CreateNewHelpActivity.this)
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
                        //??????
                        PictureSelector.create(CreateNewHelpActivity.this)
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
//                final Uri uri = data.getData();
//                String path = ImageTool.getImageAbsolutePath(this,uri);
//                System.out.println(path);
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



    private void toHelpCenterActivity(){
        Intent intent = new Intent(this,HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }

}