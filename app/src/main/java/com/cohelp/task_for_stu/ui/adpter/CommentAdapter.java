package com.cohelp.task_for_stu.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.vo.RemarkVO;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;

import java.util.List;

public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<RemarkVO> commentList;

    public OnItemListenter mItemClickListener;


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

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_help_card,parent,false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        RemarkVO remark = commentList.get(position);
        holder.helpTitle.setText(remark.getTopicId());
        holder.helpContext.setText(remark.getRemarkContent());
        holder.ownerName.setText(remark.getRemarkOwnerName());
        holder.ownerAvater.setImageURL(remark.getRemarkOwnerAvatar());

        holder.helpPaid.setText(remark.getRemarkTime().toString().split("T")[0]+remark.getRemarkTime().toString().split("T")[1]);


    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public CommentAdapter(List<RemarkVO> commentList) {
        this.commentList = commentList;
    }

    public void setCommentList(List<RemarkVO> commentList) {
        this.commentList = commentList;
    }

    public void setmItemClickListener(OnItemListenter mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
