package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jianping5
 * @createDate 1/3/2023 下午 3:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO implements Serializable {

    /**
     * 课程id
     */
    private Integer id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 组织id
     */
    private Integer teamId;

    /**
     * 课程学年
     */
    private String semester;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public CourseVO(Integer id, String name, Integer teamId, String semester) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
        this.semester = semester;
    }

    public CourseVO() {
    }

    @Override
    public String toString() {
        return name;
    }
}
