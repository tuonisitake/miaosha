package com.dj.dao;

import com.dj.domain.SuccessKill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuccessKillDao {

    /**
     * 插入购买明细,可过滤重复
     */
    int insertSuccessKill(@Param("sId") Integer sId,
                          @Param("userPhone") Integer userPhone,
                          @Param("userName") String userName);
    /**
     * 根据id查询SuccessKill并携带秒杀商品
     */
    SuccessKill queryByIdWithSkill(@Param("sId") Integer sId,
                                   @Param("userName") String userName);
    /**
     * 根据手机号和用户名查询SuccessKill并携带秒杀商品
     */
    List<SuccessKill> queryByUserNameWithSkill(@Param("userName") String userName);

}
