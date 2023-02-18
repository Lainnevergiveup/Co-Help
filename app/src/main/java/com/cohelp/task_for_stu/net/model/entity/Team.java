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
//        sb.append(getClass().getSimpleName());
//        sb.append(" [");
//        sb.append("Hash = ").append(hashCode());
//        sb.append(", id=").append(id);
//        sb.append(", teamName=");
                sb.append(teamName);
//        sb.append(", teamCreator=").append(teamCreator);
//        sb.append(", createTime=").append(createTime);
//        sb.append(", serialVersionUID=").append(serialVersionUID);
//        sb.append("]");
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamCreator() {
        return teamCreator;
    }

    public void setTeamCreator(Integer teamCreator) {
        this.teamCreator = teamCreator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Team(Integer id, String teamName, Integer teamCreator, Date createTime) {
        this.id = id;
        this.teamName = teamName;
        this.teamCreator = teamCreator;
        this.createTime = createTime;
    }

    public Team() {
    }
}