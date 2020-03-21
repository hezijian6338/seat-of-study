package com.study.room.service.impl;

import com.study.room.dao.SeatMapper;
import com.study.room.model.Seat;
import com.study.room.service.SeatService;
import com.study.room.core.AbstractService;
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
    @Resource
    private SeatMapper seatMapper;

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
                if (status == 1) {
                    return false;
                }
                if (status == 0) {
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

    @Override
    public boolean leaveSeat(String room_num, int row, int col) {
        return false;
    }
}
