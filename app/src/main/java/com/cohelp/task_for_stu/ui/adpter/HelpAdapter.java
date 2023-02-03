package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.HelpVO;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {


    private List<DetailResponse> helpList;

    Bitmap bitmap;
    Context context;
    private LayoutInflater eInflater;

    public OnItemListenter mItemClickListener;

    public HelpAdapter(Context context, List<DetailResponse> taskList) {
        this.context = context;
        this.helpList = taskList;
        this.eInflater = LayoutInflater.from(context);
    }
    public HelpAdapter(List<DetailResponse> helpList) {
        this.helpList = helpList;
    }
    public void setHelpList(List<DetailResponse> helpList) {
        this.helpList = helpList;
    }

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        AvatorImageView ownerAvater;
        TextView helpTitle;
        TextView helpPaid;
        TextView ownerName;
        TextView helpLable;
        TextView helpContext;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ownerName = itemView.findViewById(R.id.help_author_name);
            ownerAvater = itemView.findViewById(R.id.help_author_pic);
            helpLable = itemView.findViewById(R.id.help_lable);
            helpPaid = itemView.findViewById(R.id.help_paid);
            helpTitle = itemView.findViewById(R.id.help_title);
            helpContext = itemView.findViewById(R.id.help_context);
        }
    }
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_help_card,parent,false);
        HelpAdapter.ViewHolder viewHolder = new HelpAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.ViewHolder holder, int position) {
        DetailResponse detailResponse = helpList.get(position);
        HelpVO  helpVO = detailResponse.getHelpVO();
    //        holder.ownerAvater.setImageURI("");

        holder.helpContext.setText(helpVO.getHelpDetail());
        holder.helpTitle.setText(helpVO.getHelpTitle());
        holder.helpLable.setText(helpVO.getHelpLabel());
        if (helpVO.getHelpPaid()==1)
        holder.helpPaid.setText("有偿");
        else{
            holder.helpPaid.setText("无偿");
        }
        holder.ownerName.setText(helpVO.getUserName());
        holder.ownerAvater.setImageURL(detailResponse.getPublisherAvatarUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
            }
        });
//        holder.activityAddress.setText(activityVO);
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }
}
