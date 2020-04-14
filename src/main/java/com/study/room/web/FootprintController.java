package com.study.room.web;

import com.study.room.configurer.UserLoginToken;
import com.study.room.configurer.WebMvcConfigurer;
import com.study.room.core.Result;
import com.study.room.core.ResultGenerator;
import com.study.room.dto.FootprintDTO;
import com.study.room.model.Footprint;
import com.study.room.model.User;
import com.study.room.service.FootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/03/21.
*/
@RestController
@RequestMapping("/footprint")
public class FootprintController {
    @Resource
    private FootprintService footprintService;

    @ApiOperation(value = "checkTime", notes = "查看用户的已用时间")
    @UserLoginToken
    @GetMapping("/check/time")
    public Result checkTime() {
        User user = WebMvcConfigurer.getLoginUser();

        // TODO: 返回已用时间
        int time = footprintService.checkTime(user.getId());

        return ResultGenerator.genSuccessResult(time);
    }

    @ApiOperation(value = "history", notes = "查看用户的")
    @UserLoginToken
    @GetMapping("/history")
    public Result history() {
        User user = WebMvcConfigurer.getLoginUser();

        // TODO: 返回历史借座信息
        List<Footprint> footprints = footprintService.checkHistoryByUser(user.getId());

        return ResultGenerator.genSuccessResult(footprints);
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
