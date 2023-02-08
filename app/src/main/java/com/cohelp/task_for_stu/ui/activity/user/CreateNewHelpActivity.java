package com.cohelp.task_for_stu.ui.activity.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.cohelp.task_for_stu.bean.BaseTask;
import com.cohelp.task_for_stu.biz.TaskBiz;
import com.cohelp.task_for_stu.config.Config;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.ui.vo.Task;
import com.cohelp.task_for_stu.utils.ImageTool;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateNewHelpActivity extends BaseActivity {

    EditText title;
    EditText content;
    EditText reward;
    EditText startTime;
    EditText endTime;
    CheckBox lb_money;
    CheckBox lb_nomoney;
    EditText lb_diy;
    Button publish;
    TaskBiz taskBiz;
    Task task;
    BaseTask baseTask;
    TimePickerView pickerView;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    private static final int IMG_COUNT = 8;
    private static final String IMG_ADD_TAG = "a";
    private GridView gridView;
    //    private GridAdapter adapterr;
    private GVAdapter adapter;
    private ImageView img;
    private List<String> list,list_lb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_help);
        askPermissions();
        setUpToolBar();
        setTitle("创建互助");
        initView();
        initEvent();
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String te = title.getText().toString();
                String ct = content.getText().toString();
                String pt = startTime.getText().toString();
//                String lb = label.getText().toString();
                if(lb_money.isChecked()){
                    list_lb.add("有偿");
                }
                if(lb_nomoney.isChecked()){
                    list_lb.add("无偿");
                }
                HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
                for (int i =0;i<list.size()-1;i++){
                    stringStringHashMap.put(i+"",list.get(i));
                }
                new Thread(()->{
                    okHttpUtils.activityPublish(new Activity(null,null,"nice","wow", LocalDateTime.now(),0,0,"",0,0,null),stringStringHashMap);
                }).start();
                upload();
                toHelpCenterActivity();
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
        lb_money = findViewById(R.id.id_cb_money);
        lb_nomoney = findViewById(R.id.id_cb_no_money);
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