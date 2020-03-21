package com.study.room.utils;

import java.util.Date;

/**
 * Created by Macbook on 2020/3/21.
 */
public class Tools {
    /**
     * @Method getTimeStamp
     * TODO: 获取系统时间戳
     * @param
     * @Return long
     * @Exception
     * @Date 2019/8/13 下午2:03
     * @Author hezijian6338
     * @Version 1.0
     */
    public static long getTimeStamp() {
        return new Date().getTime();
    }

    public static String getStringTimeStamp() {
        return Tools.getTimeStamp() + "";
    }

}
