package com.study.room.web;

import com.study.room.configurer.UserLoginToken;
import com.study.room.configurer.WebMvcConfigurer;
import com.study.room.core.Result;
import com.study.room.core.ResultGenerator;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Seat;
import com.study.room.model.User;
import com.study.room.service.FootprintService;
import com.study.room.service.SeatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.room.service.impl.SeatServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/03/21.
*/
@RestController
@RequestMapping("/seat")
public class SeatController {
    @Resource
    private SeatService seatService;

    @Resource
    private FootprintService footprintService;

    @UserLoginToken
    @PostMapping("/check/room/{room_num}/row/{row}/col/{col}")
    public Result checkSeat(@PathVariable String room_num, @PathVariable int row, @PathVariable int col) {
        User user = WebMvcConfigurer.getLoginUser();
        if (user.getBadRecord() > User.BAD.BADCOUNT) {
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
