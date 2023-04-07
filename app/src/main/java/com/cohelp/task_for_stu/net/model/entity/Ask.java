package com.cohelp.task_for_stu.net.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName ask
 */
@Data
public class Ask implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 题干
     */
    private String question;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 学年
     */
    private String semester;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 发布者id
     */
    private Integer publisherId;

    /**
     * 
     */
    private Integer collectCount;

    /**
     * 
     */
    private Integer answerCount;

    /**
     * 是否已加入题库（0:未加入 1:已加入）
     */
    private Integer isAdded;

    private static final long serialVersionUID = 1L;

    public Ask(String question, Integer courseId, String semester) {
        this.question = question;
        this.courseId = courseId;
        this.semester = semester;
    }

    public Ask() {
    }

    public Ask(Integer id, String question, Integer courseId, String semester, Date publishTime, Integer likeCount, Integer publisherId, Integer collectCount, Integer answerCount, Integer isAdded) {
        this.id = id;
        this.question = question;
        this.courseId = courseId;
        this.semester = semester;
        this.publishTime = publishTime;
        this.likeCount = likeCount;
        this.publisherId = publisherId;
        this.collectCount = collectCount;
        this.answerCount = answerCount;
        this.isAdded = isAdded;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
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
        sb.append(", question=").append(question);
        sb.append(", courseId=").append(courseId);
        sb.append(", semester=").append(semester);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", publisherId=").append(publisherId);
        sb.append(", collectCount=").append(collectCount);
        sb.append(", answerCount=").append(answerCount);
        sb.append(", isAdded=").append(isAdded);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}