package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.ui.activity.user.BasicInfoActivity;
import com.cohelp.task_for_stu.ui.activity.user.DetailActivity;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;
import com.cohelp.task_for_stu.ui.vo.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private List<DetailResponse> activityList;
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    Bitmap bitmap;
    Context context;
    public List<DetailResponse> getActivityList() {
        return activityList;
    }



    private LayoutInflater eInflater;
    public OnItemListenter mItemClickListener;

    public ActivityAdapter(Context context, List<DetailResponse> taskList) {
        this.context = context;
        this.activityList = taskList;
        this.eInflater = LayoutInflater.from(context);
    }
    public ActivityAdapter(List<DetailResponse> activityList) {
        this.activityList = activityList;
    }

    public ActivityAdapter() {

    }

    public void setActivityList(List<DetailResponse> activityList) {
        this.activityList = activityList;
    }

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        AvatorImageView ownerAvater;
        TextView activityTitle;
        TextView activityAddress;
        TextView ownerName;
        TextView activityTime;
        TextView activityContext;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ownerAvater = itemView.findViewById(R.id.act_author_pic);
            activityTime = itemView.findViewById(R.id.act_time);
            activityAddress = itemView.findViewById(R.id.act_address);
            activityTitle = itemView.findViewById(R.id.act_title);
            activityContext = itemView.findViewById(R.id.act_context);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("listen");
//                Intent intent = new Intent(itemView.getContext(),DetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("detailResponse",);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                }
//            });
        }
    }
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_activity_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("listen");
//                Intent intent = new Intent(viewHolder.itemView.getContext(),DetailActivity.class);
//                Bundle bundle = new Bundle();
////                bundle.putSerializable("detailResponse",activityList.get());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, int position) {
        DetailResponse detailResponse = activityList.get(position);
        ActivityVO activityVO = detailResponse.getActivityVO();
        System.out.println(detailResponse.getPublisherAvatarUrl());
        holder.ownerAvater.setImageURL(detailResponse.getPublisherAvatarUrl());
        holder.activityContext.setText(activityVO.getActivityDetail());
        holder.activityTitle.setText(activityVO.getActivityTitle());
        holder.activityTime.setText(activityVO.getActivityTime().toString());
        System.out.println(position);
//        holder.activityAddress.setText(activityVO);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return activityList.size();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    System.out.println("handler excute");
                    bitmap = (Bitmap) msg.obj;
                    System.out.println("hd"+bitmap);
                    break;
                case NETWORK_ERROR:
                    break;
                case SERVER_ERROR:
                    break;
            }
        }
    };
    //设置网络图片
    public synchronized void setImageURL(final String path,ImageView imageView) {

        //开启一个线程用于联网
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    //把传过来的路径转成URL
                    URL url = new URL(path);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为10秒
                    connection.setConnectTimeout(10000);
                    //获取返回码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //利用Message把图片发给Handler
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        msg.what = GET_DATA_SUCCESS;
                        handler.sendMessage(msg);
                        inputStream.close();
                    } else {
                        //服务器发生错误
                        handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //网络连接错误
                    handler.sendEmptyMessage(NETWORK_ERROR);
                }
            }

        };
        thread.start();
        thread.setPriority(Thread.MAX_PRIORITY);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
