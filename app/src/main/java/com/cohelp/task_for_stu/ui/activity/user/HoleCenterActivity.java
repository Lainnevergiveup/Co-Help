package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.net.model.vo.AskVO;
import com.cohelp.task_for_stu.net.model.vo.CourseVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewAskListAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyAskFragmentPagerAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoleCenterActivity extends BaseActivity {
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
    private final int TAB_COUNT = 10;
    private int mCurrentItemCount = TAB_COUNT;
//    private MultiPage mDestPage = MultiPage.教育;
    private Map<String, View> mPageMap = new HashMap<>();
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return courseList.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            CourseVO courseVO = courseList.get(position);
            View view = getPageView(courseVO.getName());
            view.setTag(courseVO.getName());
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
                if (pos >= mCurrentItemCount) {
                    return POSITION_NONE;
                }
                return POSITION_UNCHANGED;
            }
            return POSITION_NONE;
        }
    };

    private View getPageView(String semeterName) {

        View view = mPageMap.get(semeterName);
        if (view == null) {
//            TextView textView = new TextView(HoleCenterActivity.this);
//            textView.setTextAppearance(HoleCenterActivity.this, R.style.TextStyle_Content_Match);
//            textView.setGravity(Gravity.CENTER);
//            textView.setText(String.format("这个是%s页面的内容", semeterName));
//            view = textView;
//            view = initSubView();
//            RecyclerView recyclerView = findViewById(R.id.id_recyclerview);
//            SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.id_swiperefresh);
//            SwipeRefreshLayout swipeRefreshLayout  = new SwipeRefreshLayout(HoleCenterActivity.this);
            RecyclerView recyclerView = new RecyclerView(HoleCenterActivity.this,null);
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
            mPageMap.put(semeterName, view);
        }
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        XUI.init(this);
//        XUI.debug(true);
        setContentView(R.layout.activity_hole_center);

        initTools();
        initData();
        initView();
        initEvent();
        setUpToolBar();
        setTitle("");
    }
    private void initTools(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            okHttpUtils = new OkHttpUtils();
        }
        okHttpUtils.setCookie(SessionUtils.getCookiePreference(this));
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
        HoleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {toHoleCenterActivity();}
        });

//
//        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshHoleList();
//            }
//        });
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

                item = (String) niceSpinner.getItemAtPosition(position);
                System.out.println(item);

                mPageMap.clear();
                courseList.clear();
                getCourseList(item);
                initTab();

            }
        });
//        cardViewListAdapter.setOnItemClickListener(new CardViewAskListAdapter.OnItemListenter() {
//            @Override
//            public void onItemClick(View view, int postion) {
//                toDetailActivity(postion);
//            }
//        });



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initData(){
        getSemesterList();
        getCourseList(currentSemster);
        getAskList(currentCourse,currentSemster);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView(){
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        searchBtn = findViewById(R.id.id_iv_search);

        System.out.println(holeList);

        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
//        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
////        eRecyclerView = findViewById(R.id.id_recyclerview);
//        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.PULL_FROM_START);
//        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        mTabSegment = findViewById(R.id.tabSegment);
        mContentViewPager = findViewById(R.id.contentViewPager);
//        mSpinnerFitOffset = findViewById(R.id.spinner_system_fit_offset);
        getSemesterList();

//        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(semesterList);
//        niceSpinner.setBackgroundDrawable();
        niceSpinner.setBackgroundResource(R.drawable.shape_for_custom_spinner);
        getCourseList((String) niceSpinner.getSelectedItem());


        initTab();
//        System.out.println("asklist"+askList);
//        cardViewListAdapter = new CardViewAskListAdapter();
//        cardViewListAdapter.setAskVOList(askList);
//        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        eRecyclerView.setAdapter(cardViewListAdapter);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
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
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toTaskCenterActivity() {
        Intent intent = new Intent(this,TaskCenterActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toCreateNewHoleActivity(){
        Intent intent = new Intent(this,CreateNewHoleActivity.class);
        startActivity(intent);
    }
    private synchronized void getAskList(Integer id , String semester){
        Thread t1 = new Thread(()->{
            askList = okHttpUtils.getAskList(id, semester);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void getCourseList(String semeter){
        Thread thread = new Thread(() -> {
             courseList = okHttpUtils.getCourseList(semeter);
             currentCourse = courseList==null&&!courseList.isEmpty()?0:courseList.get(0).getId();
            System.out.println("course"+courseList);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private synchronized void getSemesterList(){
        Thread thread = new Thread(()->{
            semesterList = okHttpUtils.getSemesterList();
            currentSemster = semesterList==null&&!semesterList.isEmpty()?"":semesterList.get(0);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(semesterList);
    }
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
}