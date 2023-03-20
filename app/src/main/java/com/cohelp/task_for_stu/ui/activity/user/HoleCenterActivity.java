package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.vo.CourseVO;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.view.SwipeRefresh;
import com.cohelp.task_for_stu.ui.view.SwipeRefreshLayout;
import com.cohelp.task_for_stu.utils.SessionUtils;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
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
    TabSegment mTabSegment1;
    TabSegment mTabSegment;
    ViewPager mContentViewPager;
    OkHttpUtils okHttpUtils;
    List<DetailResponse> holeList = new ArrayList<>();
    List<CourseVO> courseList;
    Integer conditionType = 0;
    Spinner mSpinnerFitOffset;
//    HoleAdapter holeAdapter;
    CardViewListAdapter cardViewListAdapter;

    String[] pages = MultiPage.getPageNames();
    private final int TAB_COUNT = 10;
    private int mCurrentItemCount = TAB_COUNT;
    private MultiPage mDestPage = MultiPage.教育;
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
            TextView textView = new TextView(HoleCenterActivity.this);
            textView.setTextAppearance(HoleCenterActivity.this, R.style.TextStyle_Content_Match);
            textView.setGravity(Gravity.CENTER);
            textView.setText(String.format("这个是%s页面的内容", semeterName));
            view = textView;
            mPageMap.put(semeterName, view);
        }
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        XUI.init(this);
//        XUI.debug(true);
        setContentView(R.layout.activity_hole_center);

        initTools();
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

//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 从服务端搜索
//                String s = searchedContent.getText().toString();
//                if(StringUtils.isEmpty(s)){
//                    T.showToast("查询的标题不能为空哦~");
//                }
//                else {
//                    startLoadingProgress();
//                    refreshHoleList();
//                    stopLoadingProgress();
//                }
//
//
//            }
//        });

        eSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHoleList();
            }
        });
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                item = (String) niceSpinner.getItemAtPosition(position);
                System.out.println(item);



                initTab();

            }
        });

    }

    private void initView(){
        HoleCenter = findViewById(R.id.id_ll_holeCenter);
        HelpCenter = findViewById(R.id.id_ll_helpCenter);
        TaskCenter = findViewById(R.id.id_ll_activityCenter);
        UserCenter = findViewById(R.id.id_ll_userCenter);
        searchBtn = findViewById(R.id.id_iv_search);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        eSwipeRefreshLayout = findViewById(R.id.id_swiperefresh);
        eRecyclerView = findViewById(R.id.id_recyclerview);
        eSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        eSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.YELLOW,Color.GREEN);
        mTabSegment = findViewById(R.id.tabSegment);
        mContentViewPager = findViewById(R.id.contentViewPager);
//        mSpinnerFitOffset = findViewById(R.id.spinner_system_fit_offset);

        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
//        niceSpinner.setBackgroundDrawable();
        niceSpinner.setBackgroundResource(R.drawable.shape_for_custom_spinner);
        getCourseList("2022-2023-2");



        getHoleList();
//        holeAdapter = new HoleAdapter(holeList);
//        cardViewListAdapter = new CardViewListAdapter(holeList);
////        holeList.add(new Hole("强奸","wow", 0,0,0,"friend"));
//        cardViewListAdapter.setOnItemClickListener(new CardViewListAdapter.OnItemListenter(){
//            @Override
//            public void onItemClick(View view, int postion) {
//                Intent intent = new Intent(HoleCenterActivity.this,DetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("detailResponse",holeList.get(postion));
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eRecyclerView.setAdapter(cardViewListAdapter);
        initTab();






//        WidgetUtils.setSpinnerDropDownVerticalOffset(mSpinnerFitOffset);


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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toCreateNewHoleActivity(){
        Intent intent = new Intent(this,CreateNewHoleActivity.class);
        startActivity(intent);
    }
    private synchronized void getHoleList(){
        Thread t1 = new Thread(()->{
            holeList = okHttpUtils.holeList(conditionType);
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
        getHoleList();
        cardViewListAdapter.setDetailResponseListList(holeList);
        eRecyclerView.setAdapter(cardViewListAdapter);
        eSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭刷新
                eSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }
    private void initTab(){
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mTabSegment.getSelectedIndex(), false);
        for (int i = 0; i < mCurrentItemCount; i++) {
            mTabSegment.addTab(new TabSegment.Tab(courseList.get(i).getName()));
        }
        int space = DensityUtils.dp2px(HoleCenterActivity.this, 16);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);
        mTabSegment.addOnTabSelectedListener(new TabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                XToastUtils.toast("select " + courseList.get(index).getName());
            }

            @Override
            public void onTabUnselected(int index) {
                XToastUtils.toast("unSelect " +courseList.get(index).getName());
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
}