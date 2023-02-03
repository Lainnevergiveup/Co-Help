package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jianping5
 * @createDate 2022/11/2 18:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 树洞发布者id
     */
    private Integer holeOwnerId;

    /**
     * 树洞发布者昵称
     */
    private String userName;

    /**
     * 树洞发布者头像
     */
    private Integer avatar;

    /**
     * 树洞主题
     */
    private String holeTitle;

    /**
     * 树洞内容
     */
    private String holeDetail;

    /**
     * 树洞点赞量
     */
    private Integer holeLike;

    /**
     * 树洞收藏量
     */
    private Integer holeCollect;

    /**
     * 树洞评论量
     */
    private Integer holeComment;

    /**
     * 树洞标签
     */
    private String holeLabel;


    /**
     * 树洞发布时间
     */
    private Date holeCreateTime;

    /**
     *组织id
     */
    private Integer teamId;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "HoleVO{" +
                "id=" + id +
                ", holeOwnerId=" + holeOwnerId +
                ", userName='" + userName + '\'' +
                ", avatar=" + avatar +
                ", holeTitle='" + holeTitle + '\'' +
                ", holeDetail='" + holeDetail + '\'' +
                ", holeLike=" + holeLike +
                ", holeCollect=" + holeCollect +
                ", holeComment=" + holeComment +
                ", holeLabel='" + holeLabel + '\'' +
                ", holeCreateTime=" + holeCreateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHoleOwnerId() {
        return holeOwnerId;
    }

    public void setHoleOwnerId(Integer holeOwnerId) {
        this.holeOwnerId = holeOwnerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getHoleTitle() {
        return holeTitle;
    }

    public void setHoleTitle(String holeTitle) {
        this.holeTitle = holeTitle;
    }

    public String getHoleDetail() {
        return holeDetail;
    }

    public void setHoleDetail(String holeDetail) {
        this.holeDetail = holeDetail;
    }

    public Integer getHoleLike() {
        return holeLike;
    }

    public void setHoleLike(Integer holeLike) {
        this.holeLike = holeLike;
    }

    public Integer getHoleCollect() {
        return holeCollect;
    }

    public void setHoleCollect(Integer holeCollect) {
        this.holeCollect = holeCollect;
    }

    public Integer getHoleComment() {
        return holeComment;
    }

    public void setHoleComment(Integer holeComment) {
        this.holeComment = holeComment;
    }

    public String getHoleLabel() {
        return holeLabel;
    }

    public void setHoleLabel(String holeLabel) {
        this.holeLabel = holeLabel;
    }

    public Date getHoleCreateTime() {
        return holeCreateTime;
    }

    public void setHoleCreateTime(Date holeCreateTime) {
        this.holeCreateTime = holeCreateTime;
    }
}
