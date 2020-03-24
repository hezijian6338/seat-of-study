package com.study.room.service.impl;

import com.study.room.core.AbstractService;
import com.study.room.dao.UserMapper;
import com.study.room.model.User;
import com.study.room.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        if (username == null && password == null)
            return null;
        User user = this.findBy("name", username);
        if (password.equals(user.getPassword())) {
            logger.info("{} 用户登录成功~", user.getName());
            return user;
        } else {
            logger.warn("{} 用户, {} 密码, 登录失败~", username, password);
            return null;
        }
    }
}
