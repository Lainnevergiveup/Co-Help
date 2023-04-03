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