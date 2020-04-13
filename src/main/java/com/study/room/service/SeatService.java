package com.study.room.service;
import com.study.room.model.Seat;
import com.study.room.core.Service;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
public interface SeatService extends Service<Seat> {

    /**
     * @Method checkSeat
     * TODO: 检查座位是否可以坐下, 返回不可以的原因
     * @param room_num
     * @param row
     * @param col
     * @Return int
     * @Exception
     * @Date 2020/3/22 11:53 AM
     * @Author hezijian6338
     * @Version  1.0
     */
    int checkSeat(String room_num, int row, int col);

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

    /**
     * TODO: 离开座位
     * @Return boolean
     * @Author hezijian6338
     */
    boolean leaveSeat(String userId);

    String createRoom(String room_num, int row, int col);
}
