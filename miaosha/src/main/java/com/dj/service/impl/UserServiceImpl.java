package com.dj.service.impl;

import com.dj.dao.UserDao;
import com.dj.domain.User;
import com.dj.exception.SkillException;
import com.dj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public User getUser(User user) throws SkillException {
        return userDao.getUser(user);
    }
}
