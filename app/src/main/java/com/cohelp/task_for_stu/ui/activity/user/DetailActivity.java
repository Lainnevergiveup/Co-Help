package com.cohelp.task_for_stu.ui.activity.user;

import static com.cohelp.task_for_stu.ui.adpter.CommentDialogMutiAdapter.TYPE_COMMENT_EMPTY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.cohelp.task_for_stu.net.model.vo.RemarkVO;
import com.cohelp.task_for_stu.ui.adpter.CommentDialogMutiAdapter;
import com.cohelp.task_for_stu.ui.adpter.CommentExpandableListAdapter;
import com.cohelp.task_for_stu.ui.listener.SoftKeyBoardListener;
import com.cohelp.task_for_stu.ui.view.AvatorImageView;
import com.cohelp.task_for_stu.ui.view.InputTextMsgDialog;
import com.cohelp.task_for_stu.utils.RecyclerViewUtil;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class DetailActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener{

    private long totalCount = 22;


    Button returnButton;
    Button reportButton;
    TextView topicTitle;
    TextView avatorName;
    TextView topicTime;
    TextView topicDetail;
    AvatorImageView avatorPic;
    private RecyclerView rv_dialog_lists;
    ImageButton likeButton;
    ImageButton collectButton;
    ImageButton commentButton;
    EditText commentText;
    Button commentCommitButton;
    EditText editText;
    View view;
    ExpandableListView commentListView;
//    private CommentDialogMutiAdapter bottomSheetAdapter;

    BottomSheetDialog bottomSheetDialog;

    BottomSheetBehavior bottomSheetBehavior;

    InputTextMsgDialog inputTextMsgDialog;

    CommentExpandableListAdapter commentExpandableListAdapter;

    private SoftKeyBoardListener mKeyBoardListener;

    OkHttpUtils okHttpUtils;
    Intent intent;



    DetailResponse detail;
    List<RemarkVO> remarkList;
    List<RemarkVO> firstList;
    List<List<RemarkVO>> orderRemarkVO;
    IdAndType idAndType;

    Integer commentRootType = 1;//是否为根评论
    Integer commentTargetID;//目标ID
    Integer commentTopID;//评论链首ID

    Integer detailType;

    private float slideOffset = 0;
    private int offsetY;

    private List<MultiItemEntity> data = new ArrayList<>();
    private List<FirstLevelBean> datas = new ArrayList<>();
    private String content = "我听见你的声音，有种特别的感觉。让我不断想，不敢再忘记你。如果真的有一天，爱情理想会实现，我会加倍努力好好对你，永远不改变";

    private RecyclerViewUtil mRecyclerViewUtil;
    private CommentDialogMutiAdapter bottomSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//        initTools();

//        initData();

//        setTitle("话题详情");
        //以上为最终代码
        //以下为测试代码
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initTools();
        initView();
        initData();

        initEvent();
        showSheetDialog();
    }

    private void initTools(){
        intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                detail = (DetailResponse) bundle.getSerializable("detailResponse");
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
        idAndType = new IdAndType(detail.getIdByType(detail.getType()),1);
        mRecyclerViewUtil = new RecyclerViewUtil();

    }

    private void initView(){
        view = LayoutInflater.from(this).inflate(R.layout.dialog_bottomsheet, null, false);
        likeButton = (ImageButton) findViewById(R.id.imageButton_Like);
        collectButton = (ImageButton) findViewById(R.id.imageButton_Collect);
        commentButton =  findViewById(R.id.imageButton_Comment);
//        commentText = view.findViewById(R.id.text_input_comment);
//        commentCommitButton = view.findViewById(R.id.btn_send_comment);
//        commentListView = (ExpandableListView) view.findViewById(R.id.comment_item_list);
//        bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialogStyle1);
//        inputTextMsgDialog = new InputTextMsgDialog(this,R.style.dialog);
//        showSheetDialog();

//        initCommentRecycleView();
//        ExpandableTextView mExpandableTextView = findViewById(R.id.expand_text_view);
//        mExpandableTextView.setText("getString(R.string.etv_content_demo1daawsdd\n\n\nnu\n\nde\n\nre");
//        mExpandableTextView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
//            @Override
//            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
//                ToastUtils.toast(isExpanded ? "Expanded" : "Collapsed");
//            }
//        });

    }

    private void initData(){

        getComment();

        sortCommentList();
        initData2();
        System.out.println(datas);
        initCommentTarget();

        updateButtonState();

    }
    private void initEvent(){

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer type = detail.getType();
                new Thread(()->{
                    okHttpUtils.remark(type,detail.getIdByType(type));
                }).start();
                detail.setIsLiked(detail.getIsLiked()==1?0:1);
                updateButtonState();
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(2);
                if (bottomSheetDialog!=null){
//                    initCommentListView();
//                    System.out.println("1"+remarkList);
                    bottomSheetDialog.show();
//                    bottomSheetDialog2.show();
                }
                if (inputTextMsgDialog!=null){
//                    System.out.println(1111);
                    inputTextMsgDialog.show();

                }
                if (inputTextMsgDialog == null) {
                    inputTextMsgDialog = new InputTextMsgDialog(DetailActivity.this, R.style.dialog);
                    inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
//                    addComment(isReply, item, position, msg);
                }

                @Override
                public void dismiss() {
                    //item滑动到原位
                    scrollLocation(-offsetY);
                }
            });
                }
            }
        });
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collect collect = new Collect();
                Integer type = detail.getType();
                collect.setTopicId(detail.getIdByType(type));
                collect.setTopicType(type);
                detail.setIsCollected(detail.getIsCollected()==1?0:1);
                updateButtonState();
                new Thread(()->{
                    okHttpUtils.insertCollection(collect);
                }).start();
            }
        });
//        commentCommitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RemarkRequest remarkRequest = remarkRequestBuilder();
//                remarkRequest.setType(detailType);
//                sendRemark(remarkRequest);
//                commentText.setText("");
//            }
//        });
//        commentListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
//                commentTopID = firstList.get(groupPosition).getId();
//                commentTargetID = detail.getIdByType(detailType);
//
//
//                return true;
//            }
//        });
//        commentListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
//
//                commentTopID = firstList.get(groupPosition).getId();
//                commentTargetID = orderRemarkVO.get(groupPosition).get(childPosition).getId();
//                System.out.println(orderRemarkVO.get(groupPosition).get(childPosition).getRemarkOwnerName());
//                commentRootType = 0;
////                getFocusForEditText(commentText);
////                commentText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
////                commentText.setHint("Enter text");
////
//                // 计算 EditText 在屏幕中的位置
//                int[] location = new int[2];
//                view.getLocationOnScreen(location);
//                int x = location[0];
//                int y = location[1] + view.getHeight();
////
////                // 创建 PopupWindow
////                PopupWindow popupWindow = new PopupWindow(commentText,
////                        ViewGroup.LayoutParams.MATCH_PARENT,
////                        ViewGroup.LayoutParams.WRAP_CONTENT);
////                popupWindow.showAtLocation(expandableListView, Gravity.NO_GRAVITY, x, y);
//                editText = new EditText(DetailActivity.this);
//                editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                editText.setHint("Enter text");
//                getFocusForEditText(editText);
//
////
////                ScrollView frameLayout = (ScrollView)LayoutInflater.from(DetailActivity.this).inflate(R.layout.view_input_text_with_button, (ViewGroup) view).findViewById(R.id.layout_frame);
////                frameLayout.removeAllViews();
////                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
////frameLayout.setVisibility(1);
//                // 计算 EditText 在屏幕中的位置
////                int[] location = new int[2];
////                view.getLocationOnScreen(location);
////                int x = location[0];
////                int y = location[1] + view.getHeight();
//
//                // 创建 PopupWindow
//                PopupWindow popupWindow = new PopupWindow(commentText,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                popupWindow.setFocusable(true);
//
////
//                popupWindow.showAtLocation(editText, Gravity.BOTTOM,0,0);
//
//
////                // 获取屏幕高度
////                DisplayMetrics displayMetrics = new DisplayMetrics();
////                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
////                int screenHeight = displayMetrics.heightPixels;
////
////// 获取键盘高度
////                final int keyboardHeight = screenHeight - getStatusBarHeight() - getActionBarHeight() - 0;
////
////// 设置布局的位置
////                frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
////                    @Override
////                    public void onGlobalLayout() {
////                        Rect rect = new Rect();
////                        frameLayout.getWindowVisibleDisplayFrame(rect);
////                        int scrollViewBottom = rect.bottom;
////                        int scrollViewHeight = frameLayout.getHeight();
////                        int keyboardTop = screenHeight - keyboardHeight;
////                        if (scrollViewBottom > keyboardTop) {
////                            int delta = scrollViewBottom - keyboardTop + scrollViewHeight;
////                            frameLayout.smoothScrollBy(0, delta);
////                        }
////                    }
////                });
//
//                return true;
//            }
//        });
//        commentListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//
//                return false;
//            }
//        });
//        commentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                commentTopID = firstList.get(i).getId();
//                commentTargetID = firstList.get(i).getId();
//                System.out.println(firstList.get(i).getRemarkOwnerName());
//                commentRootType = 0;
//                getFocusForEditText(commentText);
//                return true;
//            }
//        });
//        commentListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int previousGroup = -1;
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (previousGroup != groupPosition) {
//                    commentListView.collapseGroup(previousGroup);
//                }
//                previousGroup = groupPosition;
//                setListViewHeight(commentListView, groupPosition);
//            }
//        });
//        commentListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int i) {
//                //Do Nothing
//            }
//        });

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


    private void initCommentListView(){
        commentExpandableListAdapter = new CommentExpandableListAdapter(orderRemarkVO,firstList,this);
        commentListView.setAdapter(commentExpandableListAdapter);
    }
    /**
     * 计算高度(初始化可以设置默认高度)
     *
     * @return
     */
    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return  heightPixels-heightPixels /4;
    }
    private synchronized void getComment(){

        try {
            Thread t1 = new Thread(()->{
            remarkList = okHttpUtils.getCommentList(idAndType);
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
            voList.remove(0);
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
        List<DetailResponse> detailResponses = SessionUtils.getActivityPreference(DetailActivity.this);
    }
    private RemarkActivity remarkActivityBuilder(){
        RemarkActivity remarkActivity = new RemarkActivity();
        remarkActivity.setRemarkTime(new Date());
        remarkActivity.setRemarkContent(commentText.getText().toString());
        remarkActivity.setRemarkLike(0);
        remarkActivity.setTargetIsActivity(commentRootType);
        remarkActivity.setRemarkActivityId(detail.getIdByType(detailType));
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
        remarkHelp.setRemarkHelpId(detail.getIdByType(detailType));
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
            case 3:{

            }
            default:{
                break;
            }
        }
        System.out.println(okHttpUtils.getGson().toJson(remarkRequest));
        return remarkRequest;
    }
    private synchronized void sendRemark(RemarkRequest remarkRequest){
        Thread thread = new Thread(() -> {
            okHttpUtils.sendComment(remarkRequest);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private static void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();
            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10) height = 160;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    private void initCommentTarget(){
        detailType = detail.getType();
        commentRootType = 1;
        commentTargetID = detail.getIdByType(detailType);
        commentTopID = 0;
        return;
    }
    private void getFocusForEditText(EditText editText){
        if (editText!=null){
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.showSoftInput(editText,0);
        }
        return;
    }
    private void updateButtonState(){
        if (detail.getIsLiked() == 0) {
            likeButton.setImageResource(R.drawable.icon_dianzan_undo);
        } else {
            likeButton.setImageResource(R.drawable.icon_dianzan_success);
        }

        if (detail.getIsCollected()==1){
            collectButton.setImageResource(R.drawable.icon_collect_success);
        }
        else {
            collectButton.setImageResource(R.drawable.icon_collect_undo);
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
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
//                    addComment(isReply, item, position, msg);
                }

                @Override
                public void dismiss() {
                    //item滑动到原位
                    scrollLocation(-offsetY);
                }
            });
        }
//        showInputTextMsgDialog();
    }
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
    private void initData2() {
        int size = 10;
        for (RemarkVO i : firstList) {
            FirstLevelBean firstLevelBean = new FirstLevelBean();
            firstLevelBean.setContent(i.getRemarkContent());
            firstLevelBean.setCreateTime(i.getRemarkTime().getTime());
            firstLevelBean.setHeadImg(i.getRemarkOwnerAvatar());
            firstLevelBean.setId(i.getId().toString());
            firstLevelBean.setIsLike(i.getIsLiked());
            firstLevelBean.setLikeCount(i.getRemarkLike());
            firstLevelBean.setUserName(i.getRemarkOwnerName());
//            firstLevelBean.setTotalCount(i + size);

            List<SecondLevelBean> beans = new ArrayList<>();
            for (List<RemarkVO> j : orderRemarkVO) {
                if (j.size()>0&&j.get(0)== i){
                    j.remove(0);
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
                        beans.add(secondLevelBean);
                    }
                }

            }
            firstLevelBean.setTotalCount(1+(firstLevelBean.getSecondLevelBeans()==null?0:firstLevelBean.getSecondLevelBeans().size()));
            firstLevelBean.setSecondLevelBeans(beans);
            datas.add(firstLevelBean);
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
        System.out.println(datas);
        View view = View.inflate(this, R.layout.dialog_bottomsheet, null);
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
                DetailActivity.this.slideOffset = slideOffset;//记录滑动值
            }
        });

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