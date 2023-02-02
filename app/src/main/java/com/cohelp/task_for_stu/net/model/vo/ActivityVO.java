package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动视图体
 *
 * @author jianping5
 * @createDate 2022/11/2 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private Integer id;

    /**
     * 活动发布者id
     */
    private Integer activityOwnerId;

    /**
     * 活动发布者昵称
     */
    private String userName;

    /**
     * 活动发布者头像
     */
    private Integer avatar;

    /**
     * 活动标题
     */
    private String activityTitle;

    /**
     * 活动内容
     */
    private String activityDetail;

    /**
     * 活动时间
     */
    private LocalDateTime activityTime;

    /**
     * 活动点赞量
     */
    private Integer activityLike;

    /**
     * 活动评论量
     */
    private Integer activityComment;

    /**
     * 活动标签
     */
    private String activityLabel;

    /**
     * 活动收藏量
     */
    private Integer activityCollect;

    /**
     * 活动状态（0：正常 1：异常）
     */
    private Integer activityState;

    /**
     * 活动发布时间
     */
    private LocalDateTime activityCreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityOwnerId() {
        return activityOwnerId;
    }

    public void setActivityOwnerId(Integer activityOwnerId) {
        this.activityOwnerId = activityOwnerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public LocalDateTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(LocalDateTime activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getActivityLike() {
        return activityLike;
    }

    public void setActivityLike(Integer activityLike) {
        this.activityLike = activityLike;
    }

    public Integer getActivityComment() {
        return activityComment;
    }

    public void setActivityComment(Integer activityComment) {
        this.activityComment = activityComment;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public Integer getActivityCollect() {
        return activityCollect;
    }

    public void setActivityCollect(Integer activityCollect) {
        this.activityCollect = activityCollect;
    }

    public Integer getActivityState() {
        return activityState;
    }

    public void setActivityState(Integer activityState) {
        this.activityState = activityState;
    }

    public LocalDateTime getActivityCreateTime() {
        return activityCreateTime;
    }

    public void setActivityCreateTime(LocalDateTime activityCreateTime) {
        this.activityCreateTime = activityCreateTime;
    }
}
