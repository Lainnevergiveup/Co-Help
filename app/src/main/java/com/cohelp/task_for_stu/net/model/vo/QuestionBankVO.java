package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zgy
 * @create 2023-03-03 23:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBankVO implements Serializable {

    private Integer id;
    /**
     * 题干
     */
    private String content;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 难度（1-5）
     */
    private Integer level;

    /**
     * 难度（很容易，较容易，适中，较困难，很困难）
     */
    private String difficulty;

    /**
     * 题目相关图片
     */
    private List<String> imageUrl;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public QuestionBankVO() {
    }

    public QuestionBankVO(Integer id, String content, Integer courseId, String courseName, Integer level, String difficulty, List<String> imageUrl) {
        this.id = id;
        this.content = content;
        this.courseId = courseId;
        this.courseName = courseName;
        this.level = level;
        this.difficulty = difficulty;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
