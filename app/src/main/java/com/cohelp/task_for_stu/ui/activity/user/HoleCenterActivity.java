package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.net.model.vo.CourseVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewAskListAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyAskFragmentPagerAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyFragmentPagerAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.reflect.TypeToken;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HoleCenterActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout HoleCenter;
    LinearLayout UserCenter;
    EditText searchedContent;
    ImageView searchBtn;
    SwipeRefreshLayout eSwipeRefreshLayout;
    RecyclerView eRecyclerView;
    LinearLayout SearchHot;
    LinearLayout SearchTime;
    RelativeLayout SearchBox;
    FloatingActionButton floatingActionButton;
    Switch aSwitch;
    NiceSpinner niceSpinner;
    String item;
    Integer currentCourse ;
    String currentSemster;
    TabSegment mTabSegment;
    ViewPager mContentViewPager;
    OkHttpUtils okHttpUtils;
    List<DetailResponse> holeList;
    List<CourseVO> courseList;
    List<AskVO> askList;
    ArrayList<Fragment> listFragment = new ArrayList<>();
    CardViewAskListAdapter cardViewListAdapter;
    private MyAskFragmentPagerAdapter myFragmentPagerAdapter;
    String[] pages = MultiPage.getPageNames();
    List<String> semesterList;
    TextView question_bank;
    private final int TAB_COUNT = 10;
//    private int mCurrentItemCount = TAB_COUNT;
//    private MultiPage mDestPage = MultiPage.教育;
//    private Map<String, View> mPageMap = new HashMap<>();
    private int activeColor = Color.parseColor("#ff678f");
    private int normalColor = Color.parseColor("#666666");
    int activeSize = 16;
    int normalSize = 14;


//    private InterceptScrollView mScrollView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TabLayoutMediator meditor;
    private FragmentPagerAdapter mPageAdapter;
    private LinearLayout container_top;
    private LinearLayout container_normal;
    private View viewPlace;

    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Fragment> fragmentList=new ArrayList<>();

    //存放页面和滑动距离的Map
    private ArrayMap<Integer,Integer> scrollMap=new ArrayMap<>();
//    //当前页面
//    private int currentTab=0;
//    //当前页面的滑动距离
//    private int currentScrollY=0;
//    //用于判断，当前页面的导航栏是否悬浮
//    private boolean isTabLayoutSuspend;



//    private PagerAdapter mPagerAdapter = new PagerAdapter() {
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return view == object;
//        }
//
//        @Override
//        public int getCount() {
//            return courseList.size();
//        }
//
//        @Override
//        public Object instantiateItem(final ViewGroup container, int position) {
//            CourseVO courseVO = courseList.get(position);
//            View view = getPageView(courseVO.getName());
//            view.setTag(courseVO.getName());
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            container.addView(view, params);
//            return view;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override
//        public int getItemPosition(@NonNull Object object) {
//            View view = (View) object;
//            Object page = view.getTag();
//            if (page instanceof MultiPage) {
//                int pos = ((MultiPage) page).getPosition();
//                if (pos >= mCurrentItemCount) {
//                    return POSITION_NONE;
//                }
//                return POSITION_UNCHANGED;
//            }
//            return POSITION_NONE;
//        }
//    };


//    private View getPageView(String semeterName) {
//
//        View view = mPageMap.get(semeterName);
//        if (view == null) {
////            TextView textView = new TextView(HoleCenterActivity.this);
////            textView.setTextAppearance(HoleCenterActivity.this, R.style.TextStyle_Content_Match);
////            textView.setGravity(Gravity.CENTER);
////            textView.setText(String.format("这个是%s页面的内容", semeterName));
////            view = textView;
////            view = initSubView();
////            RecyclerView recyclerView = findViewById(R.id.id_recyclerview);
////            SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.id_swiperefresh);
////            SwipeRefreshLayout swipeRefreshLayout  = new SwipeRefreshLayout(HoleCenterActivity.this);
//            RecyclerView recyclerView = new RecyclerView(HoleCenterActivity.this,null);
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
//            mPageMap.put(semeterName, view);
//        }
//        return view;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_center);
        initTools();
        initView();
        initEvent();
        setUpToolBar();
        setTitle("");
    }
    private void initTools(){}



    @SuppressLint({"WrongViewCast", "ResourceAsColor"})
    private void initView(){

        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        searchBtn = findViewById(R.id.id_iv_search);
        question_bank = findViewById(R.id.id_tv_manager);
//        if(user.getType().equals(0)){
//            question_bank.setVisibility(View.GONE);
//        }
//        mScrollView=findViewById(R.id.interceptScrollView);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        container_top = findViewById(R.id.container_top);
        container_normal = findViewById(R.id.container_normal);
        viewPlace=findViewById(R.id.view_place);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        mContentViewPager = findViewById(R.id.contentViewPager);
        floatingActionButton = findViewById(R.id.fab);

        if(user.getType().equals(0)){

            question_bank.setVisibility(View.GONE);

            //初始化学期列表，并获取默认当前学期的课程列表
            niceSpinner.setBackgroundResource(R.drawable.shape_for_custom_spinner);
            niceSpinner.setTextColor(0xFFFFFFFF);
            niceSpinner.setArrowTintColor(0xFFFFFFFF);
            initSemesterList();

        }else {
            niceSpinner.setVisibility(View.GONE);
            refreshTeacherCourseList();
        }



//        initTab();
//        System.out.println("asklist"+askList);
//        cardViewListAdapter = new CardViewAskListAdapter();
//        cardViewListAdapter.setAskVOList(askList);
//        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        eRecyclerView.setAdapter(cardViewListAdapter);

    }

    private void initEvent() {
//        setToolbar(R.drawable.common_add, new ClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void click() {
//                toCreateNewHoleActivity();
//            }
//        });
        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                toHelpCenterActivity();
            }
        });

        TaskCenter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                toTaskCenterActivity();
            }
        });

        UserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserCenterActivity();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCreateAskActivity();
            }
        });

        if(user.getType().equals(0)){
            niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

                    item = (String) niceSpinner.getItemAtPosition(position);
                    courseList.clear();
                    refreshCourseList(item);
                }
            });
        }else{
            question_bank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   toQuestionActivity();
                }
            });
        }


//        cardViewListAdapter.setOnItemClickListener(new CardViewAskListAdapter.OnItemListenter() {
//            @Override
//            public void onItemClick(View view, int postion) {
//                toDetailActivity(postion);
//            }
//        });
//        initTab1();
    }

    private void initTab1(){
        fragmentList.clear();
        courseList.stream().forEach(o-> System.out.println(o.getName()));
        for (int i = 0; i < courseList.size(); i++) {
//            System.out.println("courseList.get(i).getId()"+courseList.get(i).getId());
//            System.out.println("(String) niceSpinner.getSelectedItem())"+(String) niceSpinner.getSelectedItem());
            if(user.getType().equals(0)){
                fragmentList.add(new BlankFragment2(this,courseList.get(i).getId(),(String) niceSpinner.getSelectedItem()));
            }else {
                fragmentList.add(new BlankFragment4(this,courseList.get(i).getId()));
            }


        }
        for(int i=0;i<titleList.size();i++){
            scrollMap.put(i,0);
        }
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
//        mPageAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int i) {
//                return fragmentList.get(i);
//            }
//
//            @Nullable
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return titleList.get(position);
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//            @Override
//            public int getCount() {
//                return titleList.size();
//            }
//        };

//        viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
//            @NonNull
//            @Override
//            public Fragment createFragment(int position) {
//                //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
//                // 所以不需要考虑复用的问题
//                BlankFragment2 blankFragment2 = BlankFragment2.newInstance(courseList.get(position).getName());
//                Bundle bundle = new Bundle();
//                bundle.putString("detail",new GSON().gsonSetter().toJson(askList));
//                blankFragment2.setArguments(bundle);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.addToBackStack(null)
//                        .add(R.id.,blankFragment2)
//                        .commit();
//                return blankFragment2;
//            }
//
//            @Override
//            public int getItemCount() {
//                return tabs.length;
//            }
//        });

        viewPager.setAdapter(pagerAdapter);
        System.out.println("courseList"+courseList.size());
        viewPager.setOffscreenPageLimit(courseList.size());
        viewPager.registerOnPageChangeCallback(changeCallback);

        try {
            Field recyclerViewField = ViewPager2.class.getDeclaredField("mRecyclerView");
            recyclerViewField.setAccessible(true);

            RecyclerView recyclerView = (RecyclerView) recyclerViewField.get(viewPager);

            Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop");
            touchSlopField.setAccessible(true);

            int touchSlop = (int) touchSlopField.get(recyclerView);
            touchSlopField.set(recyclerView, touchSlop * 2);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float position) {
                view.setAlpha(Math.abs(1 - Math.abs(position)));
            }
        });

        meditor = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(HoleCenterActivity.this);
//                textView.setText(tabs[position]);
                tab.setCustomView(textView);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{activeColor, normalColor};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                textView.setText(courseList.get(position).getName());
                textView.setTextSize(normalSize);
                textView.setGravity(1);
//                textView.setTextColor(colorStateList);
                tab.setCustomView(textView);

            }
        });
        meditor.attach();


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
            System.out.println("111111");
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            System.out.println("222222");
            viewPager.setCurrentItem(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case :R.id.tv
//        }
//
//    }

    private void initTab(){

        mTabSegment.reset();
//        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mTabSegment.getSelectedIndex(), false);
        for (int i = 0; i < courseList.size(); i++) {
            mTabSegment.addTab(new TabSegment.Tab(courseList.get(i).getName()));
            listFragment.add(new BlankFragment2(this,courseList.get(i).getId(),(String) niceSpinner.getSelectedItem()));
        }
        int space = DensityUtils.dp2px(HoleCenterActivity.this, 16);
        myFragmentPagerAdapter = new MyAskFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),listFragment);
        mContentViewPager.setAdapter(myFragmentPagerAdapter);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);
        mTabSegment.addOnTabSelectedListener(new TabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
//                currentCourse =courseList.get(index).getId();
//                getAskList(currentCourse,(String)niceSpinner.getSelectedItem());
//                System.out.println("asklist"+askList);
//                cardViewListAdapter = new CardViewAskListAdapter();
//                cardViewListAdapter.setAskVOList(askList);
//                View pageView = getPageView((String) niceSpinner.getSelectedItem());
//                RecyclerView pageView1 = (RecyclerView) pageView;
//                pageView1.setAdapter(cardViewListAdapter);
                XToastUtils.toast("select " + courseList.get(index).getName());
            }

            @Override
            public void onTabUnselected(int index) {
//                XToastUtils.toast("unSelect " +courseList.get(index).getName());
            }

            @Override
            public void onTabReselected(int index) {
                XToastUtils.toast("reSelect " + courseList.get(index).getName());
            }

            @Override
            public void onDoubleTap(int index) {
                XToastUtils.toast("double tap " + courseList.get(index).getName());
            }
        });
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
    private void toQuestionActivity(){
        Intent intent = new Intent(this,QuestionStoreActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }

    private void toCreateAskActivity(){
        Intent intent = new Intent(this,CreateNewAskActivity.class);
        Bundle bundle = new Bundle();
        System.out.println("semester"+currentSemster);
        bundle.putString("semester",currentSemster);
        bundle.putInt("course",currentCourse);
        intent.putExtra("data",bundle);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
    }

    private void toHelpCenterActivity() {
        Intent intent = new Intent(this, HelpCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0); // 取消Activity跳转时的动画效果
        finish();
    }
    private void toCreateNewHoleActivity(){
        Intent intent = new Intent(this,CreateNewHoleActivity.class);
        startActivity(intent);
    }
//    private synchronized void getAskList(Integer id , String semester){
//        Thread t1 = new Thread(()->{
//            askList = okHttpUtils.getAskList(id, semester);
//        });
//        t1.start();
//        try {
//            t1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private void refreshCourseList(String semester){

        //创建请求
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/course/list/" + semester, null, 300);
        //执行请求
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MyCoHelp.getAppContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Result<List<CourseVO>> result = GSON.gson.fromJson(res,new TypeToken<Result<List<CourseVO>>>(){}.getType());
                courseList =  result.getData();
                runOnUiThread(new Runnable() {
                    public void run() {
                        currentCourse = courseList!=null&&!courseList.isEmpty()?courseList.get(0).getId():0;
                        initTab1();
                    }
                });
            }
        });
    }
    private void refreshTeacherCourseList(){

        //创建请求
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/teach/listcourse", null, 300);
        //执行请求
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MyCoHelp.getAppContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Result<List<CourseVO>> result = GSON.gson.fromJson(res,new TypeToken<Result<List<CourseVO>>>(){}.getType());
                courseList =  result.getData();
                runOnUiThread(new Runnable() {
                    public void run() {
                        currentCourse = courseList!=null&&!courseList.isEmpty()?courseList.get(0).getId():0;
                        initTab1();
                    }
                });
            }
        });
    }

    private void initSemesterList(){
        //获取学期列表
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/user/semester", null, 0);
        //执行请求
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MyCoHelp.getAppContext(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = null;
                try {
                    res = response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Result<List<String>> result = GSON.gson.fromJson(res,new TypeToken<Result<List<String>>>(){}.getType());
                semesterList =  result.getData();
                runOnUiThread(new Runnable() {
                    public void run() {
                        niceSpinner.attachDataSource(semesterList);
                        //获取当前学期的课程
                        currentSemster = (String) niceSpinner.getSelectedItem();
                        refreshCourseList((String) niceSpinner.getSelectedItem());
                    }
                });
            }
        });
    }

    private synchronized void refreshHoleList(){
        cardViewListAdapter.setAskVOList(askList);
        int currentItem = mContentViewPager.getCurrentItem();
        RecyclerView childAt = (RecyclerView) mContentViewPager.getChildAt(currentItem);
        childAt.setAdapter(cardViewListAdapter);
//        eRecyclerView.setAdapter(cardViewListAdapter);
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },300);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)


    private View initSubView( Context context){
//        View view = LayoutInflater.from(context).inflate(R.layout.view_discuss_list,null);
        RecyclerView recyclerView = findViewById(R.id.id_recyclerview);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        swipeRefreshLayout.addView(recyclerView);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(cardViewListAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("isrefreshing");
            }
        });
        return swipeRefreshLayout;



    }
    private void toDetailActivity(int postion){
        Intent intent = new Intent(HoleCenterActivity.this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",holeList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(holeList.get(postion).getIdByType(holeList.get(postion).getType()),1);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        meditor.detach();
        viewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {

    }
}