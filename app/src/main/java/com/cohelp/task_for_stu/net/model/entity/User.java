package com.cohelp.task_for_stu.net.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 头像
     */
    private Integer avatar;

    /**
     * 性别（0：男 1：女）
     */
    private Integer sex;

    /**
     * 联系方式
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 用户角色（0：普通用户 1：管理员）
     */
    private Integer userRole;

    /**
     * 状态（0：正常 1：异常）
     */
    private Integer state;

    /**
     * 用户创建时间（默认当前时间）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userCreateTime;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 组织id
     */
    private Integer teamId;

    /**
     * 组织名
     */
    private String teamName;
    /**
     * 生肖
     */
    private String animalSign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LocalDateTime getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(LocalDateTime userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAnimalSign() {
        return animalSign;
    }

    public void setAnimalSign(String animalSign) {
        this.animalSign = animalSign;
    }

    public User(Integer id, String userAccount, String userName, String userPassword, Integer avatar, Integer sex, String phoneNumber, String userEmail, Integer userRole, Integer state, LocalDateTime userCreateTime, Integer age, Integer teamId, String animalSign) {
        this.id = id;
        this.userAccount = userAccount;
        this.userName = userName;
        this.userPassword = userPassword;
        this.avatar = avatar;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.state = state;
        this.userCreateTime = userCreateTime;
        this.age = age;
        this.teamId = teamId;
        this.animalSign = animalSign;
    }

    public User(Integer id, String userAccount, String userName, String userPassword, Integer avatar, Integer sex, String phoneNumber, String userEmail, Integer userRole, Integer state, LocalDateTime userCreateTime, Integer age, Integer teamId, String teamName, String animalSign) {
        this.id = id;
        this.userAccount = userAccount;
        this.userName = userName;
        this.userPassword = userPassword;
        this.avatar = avatar;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.state = state;
        this.userCreateTime = userCreateTime;
        this.age = age;
        this.teamId = teamId;
        this.teamName = teamName;
        this.animalSign = animalSign;
    }



    private static final long serialVersionUID = 1L;

    public User() {

    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userAccount='" + userAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", avatar=" + avatar +
                ", sex=" + sex +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRole=" + userRole +
                ", state=" + state +
                ", userCreateTime=" + userCreateTime +
                ", age=" + age +
                ", teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", animalSign='" + animalSign + '\'' +
                '}';
    }
}