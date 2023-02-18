package com.cohelp.task_for_stu.ui.activity.user;

import androidx.annotation.RequiresApi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.ImageTool;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.T;
import com.xuexiang.xui.widget.shadow.ShadowButton;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.ToString;

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
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    private static final int IMG_COUNT = 8;
    private static final String IMG_ADD_TAG = "a";
    private GridView gridView;
    //    private GridAdapter adapterr;
    private GVAdapter adapter;
    private ImageView img;
    private List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_help);
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
        askPermissions();
        setUpToolBar();
        setTitle("创建互助");
        initView();
        initEvent();
    }
    private void initEvent() {

        paid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.id_rb_paid:
                        Paid = 1;
                        break;
                    case R.id.id_rb_nopaid:
                        Paid = 0;
                        break;
                }
            }
        });

        label.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.id_rb_1:
                        Label = lb1.getText().toString();
                        break;
                    case R.id.id_rb_2:
                        Label = lb2.getText().toString();
                        break;
                    case R.id.id_rb_3:
                        Label = lb3.getText().toString();
                        break;
                    case R.id.id_rb_4:
                        Label = lb4.getText().toString();
                        break;
                    case R.id.id_rb_5:
                        Label = lb_diy.getText().toString();
                        break;
                }
            }
        });


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

                    HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
                    for (int i =0;i<list.size()-1;i++){
                        stringStringHashMap.put(i+"",list.get(i));
                    }
                    new Thread(()->{
                        okHttpUtils.helpPublish(help,stringStringHashMap);
                    }).start();
                    upload();
                    toHelpCenterActivity();
                }


            }
        });
        lb_diy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        initData();
    }
    private void initView() {
        title = findViewById(R.id.id_et_title);
        content = findViewById(R.id.id_et_content);
        publish = findViewById(R.id.id_btn_submit);
        startTime = findViewById(R.id.id_et_startDate);
//        endTime = findViewById(R.id.id_et_endDate);
        lb_money = findViewById(R.id.id_rb_paid);
        lb_nomoney = findViewById(R.id.id_rb_nopaid);
        paid = findViewById(R.id.id_rg_paid);
        lb1 = findViewById(R.id.id_rb_1);
        lb2 = findViewById(R.id.id_rb_2);
        lb3 = findViewById(R.id.id_rb_3);
        lb4 = findViewById(R.id.id_rb_4);
        lb5 = findViewById(R.id.id_rb_5);
        label = findViewById(R.id.id_rg_label);
        lb_diy = findViewById(R.id.id_et_diy);
        gridView = findViewById(R.id.gridview);
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

    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
            list.add(IMG_ADD_TAG);
        }
        adapter = new GVAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).equals(IMG_ADD_TAG)) {
                    if (list.size() < IMG_COUNT) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 0);
                    } else {
                        Toast.makeText(CreateNewHelpActivity.this, "最多只能放7张照片", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void toHelpCenterActivity(){
        Intent intent = new Intent(this,HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void refreshAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GVAdapter();
        }
        adapter.notifyDataSetChanged();
    }



    private class GVAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final GVAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplication()).inflate(R.layout.activity_add_photo, parent, false);
                holder = new GVAdapter.ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
                convertView.setTag(holder);
            } else {
                holder = (GVAdapter.ViewHolder) convertView.getTag();
            }
            String s = list.get(position);
            if (!s.equals(IMG_ADD_TAG)) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
            } else {
                holder.checkBox.setVisibility(View.GONE);
                holder.imageView.setImageResource(R.drawable.add);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    refreshAdapter();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            System.out.println("data null");
            return;
        }
        if (requestCode == 0) {
            final Uri uri = data.getData();
            String path = ImageTool.getImageAbsolutePath(this,uri);
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            System.out.println(bitmap);
            System.out.println("path:" + path);
            if (list.size() == IMG_COUNT) {
                removeItem();
                refreshAdapter();
                return;
            }
            list.remove(IMG_ADD_TAG);
            list.add(path);
            list.add(IMG_ADD_TAG);

            refreshAdapter();
        }
    }

    private void removeItem() {
        if (list.size() != IMG_COUNT) {
            if (list.size() != 0) {
                list.remove(list.size() - 1);
            }
        }


    }

    private void askPermissions() {//动态申请权限！
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,//联系人的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};//读写SD卡权限
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }

    }
}