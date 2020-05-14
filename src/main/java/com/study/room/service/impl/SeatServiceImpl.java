package com.study.room.service.impl;

import com.study.room.dao.SeatMapper;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Footprint;
import com.study.room.model.Seat;
import com.study.room.service.FootprintService;
import com.study.room.service.SeatService;
import com.study.room.core.AbstractService;
import com.study.room.utils.Tools;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        Seat seats = this.findBy("roomNumber", room_num);
        String[] seat_list = seats.getSeats().replace(" ", "").replace("[", "").replace("]", "").split(",");

        // 判断获取座位的行数是否大于数组的大小
        if (row <= seat_list.length) {
            String seat_row = seat_list[row - 1];
            char[] seat_col = seat_row.toCharArray();

            // 判断获取座位的列数是否大于数组的大小
            if (col <= seat_col.length) {
                char status = seat_col[col - 1];
                // 该座位有人, 但是不知道是暂离还是正在坐下但是人离开了
                if (status == Seat.SEAT.FULL) {
                    // 组合座位的格式
                    String seats_number = row + "," + col;
                    // 检查返回的数据
                    Footprint footprint = footprintService.checkSeatStatus(room_num, seats_number);

                    // TODO: 存在一种情况就是: 游离数据, 座位坐下了, 但是没有该座位的足迹数据 (需要释放座位)
                    if (footprint == null) {
                        boolean result = this.leaveSeat(room_num, row, col);
                        if (result)
                            return SEAT.AVAILABLE;
                        else
                            return SEAT.ERROR;
                    }

                    // 只做了对数据进行检索的处理, 没有进行状态过期判断 √
                    // TODO: 根据返回的足迹信息进行对自己的状态进行检查
                    int time = footprintService.checkTime(footprint.getUserId());
                    // 这两个状态都是足迹所在的用户会释放座位的状态或者是没有信息的状态
                    if (time == footprint.getWantedTime() || time == 0) {
                        return SEAT.AVAILABLE;
                    }

                    // 暂离
                    if (footprint.getStatus() == Footprint.STATUS.TEMP) {
                        return SEAT.FULL_TEMP;
                    }
                    // 正常坐着 (人不在了, 可以抢座)
                    if (footprint.getStatus() == Footprint.STATUS.IN) {
                        return SEAT.FULL;
                    }
                }
                if (status == Seat.SEAT.EMPTY) {
                    return SEAT.AVAILABLE;
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

        Seat seats = this.findBy("roomNumber", room_num);
        String[] seat_list = seats.getSeats().replace(" ", "").replace("[", "").replace("]", "").split(",");

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
                    seat_col[col - 1] = Seat.SEAT.FULL;

                    System.out.println("检查列表是否为引用类型相关的修改: " + seat_list);
                    logger.info("{}", String.valueOf(seat_col));
                    seat_list[row - 1] = String.valueOf(seat_col);

                    List<String> seat = Arrays.asList(seat_list);

                    seats.setSeats(seat.toString());

                    // TODO: 维护数据表可用座位和不可用座位的数量
                    seats.setSeatsAvailable(seats.getSeatsAvailable() - 1);
                    seats.setSeatsUnavailabe(seats.getSeatsUnavailabe() + 1);

                    this.update(seats);

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param room_num
     * @param row
     * @param col
     * @Method leaveSeat
     * TODO: 进行正常的座位离开操作
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

        Seat seats = this.findBy("roomNumber", room_num);
        String[] seat_list = seats.getSeats().replace(" ", "").replace("[", "").replace("]", "").split(",");

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
                    seat_list[row - 1] = String.valueOf(seat_col);

                    List<String> seat = Arrays.asList(seat_list);

                    seats.setSeats(seat.toString());

                    // TODO: 维护数据表可用座位和不可用座位的数量
                    seats.setSeatsAvailable(seats.getSeatsAvailable() + 1);
                    seats.setSeatsUnavailabe(seats.getSeatsUnavailabe() - 1);

                    this.update(seats);

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean leaveSeat(String userId) {
        // TODO: 根据当前用户 id进行查找足迹
        Footprint footprint = footprintService.findUseSeatByUserId(userId);

        if (footprint == null)
            return false;

        // 解析座位行列信息
        String[] seats = footprint.getSeatsNumber().split(",");

        int row = Integer.parseInt(seats[0]);

        int col = Integer.parseInt(seats[1]);

        boolean seatResult = this.leaveSeat(footprint.getRoomNumber(), row, col);

        boolean fpResult = false;

        // TODO: 座位足迹离开
        if (seatResult) {
            fpResult = footprintService.leaveSeat(userId);
        } else {
            // FIXME: 缺失回滚
            return false;
        }

        if (fpResult && seatResult)
            return true;
        else
            return false;
    }

    /**
     * @param room_num
     * @param row
     * @param col
     * @Method createRoom
     * TODO: 创建自习室, 并返回 id
     * @Return java.lang.String
     * @Exception
     * @Date 2020/4/12 8:44 PM
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public String createRoom(String room_num, int row, int col) {
        if (row == 0 && col == 0)
            return null;
        if (room_num == null)
            return null;

        // 构建空对象
        Seat seat = new Seat();

        // 构建行数组
        ArrayList<String> rowSeat = new ArrayList<>();

        // 构建列数的数组
//        char[] colSeat = new char[col];

        StringBuilder colSeatString = new StringBuilder();

        // 填写列
        for (int i = 0; i < col; i++) {
//            colSeat[i] = Seat.SEAT.EMPTY;
            colSeatString.append(Seat.SEAT.EMPTY);
        }

        // 填写行
        for (int j = 0; j < row; j++) {
            rowSeat.add(j, colSeatString.toString());
        }

        seat.setId(Tools.getUUID());
        seat.setSeats(rowSeat.toString());
        seat.setRoomNumber(room_num);
        seat.setName(room_num);
        seat.setStatus(Seat.STATUS.USE);

        seat.setSeatsCount(row * col);
        seat.setSeatsAvailable(row * col);
        seat.setSeatsUnavailabe(0);

        seat.setCreatedTime(Tools.getTimeStamp());

        this.save(seat);

        return seat.getId();
    }

    /**
     * @Method checkRoom
     * TODO: 根据自习室编号检查该自习室是否有正在使用的座位
     * @param roomNumber
     * @Return boolean
     * @Exception
     * @Date 2020-04-25 14:36
     * @Author hezijian6338
     * @Version 1.0
     */
    @Override
    public boolean checkRoom(String roomNumber) {
        Seat seat = this.findBy("roomNumber", roomNumber);
        if (seat == null)
            return false;
        String[] seatList = seat.getSeats().replace(" ", "").replace("[", "").replace("]", "").split(",");

        // TODO: 获取行数
        for (String rows : seatList) {
            // 检查每行座位是否有包含在座的信息 (等于 -1就是找不到在座信息)
            if (rows.indexOf('1') != -1) {
                return false;
            }
        }

        return true;
    }
}
