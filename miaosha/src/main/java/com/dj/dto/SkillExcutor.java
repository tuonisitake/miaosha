package com.dj.dto;

import com.dj.domain.SuccessKill;
import com.dj.enums.SkillStatEnum;

/**
 * 封装秒杀执行后的结果
 */
public class SkillExcutor {

    private Integer sId;

    private Integer state;//秒杀执行结果的状态

    private String stateInfo;//状态的标识

    private SuccessKill successKill;//秒杀成功后对象

    /**
     * 成功返回
     * @param sId
     *
     * @param successKill
     */
    public SkillExcutor(Integer sId, SkillStatEnum skillStatEnum, SuccessKill successKill) {
        this.sId = sId;
        this.state = skillStatEnum.getState();
        this.stateInfo = skillStatEnum.getStateInfo();
        this.successKill = successKill;
    }

    /**
     * 失败返回
     * @param sId
     *
     */
    public SkillExcutor(Integer sId,SkillStatEnum skillStatEnum) {
        this.sId = sId;
        this.stateInfo = skillStatEnum.getStateInfo();
        this.successKill = successKill;
    }

    @Override
    public String toString() {
        return "SkillExcutor{" +
                "sId=" + sId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKill=" + successKill +
                '}';
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKill getSuccessKill() {
        return successKill;
    }

    public void setSuccessKill(SuccessKill successKill) {
        this.successKill = successKill;
    }
}
