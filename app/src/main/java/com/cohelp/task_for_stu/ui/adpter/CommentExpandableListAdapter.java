package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.cohelp.task_for_stu.bean.Comment;

import java.util.List;

public class CommentExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Comment> commentList;
    private Context context;


    public CommentExpandableListAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    public CommentExpandableListAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public CommentExpandableListAdapter() {
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int getGroupCount() {
        return commentList==null?0:commentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (commentList.get(i).get == null) return 0;
        else return commentList.get(i).getChldrenList().size()>0?commentList.get(i).getChldrenList().size():0;
    }

    @Override
    public Object getGroup(int i) {
        return commentList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentList.get(i).getChldrenList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
