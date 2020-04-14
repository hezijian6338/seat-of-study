package com.study.room.dao;

import com.study.room.core.Mapper;
import com.study.room.model.Footprint;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FootprintMapper extends Mapper<Footprint> {
    /**
     * TODO: 根据用户 id和 当前状态筛选结果
     * @Date 2020/3/24 7:13 PM
     * @Author hezijian6338
     */
    List<Footprint> checkTime(@Param("user_id") String user_id, @Param("status") int status);

    /**
     * TODO: 根据自习室房间号, 自习室座位号, 返回前5条使用记录
     * @Date 2020/4/5 8:28 PM
     * @Author hezijian6338
     */
    List<Footprint> checkSeatStatus(@Param("room_number") String room_number, @Param("seats_number") String seats_number);

    /**
     * TODO: 根据用户 id返回历史记录前五条
     * @Date 2020/4/11 7:33 PM
     * @Author hezijian6338
     */
    List<Footprint> checkHistoryByUser(@Param("user_id") String user_id);

    /**
     * TODO: 返回当天排行榜
     * @Date 2020-04-14 20:54
     * @Author hezijian6338
     */
    List<Footprint> leaderBoard(@Param("updated_time") Date updated_time);
}