package com.cohelp.task_for_stu.net.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 组织表
 * @TableName team
 */
@Data
public class Team implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 组织名
     */
    private String teamName;

    /**
     * 组织创建人ID
     */
    private Integer teamCreator;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", teamName=").append(teamName);
        sb.append(", teamCreator=").append(teamCreator);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}