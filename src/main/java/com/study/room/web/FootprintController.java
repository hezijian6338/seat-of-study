package com.study.room.web;

import com.study.room.configurer.UserLoginToken;
import com.study.room.configurer.WebMvcConfigurer;
import com.study.room.core.Result;
import com.study.room.core.ResultGenerator;
import com.study.room.dao.FootprintMapper;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Footprint;
import com.study.room.model.User;
import com.study.room.service.FootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2020/03/21.
 */
@Api(value = "footprint", tags = "座位记录信息操作")
@RestController
@RequestMapping("/footprint")
public class FootprintController {
    @Resource
    private FootprintService footprintService;

    @Resource
    private FootprintMapper footprintMapper;

    @ApiOperation(value = "checkTime", notes = "查看用户的已用时间 (如果已经在自习室坐下了)")
    @UserLoginToken
    @GetMapping("/check/time")
    public Result checkTime() {
        User user = WebMvcConfigurer.getLoginUser();

        // TODO: 返回已用时间
        int time = footprintService.checkTime(user.getId());
        Footprint footprint = footprintService.findUseSeatByUserId(user.getId());

        if (footprint == null)
            return ResultGenerator.genFailResult("你好像没有进行自习的座位~");

        HashMap<String, Object> map = new HashMap();
        map.put("studiedTime", time);
        map.put("wantedTime", footprint.getWantedTime());
        map.put("momentTag", footprint.getMomtentTag());

        return ResultGenerator.genSuccessResult(map);
    }

    @ApiOperation(value = "history", notes = "查看用户的历史信息, 可用于检查上一个座位的位置情况 (时间降序)")
    @UserLoginToken
    @GetMapping("/history")
    public Result history() {
        User user = WebMvcConfigurer.getLoginUser();

        // TODO: 返回历史借座信息
        List<Footprint> footprints = footprintService.checkHistoryByUser(user.getId());

        return ResultGenerator.genSuccessResult(footprints);
    }

    @ApiOperation(value = "leaderBoard", notes = "选择日期展示排行榜 (例子: 2020/04/14)")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "年", name = "year", dataType = "String", paramType = "path"),
            @ApiImplicitParam(value = "月", name = "month", dataType = "String", paramType = "path"),
            @ApiImplicitParam(value = "日", name = "day", dataType = "String", paramType = "path")
    })
    @UserLoginToken
    @GetMapping("/leader/board/{year}/{month}/{day}")
    public Result leaderBoard(@PathVariable String year, @PathVariable String month, @PathVariable String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        Date date = simpleDateFormat.parse(year + "-" + month + "-" + day);

        List<Footprint> board = footprintMapper.leaderBoard(date);

        return ResultGenerator.genSuccessResult(board);
    }

    @ApiOperation(value = "counterBoard", notes = "选择日期返回自习室的统计列表 (例子: 2020)")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "自习室编号", name = "roomNumber", dataType = "String", paramType = "path"),
            @ApiImplicitParam(value = "年", name = "year", dataType = "String", paramType = "path")
    })
    @UserLoginToken
    @GetMapping("/room/{roomNumber}/counter/board/{year}")
    public Result counterBoard(@PathVariable String roomNumber, @PathVariable String year) throws ParseException {
        SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");//注意月份是MM
        Date dateYear = simpleDateFormatYear.parse(year);
        List<Footprint> count = footprintMapper.counterBoard(roomNumber, dateYear);

        return ResultGenerator.genSuccessResult(count);
    }

    @ApiOperation(value = "checkOnSeatListByRoomNumber", notes = "根据自习室编号返回在座人员列表")
    @ApiImplicitParam(value = "自习室编号", name = "roomNumber", dataType = "String", paramType = "path")
    @UserLoginToken
    @GetMapping("/room/{roomNumber}")
    public Result checkOnSeatListByRoomNumber(@PathVariable String roomNumber) {
        footprintService.checkOnSeatListByRoomNumber(roomNumber);

        return ResultGenerator.genSuccessResult(roomNumber);
    }

    @ApiIgnore
    @UserLoginToken
    @PostMapping
    public Result add(@RequestBody Footprint footprint) {
        footprintService.save(footprint);
        return ResultGenerator.genSuccessResult();
    }

    @ApiIgnore
    @UserLoginToken
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        footprintService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiIgnore
    @UserLoginToken
    @PutMapping
    public Result update(@RequestBody Footprint footprint) {
        footprintService.update(footprint);
        return ResultGenerator.genSuccessResult();
    }

    @ApiIgnore
    @UserLoginToken
    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        Footprint footprint = footprintService.findById(id);
        return ResultGenerator.genSuccessResult(footprint);
    }

    @ApiIgnore
    @UserLoginToken
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Footprint> list = footprintService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
