package com.study.room.service.impl;

import com.study.room.dao.UserMapper;
import com.study.room.model.User;
import com.study.room.service.UserService;
import com.study.room.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
