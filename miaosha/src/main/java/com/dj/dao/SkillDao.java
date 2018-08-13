package com.dj.dao;

import com.dj.domain.Skill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SkillDao {

    /**
     * 减库存
     */
    int reduceNumber(@Param("sId") Integer sId,@Param("killTime") Date killTime);

    /**
     * 根据id查询商品
     */
    Skill queryById(Integer sID);

    /**
     * 根据偏移量查询商品
     */
    List<Skill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
