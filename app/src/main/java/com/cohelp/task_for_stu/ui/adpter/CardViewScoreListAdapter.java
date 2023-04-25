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
import com.cohelp.task_for_stu.net.model.vo.ScoreVO;

import java.util.List;

public class CardViewScoreListAdapter extends RecyclerView.Adapter<CardViewScoreListAdapter.ViewHolder> {

    private List<ScoreVO> ScoreVOListList;
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

    public CardViewScoreListAdapter(List<ScoreVO> ScoreVOListList, Context context) {
        this.ScoreVOListList = ScoreVOListList;
        this.context = context;

    }

    public CardViewScoreListAdapter(List<ScoreVO> AnswerVOListList) {
        this.ScoreVOListList = ScoreVOListList;
    }

    public CardViewScoreListAdapter(List<ScoreVO> AnswerVOListList, Context context, Fragment fragment) {
        this.ScoreVOListList = ScoreVOListList;
        this.context = context;
        this.fragment = fragment;
    }


    public CardViewScoreListAdapter() {
    }

    public void setScoreVOList(List<ScoreVO> ScoreVOListList) {
        this.ScoreVOListList = ScoreVOListList;
    }

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    @NonNull
    @Override
    public CardViewScoreListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_scorelist_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewScoreListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ScoreVO ScoreVO = ScoreVOListList.get(position);
        System.out.println("12312412125235"+ScoreVO);
        if (ScoreVO!=null){

            holder.name.setText(ScoreVO.getUserName());
            holder.score.setText(ScoreVO.getScore().toString());
        }
    }

    @Override
    public int getItemCount() {
//        System.out.println("detail"+AnswerVOListList);
        if (ScoreVOListList!=null)
        return ScoreVOListList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardView_name);
            score = itemView.findViewById(R.id.cardView_score);

        }
    }

}
