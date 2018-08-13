package com.dj.dao.cache;

import com.dj.dao.SkillDao;
import com.dj.domain.Skill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class RedisDaoTest {

    private Integer id = 1;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SkillDao skillDao;//拿到我们的缓存对象，再放到redis中

    /**
     * 全局测试，分别测试get put
     */
    @Test
    public void testSkill() {
        Skill skill = redisDao.getSkill(id);
        if(skill == null){
            skill = skillDao.queryById(id);
            if(skill != null){
                String result = redisDao.putSkill(skill);
                System.out.println(result);
                skill = redisDao.getSkill(id);
                System.out.println(skill);
            }
        }
    }
}