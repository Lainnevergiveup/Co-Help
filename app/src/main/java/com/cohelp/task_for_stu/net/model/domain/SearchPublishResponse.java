package com.cohelp.task_for_stu.net.model.domain;

import com.cohelp.task_for_stu.net.model.entity.Activity;
import com.cohelp.task_for_stu.net.model.entity.Help;
import com.cohelp.task_for_stu.net.model.entity.Hole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author jianping5
 * @createDate 2022/10/26 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPublishResponse {

    /**
     * 活动list
     */
    private ArrayList<Activity> activityList;

    /**
     * 互助list
     */
    private ArrayList<Help> helpList;

    /**
     * 树洞list
     */
    private ArrayList<Hole> holeList;

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }

    public ArrayList<Help> getHelpList() {
        return helpList;
    }

    public void setHelpList(ArrayList<Help> helpList) {
        this.helpList = helpList;
    }

    public ArrayList<Hole> getHoleList() {
        return holeList;
    }

    public void setHoleList(ArrayList<Hole> holeList) {
        this.holeList = holeList;
    }
}
