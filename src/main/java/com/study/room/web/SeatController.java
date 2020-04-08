package com.study.room.web;

import com.study.room.configurer.UserLoginToken;
import com.study.room.configurer.WebMvcConfigurer;
import com.study.room.core.Result;
import com.study.room.core.ResultGenerator;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Footprint;
import com.study.room.model.Seat;
import com.study.room.model.User;
import com.study.room.service.FootprintService;
import com.study.room.service.SeatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.room.service.UserService;
import com.study.room.service.impl.SeatServiceImpl;
import com.study.room.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
* Created by CodeGenerator on 2020/03/21.
*/
@Api(value = "seat", tags = "座位管理接口")
@RestController
@RequestMapping("/seat")
public class SeatController {
    @Resource
    private SeatService seatService;

    @Resource
    private FootprintService footprintService;

    @Resource
    private UserService userService;

    // 记录被抢座的信息
    private static HashMap<String, Timestamp> grabSeatMap = new HashMap<>();

    @ApiOperation(value = "checkSeat", notes = "检查座位信息 (是否可坐下)")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "room_num", name = "自习室编号", dataType = "String", paramType = "path"),
            @ApiImplicitParam(value = "row", name = "座位行", dataType = "int", paramType = "path"),
            @ApiImplicitParam(value = "col", name = "座位列", dataType = "int", paramType = "path")
    })
    @UserLoginToken
    @PostMapping("/check/room/{room_num}/row/{row}/col/{col}")
    public Result checkSeat(@PathVariable String room_num, @PathVariable int row, @PathVariable int col) {
        User user = WebMvcConfigurer.getLoginUser();

        // TODO: 检查用户的黑名单次数是否超过控制次数
        if (user.getBadRecord() > User.BAD.BADCOUNT) {

            // 如果该用户状态超过黑名单录入次数, 但是状态不是黑名单状态, 手动更新状态
            if (user.getStatus() != User.STATUS.BAD) {
                user.setStatus(User.STATUS.BAD);
                userService.update(user);
            }
            return ResultGenerator.genFailResult("已列入黑名单, 无法进行座位查阅与借座~");
        }

        int status = seatService.checkSeat(room_num, row, col);
        if (status == SeatServiceImpl.SEAT.AVAILABLE)
            return ResultGenerator.genSuccessResult("座位可以坐下");
        if (status == SeatServiceImpl.SEAT.FULL)
            return ResultGenerator.genFailResult("座位已有人做");
        if (status == SeatServiceImpl.SEAT.FULL_TEMP)
            return ResultGenerator.genFailResult("正在使用座位保护");
        if (status == SeatServiceImpl.SEAT.ERROR)
            return ResultGenerator.genFailResult("座位信息有误");
        return ResultGenerator.genFailResult("座位信息有误");
    }

    @ApiOperation(value = "seatAnyway", notes = "抢座位")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "room_num", name = "自习室编号", dataType = "String", paramType = "path"),
            @ApiImplicitParam(value = "row", name = "座位行", dataType = "int", paramType = "path"),
            @ApiImplicitParam(value = "col", name = "座位列", dataType = "int", paramType = "path")
    })
    @UserLoginToken
    @PostMapping("/anyway/{room_num}/row/{row}/col/{col}")
    public Result seatAnyway(@PathVariable String room_num, @PathVariable int row, @PathVariable int col) {
        User user = WebMvcConfigurer.getLoginUser();

        String seats_number = row + "," + col;
        Footprint footprint = footprintService.checkSeatStatus(room_num, seats_number);

        // TODO: 检查是否已经在抢座了
        if (grabSeatMap.containsKey(footprint.getId())) {
            // 当前时间减去记录的时间, 看看大不大与 20min
            long time = Tools.getLongTimeStamp() - grabSeatMap.get(footprint.getId()).getTime();

            if (time <= 0) {
                return ResultGenerator.genFailResult("系统错误~");
            }

            // 检查时间是否已经超过 20分钟了
            if (time >= 20 * 60) {
                // TODO: 这里需要手动把足迹状态更新为离开, 并且记录黑名单次数
                footprint.setStatus(Footprint.STATUS.OUT);

                // 找到被抢座的用户
                User badUser = userService.findById(footprint.getUserId());

                // 黑名单次数加一
                badUser.setBadRecord(badUser.getBadRecord() + 1);

                // 黑名单次数大于3, 黑名单状态开启
                if (badUser.getBadRecord() >= 3) {
                    badUser.setStatus(User.STATUS.BAD);

                    // 跟新被抢座的人的数据
                    userService.update(badUser);
                }

                return ResultGenerator.genSuccessResult("你可以进行抢座了~");
            }
        }

        if (footprint.getStatus() == Footprint.STATUS.TEMP){
            return ResultGenerator.genFailResult("用户已经使用暂离, 不允许抢座~");
        }

        if (footprint.getStatus() == Footprint.STATUS.IN) {
            // 当前被抢座的用户 id, 和当前的时间戳
            grabSeatMap.put(footprint.getId(), Tools.getTimeStamp());
            return ResultGenerator.genSuccessResult("开始抢座~");
        }

        return null;
    }

    @ApiOperation(value = "haveSeat", notes = "正常坐下")
    @ApiImplicitParam(value = "footprintDTO", name = "足迹对象", dataType = "FootprintDTO", paramType = "body")
    @UserLoginToken
    @PostMapping("/down")
    public Result haveSeat(@RequestBody FootprintDTO footprintDTO) {
        String room_num = null;
        int row = 0;
        int col = 0;

        // 检查自习室编号是否为空
        if (footprintDTO.getRoomNumber() != null) {
            room_num = footprintDTO.getRoomNumber();
        } else {
            return ResultGenerator.genFailResult("自习室编号为空~");
        }

        // 检查自习室座位编号
        if (footprintDTO.getSeatsNumber() != null) {
            String seats_number = footprintDTO.getSeatsNumber();
            String[] seats = seats_number.split(",");

            // 行, 列
            row = Integer.parseInt(seats[0]);
            col = Integer.parseInt(seats[1]);
        } else {
            return ResultGenerator.genFailResult("自习室座位编号为空~");
        }

        // 检查该座位是否能够坐下
        Boolean isAvailable = seatService.haveSeat(room_num, row, col);
        Boolean isSeat = false;

        // 如果可以, 则留下足迹
        if (isAvailable) {
            if (footprintDTO.getUserId().isEmpty())
                footprintDTO.setUserId(WebMvcConfigurer.getLoginUser().getId());
            isSeat = footprintService.haveSeat(footprintDTO);
        } else {
            // 不可以坐下, 返回信息
            // FIXME: 这里应该缺失一个回滚...
            return ResultGenerator.genFailResult("该座位无法坐下, 请稍后再试~");
        }
        if (isAvailable && isSeat) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("系统错误, 无法留下足迹~");
        }
    }

    @ApiOperation(value = "tempLeaveSeat", notes = "暂时离开座位")
    @UserLoginToken
    @PostMapping("/temp/leave")
    public Result tempLeaveSeat() {
        // 因为需要登录 token, 所以不存在找不到用户的情况
        User user = WebMvcConfigurer.getLoginUser();

        // TODO: 根据用户 id
        Boolean result = footprintService.pauseSeat(user.getId());

        if (result) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("暂停座位失败~");
        }
    }

    @UserLoginToken
    @PostMapping("/leave")
    public Result leaveSeat() {
        // TODO: 离开座位
        return null;
    }

    @UserLoginToken
    @PostMapping
    public Result add(@RequestBody Seat seat) {
        seatService.save(seat);
        return ResultGenerator.genSuccessResult();
    }

    @UserLoginToken
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        seatService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @UserLoginToken
    @PutMapping
    public Result update(@RequestBody Seat seat) {
        seatService.update(seat);
        return ResultGenerator.genSuccessResult();
    }

    @UserLoginToken
    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        Seat seat = seatService.findById(id);
        return ResultGenerator.genSuccessResult(seat);
    }

    @UserLoginToken
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Seat> list = seatService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
