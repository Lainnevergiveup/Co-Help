package com.cohelp.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;

import java.util.List;

public class CardViewAskListAdapter extends RecyclerView.Adapter<CardViewAskListAdapter.ViewHolder> {

    private List<AskVO> AskVOListList;
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    Bitmap bitmap;
    Context context;
    /**
     * 是否是管理模式，默认是false
     */
    private boolean mIsManageMode;

    /**
     * 记录选中的信息
     */
    private SparseBooleanArray mSparseArray = new SparseBooleanArray();

    private boolean mIsSelectAll;

//    private OnAllSelectStatusChangedListener mListener;

     public Fragment fragment;




    private LayoutInflater eInflater;
    public OnItemListenter mItemClickListener;

    public CardViewAskListAdapter(List<AskVO> AskVOListList, Context context) {
        this.AskVOListList = AskVOListList;
        this.context = context;
        this.eInflater = LayoutInflater.from(context);
    }

    public CardViewAskListAdapter(List<AskVO> AskVOListList) {
        this.AskVOListList = AskVOListList;
    }

    public CardViewAskListAdapter(List<AskVO> AskVOListList, Context context, Fragment fragment) {
        this.AskVOListList = AskVOListList;
        this.context = context;
        this.fragment = fragment;
    }

    public CardViewAskListAdapter() {
    }

    public void setAskVOList(List<AskVO> AskVOListList) {
        this.AskVOListList = AskVOListList;
    }

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    @NonNull
    @Override
    public CardViewAskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cardlist_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CardViewAskListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AskVO AskVO = AskVOListList.get(position);


        holder.authorAvator.setImageURL(AskVO.getAvatarUrl());
        List<String> imageList = AskVO.getImageUrl();
//        System.out.println("imagelist="+imageList);
        if (imageList!=null&&imageList.size()>0){
//            System.out.println(1);
            holder.firstImage.setImageURL(imageList.get(0));
        }
//        System.out.println(AskVO.getReadNum());
        holder.readNumber.setText("");

        if (AskVO!=null){
//            System.out.println("id="+holeVO.getId());
            holder.authorName.setText(AskVO.getUserName());
            holder.title.setText(AskVO.getQuestion());
            holder.summary.setText("");
            holder.commentNumber.setText(AskVO.getAnswerCount().toString());
            holder.praiseNumber.setText(AskVO.getLikeCount().toString());
            holder.collectNumber.setText(AskVO.getCollectCount().toString());
            holder.tag.setText(AskVO.getSemester());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
//        System.out.println("detail"+AskVOListList);
        if (AskVOListList!=null)
        return AskVOListList.size();
        else return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        NetRadiusImageView authorAvator;
        NetRadiusImageView firstImage;
        AppCompatImageView praiseIcon;
        AppCompatImageView collectIcon;
        AppCompatImageView commentIcon;
        TextView authorName;
        TextView tag;
        TextView title;
        TextView summary;
        TextView praiseNumber;
        TextView commentNumber;
        TextView collectNumber;
        TextView readNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            authorAvator = itemView.findViewById(R.id.cardView_author_pic);
            authorName = itemView.findViewById(R.id.cardView_author_name);
            tag = itemView.findViewById(R.id.cardView_tag);
            title = itemView.findViewById(R.id.cardView_title);
            summary = itemView.findViewById(R.id.cardView_summary);
            firstImage = itemView.findViewById(R.id.cardView_firstImage);
            praiseIcon = itemView.findViewById(R.id.cardView_praiseIcon);
            collectIcon = itemView.findViewById(R.id.cardView_collectIcon);
            commentIcon = itemView.findViewById(R.id.cardView_commentIcon);
            praiseNumber = itemView.findViewById(R.id.cardView_praiseNumber);
            collectNumber = itemView.findViewById(R.id.cardView_collectNumber);
            commentNumber = itemView.findViewById(R.id.cardView_commentNumber);
            readNumber = itemView.findViewById(R.id.cardView_readNumber);
        }
    }

}
