package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jianping5
 * @createDate 3/3/2023 下午 8:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectionVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学生昵称
     */
    private String userName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 学年
     */
    private String semester;

    /**
     * 积分
     */
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public SelectionVO() {
    }

    public SelectionVO(Integer id, Integer studentId, String userName, String courseName, Integer courseId, String semester, Integer score) {
        this.id = id;
        this.studentId = studentId;
        this.userName = userName;
        this.courseName = courseName;
        this.courseId = courseId;
        this.semester = semester;
        this.score = score;
    }
}
