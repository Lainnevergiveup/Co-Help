package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
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

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {
    private List<DetailResponse> helpList;
    Context context;
    private LayoutInflater eInflater;
    public HelpAdapter(Context context, List<DetailResponse> taskList) {
        this.context = context;
        this.helpList = taskList;
        this.eInflater = LayoutInflater.from(context);
    }
    public HelpAdapter(List<DetailResponse> helpList) {
        this.helpList = helpList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ownerAvater;
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
        }
    }
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_activity_card,parent,false);
        HelpAdapter.ViewHolder viewHolder = new HelpAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.ViewHolder holder, int position) {
        DetailResponse detailResponse = helpList.get(position);
        ActivityVO activityVO = detailResponse.getActivityVO();
//        holder.ownerAvater.setImageURI("");

        holder.activityContext.setText(activityVO.getActivityDetail());
        holder.activityTitle.setText(activityVO.getActivityTitle());
        holder.activityTime.setText(activityVO.getActivityTime().toString());
//        holder.activityAddress.setText(activityVO);
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }
}
