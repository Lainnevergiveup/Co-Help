package com.cohelp.task_for_stu.net.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerVO implements Serializable {
    /**
     * ID
     */
    private Integer id;
    /**
     * 回答的内容
     */
    private String content;
    /**
     * 提问ID
     */
    private Integer askId;
    /**
     * 回答的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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
     * 回答目标的ID
     */
    private Integer answerTargetId;

    /**
     * 回答目标类型
     */
    private Integer answerTargetType;



    /**
     * 回答发布者的昵称
     */
    private String publisherName;
    /**
     * 回答发布者的头像
     */
    private String publisherAvatar;


    /**
     * 回答目标发布者的昵称
     */
    private String answerTargetName;


    /**
     * 回答内容中的图片
     */
    private List<String> answerImageUrl;


    /**
     * 是否已点赞
     */
    private Integer isLiked;

    private static final long serialVersionUID = 1L;

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

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAvatar() {
        return publisherAvatar;
    }

    public void setPublisherAvatar(String publisherAvatar) {
        this.publisherAvatar = publisherAvatar;
    }

    public String getAnswerTargetName() {
        return answerTargetName;
    }

    public void setAnswerTargetName(String answerTargetName) {
        this.answerTargetName = answerTargetName;
    }

    public List<String> getAnswerImageUrl() {
        return answerImageUrl;
    }

    public void setAnswerImageUrl(List<String> answerImageUrl) {
        this.answerImageUrl = answerImageUrl;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    public AnswerVO(Integer id, String content, Integer askId, Date publishTime, Integer publisherId, Integer likeCount, Integer answerTargetId, Integer answerTargetType, String publisherName, String publisherAvatar, String answerTargetName, List<String> answerImageUrl, Integer isLiked) {
        this.id = id;
        this.content = content;
        this.askId = askId;
        this.publishTime = publishTime;
        this.publisherId = publisherId;
        this.likeCount = likeCount;
        this.answerTargetId = answerTargetId;
        this.answerTargetType = answerTargetType;
        this.publisherName = publisherName;
        this.publisherAvatar = publisherAvatar;
        this.answerTargetName = answerTargetName;
        this.answerImageUrl = answerImageUrl;
        this.isLiked = isLiked;
    }

    public AnswerVO() {
    }
}
