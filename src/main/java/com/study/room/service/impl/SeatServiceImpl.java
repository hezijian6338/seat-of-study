package com.study.room.service.impl;

import com.study.room.dao.SeatMapper;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Footprint;
import com.study.room.model.Seat;
import com.study.room.service.FootprintService;
import com.study.room.service.SeatService;
import com.study.room.core.AbstractService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
@Service
@Transactional
public class SeatServiceImpl extends AbstractService<Seat> implements SeatService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SeatServiceImpl.class);

    @Resource
    private SeatMapper seatMapper;

    @Autowired
    private FootprintService footprintService;

    public interface SEAT {
        int AVAILABLE = 0;
        int FULL = 1;
        int FULL_TEMP = -1;
        int ERROR = -2;

    }

    /**
     * @param room_num
     * @param row
     * @param col
     * @Method checkSeat
     * TODO: 联调各方面的数据, 确认是否能够坐下, 以及不可以的原因 (0: 可以坐下; 1: 不可以坐下 (有人); -1: 不可以坐下 (暂离); -2: 座位信息有误)
     * @Return int
     * @Exception
     * @Date 2020/3/22 11:56 AM
     * @Author hezijian6338
     * @Version 1.0
     */
    public int checkSeat(String room_num, int row, int col) {
        // 传递的座位位置信息有误
        if (row == 0 || col == 0)
            return -2;
        Seat seats = this.findBy("room_number", room_num);
        String[] seat_list = seats.getSeats().split(",");

        // 判断获取座位的行数是否大于数组的大小
        if (row <= seat_list.length) {
            String seat_row = seat_list[row - 1];
            char[] seat_col = seat_row.toCharArray();

            // 判断获取座位的列数是否大于数组的大小
            if (col <= seat_col.length) {
                char status = seat_col[col - 1];
                // 该座位有人, 但是不知道是暂离还是正在坐下但是人离开了
                if (status == 1) {
                    // 组合座位的格式
                    String seats_number = row + "," + col;
                    // 检查返回的数据
                    Footprint footprint = footprintService.checkSeatStatus(room_num, seats_number);
                    // 暂离
                    if (footprint.getStatus() == Footprint.STATUS.OUT) {
                        return -1;
                    }
                    // 正常坐着 (人不在了, 可以抢座)
                    if (footprint.getStatus() == Footprint.STATUS.TEMP) {
                        return 1;
                    }
                }
                if (status == 0) {
                    return 0;
                }
            }
        }
        return -2;
    }

    /**
     * @param room_num
     * @param row
     * @param col
     * @Method haveSeat
     * TODO: 进行正常座位坐下
     * @Return boolean
     * @Exception
     * @Date 2020/3/21 5:34 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean haveSeat(String room_num, int row, int col) {
        // 传递的座位位置信息有误
        if (row == 0 || col == 0)
            return false;

        Seat seats = this.findBy("room_number", room_num);
        String[] seat_list = seats.getSeats().split(",");

        // 判断获取座位的行数是否大于数组的大小
        if (row <= seat_list.length) {
            String seat_row = seat_list[row - 1];
            char[] seat_col = seat_row.toCharArray();

            // 判断获取座位的列数是否大于数组的大小
            if (col <= seat_col.length) {
                char status = seat_col[col - 1];
                if (status == Seat.SEAT.FULL) {
                    return false;
                }
                if (status == Seat.SEAT.EMPTY) {
                    // 返回可以坐下, 并且设置为坐下
                    seat_col[col - 1] = 1;

                    System.out.println("检查列表是否为引用类型相关的修改: " + seat_list);
                    seat_list[row - 1] = seat_col.toString();
                    seats.setSeats(seat_list.toString());

                    this.update(seats);

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Method leaveSeat
     * TODO: 进行正常的座位离开操作
     * @param room_num
     * @param row
     * @param col
     * @Return boolean
     * @Exception
     * @Date 2020/4/5 9:36 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean leaveSeat(String room_num, int row, int col) {
        // 传递的座位位置信息有误
        if (row == 0 || col == 0)
            return false;

        Seat seats = this.findBy("room_number", room_num);
        String[] seat_list = seats.getSeats().split(",");

        // 判断获取座位的行数是否大于数组的大小
        if (row <= seat_list.length) {
            String seat_row = seat_list[row - 1];
            char[] seat_col = seat_row.toCharArray();

            // 判断获取座位的列数是否大于数组的大小
            if (col <= seat_col.length) {
                char status = seat_col[col - 1];

                // 状态为 0, 证明本来就没人坐
                if (status == Seat.SEAT.EMPTY) {
                    return false;
                }
                if (status == Seat.SEAT.FULL) {
                    // 返回离开成功, 并修改状态为空
                    seat_col[col - 1] = Seat.SEAT.EMPTY;

                    System.out.println("检查列表是否为引用类型相关的修改: " + seat_list);
                    seat_list[row - 1] = seat_col.toString();
                    seats.setSeats(seat_list.toString());

                    this.update(seats);

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean leaveSeat(String userId) {


        footprintService.findUseSeatByUserId(userId);

        return true;
    }
}
