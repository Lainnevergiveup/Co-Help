package com.cohelp.task_for_stu.net.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 浏览记录表
 * @TableName history
 */
@Data
public class History implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 话题类型（1：活动 2：互助 3：树洞）
     */
    private Integer topicType;

    /**
     * 话题id
     */
    private Integer topicId;

    /**
     * 查看/浏览时间
     */
    private Date viewTime;

    /**
     * 是否参与话题讨论（0:未参与 1：已参与）
     */
    private Integer isInvolved;

    private static final long serialVersionUID = 1L;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", topicType=").append(topicType);
        sb.append(", topicId=").append(topicId);
        sb.append(", viewTime=").append(viewTime);
        sb.append(", isInvolved=").append(isInvolved);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}