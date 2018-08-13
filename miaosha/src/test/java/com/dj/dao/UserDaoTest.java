package com.dj.dao;

import com.dj.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getUser() {
        User user = new User("jack","123");
        User user1 = userDao.getUser(user);
        System.out.println(user1);
    }
}