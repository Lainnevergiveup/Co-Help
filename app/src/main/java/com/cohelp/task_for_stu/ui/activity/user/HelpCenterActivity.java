package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyFragmentPagerAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyPagerAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wyt.searchbox.SearchFragment;
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
//    TextView lb1,lb2,lb3,lb4,lb5;
//    NiceSpinner niceSpinner;
//    String item;
    TextView title;
//    TabSegment mTabSegment;
    ViewPager2 viewPager;

    androidx.appcompat.widget.Toolbar toolbar;
    SearchFragment searchFragment = SearchFragment.newInstance();
    EditText searchedContent;
    ImageView searchBtn,search;
    SwipeRefreshLayout eSwipeRefreshLayout;
//    RecyclerView eRecyclerView;
//    Integer conditionState = 0;
    List<DetailResponse> helpList = new ArrayList<>();

//    HelpAdapter helpAdapter;
    CardViewListAdapter cardViewListAdapter = new CardViewListAdapter();
//    String helpTag = "组团招人";
//    private final int TAB_COUNT = 10;
//    private int mCurrentItemCount = TAB_COUNT;
    String[] pages = MultiPage.getPageNames();
//    private MultiPage mDestPage = MultiPage.组团招人;

//    private Map<MultiPage, View> mPageMap = new HashMap<>();
//    private MyPagerAdapter mPagerAdapter ;
    private TabLayout tabLayout;
    private int activeColor = Color.parseColor("#ff678f");
    private int normalColor = Color.parseColor("#666666");
//    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Fragment> fragmentList=new ArrayList<>();
    private TabLayoutMediator meditor;
    int activeSize = 16;
    int normalSize = 14;

    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case GET_DATA_SUCCESS:
//                    helpList = (List<DetailResponse>) msg.obj;
//
//                    cardViewListAdapter.setDetailResponseListList(helpList);
//                    System.out.println("handler's working");
//
////                    eRecyclerView.setLayoutManager(new LinearLayoutManager(HelpCenterActivity.this));
////                    eRecyclerView.setAdapter(cardViewListAdapter);
//                    break;
//                case NETWORK_ERROR:
//                    Toast.makeText(HelpCenterActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
//                    break;
//                case SERVER_ERROR:
//                    Toast.makeText(HelpCenterActivity.this,"服务器发生错误",Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };


//    private View getPageView(MultiPage page) {
//
//        View view = mPageMap.get(page);
//        if (view == null) {
//
//
//            RecyclerView recyclerView = new RecyclerView(HelpCenterActivity.this,null);
////            swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
////            swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
////            swipeRefreshLayout.addView(recyclerView);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(cardViewListAdapter);
////            swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
////                @Override
////                public void onRefresh() {
////                    System.out.println("isrefreshing");
////                }
////            });
//            view = recyclerView;
//            mPageMap.put(page, view);
//
//        }
//        return view;
//    }


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
        
    }

    private void initToolbar(){
        toolbar.setNavigationOnClickListener(onClickListener);
        toolbar.inflateMenu(R.menu.menu_help);
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        title.setText("互助");
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
//        mPagerAdapter = new MyPagerAdapter(){
//            private RecyclerView recyclerView;
//            public RecyclerView getPrimaryItem() {
//                return this.recyclerView;
//            }
//            @Override
//            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                this.recyclerView = (RecyclerView) object;
//            }
//
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
//
//
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            private View getPageView(MultiPage page) {
//
//                View view = mPageMap.get(page);
//                if (view == null) {
//
//
//                    RecyclerView recyclerView = new RecyclerView(HelpCenterActivity.this,null);
////            swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
////            swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
////            swipeRefreshLayout.addView(recyclerView);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(HelpCenterActivity.this));
//                    recyclerView.setAdapter(cardViewListAdapter);
////            swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
////                @Override
////                public void onRefresh() {
////                    System.out.println("isrefreshing");
////                }
////            });
//                    view = recyclerView;
//                    mPageMap.put(page, view);
//
//                }
//                return view;
//            }
//        };
        fragmentList.clear();

        fragmentList.add(new BlankFragment3(this,"组团招人"));
        fragmentList.add(new BlankFragment3(this,"寻物启事"));
        fragmentList.add(new BlankFragment3(this,"跑腿代取"));
        fragmentList.add(new BlankFragment3(this,"问题求助"));
        fragmentList.add(new BlankFragment3(this,"其他"));
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
//        mTabSegment.reset();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.registerOnPageChangeCallback(changeCallback);
        meditor = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(HelpCenterActivity.this);
//                textView.setText(tabs[position]);
                tab.setCustomView(textView);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{activeColor, normalColor};
//                ColorStateList colorStateList = new ColorStateList(states, colors);
                textView.setText(pages[position]);
                textView.setTextSize(normalSize);
                textView.setGravity(1);
//                textView.setTextColor(colorStateList);
                tab.setCustomView(textView);

            }
        });
        meditor.attach();

    }
    private View.OnClickListener onClickListener = v -> toCreateNewHelpActivity();

    private void initEvent() {

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
//        mTabSegment.addOnTabSelectedListener(new TabSegment.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(int index) {
//
//                helpTag = pages[index];
//                System.out.println("tag"+helpTag);
////                getHelpList();
//                List<DetailResponse> list = changeTag();
//                System.out.println(list);
//                for (DetailResponse i:list){
//                    System.out.println(i.getHelpVO().getHelpLabel());
//                }
//                cardViewListAdapter.setDetailResponseListList(list);
//                RecyclerView primaryItem = mPagerAdapter.getPrimaryItem();
//                primaryItem.setAdapter(cardViewListAdapter);
////                int currentItem = mContentViewPager.getCurrentItem();
////                System.out.println("current"+currentItem);
////                getHelpList();
//////        cardViewListAdapter.setDetailResponseListList(helpList);
////                RecyclerView childAt = (RecyclerView) mContentViewPager.get(currentItem);
////                childAt.setAdapter(cardViewListAdapter);
////                refreshHelpListData();
//            }
//
//            @Override
//            public void onTabUnselected(int index) {
//
//            }
//
//            @Override
//            public void onTabReselected(int index) {
//
//            }
//
//            @Override
//            public void onDoubleTap(int index) {
//
//            }
//        });
    }

    androidx.appcompat.widget.Toolbar.OnMenuItemClickListener menuItemClickListener = item -> {
       if(item.getItemId() == R.id.item_search){
            searchFragment.showFragment(getSupportFragmentManager(), SearchFragment.TAG);
        }
        return false;
    };


    private synchronized void refreshHelpListData(){

        int currentItem = viewPager.getCurrentItem();
        System.out.println("current"+currentItem);
        getHelpList();
//        cardViewListAdapter.setDetailResponseListList(helpList);
        RecyclerView childAt = (RecyclerView) viewPager.getChildAt(currentItem);
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

        viewPager = findViewById(R.id.contentViewPager);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
//        eRecyclerView = findViewById(R.id.id_recyclerview);
        tabLayout = findViewById(R.id.tabLayout);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.PULL_FROM_START);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
//        getHelpList();
//        cardViewListAdapter = new CardViewListAdapter(helpList);

//        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        eRecyclerView.setAdapter(cardViewListAdapter);

    }




    private synchronized void getHelpList(){
        Thread t1 = new Thread(()->{
//            helpList = okHttpUtils.helpListByTag(helpTag);
            helpList =OkHttpUtils.helpList(0);
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



    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //可以来设置选中时tab的大小
            TextView tabView;
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTextSize(activeSize);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            viewPager.setCurrentItem(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    @Override
    protected void onDestroy() {
        meditor.detach();
        viewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }

    private void toDetailActivity(int postion){
        Intent intent = new Intent(HelpCenterActivity.this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",helpList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(helpList.get(postion).getIdByType(helpList.get(postion).getType()),1);
        new Thread(()->{
            System.out.println(OkHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }

    private void toCreateNewHelpActivity() {
        Intent intent = new Intent(this,CreateNewHelpActivity.class);
        startActivity(intent);
    }

    private void toUserCenterActivity() {
        Intent intent = new Intent(this,BasicInfoActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }

    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }

    private void toHoleCenterActivity(){
        Intent intent = new Intent(this, HoleCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }

//    private  List<DetailResponse> changeTag(){
//
//        return helpList.stream().filter(x->x.getHelpVO().getHelpLabel().toString().equals(helpTag)).collect(Collectors.toList());
//    }


}