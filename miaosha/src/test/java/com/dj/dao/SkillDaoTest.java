package com.dj.dao;

import com.dj.domain.Skill;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SkillDaoTest {
    @Autowired
    private SkillDao skillDao;

    @Test
    public void testQueryById() throws Exception{
        Integer id = 1;
        Skill skill = skillDao.queryById(id);
        System.out.println(skill.getName());

    }

    @Test
    public void testQueryAll() throws Exception{
        List<Skill> skills = skillDao.queryAll(0,100);
        for (Skill skill : skills){
            System.out.println(skill);
        }
    }

    @Test
    public void testReduceNumber() throws Exception{
        Date killt = new Date();
        int count = skillDao.reduceNumber(1,killt);
        System.out.println(count);
    }
}
