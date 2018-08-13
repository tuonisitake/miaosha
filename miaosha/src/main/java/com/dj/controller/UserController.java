package com.dj.controller;

import com.dj.domain.User;
import com.dj.dto.SkillResult;
import com.dj.enums.SkillStatEnum;
import com.dj.exception.SkillException;
import com.dj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/skill")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public SkillResult<User> login(User user, HttpSession httpSession){
        SkillResult<User> result;
        try {
            User userExist = userService.getUser(user);
            if(userExist!=null){
                httpSession.setAttribute("user",userExist);
                result = new SkillResult<User>(true,user);
            }else {
                result = new SkillResult<User>(false,"用户名或密码错误");
            }
        } catch (SkillException e) {
            result = new SkillResult<User>(false,"系统异常");
        }
        return result;
    }
}
