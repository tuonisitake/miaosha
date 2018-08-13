package com.dj.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SuccessKill {

    private Integer sID;

    private Integer userPhone;

    private short state;

    private Date createTime;

    private String userName;

    private Skill skill;

    public Integer getsID() {
        return sID;
    }

    public void setsID(Integer sID) {
        this.sID = sID;
    }

    public Integer getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Integer userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "SuccessKill{" +
                "sID=" + sID +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", userName='" + userName + '\'' +
                ", skill=" + skill +
                '}';
    }
}
