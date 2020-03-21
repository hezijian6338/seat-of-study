package com.study.room.service;
import com.study.room.model.Seat;
import com.study.room.core.Service;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
public interface SeatService extends Service<Seat> {

    /**
     * @Method haveSeat
     * TODO: 扫描传递参数坐下座位
     * @param row
     * @param col
     * @Return boolean
     * @Exception
     * @Date 2020/3/21 11:02 AM
     * @Author hezijian6338
     * @Version  1.0
     */
    boolean haveSeat(String room_num, int row, int col);

    /**
     * @Method leaveSeat
     * TODO: 离开座位
     * @param room_num
     * @param row
     * @param col
     * @Return boolean
     * @Exception
     * @Date 2020/3/21 11:27 AM
     * @Author hezijian6338
     * @Version  1.0
     */
    boolean leaveSeat(String room_num, int row, int col);
}
