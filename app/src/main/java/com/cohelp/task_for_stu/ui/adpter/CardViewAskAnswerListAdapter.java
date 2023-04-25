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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.vo.AnswerBankVO;

import java.util.List;

public class CardViewAskAnswerListAdapter extends RecyclerView.Adapter<CardViewAskAnswerListAdapter.ViewHolder> {

    private List<AnswerBankVO> AnswerVOListList;
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
    private ViewHolder holder;
    private int position;

    public CardViewAskAnswerListAdapter(List<AnswerBankVO> AnswerVOListList, Context context) {
        this.AnswerVOListList = AnswerVOListList;
        this.context = context;

    }

    public CardViewAskAnswerListAdapter(List<AnswerBankVO> AnswerVOListList) {
        this.AnswerVOListList = AnswerVOListList;
    }

    public CardViewAskAnswerListAdapter(List<AnswerBankVO> AnswerVOListList, Context context, Fragment fragment) {
        this.AnswerVOListList = AnswerVOListList;
        this.context = context;
        this.fragment = fragment;
    }


    public CardViewAskAnswerListAdapter() {
    }

    public void setAnswerVOList(List<AnswerBankVO> AnswerVOListList) {
        this.AnswerVOListList = AnswerVOListList;
    }

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    @NonNull
    @Override
    public CardViewAskAnswerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_answerbank_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAskAnswerListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AnswerBankVO AnswerVO = AnswerVOListList.get(position);
        System.out.println("Answervo");

        if (AnswerVO!=null){

            holder.content.setText(AnswerVO.getContent());
            holder.hot.setText(AnswerVO.getRecommendedDegree().toString());
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemClickListener.onItemClick(view,position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
//        System.out.println("detail"+AnswerVOListList);
        if (AnswerVOListList!=null)
        return AnswerVOListList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView content;
        TextView hot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.cardView_summary);
            hot = itemView.findViewById(R.id.cardView_hot);

        }
    }

}
