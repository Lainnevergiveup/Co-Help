//package com.cohelp.task_for_stu.ui.adpter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.cohelp.task_for_stu.R;
//import com.cohelp.task_for_stu.net.model.domain.DetailResponse;
//import com.cohelp.task_for_stu.net.model.vo.AskVO;
//import com.cohelp.task_for_stu.net.model.vo.HoleVO;
//
//import java.util.List;
//@Deprecated
//public class HoleAdapter extends RecyclerView.Adapter<HoleAdapter.ViewHolder> {
//    private List<DetailResponse> holeList;
//    private OnItemListenter mItemClickListener;
//    static class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView ownerAvater;
//        TextView holeTitle;
//        TextView holeAddress;
//        TextView ownerName;
//        TextView holeTime;
//        TextView holeContext;
//        public ViewHolder(@NonNull View itemView){
//            super(itemView);
//            ownerAvater = itemView.findViewById(R.id.hole_author_pic);
//            holeTime = itemView.findViewById(R.id.hole_time);
//            holeAddress = itemView.findViewById(R.id.hole_address);
//            holeTitle = itemView.findViewById(R.id.hole_title);
//            holeContext = itemView.findViewById(R.id.hole_context);
//        }
//    }
//    public HoleAdapter(List<DetailResponse> holeList){
//        this.holeList = holeList;
//    }
//
//    public void setHoleList(List<DetailResponse> holeList) {
//        this.holeList = holeList;
//    }
//    public interface OnItemListenter{
//        void onItemClick(View view, int postion);
//    }
//
//    public void setOnItemClickListener(OnItemListenter mItemClickListener){
//        this.mItemClickListener = mItemClickListener;
//    }
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_hole_card,parent,false);
//        HoleAdapter.ViewHolder viewHolder = new HoleAdapter.ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        DetailResponse detailResponse = holeList.get(position);
//        AskVO ask = detailResponse.getHoleVO();
//        holder.holeTitle.setText(ask.getHoleTitle());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemClickListener.onItemClick(view,position);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return holeList.size();
//    }
//}
