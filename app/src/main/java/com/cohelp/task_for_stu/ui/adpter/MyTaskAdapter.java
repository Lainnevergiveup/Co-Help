package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.net.model.vo.HelpVO;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;

import java.util.ArrayList;
import java.util.List;

public class MyTaskAdapter extends BaseAdapter {
    Context context;
    Fragment fragment;
    List<DetailResponse> list = new ArrayList<>();

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

    public MyTaskAdapter(Context context, Fragment fragment, List<DetailResponse> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    public MyTaskAdapter() {
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

        DetailResponse detailResponse = list.get(position);

        ActivityVO activityVO = detailResponse.getActivityVO();
        HelpVO helpVO = detailResponse.getHelpVO();
        AskVO askVO = detailResponse.getAskVO();

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


        if (activityVO!=null){
            System.out.println("id="+activityVO.getId());
            System.out.println(activityVO.getActivityComment());
            authorName.setText(activityVO.getUserName());
            title.setText(activityVO.getActivityTitle());
            summary.setText(activityVO.getActivityDetail());
            commentNumber.setText(activityVO.getActivityComment().toString());
            praiseNumber.setText(activityVO.getActivityLike().toString());
            collectNumber.setText(activityVO.getActivityCollect().toString());
            tag.setText(activityVO.getActivityLabel());
        }
        if (helpVO!=null){
            System.out.println("id="+helpVO.getId());
            authorName.setText(helpVO.getUserName());
            title.setText(helpVO.getHelpTitle());
            summary.setText(helpVO.getHelpDetail());
            commentNumber.setText(helpVO.getHelpComment().toString());
            praiseNumber.setText(helpVO.getHelpLike().toString());
            collectNumber.setText(helpVO.getHelpCollect().toString());
            if (helpVO.getHelpPaid()==0)
                tag.setText("无偿");
            else tag.setText("有偿");

        }
        if (askVO!=null){
            System.out.println("id="+askVO.getId());
            authorName.setText(askVO.getUserName());
            title.setText(askVO.getUserName());
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
