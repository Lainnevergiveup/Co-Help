package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;

/**
 * @author xuexiang
 * @date 2017/11/21 上午10:44
 */
public class FlowTagPaidAdapter extends BaseTagAdapter<String, TextView> {

    public FlowTagPaidAdapter(Context context) {
        super(context);
    }

    @Override
    protected TextView newViewHolder(View convertView) {
        return (TextView) convertView.findViewById(R.id.tv_tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_item_tag_paid;
    }

    @Override
    protected void convert(TextView textView, String item, int position) {
        textView.setText(item);
    }
}
