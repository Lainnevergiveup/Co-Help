package com.cohelp.task_for_stu.net.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jianping5
 * @createDate 2022/11/2 21:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoleListRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    /**
     * 条件类型（0：热度 1：时间）
     */
    private Integer conditionType;
}
