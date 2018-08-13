package com.dj.dao;

import com.dj.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    //根据用户名和密码查询用户@Param("username") String username,@Param("password") String password
    User getUser(User user);
}
