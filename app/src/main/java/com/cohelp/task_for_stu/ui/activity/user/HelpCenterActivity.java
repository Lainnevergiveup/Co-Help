package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.activity.BaseActivity;
import com.cohelp.task_for_stu.ui.adpter.CardViewListAdapter;
import com.cohelp.task_for_stu.ui.adpter.MyFragmentPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wyt.searchbox.SearchFragment;

import java.util.ArrayList;
import java.util.List;

/*
问答中心
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HelpCenterActivity extends BaseActivity {
    LinearLayout HelpCenter;
    LinearLayout TaskCenter;
    LinearLayout HoleCenter;
    LinearLayout UserCenter;

    TextView title;

    ViewPager2 viewPager;

    FloatingActionButton floatingActionButton;
    androidx.appcompat.widget.Toolbar toolbar;
    SearchFragment searchFragment = SearchFragment.newInstance();
    EditText searchedContent;
    ImageView searchBtn,search;


    List<DetailResponse> helpList = new ArrayList<>();


    CardViewListAdapter cardViewListAdapter = new CardViewListAdapter();

    String[] pages = MultiPage.getPageNames();
    String currentTab;

    private TabLayout tabLayout;
    private int activeColor = Color.parseColor("#ff678f");
    private int normalColor = Color.parseColor("#666666");

    private ArrayList<Fragment> fragmentList=new ArrayList<>();
    private TabLayoutMediator meditor;
    int activeSize = 16;
    int normalSize = 14;

    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;


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
//        toolbar.setNavigationOnClickListener(onClickListener);
        toolbar.inflateMenu(R.menu.menu_help);
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        title.setText("互助");
    }

    private void initTab(){
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
                tab.setCustomView(textView);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{activeColor, normalColor};
                currentTab = pages[position];
                textView.setText(pages[position]);
                textView.setTextSize(normalSize);
                textView.setGravity(1);
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCreateNewHelpActivity();
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
    }

    androidx.appcompat.widget.Toolbar.OnMenuItemClickListener menuItemClickListener = item -> {
       if(item.getItemId() == R.id.item_search){
            searchFragment.showFragment(getSupportFragmentManager(), SearchFragment.TAG);
        }
        return false;
    };




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
        floatingActionButton = findViewById(R.id.fab);
        searchedContent = findViewById(R.id.id_et_search);
        searchBtn = findViewById(R.id.id_iv_search);

        viewPager = findViewById(R.id.contentViewPager);
        tabLayout = findViewById(R.id.tabLayout);

    }




    private synchronized void getHelpList(){
        Thread t1 = new Thread(()->{
            helpList =OkHttpUtils.helpList(0);
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