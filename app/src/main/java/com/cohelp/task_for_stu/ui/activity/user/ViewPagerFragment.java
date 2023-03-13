package com.cohelp.task_for_stu.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cohelp.task_for_stu.R;
import com.cohelp.task_for_stu.net.OKHttpTools.OkHttpUtils;
import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
import com.cohelp.task_for_stu.net.model.domain.IdAndType;
import com.cohelp.task_for_stu.ui.adpter.NewsListEditAdapter;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ViewPagerFragment extends Fragment {
    private TextView tvShow;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NewsListEditAdapter mAdapter;
    private FrameLayout flEdit;
    private SmoothCheckBox scbSelectAll;
    private Button btn_delete;
    private OkHttpUtils okHttpUtils = new OkHttpUtils();
    private List<DetailResponse> taskList;


    private TextView mTvSwitch;
    public ViewPagerFragment() {
        // Required empty public constructor
    }
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.viewpagerfragment, container, false);

//        tvShow = (TextView) view.findViewById(R.id.tv_show);
//
//        Bundle arguments = getArguments();
//        String name = arguments.getString("name");
//        tvShow.setText(name);
        System.out.println("fview:::"+view);
        flEdit = this.view.findViewById(R.id.fl_edit);
        scbSelectAll = this.view.findViewById(R.id.scb_select_all);
        recyclerView = this.view.findViewById(R.id.recyclerView);
        refreshLayout = this.view.findViewById(R.id.refreshLayout);
        btn_delete = this.view.findViewById(R.id.btn_delete);
        mTvSwitch = this.view.findViewById(R.id.id_tv_manager);

        initEvent();
        Bundle arguments = getArguments();
        String name = arguments.getString("datailResponse");
        System.out.println(name);
        List<DetailResponse> json = okHttpUtils.getGson().fromJson(name, new TypeToken<List<DetailResponse>>() {
        }.getType());

        System.out.println("json"+json);
        System.out.println("11111111111111111111111111111111111111");
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new NewsListEditAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        },json));
        scbSelectAll.setOnCheckedChangeListener((checkBox, isChecked) -> mAdapter.setSelectAll(isChecked));

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.refresh(json);
            refreshLayout.finishRefresh();
        }, 1000));
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.loadMore(json);
            refreshLayout.finishLoadMore();
        }, 1000));
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        mAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (mAdapter.isManageMode()) {
                mAdapter.updateSelectStatus(position);
            } else {
                toDetailActivity(position);
//                Utils.goWeb(getContext(), item.getDetailUrl());
            }
        });
        mAdapter.setOnItemClickListener(new NewsListEditAdapter.OnItemListenter() {
            @Override
            public void onItemClick(View view, int postion) {
                toDetailActivity(postion);
            }
        });
        mAdapter.setOnItemLongClickListener((itemView, item, position) -> {
            if (!mAdapter.isManageMode()) {
                mAdapter.enterManageMode(position);
                refreshManageMode();
            }
        });

        return this.view;
    }



    private void initEvent(){
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showSimpleConfirmDialog();
            }
        });
    }



    @Nullable
    @Override
    public View getView() {
        return this.view;
    }

    /**
     * 简单的确认对话框
     */
    private void showSimpleConfirmDialog() {
        new MaterialDialog.Builder(getContext())
                .content("是否确认删除")
                .positiveText(R.string.lab_yes)
                .negativeText(R.string.lab_no)
                .show();
    }

//
//    /**
//     *  指示器
//     */
//    private Map<ContentPage, View> mPageMap = new HashMap<>();
//
//    private PagerAdapter mPagerAdapter = new PagerAdapter() {
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return view == object;
//        }
//
//        @Override
//        public int getCount() {
//            return ContentPage.size();
//        }
//
//        @Override
//        public Object instantiateItem(final ViewGroup container, int position) {
//            ContentPage page = ContentPage.getPage(position);
//            View view = getPageView(page);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            container.addView(view, params);
//            return view;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
//        }
//    };
//
//    private View getPageView(ContentPage page) {
//        View view = mPageMap.get(page);
//        if (view == null) {
//
//
//            mPageMap.put(page, view);
//        }
//        return view;
//    }
//



    private void toDetailActivity(int postion){
        Intent intent = new Intent(getContext(),DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailResponse",taskList.get(postion));
        intent.putExtras(bundle);
        IdAndType idAndType = new IdAndType(taskList.get(postion).getActivityVO().getId(),1);
        new Thread(()->{
            System.out.println(okHttpUtils.getDetail(idAndType));
        }).start();
        startActivity(intent);
    }

    private void refreshManageMode() {
        if (mTvSwitch != null) {
            mTvSwitch.setText(mAdapter.isManageMode() ? "退出" : "管理");
            mTvSwitch.getCompoundPaddingLeft();

        }
        ViewUtils.setVisibility(flEdit, mAdapter.isManageMode());
    }


    public TextView getTvShow() {
        return tvShow;
    }

    public void setTvShow(TextView tvShow) {
        this.tvShow = tvShow;
    }

    public SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setRefreshLayout(SmartRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public NewsListEditAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(NewsListEditAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public FrameLayout getFlEdit() {
        return flEdit;
    }

    public void setFlEdit(FrameLayout flEdit) {
        this.flEdit = flEdit;
    }

    public SmoothCheckBox getScbSelectAll() {
        return scbSelectAll;
    }

    public void setScbSelectAll(SmoothCheckBox scbSelectAll) {
        this.scbSelectAll = scbSelectAll;
    }

    public Button getBtn_delete() {
        return btn_delete;
    }

    public void setBtn_delete(Button btn_delete) {
        this.btn_delete = btn_delete;
    }

    public OkHttpUtils getOkHttpUtils() {
        return okHttpUtils;
    }

    public void setOkHttpUtils(OkHttpUtils okHttpUtils) {
        this.okHttpUtils = okHttpUtils;
    }

    public List<DetailResponse> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<DetailResponse> taskList) {
        this.taskList = taskList;
    }

    public TextView getmTvSwitch() {
        return mTvSwitch;
    }

    public void setmTvSwitch(TextView mTvSwitch) {
        this.mTvSwitch = mTvSwitch;
    }

    public void setView(View view) {
        this.view = view;
    }
}
