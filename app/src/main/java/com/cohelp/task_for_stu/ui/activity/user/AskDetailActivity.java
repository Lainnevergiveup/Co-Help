package com.cohelp.task_for_stu.ui.activity.user;

import static com.cohelp.task_for_stu.ui.adpter.CommentDialogMutiAdapter.TYPE_COMMENT_CHILD;
import static com.cohelp.task_for_stu.ui.adpter.CommentDialogMutiAdapter.TYPE_COMMENT_EMPTY;
import static com.cohelp.task_for_stu.ui.adpter.CommentDialogMutiAdapter.TYPE_COMMENT_MORE;
import static com.cohelp.task_for_stu.ui.adpter.CommentDialogMutiAdapter.TYPE_COMMENT_PARENT;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.RemarkRequest;
import com.cohelp.task_for_stu.net.model.entity.Collect;
import com.cohelp.task_for_stu.net.model.entity.CommentMoreBean;
import com.cohelp.task_for_stu.net.model.entity.FirstLevelBean;
import com.cohelp.task_for_stu.net.model.entity.RemarkActivity;
import com.cohelp.task_for_stu.net.model.entity.RemarkHelp;
import com.cohelp.task_for_stu.net.model.entity.SecondLevelBean;
import com.cohelp.task_for_stu.net.model.entity.User;
import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.net.model.vo.HelpVO;
import com.cohelp.task_for_stu.net.model.vo.QuestionBankVO;
import com.cohelp.task_for_stu.net.model.vo.RemarkVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CommentDialogMutiAdapter;
import com.cohelp.task_for_stu.ui.adpter.GridViewImageAdapter;
import com.cohelp.task_for_stu.ui.listener.SoftKeyBoardListener;
import com.cohelp.task_for_stu.ui.view.InputTextMsgDialog;
import com.cohelp.task_for_stu.ui.view.NetRadiusImageView;
import com.cohelp.task_for_stu.utils.RecyclerViewUtil;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.cohelp.task_for_stu.utils.TimeUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class AskDetailActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{
    private  int INITBEANNUM = 5;
    private long totalCount = 5;
    private float slideOffset = 0;
    private int positionCount = 0;
    Toolbar toolbar;
    TextView topicTitle;
    TextView avatorName;
    TextView topicTime;
    TextView topicDetail;
    TextView titleBar;
    NetRadiusImageView avatorPic;
    private RecyclerView rv_dialog_lists;
    private NineGridView imageGridView;
    ImageButton likeButton;
    ImageButton collectButton;
    ImageButton commentButton;
    EditText commentText;
    View view;

    BottomSheetDialog bottomSheetDialog;

    BottomSheetBehavior bottomSheetBehavior;

    InputTextMsgDialog inputTextMsgDialog;


    private SoftKeyBoardListener mKeyBoardListener;

    OkHttpUtils okHttpUtils;
    Intent intent;

    User user;

    AskVO detail;
    List<RemarkVO> remarkList;
    List<RemarkVO> firstList;
    List<List<RemarkVO>> orderRemarkVO;
    IdAndType idAndType;

    Integer commentRootType = 1;//是否为根评论
    Integer commentTargetID;//目标ID
    Integer commentTopID;//评论链首ID

    Integer detailType;


    private int offsetY;

    private List<MultiItemEntity> data = new ArrayList<>();
    private List<FirstLevelBean> datas = new ArrayList<>();

    private RecyclerViewUtil mRecyclerViewUtil;
    private CommentDialogMutiAdapter bottomSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initTools();

        setUpToolBar();
        initView();
        initData();
        showSheetDialog();
        initEvent();

    }

    private void initTools(){
        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                detail = (AskVO) bundle.getSerializable("ask");
//                detailType = detail.getType();

            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
//        idAndType = new IdAndType(detail.getIdByType(detail.getType()),1);
        mRecyclerViewUtil = new RecyclerViewUtil();

    }

    private void initView(){
        view = LayoutInflater.from(this).inflate(R.layout.dialog_bottomsheet, null, false);
        likeButton = (ImageButton) findViewById(R.id.imageButton_Like);
        collectButton = (ImageButton) findViewById(R.id.imageButton_Collect);
        commentButton =  findViewById(R.id.imageButton_Comment);
        toolbar = findViewById(R.id.id_toolbar);
        titleBar = findViewById(R.id.id_title);
        avatorPic = (NetRadiusImageView) findViewById(R.id.image_UserIcon);
        avatorName = (TextView) findViewById(R.id.text_UserId);
        topicTime = (TextView) findViewById(R.id.text_TopicCreateTime);
        topicTitle = (TextView) findViewById(R.id.text_MessageTitle);
        topicDetail = (TextView) findViewById(R.id.text_TopicDetail);
        imageGridView = (NineGridView) findViewById(R.id.grid_item_image);

        imageGridView.setVerticalScrollBarEnabled(false);
        imageGridView.setHorizontalScrollBarEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initData(){
        setDetailData();

        getUser();

        refreshComment();

        updateButtonState();

    }
    private void initEvent(){

//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onBackPressed();
//                    }
//                }
//        );

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Integer type = detail.getType();
                new Thread(()->{
//                    okHttpUtils.remark(type,detail.getIdByType(type));
                }).start();
                detail.setIsLiked(detail.getIsLiked()==1?0:1);
                updateButtonState();
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog!=null){
                    bottomSheetAdapter.notifyDataSetChanged();
                    slideOffset = 0;
                    bottomSheetDialog.show();
                }
            }
        });
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Collect collect = new Collect();
//                Integer type = detail.getType();
//                collect.setTopicId(detail.getIdByType(type));
//                collect.setTopicType(type);
                detail.setIsCollected(detail.getIsCollected()==1?0:1);
                updateButtonState();
                new Thread(()->{
//                    okHttpUtils.insertCollection(collect);
                }).start();
            }
        });

        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        for(String img : detail.getImageUrl()){
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setThumbnailUrl(img);
            imageInfo.setBigImageUrl(img);
            imageInfos.add(imageInfo);
        }
        imageGridView.setGridSpacing(10);
        imageGridView.setSingleImageSize(1200);
        imageGridView.setMaxSize((imageInfos.size()<=9?imageInfos.size():9));
        imageGridView.setAdapter(new NineGridViewClickAdapter(this, imageInfos));
        // 点击事件
        bottomSheetAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view1, int position) {
                switch ((int) view1.getTag()) {
                    case TYPE_COMMENT_PARENT:
                        if (view1.getId() == R.id.rl_group) {
                            //添加二级评论
                            initInputTextMsgDialog((View) view1.getParent(), false, bottomSheetAdapter.getData().get(position), position);
                        } else if (view1.getId() == R.id.ll_like) {
                            //一级评论点赞 项目中还得通知服务器 成功才可以修改
                            FirstLevelBean bean = (FirstLevelBean) bottomSheetAdapter.getData().get(position);
                            bean.setLikeCount(bean.getLikeCount() + (bean.getIsLike() == 0 ? 1 : -1));
                            bean.setIsLike(bean.getIsLike() == 0 ? 1 : 0);
                            datas.set(bean.getPosition(), bean);
                            initData();
                            bottomSheetAdapter.notifyDataSetChanged();
                        }
                        break;
                    case TYPE_COMMENT_CHILD:

                        if (view1.getId() == R.id.rl_group) {
                            //添加二级评论（回复）
                            initInputTextMsgDialog(view1, true, bottomSheetAdapter.getData().get(position), position);
                        } else if (view1.getId() == R.id.ll_like) {
                            //二级评论点赞 项目中还得通知服务器 成功才可以修改
                            SecondLevelBean bean = (SecondLevelBean) bottomSheetAdapter.getData().get(position);
                            bean.setLikeCount(bean.getLikeCount() + (bean.getIsLike() == 0 ? 1 : -1));
                            bean.setIsLike(bean.getIsLike() == 0 ? 1 : 0);

                            List<SecondLevelBean> secondLevelBeans = datas.get((int) bean.getPosition()).getSecondLevelBeans();
                            secondLevelBeans.set(bean.getChildPosition(), bean);
//                            CommentMultiActivity.this.dataSort(0);
                            bottomSheetAdapter.notifyDataSetChanged();
                        }

                        break;
                    case TYPE_COMMENT_MORE:
                        //在项目中是从服务器获取数据，其实就是二级评论分页获取
                        CommentMoreBean moreBean = (CommentMoreBean) bottomSheetAdapter.getData().get(position);
                        long beanPosition = moreBean.getPosition();
                        FirstLevelBean firstLevelBean = datas.get((int)beanPosition);
                        int showingSecondCount = firstLevelBean.getShowingSecondCount();
                        SecondLevelBean secondLevelBean = firstLevelBean.getSecondLevelBeans().get(showingSecondCount);
                        firstLevelBean.setShowingSecondCount(showingSecondCount+1);
                        datas.get((int) moreBean.getPosition()).getSecondLevelBeans().add(secondLevelBean);

                        System.out.println(secondLevelBean);
                        dataSort(0);

                        bottomSheetAdapter.notifyDataSetChanged();
                        break;
                    case TYPE_COMMENT_EMPTY:
//                        initRefresh();
                        break;

                }

            }
        });

        //滚动事件
        if (mRecyclerViewUtil != null) mRecyclerViewUtil.initScrollListener(rv_dialog_lists);

        mKeyBoardListener = new SoftKeyBoardListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                dismissInputDialog();
            }
        });

    }

    private void refreshComment(){
        if (remarkList!=null)remarkList.clear();
        if (firstList!=null)firstList.clear();
        if (orderRemarkVO!=null)orderRemarkVO.clear();
        if (data!=null)data.clear();
        if (datas!=null)datas.clear();
        getComment();
        sortCommentList();
        toCommentBeanList();
        dataSort(0);
        initCommentTarget();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDetailData(){

        if (detail!=null){
            avatorPic.setImageURL(detail.getAvatarUrl());
            avatorName.setText(detail.getUserName());
            topicTime.setText(TimeUtils.getRecentTimeSpanByNow(detail.getPublishTime().toInstant().toEpochMilli()));
            titleBar.setText(detail.getSemester());
//            topicTitle.setText(detail.get);
            topicDetail.setText(detail.getQuestion());
        }
    }
    private int getWindowHeight() {
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
    //获取评论并排序
    private synchronized void getComment(){

        try {
            Thread t1 = new Thread(()->{
//                remarkList = okHttpUtils.getCommentList(idAndType);
                System.out.println(remarkList);
            });
            t1.start();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void sortCommentList(){
        orderRemarkVO = orderRemarkVO(remarkList);
        firstList = new ArrayList<>();
        for (List<RemarkVO> voList:orderRemarkVO){
            RemarkVO vo = voList.get(0);
            firstList.add(vo);
//            voList.remove(0);
        }
    }

    public List<List<RemarkVO>> orderRemarkVO(List<RemarkVO> data){
        if(data==null){
            return null;
        }
        Stack<RemarkVO> stackA = new Stack<>();
        Stack<RemarkVO> stackB = new Stack<>();
        ArrayList<RemarkVO> topRemark = new ArrayList<>();
        List<List<RemarkVO>> arrayLists = new ArrayList<>();
        //筛选出一级评论
        Iterator<RemarkVO> iter = data.iterator();
        while(iter.hasNext()){
            RemarkVO remarkVO = iter.next();
            if(remarkVO.getTargetIsTopic().equals(1)){
                topRemark.add(remarkVO);
                iter.remove();
            }
        }
        //将每条评论链的评论按逻辑顺序压入List
        for(RemarkVO remarkTop:topRemark){
            ArrayList<RemarkVO> remarkVOS = new ArrayList<>();
            stackA.push(remarkTop);
            while(!stackA.isEmpty()){
                RemarkVO peek = stackA.peek();
                Integer peekRemarkId = peek.getId();
                iter = data.iterator();
                while(iter.hasNext()){
                    RemarkVO remark = iter.next();
                    if(remark.getRemarkTargetId().equals(peekRemarkId)){
                        remark.setRemarkTargetName(peek.getRemarkOwnerName());
                        stackA.push(remark);
                        iter.remove();
                    }
                }
                if(stackA.peek().equals(peek)){
                    RemarkVO pop = stackA.pop();
                    //设置当前评论的评论对象
//                    if (!stackA.isEmpty()){
//                        pop.setRemarkTargetName(stackA.peek().getRemarkOwnerName());
//                    }else {
//                        pop.setRemarkTargetName(null);
//                    }
                    stackB.push(pop);
                }
            }
            while(!stackB.isEmpty()){
                remarkVOS.add(stackB.pop());
            }
            arrayLists.add(remarkVOS);
        }




        return arrayLists;
    }
    private void changeLocalState(){
        List<DetailResponse> detailResponses = SessionUtils.getActivityPreference(AskDetailActivity.this);
    }
    private RemarkActivity remarkActivityBuilder(){
        RemarkActivity remarkActivity = new RemarkActivity();
        remarkActivity.setRemarkTime(new Date());
        remarkActivity.setRemarkContent(commentText.getText().toString());
        remarkActivity.setRemarkLike(0);
        remarkActivity.setTargetIsActivity(commentRootType);
        remarkActivity.setRemarkActivityId(detail.getId());
        remarkActivity.setRemarkTargetId(commentTargetID);
        remarkActivity.setTopId(commentTopID);
        return remarkActivity;
    }
    private RemarkHelp remarkHelpBiulder(){
        RemarkHelp remarkHelp = new RemarkHelp();
        remarkHelp.setRemarkTime(new Date());
        remarkHelp.setRemarkContent(commentText.getText().toString());
        remarkHelp.setRemarkLike(0);
        remarkHelp.setRemarkTargetId(commentTargetID);
        remarkHelp.setRemarkHelpId(detail.getId());
        remarkHelp.setTargetIsHelp(commentRootType);
        remarkHelp.setTopId(commentTopID);
        return remarkHelp;
    }
    private RemarkRequest remarkRequestBuilder(){
        RemarkRequest remarkRequest = new RemarkRequest();
        remarkRequest.setType(detailType);
        switch (detailType){
            case 1:{
                remarkRequest.setRemarkActivity(remarkActivityBuilder());
                break;
            }
            case 2:{
                remarkRequest.setRemarkHelp((remarkHelpBiulder()));
                break;
            }
            default:{
                break;
            }
        }
//        System.out.println(okHttpUtils.getGson().toJson(remarkRequest));
        return remarkRequest;
    }
    private synchronized void sendRemark(RemarkRequest remarkRequest){
        Thread thread = new Thread(() -> {
//            okHttpUtils.sendComment(remarkRequest);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initCommentTarget(){
        commentRootType = 1;
        commentTargetID = detail.getId();
        commentTopID = 0;
        return;
    }

    //更新按钮状态
    private void updateButtonState(){
        if (detail.getIsLiked() == 0) {
            likeButton.setImageResource(R.drawable.ic_dianzan_undo);
        } else {
            likeButton.setImageResource(R.drawable.ic_dianzan_success);
        }

        if (detail.getIsCollected()==1){
            collectButton.setImageResource(R.drawable.icon_collect_success);
        }
        else {
            collectButton.setImageResource(R.drawable.ic_collect_undo);
        }
    }
    public void closeDefaultAnimator(RecyclerView mRvCustomer) {
        if (null == mRvCustomer) return;
        mRvCustomer.getItemAnimator().setAddDuration(0);
        mRvCustomer.getItemAnimator().setChangeDuration(0);
        mRvCustomer.getItemAnimator().setMoveDuration(0);
        mRvCustomer.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);
    }
    private void initInputTextMsgDialog(View view, final boolean isReply, final MultiItemEntity item, final int position) {
        dismissInputDialog();
        if (view != null) {
            offsetY = view.getTop();
            scrollLocation(offsetY);
        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(this, R.style.dialog);
            commentText = inputTextMsgDialog.getMessageTextView();
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    addComment(isReply, item, position, msg);
                }

                @Override
                public void dismiss() {
                    //item滑动到原位
                    scrollLocation(-offsetY);
                }
            });
        }
        showInputTextMsgDialog();
    }
    //隐藏输入会话框
    private void dismissInputDialog() {
        if (inputTextMsgDialog != null) {
            if (inputTextMsgDialog.isShowing()) inputTextMsgDialog.dismiss();
            inputTextMsgDialog.cancel();
            inputTextMsgDialog = null;
        }
    }
    // item滑动
    public void scrollLocation(int offsetY) {
        try {
            rv_dialog_lists.smoothScrollBy(0, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //原始数据 一般是从服务器接口请求过来的
    private void toCommentBeanList() {
        for (RemarkVO i : firstList) {
            int childPos = 0;
            FirstLevelBean firstLevelBean = new FirstLevelBean();
            firstLevelBean.setContent(i.getRemarkContent());
            firstLevelBean.setCreateTime(i.getRemarkTime().getTime());
            firstLevelBean.setHeadImg(i.getRemarkOwnerAvatar());
            firstLevelBean.setId(i.getId().toString());
            firstLevelBean.setIsLike(i.getIsLiked());
            firstLevelBean.setLikeCount(i.getRemarkLike());
            firstLevelBean.setUserName(i.getRemarkOwnerName());
//            firstLevelBean.setTotalCount(i + size);
//            firstLevelBean.setPosition(position);


            List<SecondLevelBean> beans = new ArrayList<>();
            int beanSize = 0;
            for (List<RemarkVO> j : orderRemarkVO) {
//                posCount += 2;
                if (j.size()>0&&j.get(0)== i){
                    j.remove(i);
                    beanSize = j.size();
//                    posCount += beanSize;
//                    firstLevelBean.setPositionCount(posCount);
                    for (RemarkVO k:j){
                        SecondLevelBean secondLevelBean = new SecondLevelBean();
                        secondLevelBean.setContent(k.getRemarkContent());
                        secondLevelBean.setCreateTime(k.getRemarkTime().getTime());
                        secondLevelBean.setHeadImg(k.getRemarkOwnerAvatar());
                        secondLevelBean.setId(k.getId().toString());
                        secondLevelBean.setIsLike(k.getIsLiked());
                        secondLevelBean.setLikeCount(k.getRemarkLike());
                        secondLevelBean.setUserName(k.getRemarkOwnerName());
                        secondLevelBean.setIsReply(k.getTargetIsTopic()^1);
                        secondLevelBean.setReplyUserName(k.getRemarkTargetName());
                        secondLevelBean.setTotalCount(j.size()+1);
//                        secondLevelBean.setPosition(position);
//                        secondLevelBean.setPositionCount(posCount);
                        secondLevelBean.setChildPosition(childPos++);
//                        data.add(secondLevelBean);
                        beans.add(secondLevelBean);
                    }
                }
            }
//            if (beanSize <= 10) {
////                posCount+=2;
//                CommentMoreBean moreBean = new CommentMoreBean();
////                moreBean.setPosition(position);
////                moreBean.setPositionCount(posCount);
//                moreBean.setTotalCount(firstLevelBean.getTotalCount());
//                data.add(moreBean);
//            }
            firstLevelBean.setTotalCount(1+(firstLevelBean.getSecondLevelBeans()==null?0:firstLevelBean.getSecondLevelBeans().size()));
            firstLevelBean.setSecondLevelBeans(beans);
            datas.add(firstLevelBean);
//            data.add(firstLevelBean);

//            position++;
        }

    }
    /**
     * 对数据重新进行排列
     * 目的是为了让一级评论和二级评论同为item
     * 解决滑动卡顿问题
     *
     * @param position
     */
    private void dataSort(int position) {
        if (datas.isEmpty()) {
            data.add(new MultiItemEntity() {
                @Override
                public int getItemType() {
                    return TYPE_COMMENT_EMPTY;
                }
            });
            return;
        }
        int beanNum = INITBEANNUM;
        if (position <= 0) data.clear();
        int posCount = data.size();
        int count = datas.size();
        for (int i = 0; i < count; i++) {
            if (i < position) continue;

            //一级评论
            FirstLevelBean firstLevelBean = datas.get(i);
            if (firstLevelBean == null) continue;
            firstLevelBean.setPosition(i);
            posCount += 2;
            List<SecondLevelBean> secondLevelBeans = firstLevelBean.getSecondLevelBeans();
            if (secondLevelBeans == null || secondLevelBeans.isEmpty()) {
                firstLevelBean.setPositionCount(posCount);
                data.add(firstLevelBean);
                continue;
            }
            int beanSize = secondLevelBeans.size();
            posCount += beanSize;
            firstLevelBean.setPositionCount(posCount);
            data.add(firstLevelBean);

            //二级评论
            for (int j = 0; j < INITBEANNUM&&j<beanSize ; j++) {
                SecondLevelBean secondLevelBean = secondLevelBeans.get(j);
                secondLevelBean.setChildPosition(j);
                secondLevelBean.setPosition(i);
                secondLevelBean.setPositionCount(posCount);
                data.add(secondLevelBean);

            }

            //展示更多的item
            if (beanNum<beanSize-1) {
//                SecondLevelBean moreBean = secondLevelBeans.get(++beanNum);
                CommentMoreBean moreBean = new CommentMoreBean();
                moreBean.setPosition(i);
                moreBean.setPositionCount(posCount);
                moreBean.setTotalCount(firstLevelBean.getTotalCount());
//                System.out.println(beanNum++);
                data.add(moreBean);
                INITBEANNUM++;
            }

        }
    }
    public void show(View view) {
        bottomSheetAdapter.notifyDataSetChanged();
        slideOffset = 0;
        bottomSheetDialog.show();
    }
    private void showSheetDialog() {
        if (bottomSheetDialog != null) {
            return;
        }

        //view
        view = View.inflate(this, R.layout.dialog_bottomsheet, null);
        ImageView iv_dialog_close = (ImageView) view.findViewById(R.id.dialog_bottomsheet_iv_close);
        rv_dialog_lists = (RecyclerView) view.findViewById(R.id.dialog_bottomsheet_rv_lists);
        RelativeLayout rl_comment = view.findViewById(R.id.rl_comment);
        iv_dialog_close.setOnClickListener(v -> bottomSheetDialog.dismiss());
        rl_comment.setOnClickListener(v -> {
            //添加二级评论
            initInputTextMsgDialog(null, false, null, -1);
        });

        //adapter
        bottomSheetAdapter = new CommentDialogMutiAdapter(data);
        rv_dialog_lists.setHasFixedSize(true);
        rv_dialog_lists.setLayoutManager(new LinearLayoutManager(this));
        closeDefaultAnimator(rv_dialog_lists);
        bottomSheetAdapter.setOnLoadMoreListener(this, rv_dialog_lists);
        rv_dialog_lists.setAdapter(bottomSheetAdapter);

        //dialog
        bottomSheetDialog = new BottomSheetDialog(this, R.style.dialog);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(getWindowHeight());
        //dialog滑动监听
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (slideOffset <= -0.28) {
                        //当向下滑动时 值为负数
                        bottomSheetDialog.dismiss();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                AskDetailActivity.this.slideOffset = slideOffset;
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRefresh() {
        datas.clear();
        initData();
        bottomSheetAdapter.setNewData(data);
    }
    private void showInputTextMsgDialog() {
        inputTextMsgDialog.show();
    }
    private void addComment(boolean isReply, MultiItemEntity item, final int position, String msg) {
        final String userName = user.getUserName();
        if (position >= 0) {
            //添加二级评论
            int pos = 0;
            String replyUserName = "";

            if (item instanceof FirstLevelBean) {
                FirstLevelBean firstLevelBean = (FirstLevelBean) item;
                positionCount = (int) (firstLevelBean.getPositionCount() + 1);
                pos = (int) firstLevelBean.getPosition();
                replyUserName = firstLevelBean.getUserName();
                commentTargetID = Integer.valueOf(firstLevelBean.getId());
                commentText.setText(msg);
                commentRootType = 0;
                commentTopID = Integer.valueOf(datas.get(firstLevelBean.getPosition()).getId());

            } else if (item instanceof SecondLevelBean) {
                SecondLevelBean secondLevelBean = (SecondLevelBean) item;
                positionCount = (int) (secondLevelBean.getPositionCount() + 1);
                pos = (int) secondLevelBean.getPosition();
                replyUserName = secondLevelBean.getUserName();
                commentTargetID = Integer.valueOf(secondLevelBean.getId());
                commentText.setText(msg);
                commentRootType = 0;
                commentTopID = Integer.valueOf(datas.get(secondLevelBean.getPosition()).getId());
            }

//            SecondLevelBean secondLevelBean = new SecondLevelBean();
//            secondLevelBean.setReplyUserName(replyUserName);
//            secondLevelBean.setIsReply(isReply ? 1 : 0);
//            secondLevelBean.setContent(msg);
//            secondLevelBean.setHeadImg(this.user.);
//            secondLevelBean.setCreateTime(System.currentTimeMillis());
//            secondLevelBean.setIsLike(0);
//            secondLevelBean.setUserName(userName);
//            secondLevelBean.setId("");
//            secondLevelBean.setPosition(positionCount);
//
//            datas.get(pos).getSecondLevelBeans().add(secondLevelBean);
//            DetailActivity.this.dataSort(0);
        } else {
            commentTargetID = Integer.valueOf(detail.getId());
//            commentText.setText(msg);
            System.out.println(commentText.getText());
            commentRootType = 1;
            commentTopID = null;
            //添加一级评论
//            FirstLevelBean firstLevelBean = new FirstLevelBean();
//            firstLevelBean.setUserName(userName);
//            firstLevelBean.setId(bottomSheetAdapter.getItemCount() + 1 + "");
//            firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
//            firstLevelBean.setCreateTime(System.currentTimeMillis());
//            firstLevelBean.setContent(msg);
//            firstLevelBean.setLikeCount(0);
//            firstLevelBean.setSecondLevelBeans(new ArrayList<SecondLevelBean>());
//            datas.add(0, firstLevelBean);
//            DetailActivity.this.dataSort(0);
//            bottomSheetAdapter.notifyDataSetChanged();
//            rv_dialog_lists.scrollToPosition(0);
        }
        sendRemark(remarkRequestBuilder());
        refreshComment();
        bottomSheetAdapter.notifyDataSetChanged();
        rv_dialog_lists.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayoutManager) rv_dialog_lists.getLayoutManager())
                        .scrollToPositionWithOffset(positionCount >= data.size() - 1 ? data.size() - 1
                                : positionCount, positionCount >= data.size() - 1 ? Integer.MIN_VALUE : rv_dialog_lists.getHeight());
            }
        }, 100);
    }

    private synchronized void getUser(){
        Thread t1 = new Thread(()->{
            user=okHttpUtils.getUser();
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (datas.size() >= totalCount) {
            bottomSheetAdapter.loadMoreEnd(false);
            return;
        }
        FirstLevelBean firstLevelBean = new FirstLevelBean();
        firstLevelBean.setUserName("hui");
        firstLevelBean.setId((datas.size() + 1) + "");
        firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
        firstLevelBean.setCreateTime(System.currentTimeMillis());
        firstLevelBean.setContent("add loadmore comment");
        firstLevelBean.setLikeCount(0);
        firstLevelBean.setSecondLevelBeans(new ArrayList<SecondLevelBean>());
        datas.add(firstLevelBean);
        bottomSheetAdapter.notifyDataSetChanged();
        bottomSheetAdapter.loadMoreComplete();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    @Override
    protected void onDestroy() {
        if (mRecyclerViewUtil != null){
            mRecyclerViewUtil.destroy();
            mRecyclerViewUtil = null;
        }
        if (mKeyBoardListener != null) {
            mKeyBoardListener.setOnSoftKeyBoardChangeListener(null);
            mKeyBoardListener = null;
        }
        bottomSheetAdapter = null;
        super.onDestroy();
    }
}