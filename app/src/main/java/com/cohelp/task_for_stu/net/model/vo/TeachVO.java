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
public class TeachVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 教师 id
     */
    private Integer teacherId;

    /**
     * 教师姓名
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
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

    public TeachVO(Integer id, Integer teacherId, String userName, String courseName, Integer courseId, String semester) {
        this.id = id;
        this.teacherId = teacherId;
        this.userName = userName;
        this.courseName = courseName;
        this.courseId = courseId;
        this.semester = semester;
    }

    public TeachVO() {
    }
}
