package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 互助视图体
 *
 * @author jianping5
 * @createDate 2022/11/2 18:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 互助发布者id
     */
    private Integer helpOwnerId;

    /**
     * 互助发布者昵称
     */
    private String userName;

    /**
     * 互助发布者头像
     */
    private Integer avatar;

    /**
     * 互助标题
     */
    private String helpTitle;

    /**
     * 互助内容
     */
    private String helpDetail;

    /**
     * 互助有/无偿
     */
    private Integer helpPaid;

    /**
     * 互助点赞量
     */
    private Integer helpLike;

    /**
     * 互助收藏量
     */
    private Integer helpCollect;

    /**
     * 互助评论量
     */
    private Integer helpComment;

    /**
     * 互助标签
     */
    private String helpLabel;

    /**
     * 互助发布时间
     */
    private Date helpCreateTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHelpOwnerId() {
        return helpOwnerId;
    }

    public void setHelpOwnerId(Integer helpOwnerId) {
        this.helpOwnerId = helpOwnerId;
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

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public String getHelpDetail() {
        return helpDetail;
    }

    public void setHelpDetail(String helpDetail) {
        this.helpDetail = helpDetail;
    }

    public Integer getHelpPaid() {
        return helpPaid;
    }

    public void setHelpPaid(Integer helpPaid) {
        this.helpPaid = helpPaid;
    }

    public Integer getHelpLike() {
        return helpLike;
    }

    public void setHelpLike(Integer helpLike) {
        this.helpLike = helpLike;
    }

    public Integer getHelpCollect() {
        return helpCollect;
    }

    public void setHelpCollect(Integer helpCollect) {
        this.helpCollect = helpCollect;
    }

    public Integer getHelpComment() {
        return helpComment;
    }

    public void setHelpComment(Integer helpComment) {
        this.helpComment = helpComment;
    }

    public String getHelpLabel() {
        return helpLabel;
    }

    public void setHelpLabel(String helpLabel) {
        this.helpLabel = helpLabel;
    }

    public Date getHelpCreateTime() {
        return helpCreateTime;
    }

    public void setHelpCreateTime(Date helpCreateTime) {
        this.helpCreateTime = helpCreateTime;
    }
}
