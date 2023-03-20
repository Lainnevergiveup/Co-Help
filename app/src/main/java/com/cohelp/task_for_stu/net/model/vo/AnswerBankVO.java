package com.cohelp.task_for_stu.net.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zgy
 * @create 2023-03-04 20:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerBankVO implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 答案
     */
    private String content;

    /**
     * 推荐度
     */
    private Integer recommendedDegree;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 问题id
     */
    private Integer questionId;

    /**
     * 对应回答id
     */
    private Integer answerId;

    /**
     * 答案相关图片
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

    public Integer getRecommendedDegree() {
        return recommendedDegree;
    }

    public void setRecommendedDegree(Integer recommendedDegree) {
        this.recommendedDegree = recommendedDegree;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public AnswerBankVO(Integer id, String content, Integer recommendedDegree, String courseName, Integer questionId, Integer answerId, List<String> imageUrl) {
        this.id = id;
        this.content = content;
        this.recommendedDegree = recommendedDegree;
        this.courseName = courseName;
        this.questionId = questionId;
        this.answerId = answerId;
        this.imageUrl = imageUrl;
    }

    public AnswerBankVO() {
    }
}
