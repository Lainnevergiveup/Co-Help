package com.cohelp.task_for_stu.net.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RemarkVO {
    /**
     * ID
     */
    private Integer id;
    /**
     * 话题ID
     */
    private Integer topicId;
    /**
     * 评论目标的ID
     */
    private Integer remarkTargetId;
    /**
     * 评论链顶层（根）ID
     */
       // 2->4
    private Integer topId;
    /**
     * 评论目标是否为话题（0：否 1：是）
     */
    private Integer targetIsTopic;
    /**
     * 评论发布者的昵称
     */
    private String remarkOwnerName;
    /**
     * 评论发布者的头像
     */
    private String remarkOwnerAvatar;
    /**
     * 评论的内容
     */
    private String remarkContent;
    /**
     * 评论点赞量
     */
    private Integer remarkLike;
    /**
     * 评论的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date remarkTime;
    /**
     * 是否已点赞
     */
    private Integer isLiked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getRemarkTargetId() {
        return remarkTargetId;
    }

    public void setRemarkTargetId(Integer remarkTargetId) {
        this.remarkTargetId = remarkTargetId;
    }

    public Integer getTopId() {
        return topId;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    public Integer getTargetIsTopic() {
        return targetIsTopic;
    }

    public void setTargetIsTopic(Integer targetIsTopic) {
        this.targetIsTopic = targetIsTopic;
    }

    public String getRemarkOwnerName() {
        return remarkOwnerName;
    }

    public void setRemarkOwnerName(String remarkOwnerName) {
        this.remarkOwnerName = remarkOwnerName;
    }

    public String getRemarkOwnerAvatar() {
        return remarkOwnerAvatar;
    }

    public void setRemarkOwnerAvatar(String remarkOwnerAvatar) {
        this.remarkOwnerAvatar = remarkOwnerAvatar;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }

    public Integer getRemarkLike() {
        return remarkLike;
    }

    public void setRemarkLike(Integer remarkLike) {
        this.remarkLike = remarkLike;
    }

    public Date getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(Date remarkTime) {
        this.remarkTime = remarkTime;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return "RemarkVO{" +
                "id=" + id +
                ", topicId=" + topicId +
                ", remarkTargetId=" + remarkTargetId +
                ", topId=" + topId +
                ", targetIsTopic=" + targetIsTopic +
                ", remarkOwnerName='" + remarkOwnerName + '\'' +
                ", remarkOwnerAvatar='" + remarkOwnerAvatar + '\'' +
                ", remarkContent='" + remarkContent + '\'' +
                ", remarkLike=" + remarkLike +
                ", remarkTime=" + remarkTime +
                ", isLiked=" + isLiked +
                '}';
    }

    public RemarkVO(Integer id, Integer topicId, Integer remarkTargetId, Integer topId, Integer targetIsTopic, String remarkOwnerName, String remarkOwnerAvatar, String remarkContent, Integer remarkLike, Date remarkTime, Integer isLiked) {
        this.id = id;
        this.topicId = topicId;
        this.remarkTargetId = remarkTargetId;
        this.topId = topId;
        this.targetIsTopic = targetIsTopic;
        this.remarkOwnerName = remarkOwnerName;
        this.remarkOwnerAvatar = remarkOwnerAvatar;
        this.remarkContent = remarkContent;
        this.remarkLike = remarkLike;
        this.remarkTime = remarkTime;
        this.isLiked = isLiked;
    }

    public RemarkVO() {
    }
}
