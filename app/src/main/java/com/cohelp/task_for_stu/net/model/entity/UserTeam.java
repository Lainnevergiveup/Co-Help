package com.cohelp.task_for_stu.net.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 用户组织表
 * @TableName user_team
 */
@Data
public class UserTeam implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 目标组织id
     */
    private Integer targetTeamId;

    /**
     * 加入状态（0：待审批，1：同意，2：不同意）
     */
    private Integer joinState;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    private String userName;

    private String teamName;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTeam userTeam = (UserTeam) o;
        return id.equals(userTeam.id) && userId.equals(userTeam.userId) && targetTeamId.equals(userTeam.targetTeamId) && joinState.equals(userTeam.joinState) && createTime.equals(userTeam.createTime) && userName.equals(userTeam.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, targetTeamId, joinState, createTime, userName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", targetTeamId=").append(targetTeamId);
        sb.append(", joinState=").append(joinState);
        sb.append(", createTime=").append(createTime);
        sb.append(", userName=").append(userName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTargetTeamId() {
        return targetTeamId;
    }

    public void setTargetTeamId(Integer targetTeamId) {
        this.targetTeamId = targetTeamId;
    }

    public Integer getJoinState() {
        return joinState;
    }

    public void setJoinState(Integer joinState) {
        this.joinState = joinState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}