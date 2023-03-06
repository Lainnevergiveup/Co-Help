/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.cohelp.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;

import androidx.annotation.NonNull;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.PublishDeleteRequest;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.HelpVO;
import com.cohelp.task_for_stu.net.model.vo.HoleVO;
import com.cohelp.task_for_stu.ui.adpter.base.broccoli.BroccoliRecyclerAdapter;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.recyclerview.XRecyclerAdapter;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xutil.common.CollectionUtils;
import com.xuexiang.xutil.common.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.samlss.broccoli.Broccoli;

/**
 * @author xuexiang
 * @since 2019/4/7 下午12:06
 */
public class NewsListEditAdapter extends BroccoliRecyclerAdapter<DetailResponse> {

    private static final String KEY_SELECT_STATUS = "key_select_status";
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;

    /**
     * 是否是管理模式，默认是false
     */
    private boolean mIsManageMode;

    /**
     * 记录选中的信息
     */
    private SparseBooleanArray mSparseArray = new SparseBooleanArray();

    private boolean mIsSelectAll;

    private OnAllSelectStatusChangedListener mListener;

//    public NewsListEditAdapter(OnAllSelectStatusChangedListener listener) {
//        super(DemoDataProvider.getEmptyDetailRespone());
//        mListener = listener;
//    }

    public OnItemListenter mItemClickListener;

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public NewsListEditAdapter(OnAllSelectStatusChangedListener listener,List<DetailResponse> detailResponseList){
        super(detailResponseList);
        mListener = listener;
//        this.detailResponseListList = detailResponseList;
    }


    @Override
    protected int getItemLayoutId(int viewType) {
            return R.layout.adapter_news_edit_list_item;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, DetailResponse model, int position) {

        ActivityVO activityVO = model.getActivityVO();
        HelpVO helpVO = model.getHelpVO();
        HoleVO holeVO = model.getHoleVO();

        NetRadiusImageView imageView = holder.findViewById(R.id.cardView_author_pic);
        ImageLoader.get().loadImage(imageView, model.getPublisherAvatarUrl());

        List<String> imageList = model.getImagesUrl();
//        System.out.println("imagelist="+imageList);
        if (imageList!=null&&imageList.size()>0){
            NetRadiusImageView firstImage = holder.findViewById(R.id.cardView_firstImage);
            ImageLoader.get().loadImage(firstImage, model.getImagesUrl().get(0));
        }
//        System.out.println(detailResponse.getReadNum());
        holder.text(R.id.cardView_readNumber, "阅读量 " +model.getReadNum().toString());
        if (activityVO!=null){
            System.out.println("id="+activityVO.getId());
            System.out.println(activityVO.getActivityComment());

            holder.text(R.id.cardView_author_name, activityVO.getUserName());
            holder.text(R.id.cardView_tag, activityVO.getActivityLabel());
            holder.text(R.id.cardView_title, activityVO.getActivityTitle());
            holder.text(R.id.cardView_summary, activityVO.getActivityDetail());
            holder.text(R.id.cardView_praiseNumber, activityVO.getActivityLike().toString());
            holder.text(R.id.cardView_commentNumber, activityVO.getActivityComment().toString());
            holder.text(R.id.cardView_collectNumber, activityVO.getActivityCollect().toString());
        }
        if (helpVO!=null){
            System.out.println("id="+helpVO.getId());
            holder.text(R.id.cardView_author_name, helpVO.getUserName());
            holder.text(R.id.cardView_title, helpVO.getHelpTitle());
            holder.text(R.id.cardView_summary,helpVO.getHelpDetail());
            holder.text(R.id.cardView_praiseNumber, helpVO.getHelpLike().toString());
            holder.text(R.id.cardView_commentNumber, helpVO.getHelpComment().toString());
            holder.text(R.id.cardView_collectNumber, helpVO.getHelpCollect().toString());
            if (helpVO.getHelpPaid()==0){
                holder.text(R.id.cardView_tag, "无偿");
            }
            else {
                holder.text(R.id.cardView_tag, "有偿");
            }


        }
        if (holeVO!=null){
            holder.text(R.id.cardView_author_name, holeVO.getUserName());
            holder.text(R.id.cardView_tag, holeVO.getHoleLabel());
            holder.text(R.id.cardView_title, holeVO.getHoleTitle());
            holder.text(R.id.cardView_summary, holeVO.getHoleDetail());
            holder.text(R.id.cardView_praiseNumber, holeVO.getHoleLike().toString());
            holder.text(R.id.cardView_commentNumber, holeVO.getHoleComment().toString());
            holder.text(R.id.cardView_collectNumber, holeVO.getHoleCollect().toString());

        }
//        HoleVO h = model.getHoleVO();
//        System.out.println(h);
//        if (null != h){
//            holder.text(R.id.cardView_author_name, h.getUserName());
//            holder.text(R.id.cardView_tag, h.getHoleLabel());
//            holder.text(R.id.cardView_title, h.getHoleTitle());
//            holder.text(R.id.cardView_summary, h.getHoleDetail());
//            holder.text(R.id.cardView_praiseNumber, h.getHoleLike().toString());
//            holder.text(R.id.cardView_commentNumber, h.getHoleComment().toString());
//            holder.text(R.id.cardView_readNumber, "阅读量 " +h.getReadNum().toString());
//        }
//        else {
//        }



        holder.visible(R.id.scb_select, mIsManageMode ? View.VISIBLE : View.GONE);
        if (mIsManageMode) {
            SmoothCheckBox checkBox = holder.findViewById(R.id.scb_select);
            System.out.println(checkBox);

            checkBox.setCheckedSilent(mSparseArray.get(position));
            checkBox.setOnCheckedChangeListener((checkBox1, isChecked) -> {
                mSparseArray.append(position, isChecked);
                refreshAllSelectStatus();
            });
        }
    }




    @Override
    protected void onBindBroccoli(RecyclerViewHolder holder, Broccoli broccoli) {
        broccoli.addPlaceholders(
                holder.findView(R.id.cardView_firstImage),
                holder.findView(R.id.cardView_author_name),
                holder.findView(R.id.cardView_tag),
                holder.findView(R.id.cardView_title),
                holder.findView(R.id.cardView_summary),
                holder.findView(R.id.cardView_praiseIcon),
                holder.findView(R.id.cardView_praiseNumber),
                holder.findView(R.id.cardView_commentIcon),
                holder.findView(R.id.cardView_commentNumber),
                holder.findView(R.id.cardView_readNumber),
                holder.findView(R.id.cardView_collectIcon),
                holder.findView(R.id.cardView_collectNumber),
                holder.findView(R.id.cardView_author_pic)
        );

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
            }
        });
        if (CollectionUtils.isEmpty(payloads)) {
            Logger.e("正在进行全量刷新:" + position);
            onBindViewHolder(holder, position);
            return;
        }
        // payloads为非空的情况，进行局部刷新
        //取出我们在getChangePayload（）方法返回的bundle
        Bundle payload = WidgetUtils.getChangePayload(payloads);
        if (payload == null) {
            return;
        }
        Logger.e("正在进行增量刷新:" + position);
        for (String key : payload.keySet()) {
            if (KEY_SELECT_STATUS.equals(key)) {
                holder.checked(R.id.scb_select, payload.getBoolean(key));
            }
        }
    }

    /**
     * 切换管理模式
     */
    public void switchManageMode() {
        setManageMode(!mIsManageMode);
    }

    /**
     * 设置管理模式
     *
     * @param isManageMode 是否是管理模式
     */
    public void setManageMode(boolean isManageMode) {
        if (mIsManageMode != isManageMode) {
            mIsManageMode = isManageMode;
            notifyDataSetChanged();
            if (!mIsManageMode) {
                // 退出管理模式时清除选中状态
                mSparseArray.clear();
                onAllSelectStatusChanged(false);
            }
        }
    }

    @Override
    public XRecyclerAdapter refresh(Collection<DetailResponse> collection) {
        // 刷新时清除选中状态
        mSparseArray.clear();
        onAllSelectStatusChanged(false);
        return super.refresh(collection);
    }

    @Override
    public XRecyclerAdapter loadMore(Collection<DetailResponse> collection) {
        onAllSelectStatusChanged(false);
        return super.loadMore(collection);
    }

    /**
     * 进入管理模式
     */
    public void enterManageMode(int position) {
        mSparseArray.append(position, true);
        setManageMode(true);
    }

    /**
     * 更新选中状态
     *
     * @param position 位置
     */
    public void updateSelectStatus(int position) {
        mSparseArray.append(position, !mSparseArray.get(position));
        refreshAllSelectStatus();
        // 这里进行增量刷新
        refreshPartly(position, KEY_SELECT_STATUS, mSparseArray.get(position));
    }

    private void refreshAllSelectStatus() {
        for (int i = 0; i < getItemCount(); i++) {
            if (!mSparseArray.get(i)) {
                onAllSelectStatusChanged(false);
                return;
            }
        }
        onAllSelectStatusChanged(true);
    }

    /**
     * 设置是否全选
     *
     * @param isSelectAll 是否全选
     */
    public void setSelectAll(boolean isSelectAll) {
        mIsSelectAll = isSelectAll;
        if (isSelectAll) {
            for (int i = 0; i < getItemCount(); i++) {
                mSparseArray.append(i, true);
            }
        } else {
            mSparseArray.clear();
        }
        notifyDataSetChanged();
    }

    public boolean isManageMode() {
        return mIsManageMode;
    }

    public void onAllSelectStatusChanged(boolean isSelectAll) {
        if (mIsSelectAll != isSelectAll) {
            mIsSelectAll = isSelectAll;
            if (mListener != null) {
                mListener.onAllSelectStatusChanged(isSelectAll);
            }
        }
    }

    public List<Integer> getSelectedIndexList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                list.add(i);
            }
        }
        return list;
    }

    public List<DetailResponse> getSelectedDetailResponseList() {
        List<DetailResponse> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                list.add(getItem(i));
            }
        }
        return list;
    }

    public List<PublishDeleteRequest> getDeleteList() {
        List<PublishDeleteRequest> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                DetailResponse item = getItem(i);
                Integer type = item.getType();
                switch (type){
                    case 1:{
                        ActivityVO x = item.getActivityVO();
                        list.add(new PublishDeleteRequest(x.getId().intValue(),x.getActivityOwnerId().intValue(),type.intValue()));
                        break;
                    }
                    case 2:{
                        HelpVO x = item.getHelpVO();
                        list.add(new PublishDeleteRequest(x.getId().intValue(),x.getHelpOwnerId().intValue(),type.intValue()));
                        break;

                    }
                    case 3:{
                        HoleVO x = item.getHoleVO();
                        list.add(new PublishDeleteRequest(x.getId().intValue(),x.getHoleOwnerId().intValue(),type.intValue()));
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }
        }
        return list;
    }

    public interface OnAllSelectStatusChangedListener {

        /**
         * 全选状态发生变化
         *
         * @param isSelectAll 是否全选
         */
        void onAllSelectStatusChanged(boolean isSelectAll);
    }
}
