//package com.cohelp.task_for_stu.ui.adpter;
//
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
//
//import java.util.List;
//
//public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {
//
//    private List<DetailResponse> releaseList;
//
//    public OnItemListenter mItemClickListener;
//
//    public void setReleaseList(List<DetailResponse> releaseList) {
//        this.releaseList = releaseList;
//    }
//
//    public void setmItemClickListener(OnItemListenter mItemClickListener) {
//        this.mItemClickListener = mItemClickListener;
//    }
//
//    public interface OnItemListenter{
//        void onItemClick(View view, int postion);
//    }
//
//    @NonNull
//    @Override
//    public MyTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyTaskAdapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return releaseList.size();
//    }
//
//    public MyTaskAdapter(List<DetailResponse> releaseList) {
//        this.releaseList = releaseList;
//    }
//
//    public MyTaskAdapter() {
//    }
//}
