package com.dj.service;

import com.dj.domain.Skill;
import com.dj.domain.SuccessKill;
import com.dj.dto.Exposer;
import com.dj.dto.SkillExcutor;
import com.dj.exception.RepeatKillException;
import com.dj.exception.SkillCloseException;
import com.dj.exception.SkillException;

import java.util.List;

public interface SkillService {

    /**
     * 查询所有秒杀哦商品
     */
    List<Skill> getSkillList();
    /**
     * 查询单个秒杀记录
     */
    Skill getSkillById(Integer sId);
    /**
     * 秒杀开始时输出秒杀哦接口地址，否则输出系统时间和秒杀哦时间
     */
    Exposer exportSkillUrl(Integer sId);

    /**
     *执行秒杀操作
     */
    SkillExcutor executeSkill(Integer sId, Integer userPhone,String userName, String md5)
            throws SkillException,SkillCloseException,RepeatKillException;
    /**
     * 根据手机号和用户名查询SuccessKill并携带秒杀商品
     */
    List<SuccessKill> queryByUserNameWithSkill(String userName);
}
