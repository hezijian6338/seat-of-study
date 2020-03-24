package com.study.room.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.study.room.model.User;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Macbook on 2020/3/21.
 */
public class Tools {
    /**
     * @param
     * @Method getTimeStamp
     * TODO: 获取系统时间戳
     * @Return Timestamp
     * @Exception
     * @Date 2019/8/13 下午2:03
     * @Author hezijian6338
     * @Version 1.0
     */
    public static Timestamp getTimeStamp() {
        return new Timestamp(new Date().getTime());
    }

    public static long getLongTimeStamp() {
        return new Date().getTime();
    }

    public static String getStringTimeStamp() {
        return Tools.getTimeStamp() + "";
    }

    /**
     * @Method getToken
     * TODO: 根据 User生成 Token
     * @param user
     * @Return java.lang.String
     * @Exception
     * @Date 2020/3/21 9:39 PM
     * @Author hezijian6338
     * @Version  1.0
     */
    public static String getToken(User user) {
        String token = "com.study.room";
        token = JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

}
