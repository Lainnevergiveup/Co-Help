package com.cohelp.task_for_stu.net.model.vo;

import com.cohelp.task_for_stu.net.model.domain.DetailResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zgy
 * @create 2023-03-20 18:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultVO {
    /**
     * 收藏记录/浏览记录id
     */
    private Integer id;
    /**
     * 详情
     */
    private DetailResponse detailResponse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DetailResponse getDetailResponse() {
        return detailResponse;
    }

    public void setDetailResponse(DetailResponse detailResponse) {
        this.detailResponse = detailResponse;
    }
}
