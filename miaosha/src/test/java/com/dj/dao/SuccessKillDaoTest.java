package com.dj.dao;

import com.dj.domain.SuccessKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SuccessKillDaoTest {
    @Autowired
    private SuccessKillDao successKillDao;

    @Test
    public void insertSuccessKill() throws Exception{
        int id = 2;
        int phone = 6666;
        int count = successKillDao.insertSuccessKill(id,phone);
        System.out.println(count);
    }

    @Test
    public void queryByIdWithSkill() throws Exception{
        int id = 2;
        int phone = 6666;
        SuccessKill successKill = successKillDao.queryByIdWithSkill(id, phone);
        System.out.println(successKill);
        System.out.println(successKill.getSkill());
    }
}