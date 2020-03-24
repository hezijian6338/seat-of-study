package com.study.room.dao;

import com.study.room.core.Mapper;
import com.study.room.model.Footprint;

import java.util.List;

public interface FootprintMapper extends Mapper<Footprint> {
    /**
     * TODO: 根据用户 id和 当前状态筛选结果
     * @Date 2020/3/24 7:13 PM
     * @Author hezijian6338
     */
    List<Footprint> checkTime(String user_id, int status);
}