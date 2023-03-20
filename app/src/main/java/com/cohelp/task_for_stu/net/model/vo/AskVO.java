package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author jianping5
 * @createDate 1/3/2023 下午 3:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskVO implements Serializable {

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
     * 活动发布者昵称
     */
    private String userName;

    /**
     * 活动发布者头像 Url
     */
    private String avatarUrl;

    /**
     * 图片
     */
    private List<String> imageUrl;

    /**
     * 是否被点赞
     */
    private Integer isLiked;
    /**
     * 是否被收藏
     */
    private Integer isCollected;

    /**
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 答案量
     */
    private Integer answerCount;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
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

    public AskVO(Integer id, String question, Integer courseId, String semester, Date publishTime, Integer likeCount, Integer publisherId, String userName, String avatarUrl, List<String> imageUrl, Integer isLiked, Integer isCollected, Integer collectCount, Integer answerCount) {
        this.id = id;
        this.question = question;
        this.courseId = courseId;
        this.semester = semester;
        this.publishTime = publishTime;
        this.likeCount = likeCount;
        this.publisherId = publisherId;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.imageUrl = imageUrl;
        this.isLiked = isLiked;
        this.isCollected = isCollected;
        this.collectCount = collectCount;
        this.answerCount = answerCount;
    }

    public AskVO() {
    }
}
