package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyPagerAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.wyt.searchbox.SearchFragment;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
问答中心
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HelpCenterActivity extends BaseActivity {
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout HoleCenter;
    LinearLayout UserCenter;
    TextView lb1,lb2,lb3,lb4,lb5;
    NiceSpinner niceSpinner;
    String item;
    TextView title;
    TabSegment mTabSegment;
    ViewPager mContentViewPager;

    androidx.appcompat.widget.Toolbar toolbar;
    SearchFragment searchFragment = SearchFragment.newInstance();
    EditText searchedContent;
    ImageView searchBtn,search;
    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;
    Integer conditionState = 0;
    List<DetailResponse> helpList = new ArrayList<>();

//    HelpAdapter helpAdapter;
    CardViewListAdapter cardViewListAdapter = new CardViewListAdapter();
    OkHttpUtils okHttpUtils;
    String helpTag = "组团招人";
    private final int TAB_COUNT = 10;
    private int mCurrentItemCount = TAB_COUNT;
    String[] pages = MultiPage.getPageNames();
    private MultiPage mDestPage = MultiPage.组团招人;

    private Map<MultiPage, View> mPageMap = new HashMap<>();
    private MyPagerAdapter mPagerAdapter ;

    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    helpList = (List<DetailResponse>) msg.obj;

                    cardViewListAdapter.setDetailResponseListList(helpList);
                    System.out.println("handler's working");

//                    eRecyclerView.setLayoutManager(new LinearLayoutManager(HelpCenterActivity.this));
//                    eRecyclerView.setAdapter(cardViewListAdapter);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(HelpCenterActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(HelpCenterActivity.this,"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private View getPageView(MultiPage page) {

        View view = mPageMap.get(page);
        if (view == null) {


            RecyclerView recyclerView = new RecyclerView(HelpCenterActivity.this,null);
//            swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
//            swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
//            swipeRefreshLayout.addView(recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(cardViewListAdapter);
//            swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    System.out.println("isrefreshing");
//                }
//            });
            view = recyclerView;
            mPageMap.put(page, view);

        }
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        initTools();
        initView();
        initTab();
        initToolbar();
        initEvent();
        setTitle("互助");
    }

    private void initTools(){
//        intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
    }

    private void initEvent() {


        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHelpCenterActivity();
            }
        });

        TaskCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTaskCenterActivity();
            }
        });

        HoleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHoleCenterActivity();
            }
        });

        UserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserCenterActivity();
            }
        });

//        lb1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startLoadingProgress();
//                conditionState = 0;
//                refreshHelpListData();
//                stopLoadingProgress();
//            }
//        });
//
//        lb2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startLoadingProgress();
//                conditionState = 1;
//                refreshHelpListData();
//                stopLoadingProgress();
//            }
//        });
//
//        lb3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startLoadingProgress();
//                conditionState = 2;
//                refreshHelpListData();
//                stopLoadingProgress();
//            }
//        });
//
//        lb4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startLoadingProgress();
//                conditionState = 3;
//                refreshHelpListData();
//                stopLoadingProgress();
//            }
//        });
//
//        lb5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startLoadingProgress();
//                conditionState = 4;
//                refreshHelpListData();
//                stopLoadingProgress();
//            }
//        });





//        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(aSwitch.isChecked()){
////
//                    SearchBox.setVisibility(buttonView.VISIBLE);
//                }else {
////
//                    SearchBox.setVisibility(buttonView.GONE);
//                }
//
//            }
//        });

//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 从服务端搜索
//                String s = searchedContent.getText().toString();
//                if (StringUtils.isEmpty(s)) {
//                    T.showToast("查询的标题不能为空哦~");
//                } else {
//                    startLoadingProgress();
//                    refreshHelpListData();
//                    stopLoadingProgress();
//                }
//
//
//            }
//        });
        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHelpListData();
            }
        });

        cardViewListAdapter.setOnItemClickListener(new CardViewListAdapter.OnItemListenter(){
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(HelpCenterActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailResponse",helpList.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mTabSegment.addOnTabSelectedListener(new TabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {

                helpTag = pages[index];
                System.out.println("tag"+helpTag);
//                getHelpList();
                List<DetailResponse> list = changeTag();
                System.out.println(list);
                for (DetailResponse i:list){
                    System.out.println(i.getHelpVO().getHelpLabel());
                }
                cardViewListAdapter.setDetailResponseListList(list);
                RecyclerView primaryItem = mPagerAdapter.getPrimaryItem();
                primaryItem.setAdapter(cardViewListAdapter);
//                int currentItem = mContentViewPager.getCurrentItem();
//                System.out.println("current"+currentItem);
//                getHelpList();
////        cardViewListAdapter.setDetailResponseListList(helpList);
//                RecyclerView childAt = (RecyclerView) mContentViewPager.get(currentItem);
//                childAt.setAdapter(cardViewListAdapter);
//                refreshHelpListData();
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {

            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    private void initToolbar(){
        toolbar.setNavigationOnClickListener(onClickListener);
        toolbar.inflateMenu(R.menu.menu_help);
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        title.setText("互助");
    }
    private View.OnClickListener onClickListener = v -> toCreateNewHelpActivity();  ;

    androidx.appcompat.widget.Toolbar.OnMenuItemClickListener menuItemClickListener = item -> {
//        XToastUtils.toast("点击了:" + item.getTitle());
       if(item.getItemId() == R.id.item_search){
            searchFragment.showFragment(getSupportFragmentManager(), SearchFragment.TAG);
        }
        return false;
    };



    private synchronized void refreshHelpListData(){

        int currentItem = mContentViewPager.getCurrentItem();
        System.out.println("current"+currentItem);
        getHelpList();
//        cardViewListAdapter.setDetailResponseListList(helpList);
        RecyclerView childAt = (RecyclerView) mContentViewPager.getChildAt(currentItem);
        childAt.setAdapter(cardViewListAdapter);
//        helpAdapter.setHelpList(helpList);
//        cardViewListAdapter.setDetailResponseListList(helpList);
//        eRecyclerView.setAdapter(cardViewListAdapter);
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void initView() {

        toolbar = findViewById(R.id.tool_bar_2);
        title = findViewById(R.id.tv_title);
        search = findViewById(R.id.iv_search_search);

        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        searchedContent = findViewById(R.id.id_et_search);
        searchBtn = findViewById(R.id.id_iv_search);
        mTabSegment = findViewById(R.id.tabSegment);
        mContentViewPager = findViewById(R.id.contentViewPager);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
//        eRecyclerView = findViewById(R.id.id_recyclerview);

        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.PULL_FROM_START);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        getHelpList();
//        cardViewListAdapter = new CardViewListAdapter(helpList);

//        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        eRecyclerView.setAdapter(cardViewListAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toCreateNewHelpActivity() {
        Intent intent = new Intent(this,CreateNewHelpActivity.class);
        startActivity(intent);
    }



    private void toUserCenterActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        finish();
    }

    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHoleCenterActivity(){
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        finish();
    }
//    private synchronized void getHelpList(){
//        Thread t1 = new Thread(()->{
//            helpList = okHttpUtils.helpListByTag(helpTag);
////            Message msg = Message.obtain();
////            msg.obj = helpList;
////            msg.what = GET_DATA_SUCCESS;
////            handler.sendMessage(msg);
//            cardViewListAdapter.setDetailResponseListList(helpList);
//        });
//        t1.start();
//        try {
//            t1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private synchronized void getHelpList(){
        Thread t1 = new Thread(()->{
//            helpList = okHttpUtils.helpListByTag(helpTag);
            helpList = okHttpUtils.helpList(0);
//            Message msg = Message.obtain();
//            msg.obj = helpList;
//            msg.what = GET_DATA_SUCCESS;
//            handler.sendMessage(msg);
            cardViewListAdapter.setDetailResponseListList(helpList);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void toDetailActivity(int postion){
        Intent intent = new Intent(HelpCenterActivity.this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",helpList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(helpList.get(postion).getIdByType(helpList.get(postion).getType()),1);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }

    private void initTab(){
//        mPagerAdapter = new PagerAdapter() {
//            private RecyclerView recyclerView;
//            @Override
//            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                this.recyclerView = (RecyclerView) object;
//            }
//            public RecyclerView getPrimaryItem() {
//                return this.recyclerView;
//            }
//
//            @Override
//            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//                return view == object;
//            }
//
//            @Override
//            public int getCount() {
//                return mDestPage.size();
//            }
//
//            @Override
//            public Object instantiateItem(final ViewGroup container, int position) {
//                MultiPage page = MultiPage.getPage(position);
//                System.out.println("position"+position);
//                View view = getPageView(page);
//                view.setTag(page);
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                container.addView(view, params);
//                return view;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
//                container.removeView((View) object);
//            }
//
//            @Override
//            public int getItemPosition(@NonNull Object object) {
//                View view = (View) object;
//                Object page = view.getTag();
//                if (page instanceof MultiPage) {
//                    int pos = ((MultiPage) page).getPosition();
//                    System.out.println("pos"+pos);
//                    if (pos >= mCurrentItemCount) {
//                        return POSITION_NONE;
//                    }
//                    return POSITION_UNCHANGED;
//                }
//                return POSITION_NONE;
//            }
//        };
        mPagerAdapter = new MyPagerAdapter(){
            private RecyclerView recyclerView;
            public RecyclerView getPrimaryItem() {
                return this.recyclerView;
            }
            @Override
            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                this.recyclerView = (RecyclerView) object;
            }


            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public int getCount() {
                return mDestPage.size();
            }

            @Override
            public Object instantiateItem(final ViewGroup container, int position) {
                MultiPage page = MultiPage.getPage(position);
                System.out.println("position"+position);
                View view = getPageView(page);
                view.setTag(page);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                container.addView(view, params);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                View view = (View) object;
                Object page = view.getTag();
                if (page instanceof MultiPage) {
                    int pos = ((MultiPage) page).getPosition();
                    System.out.println("pos"+pos);
                    if (pos >= mCurrentItemCount) {
                        return POSITION_NONE;
                    }
                    return POSITION_UNCHANGED;
                }
                return POSITION_NONE;
            }


            @RequiresApi(api = Build.VERSION_CODES.O)
            private View getPageView(MultiPage page) {

                View view = mPageMap.get(page);
                if (view == null) {


                    RecyclerView recyclerView = new RecyclerView(HelpCenterActivity.this,null);
//            swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
//            swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
//            swipeRefreshLayout.addView(recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HelpCenterActivity.this));
                    recyclerView.setAdapter(cardViewListAdapter);
//            swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    System.out.println("isrefreshing");
//                }
//            });
                    view = recyclerView;
                    mPageMap.put(page, view);

                }
                return view;
            }
        };

        System.out.println("adapter init finish");
//        mTabSegment.reset();
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mTabSegment.getSelectedIndex(), false);
        for (int i = 0; i < 5; i++) {
            mTabSegment.addTab(new TabSegment.Tab(pages[i]));
            System.out.println("ii"+i);
        }
        int space = DensityUtils.dp2px(HelpCenterActivity.this, 16);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);

    }
    private  List<DetailResponse> changeTag(){

        return helpList.stream().filter(x->x.getHelpVO().getHelpLabel().toString().equals(helpTag)).collect(Collectors.toList());
    }


}