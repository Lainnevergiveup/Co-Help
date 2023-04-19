package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;

import java.util.ArrayList;
import java.util.List;

public class MyAskAdapter extends BaseAdapter {
    Context context;
    Fragment fragment;
    List<AskVO> list = new ArrayList<>();

    public OnItemListenter mItemClickListener;
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

    public MyAskAdapter(Context context, Fragment fragment, List<AskVO> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    public MyAskAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        System.out.println("getview");
        AskVO askVO = list.get(position);

        if(view == null ) {
            view = LayoutInflater.from(context).inflate(R.layout.view_cardlist_card, parent, false);
        }
        authorAvator = view.findViewById(R.id.cardView_author_pic);
        authorName = view.findViewById(R.id.cardView_author_name);
        tag = view.findViewById(R.id.cardView_tag);
        title = view.findViewById(R.id.cardView_title);
        summary = view.findViewById(R.id.cardView_summary);
        firstImage = view.findViewById(R.id.cardView_firstImage);
        praiseIcon = view.findViewById(R.id.cardView_praiseIcon);
        collectIcon = view.findViewById(R.id.cardView_collectIcon);
        commentIcon = view.findViewById(R.id.cardView_commentIcon);
        praiseNumber = view.findViewById(R.id.cardView_praiseNumber);
        collectNumber = view.findViewById(R.id.cardView_collectNumber);
        commentNumber = view.findViewById(R.id.cardView_commentNumber);
        readNumber = view.findViewById(R.id.cardView_readNumber);



        if (askVO!=null){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.tuku)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(MyCoHelp.getAppContext()).load(askVO.getAvatarUrl()).apply(options).into(authorAvator);
            System.out.println("id="+askVO.getId());
            authorName.setText(askVO.getUserName());
            title.setText(askVO.getQuestion());
            summary.setText("");
            commentNumber.setText(askVO.getAnswerCount().toString());
            praiseNumber.setText(askVO.getLikeCount().toString());
            collectNumber.setText(askVO.getCollectCount().toString());
            tag.setText(askVO.getSemester());

        }


        return view;
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

}
