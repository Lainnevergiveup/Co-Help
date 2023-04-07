package com.cohelp.task_for_stu.ui.adpter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.ui.holder.GridImageViewHolder;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;
import com.xuexiang.xui.adapter.simple.ViewHolder;

import java.util.List;

public class GridViewImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageURLlist;

    public GridViewImageAdapter(Context context, List<String> imageURLlist) {
        this.context = context;
        this.imageURLlist = imageURLlist;
    }

    public GridViewImageAdapter() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setImageURLlist(List<String> imageURLlist) {
        this.imageURLlist = imageURLlist;
    }

    @Override
    public int getCount() {
        return imageURLlist.size();
    }

    @Override
    public Object getItem(int i) {
        return imageURLlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        GridImageViewHolder holder;
//        if (view == null){
//            view = LayoutInflater.from(context).inflate(R.layout.activity_detail,viewGroup,false);
//            holder = new GridImageViewHolder();
//            holder.netRadiusImageView = new NetRadiusImageView(context);
//            holder.netRadiusImageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
//            holder.netRadiusImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }else {
//            holder = new GridImageViewHolder();
//        }
//
//        String s = imageURLlist.get(i);
//        holder.netRadiusImageView.setImageURL(s);
//        holder.netRadiusImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bitmap bitmap0 = ((BitmapDrawable)holder.netRadiusImageView.getDrawable()).getBitmap();
//                bigImageLoader(bitmap0);
//            }
//        });
        NetRadiusImageView imageView;
        if (view == null) {
            imageView = new NetRadiusImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (NetRadiusImageView) view;
        }
        imageView.setImageURL(imageURLlist.get(i));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap0 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                bigImageLoader(bitmap0);
            }
        });
        return imageView;

    }
    private void bigImageLoader(Bitmap bitmap){
        final Dialog dialog = new Dialog(context);
        ImageView image = new ImageView(context);
        image.setImageBitmap(bitmap);
        dialog.setContentView(image);
        //将dialog周围的白块设置为透明
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //显示
        dialog.show();
        //点击图片取消
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.cancel();
            }
        });
    }
}
