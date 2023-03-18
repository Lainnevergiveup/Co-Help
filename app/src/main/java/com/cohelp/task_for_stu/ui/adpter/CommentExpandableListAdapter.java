package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.vo.RemarkVO;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;
import com.cohelp.task_for_stu.ui.view.CircleImageView;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;
import com.cohelp.task_for_stu.utils.T;

import java.util.List;

public class CommentExpandableListAdapter extends BaseExpandableListAdapter {

    private List<List<RemarkVO>> commentList;
    private List<RemarkVO>  rootList;
    private Context context;


    public CommentExpandableListAdapter(List<List<RemarkVO>> commentList, List<RemarkVO> rootList, Context context) {
        this.commentList = commentList;
        this.rootList = rootList;
        this.context = context;
    }

    public CommentExpandableListAdapter() {
    }

    public void setCommentList(List<List<RemarkVO>> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int getGroupCount() {
        return commentList==null?0:commentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return commentList.get(i)!=null?commentList.get(i).size():0;
    }

    @Override
    public Object getGroup(int i) {
        return commentList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return getCombinedChildId(i, i1);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(view);
            view.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) view.getTag();
        }
        groupHolder.logo.setImageURL(rootList.get(i).getRemarkOwnerAvatar());
        groupHolder.tv_name.setText(rootList.get(i).getRemarkOwnerName());
        groupHolder.tv_time.setText(rootList.get(i).getRemarkTime().toString());
        groupHolder.tv_content.setText(rootList.get(i).getRemarkContent());
        groupHolder.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("report!!!");
            }
        });
//        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isLike){
//                    isLike = false;
//                    groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
//                }else {
//                    isLike = true;
//                    groupHolder.iv_like.setColorFilter(Color.parseColor("#FF5C5C"));
//                }
//            }
//        });
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout,viewGroup, false);
            childHolder = new ChildHolder(view);
            view.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) view.getTag();
        }
        RemarkVO remarkVO = commentList.get(i).get(i1);
        String replyUser = remarkVO.getRemarkOwnerName();
        if(!TextUtils.isEmpty(replyUser)){
            childHolder.tv_sourseName.setText(replyUser);
        }
//        childHolder.logo.setImageURL(remarkVO.getRemarkOwnerAvatar());
        childHolder.tv_content.setText(remarkVO.getRemarkContent());
        childHolder.tv_targetName.setText(remarkVO.getRemarkTargetName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private NetRadiusImageView logo;
        private TextView  tv_name,tv_content, tv_time;
        private ImageView reportButton;
        private AvatorImageView iv_like;
        public GroupHolder(View view) {
            logo =  view.findViewById(R.id.comment_item_logo);
            tv_content = view.findViewById(R.id.comment_item_content);
            tv_name = view.findViewById(R.id.comment_item_userName);
            tv_time = view.findViewById(R.id.comment_item_time);
            reportButton = view.findViewById(R.id.btn_comment_report);
//            iv_like = view.findViewById(R.id.comment_item_like);
        }
    }

    private class ChildHolder{
        private NetRadiusImageView logo;
        private TextView tv_sourseName,tv_targetName,tv_content;
        public ChildHolder(View view) {
//            logo =  view.findViewById(R.id.reply_item_logo);
            tv_sourseName = (TextView) view.findViewById(R.id.reply_item_userSourse);
            tv_targetName = (TextView) view.findViewById(R.id.reply_item_userTarget);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }

}
