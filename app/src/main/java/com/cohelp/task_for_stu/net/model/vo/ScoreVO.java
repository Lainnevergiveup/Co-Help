package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2023-03-04 23:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreVO implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 积分
     */
    private Integer score;
    /**
     * 课程名
     */
    private String courseName;
    /**
     * 学期
     */
    private String semester;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public ScoreVO(Integer userId, String userName, Integer score, String courseName, String semester) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        this.courseName = courseName;
        this.semester = semester;
    }

    public ScoreVO() {
    }
}
