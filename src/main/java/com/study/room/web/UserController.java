package com.study.room.web;

import com.study.room.configurer.PassToken;
import com.study.room.configurer.UserLoginToken;
import com.study.room.configurer.WebMvcConfigurer;
import com.study.room.core.Result;
import com.study.room.core.ResultGenerator;
import com.study.room.dto.CreateUserDTO;
import com.study.room.dto.UserDTO;
import com.study.room.model.User;
import com.study.room.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.room.utils.MD5Utils;
import com.study.room.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/03/21.
*/
@Api(value = "userController", tags = "用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "login", notes = "用户登录接口")
    @PassToken
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);

        if (user == null) {
            return ResultGenerator.genFailResult("登录失败~");
        } else {
            String token = Tools.getToken(user);
            return ResultGenerator.genSuccessResult(token);
        }
    }

//    @UserLoginToken
    @PostMapping
    public Result add(@RequestBody CreateUserDTO userDTO) {
        User user = new User();

        BeanUtils.copyProperties(userDTO, user);

        user.setId(Tools.getUUID());

        user.setPassword(MD5Utils.StringToMD5_hex("admin"));

        user.setCreatedTime(Tools.getTimeStamp());

        // 实体类里没有添加控制
        if (user.getBadRecord() == null) {
            user.setBadRecord(0);
        }
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
    @GetMapping("/info")
    public Result detail() {
        User user = WebMvcConfigurer.getLoginUser();

        return ResultGenerator.genSuccessResult(user);
    }

    @UserLoginToken
    @GetMapping("/{id}")
    public Result getUserInfoById(@PathVariable String id) {
//        User user = WebMvcConfigurer.getLoginUser();
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

    @UserLoginToken
    @GetMapping("/admin/list")
    public Result getAdminUser() {
        List<User> user = userService.findAdminUsers();

        return ResultGenerator.genSuccessResult(user);
    }

    @UserLoginToken
    @PutMapping("/modification/password")
    public Result changePassword(@RequestParam String oldPass, @RequestParam String newPass) {
        User user = WebMvcConfigurer.getLoginUser();

        if (user.getPassword().equals(MD5Utils.StringToMD5_hex(oldPass))) {
            user.setPassword(MD5Utils.StringToMD5_hex(newPass));
        } else {
            return ResultGenerator.genFailResult("原密码错误, 请再次尝试~");
        }

        this.update(user);

        return ResultGenerator.genSuccessResult();
    }
}
