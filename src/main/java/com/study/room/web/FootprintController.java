package com.study.room.web;

import com.study.room.core.Result;
import com.study.room.core.ResultGenerator;
import com.study.room.model.Footprint;
import com.study.room.service.FootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Result add(@RequestBody Footprint footprint) {
        footprintService.save(footprint);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        footprintService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Footprint footprint) {
        footprintService.update(footprint);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Footprint footprint = footprintService.findById(id);
        return ResultGenerator.genSuccessResult(footprint);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Footprint> list = footprintService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
