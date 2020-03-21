package com.study.room.service.impl;

import com.study.room.dao.SeatMapper;
import com.study.room.model.Seat;
import com.study.room.service.SeatService;
import com.study.room.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/03/21.
 */
@Service
@Transactional
public class SeatServiceImpl extends AbstractService<Seat> implements SeatService {
    @Resource
    private SeatMapper seatMapper;

    @Override
    public boolean haveSeat(String room_num, int row, int col) {
        Seat seat = this.findBy("room_number", room_num);
        return false;
    }

    @Override
    public boolean leaveSeat(String room_num, int row, int col) {
        return false;
    }
}
