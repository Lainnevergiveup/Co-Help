package com.cohelp.task_for_stu;

import android.app.Application;

import com.xuexiang.xui.XUI;

public class MyCoHelp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
    }
}
