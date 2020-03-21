package com.study.room.service;
import com.study.room.model.User;
import com.study.room.core.Service;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
public interface UserService extends Service<User> {

    /**
     * @Method login
     * TODO: 登陆接口
     * @param username
     * @param password
     * @Return boolean
     * @Exception
     * @Date 2020/3/21 10:58 AM
     * @Author hezijian6338
     * @Version  1.0
     */
    boolean login(String username, String password);
    
}
