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
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.HelpVO;
import com.cohelp.task_for_stu.net.model.vo.HoleVO;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;

import java.util.List;

public class CardViewListAdapter extends RecyclerView.Adapter<CardViewListAdapter.ViewHolder>{

    private List<DetailResponse> detailResponseListList;
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

    public CardViewListAdapter(List<DetailResponse> detailResponseListList, Context context) {
        this.detailResponseListList = detailResponseListList;
        this.context = context;
        this.eInflater = LayoutInflater.from(context);
    }

    public CardViewListAdapter(List<DetailResponse> detailResponseListList) {
        this.detailResponseListList = detailResponseListList;
    }

    public CardViewListAdapter(List<DetailResponse> detailResponseListList, Context context, Fragment fragment) {
        this.detailResponseListList = detailResponseListList;
        this.context = context;
        this.fragment = fragment;
    }

    public CardViewListAdapter() {
    }

    public void setDetailResponseListList(List<DetailResponse> detailResponseListList) {
        this.detailResponseListList = detailResponseListList;
    }

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    @NonNull
    @Override
    public CardViewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cardlist_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DetailResponse detailResponse = detailResponseListList.get(position);

        ActivityVO activityVO = detailResponse.getActivityVO();
        HelpVO helpVO = detailResponse.getHelpVO();
        HoleVO holeVO = detailResponse.getHoleVO();

        holder.authorAvator.setImageURL(detailResponse.getPublisherAvatarUrl());
        List<String> imageList = detailResponse.getImagesUrl();
        System.out.println("imagelist="+imageList);
        if (imageList!=null&&imageList.size()>0){
            System.out.println(1);
            holder.firstImage.setImageURL(imageList.get(0));
        }
//        System.out.println(detailResponse.getReadNum());
        holder.readNumber.setText("阅读量 "+detailResponse.getReadNum().toString());
        if (activityVO!=null){
            System.out.println("id="+activityVO.getId());
            System.out.println(activityVO.getActivityComment());
            holder.authorName.setText(activityVO.getUserName());
            holder.title.setText(activityVO.getActivityTitle());
            holder.summary.setText(activityVO.getActivityDetail());
            holder.commentNumber.setText(activityVO.getActivityComment().toString());
            holder.praiseNumber.setText(activityVO.getActivityLike().toString());
            holder.collectNumber.setText(activityVO.getActivityCollect().toString());
            holder.tag.setText(activityVO.getActivityLabel());
        }
        if (helpVO!=null){
            System.out.println("id="+helpVO.getId());
            holder.authorName.setText(helpVO.getUserName());
            holder.title.setText(helpVO.getHelpTitle());
            holder.summary.setText(helpVO.getHelpDetail());
            holder.commentNumber.setText(helpVO.getHelpComment().toString());
            holder.praiseNumber.setText(helpVO.getHelpLike().toString());
            holder.collectNumber.setText(helpVO.getHelpCollect().toString());
            if (helpVO.getHelpPaid()==0)
            holder.tag.setText("无偿");
            else holder.tag.setText("有偿");

        }
        if (holeVO!=null){
            System.out.println("id="+holeVO.getId());
            holder.authorName.setText(holeVO.getUserName());
            holder.title.setText(holeVO.getHoleTitle());
            holder.summary.setText(holeVO.getHoleDetail());
            holder.commentNumber.setText(holeVO.getHoleComment().toString());
            holder.praiseNumber.setText(holeVO.getHoleLike().toString());
            holder.collectNumber.setText(holeVO.getHoleCollect().toString());
            holder.tag.setText(holeVO.getHoleLabel());
        }
//        holder.authorAvator.setimage;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println("detail"+detailResponseListList);
        if (detailResponseListList!=null)
        return detailResponseListList.size();
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


//    /**
//     * 切换管理模式
//     */
//    public void switchManageMode() {
//        setManageMode(!mIsManageMode);
//    }
//
//    /**
//     * 设置管理模式
//     *
//     * @param isManageMode 是否是管理模式
//     */
//    public void setManageMode(boolean isManageMode) {
//        if (mIsManageMode != isManageMode) {
//            mIsManageMode = isManageMode;
//            notifyDataSetChanged();
//            if (!mIsManageMode) {
//                // 退出管理模式时清除选中状态
//                mSparseArray.clear();
//                onAllSelectStatusChanged(false);
//            }
//        }
//    }
//
//    /**
//     * 进入管理模式
//     */
//    public void enterManageMode(int position) {
//        mSparseArray.append(position, true);
//        setManageMode(true);
//    }
//
//    /**
//     * 更新选中状态
//     *
//     * @param position 位置
//     */
//    public void updateSelectStatus(int position) {
//        mSparseArray.append(position, !mSparseArray.get(position));
//        refreshAllSelectStatus();
//        // 这里进行增量刷新
//        refreshPartly(position, KEY_SELECT_STATUS, mSparseArray.get(position));
//    }
//
//    private void refreshAllSelectStatus() {
//        for (int i = 0; i < getItemCount(); i++) {
//            if (!mSparseArray.get(i)) {
//                onAllSelectStatusChanged(false);
//                return;
//            }
//        }
//        onAllSelectStatusChanged(true);
//    }
//
//    /**
//     * 设置是否全选
//     *
//     * @param isSelectAll 是否全选
//     */
//    public void setSelectAll(boolean isSelectAll) {
//        mIsSelectAll = isSelectAll;
//        if (isSelectAll) {
//            for (int i = 0; i < getItemCount(); i++) {
//                mSparseArray.append(i, true);
//            }
//        } else {
//            mSparseArray.clear();
//        }
//        notifyDataSetChanged();
//    }
//
//    public boolean isManageMode() {
//        return mIsManageMode;
//    }
//
//    public void onAllSelectStatusChanged(boolean isSelectAll) {
//        if (mIsSelectAll != isSelectAll) {
//            mIsSelectAll = isSelectAll;
//            if (mListener != null) {
//                mListener.onAllSelectStatusChanged(isSelectAll);
//            }
//        }
//    }
//
//    public List<Integer> getSelectedIndexList() {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < getItemCount(); i++) {
//            if (mSparseArray.get(i)) {
//                list.add(i);
//            }
//        }
//        return list;
//    }
//
//    public List<DetailResponse> getSelectedNewInfoList() {
//        List<DetailResponse> list = new ArrayList<>();
//        for (int i = 0; i < getItemCount(); i++) {
//            if (mSparseArray.get(i)){
//                list.add(getItem(i));
//            }
//        }
//        return list;
//    }
//
//    public interface OnAllSelectStatusChangedListener {
//
//        /**
//         * 全选状态发生变化
//         *
//         * @param isSelectAll 是否全选
//         */
//        void onAllSelectStatusChanged(boolean isSelectAll);
//    }

}
