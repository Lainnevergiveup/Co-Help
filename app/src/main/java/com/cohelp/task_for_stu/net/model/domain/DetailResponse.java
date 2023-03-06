package com.cohelp.task_for_stu.net.model.domain;

import androidx.annotation.NonNull;

import com.cohelp.task_for_stu.net.model.vo.ActivityVO;
import com.cohelp.task_for_stu.net.model.vo.HelpVO;
import com.cohelp.task_for_stu.net.model.vo.HoleVO;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zgy
 * @create 2022-11-03 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailResponse implements Serializable,Cloneable {
    /**
     * 活动视图体，封装活动基本信息(不包括发布者头像及话题相关图片)
     */
    private ActivityVO activityVO;
    /**
     * 互助视图体，封装互助基本信息(不包括发布者头像及话题相关图片)
     */
    private HelpVO helpVO;
    /**
     * 树洞视图体，封装树洞基本信息(不包括发布者头像及话题相关图片)
     */
    private HoleVO holeVO;
    /**
     * 发布者头像Url
     */
    private String publisherAvatarUrl;
    /**
     * 话题相关图片Url
     */
    ArrayList<String> imagesUrl;

    /**
     *是否点赞
     */
    private Integer isLiked;
    /**
     *是否收藏
     */
    private Integer isCollected;

    /**
     *访问量
     */
    private Integer readNum;

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public ActivityVO getActivityVO() {
        return activityVO;
    }

    @Override
    public String toString() {
        return "DetailResponse{" +
                "activityVO=" + activityVO +
                ", helpVO=" + helpVO +
                ", holeVO=" + holeVO +
                ", publisherAvatarUrl='" + publisherAvatarUrl + '\'' +
                ", imagesUrl=" + imagesUrl +
                ", isLiked=" + isLiked +
                ", isCollected=" + isCollected +
                ", readNum=" + readNum +
                '}';
    }

//    @NonNull
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
    @NonNull
    @Override
    public HoleVO clone() {
        try {
            return (HoleVO) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new HoleVO();
    }

    public void setActivityVO(ActivityVO activityVO) {
        this.activityVO = activityVO;
    }

    public HelpVO getHelpVO() {
        return helpVO;
    }

    public void setHelpVO(HelpVO helpVO) {
        this.helpVO = helpVO;
    }

    public HoleVO getHoleVO() {
        return holeVO;
    }

    public void setHoleVO(HoleVO holeVO) {
        this.holeVO = holeVO;
    }

    public String getPublisherAvatarUrl() {
        return publisherAvatarUrl;
    }

    public void setPublisherAvatarUrl(String publisherAvatarUrl) {
        this.publisherAvatarUrl = publisherAvatarUrl;
    }

    public ArrayList<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(ArrayList<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }

    public Integer getType(){
        if (this.getActivityVO()!=null) return 1;
        else if (this.getHelpVO()!=null)    return 2;
        else if (this.getHoleVO()!=null)    return 3;
        return -1;
    }
}
