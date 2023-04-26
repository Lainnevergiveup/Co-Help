package com.cohelp.task_for_stu.ui.adpter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cohelp.task_for_stu.R;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final LayoutInflater mInflater;
    private final Context mContext;

    public CustomArrayAdapter(Context context, List<String> items) {
        super(context, R.layout.activity_hole_center, items);
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
