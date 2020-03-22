package com.study.room.web;

import com.study.room.configurer.UserLoginToken;
import com.study.room.core.Result;
import com.study.room.core.ResultGenerator;
import com.study.room.dto.UserDTO;
import com.study.room.model.User;
import com.study.room.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.room.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/03/21.
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping
    public Result login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);

        if (user == null) {
            return ResultGenerator.genFailResult("登录失败~");
        } else {
            String token = Tools.getToken(user);
            return ResultGenerator.genSuccessResult(token);
        }
    }

    @UserLoginToken
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @UserLoginToken
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @UserLoginToken
    @PutMapping
    public Result update(@RequestBody User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @UserLoginToken
    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @UserLoginToken
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
