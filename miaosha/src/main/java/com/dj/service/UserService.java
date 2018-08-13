package com.dj.service;

import com.dj.domain.User;
import com.dj.exception.SkillException;

public interface UserService {

    User getUser(User user) throws SkillException;
}
