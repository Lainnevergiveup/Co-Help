package com.cohelp.task_for_stu.ui.activity.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.FullyGridLayoutManager;
import com.cohelp.task_for_stu.ui.adpter.GridImageAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

public class CreateNewAskActivity extends BaseActivity {

    EditText title;
    EditText content;

    Button publish;
    TaskBiz taskBiz;


    BaseTask baseTask;

    TextView title1;
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
        setContentView(R.layout.activity_create_new_ask);


        setUpToolBar();
        setTitle("");
        initView();
        initEvent();
//        takePhoto();
        title1.setText("发布提问");
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

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String te = title.getText().toString();
                String ct = content.getText().toString();

//                String lb = label.getText().toString();
                System.out.println("te" + te);
                System.out.println("ct" + ct);
                if (te.isEmpty() || ct.isEmpty()) {
                    Toast.makeText(CreateNewAskActivity.this, "您输入的信息不完整\n请再次检查!", Toast.LENGTH_LONG).show();
                    System.out.println("ssss");
                } else {
                    getPath();
                }

            }
        });

//        initData();
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
                            PictureSelector.create(CreateNewAskActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(CreateNewAskActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(CreateNewAskActivity.this).externalPictureAudio(media.getPath());
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
            RxPermissions rxPermission = new RxPermissions(CreateNewAskActivity.this);
            rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new io.reactivex.functions.Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) {
                            if (permission.granted) {// 用户已经同意该权限
                                //第一种方式，弹出选择和拍照的dialog
                                showPop();

                                //第二种方式，直接进入相册，但是 是有拍照得按钮的
//                                showAlbum();
                            } else {
                                Toast.makeText(CreateNewAskActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    private void initView() {
        title1 = findViewById(R.id.id_title2);
        title = findViewById(R.id.id_et_title);
        content = findViewById(R.id.id_et_content);
        publish = findViewById(R.id.id_btn_publish);

//        endTime = findViewById(R.id.id_et_endDate);
        gridView = findViewById(R.id.gridview);
        mRecyclerView = findViewById(R.id.id_recycler_view);

        taskBiz = new TaskBiz();
        baseTask = new BaseTask();


    }

    private void showPop() {
        View bottomView = View.inflate(CreateNewAskActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(CreateNewAskActivity.this)
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

    private void getPath() {
        for (int i = 0; i < selectList.size() - 1; i++) {
            list.add(selectList.get(i).getPath());
            System.out.println(list.get(i));
        }
    }
}