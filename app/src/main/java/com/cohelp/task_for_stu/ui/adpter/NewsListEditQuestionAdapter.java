package com.cohelp.task_for_stu.ui.adpter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.model.vo.AnswerVO;
import com.cohelp.task_for_stu.net.model.vo.QuestionBankVO;
import com.cohelp.task_for_stu.ui.adpter.base.broccoli.BroccoliRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.recyclerview.XRecyclerAdapter;
import com.xuexiang.xui.utils.CollectionUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.samlss.broccoli.Broccoli;

@RequiresApi(api = Build.VERSION_CODES.O)

public class NewsListEditQuestionAdapter extends BroccoliRecyclerAdapter<QuestionBankVO> {

    private static final String KEY_SELECT_STATUS = "key_select_status";
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    private boolean mIsManageMode;
    private SparseBooleanArray mSparseArray = new SparseBooleanArray();

    private boolean mIsSelectAll;

    private OnAllSelectStatusChangedListener mListener;
    public OnItemListenter mItemClickListener;

    private List<AnswerVO> answerList = new ArrayList<>();

    public interface OnItemListenter{
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemListenter mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public NewsListEditQuestionAdapter(OnAllSelectStatusChangedListener listener, List<QuestionBankVO> questionBankVOList){
        super(questionBankVOList);
        mListener = listener;
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, QuestionBankVO model, int position) {
//        System.out.println(model.toString());
        if (model!=null){
//            holder.text(R.id.cardView_course, model.getCourseName());
//            holder.text(R.id.cardView_tag, model.getContent());
//            ExpandableCardView cardView = holder.itemView.findViewById(R.id.card_question);
//            LinearLayout[] linearLayouts = new LinearLayout[3];
//            TextView[] textViews = new TextView[3];
//            linearLayouts[0] = holder.itemView.findViewById(R.id.ll_card_ans1);
//            linearLayouts[1] = holder.itemView.findViewById(R.id.ll_card_ans2);
//            linearLayouts[2] = holder.itemView.findViewById(R.id.ll_card_ans3);
//            textViews[0] = holder.itemView.findViewById(R.id.tv_card_ans1);
//            textViews[1] = holder.itemView.findViewById(R.id.tv_card_ans2);
//            textViews[2] = holder.itemView.findViewById(R.id.tv_card_ans3);
            holder.text(R.id.cardView_difficulity, model.getDifficulty());
            System.out.println(model.getContent());
//            cardView.setTitle(model.getContent().toString());
            holder.text(R.id.cardView_summary, model.getContent().toString());
            List<String> imageUrl = model.getImageUrl();
            if (imageUrl!=null){
                if (imageUrl.size()>0){
                    String image = imageUrl.get(0);
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.tuku)
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(MyCoHelp.getAppContext())
                            .load(image)
                            .apply(options)
                            .into(holder.getImageView(R.id.cardView_firstImage));
                    holder.getImageView(R.id.cardView_firstImage).setVisibility(View.VISIBLE);
                }
                else {
                    holder.getImageView(R.id.cardView_firstImage).setVisibility(View.GONE);
                }
            }
//            Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/teach/listanswerbankbyquestionbankid/" + model.getId().toString(), null, 300);
//            OKHttp.client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//                }
//                @Override
//                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                    String string = response.body().string();
//                    Result<List<AnswerVO>> result = GSON.gsonSetter().fromJson(string, new TypeToken<Result<List<AnswerVO>>>(){}.getType());
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            answerList = result.getData();
//                            for (int i=0;i<answerList.size()&&i<3;i++){
//                                AnswerVO answerVO = answerList.get(i);
//                                if (answerVO!=null){
//                                    textViews[i].setText(answerVO.getContent());
//                                }
//                                else {
//                                    linearLayouts[i].setVisibility(View.GONE);
//                                }
//                            }
////                        Toast.makeText(MyCoHelp.getAppContext(), json.getData(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            });

        }
        holder.visible(R.id.scb_select, mIsManageMode ? View.VISIBLE : View.GONE);
        if (mIsManageMode) {
            SmoothCheckBox checkBox = holder.findViewById(R.id.scb_select);

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
//                holder.findView(R.id.card_question),
//                holder.findView(R.id.ll_card_ans1),
//                holder.findView(R.id.ll_card_ans2),
//                holder.findView(R.id.ll_card_ans3)

                holder.findView(R.id.cardView_author_name),
                holder.findView(R.id.cardView_difficulity),
//                holder.findView(R.id.cardView_course),
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
    protected int getItemLayoutId(int viewType) {
        return R.layout.view_card_question_store;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads) {
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemClickListener.onItemClick(view,position);
//            }
//        });
        if (CollectionUtils.isEmpty(payloads)) {
//            Logger.e("正在进行全量刷新:" + position);
            onBindViewHolder(holder, position);
            return;
        }
        // payloads为非空的情况，进行局部刷新
        //取出我们在getChangePayload（）方法返回的bundle
        Bundle payload = WidgetUtils.getChangePayload(payloads);
        if (payload == null) {
            return;
        }
//        Logger.e("正在进行增量刷新:" + position);
        for (String key : payload.keySet()) {
            if (KEY_SELECT_STATUS.equals(key)) {
                holder.checked(R.id.scb_select, payload.getBoolean(key));
            }
        }
    }
    @Override
    public XRecyclerAdapter refresh(Collection<QuestionBankVO> collection) {
        // 刷新时清除选中状态
        mSparseArray.clear();
        onAllSelectStatusChanged(false);
        return super.refresh(collection);
    }

    @Override
    public XRecyclerAdapter loadMore(Collection<QuestionBankVO> collection) {
        onAllSelectStatusChanged(false);
        return super.loadMore(collection);
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

    public interface OnAllSelectStatusChangedListener {

        /**
         * 全选状态发生变化
         *
         * @param isSelectAll 是否全选
         */
        void onAllSelectStatusChanged(boolean isSelectAll);
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

    /**
     * 返回选中的表
     * @return
     */
    public List<Integer> getSelectedIndexList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                list.add(i);
            }
        }
        return list;
    }

    public List<QuestionBankVO> getSelectedQuestionList() {
        List<QuestionBankVO> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                list.add(getItem(i));
            }
        }
        return list;
    }
}
