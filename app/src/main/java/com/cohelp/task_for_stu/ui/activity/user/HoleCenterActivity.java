package com.cohelp.task_for_stu.ui.activity.user;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.cohelp.task_for_stu.MyCoHelp;
import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OKHttp;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.gsonTools.GSON;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.Result;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.net.model.vo.CourseVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewAskListAdapter;
import com.cohelp.task_for_stu.ui.adpter.CustomArrayAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyAskFragmentPagerAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyFragmentPagerAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.reflect.TypeToken;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    List<String> coursename;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_center);

        initView();
        initEvent();
        setUpToolBar();
        setTitle("");
    }




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
            CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(this, coursename);
            niceSpinner.setAdapter(customArrayAdapter);
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

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);

        viewPager.setAdapter(pagerAdapter);
        if(courseList.size()!=0){
            viewPager.setOffscreenPageLimit(courseList.size());
            viewPager.registerOnPageChangeCallback(changeCallback);
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

        try {
            Field recyclerViewField = ViewPager2.class.getDeclaredField("mRecyclerView");
            recyclerViewField.setAccessible(true);

            RecyclerView recyclerView = (RecyclerView) recyclerViewField.get(viewPager);

            Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop");
            touchSlopField.setAccessible(true);

            int touchSlop = (int) touchSlopField.get(recyclerView);
            touchSlopField.set(recyclerView, touchSlop * 4);
        } catch (NoSuchFieldException | IllegalAccessException e) {
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

    private void refreshCourseList(String semester){
        if (semester==null||semester.equals(""))
            return;
        //创建请求
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/course/list/" + semester, null, 300);
        //执行请求
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

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
                if(result!=null){
                    courseList =  result.getData();
                }else{
                    courseList = new ArrayList<>();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        coursename = courseList.stream().map(k -> k.getName()).collect(Collectors.toList());
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
                if(result!=null){
                    courseList = result.getData();
                }else{
                    courseList = new ArrayList<>();
                }
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
        Request request = OKHttp.buildGetRequest(OkHttpUtils.baseURL + "/user/semester", null, 300);
        //执行请求
        OKHttp.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

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
                if(result!=null){
                    semesterList = result.getData();
                }else{
                    semesterList = new ArrayList<>();

                }
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
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },300);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)


    @Override
    protected void onDestroy() {
        if (meditor!=null && viewPager !=null){
            meditor.detach();
            viewPager.unregisterOnPageChangeCallback(changeCallback);
        }
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {

    }
}