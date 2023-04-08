package com.cohelp.task_for_stu.net.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName answer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 题目id
     */
    private Integer askId;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 发布者id
     */
    private Integer publisherId;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 回答目标id
     */
    private Integer answerTargetId;

    /**
     * 回答目标类型
     */
    private Integer answerTargetType;

    /**
     * 是否已加入答案库（0：未加入 1：已加入）
     */
    private Integer isAdded;

    private static final long serialVersionUID = 1L;

    public Answer(Integer id, String content, Integer askId, Date publishTime, Integer publisherId, Integer likeCount, Integer answerTargetId, Integer answerTargetType, Integer isAdded) {
        this.id = id;
        this.content = content;
        this.askId = askId;
        this.publishTime = publishTime;
        this.publisherId = publisherId;
        this.likeCount = likeCount;
        this.answerTargetId = answerTargetId;
        this.answerTargetType = answerTargetType;
        this.isAdded = isAdded;
    }

    public Answer(String content, Integer askId, Integer answerTargetId, Integer answerTargetType) {
        this.content = content;
        this.askId = askId;
        this.answerTargetId = answerTargetId;
        this.answerTargetType = answerTargetType;
    }

    public Answer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAskId() {
        return askId;
    }

    public void setAskId(Integer askId) {
        this.askId = askId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getAnswerTargetId() {
        return answerTargetId;
    }

    public void setAnswerTargetId(Integer answerTargetId) {
        this.answerTargetId = answerTargetId;
    }

    public Integer getAnswerTargetType() {
        return answerTargetType;
    }

    public void setAnswerTargetType(Integer answerTargetType) {
        this.answerTargetType = answerTargetType;
    }

    public Integer getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(Integer isAdded) {
        this.isAdded = isAdded;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", askId=").append(askId);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", publisherId=").append(publisherId);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", answerTargetId=").append(answerTargetId);
        sb.append(", answerTargetType=").append(answerTargetType);
        sb.append(", isAdded=").append(isAdded);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}