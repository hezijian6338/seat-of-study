package com.study.room.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Macbook on 2020/3/21.
 */
@ApiModel(value = "用户信息传输对象")
public class UserDTO {

    @ApiModelProperty(value = "用户 id")
    private String id;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "学生名称")
    private String name;


    /**
     * 学生学号
     */
    @ApiModelProperty(value = "学生学号")
    private String studentNum;

    /**
     * 用户电话
     */
    @ApiModelProperty(value = "学生电话")
    private String phone;

    /**
     * 0: 普通用户;1: 管理员; 2: 超级管理员
     */
    @ApiModelProperty(value = "0: 普通用户;1: 管理员; 2: 超级管理员")
    private Integer role;

    /**
     * 0: 停用; 1: 启用; -1: 黑名单
     */
    @ApiModelProperty(value = "0: 停用; 1: 启用; -1: 黑名单")
    private Integer status;

    /**
     * 默认0, 到3 自动转换黑名单
     */
    @ApiModelProperty(value = "黑名单次数记录")
    private Integer badRecord;

    /**
     * 登陆必须携带的 token
     */
    @ApiModelProperty(value = "登陆携带的 token")
    private String token;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名称
     *
     * @return name - 用户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名称
     *
     * @param name 用户名称
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * 获取学生学号
     *
     * @return student_num - 学生学号
     */
    public String getStudentNum() {
        return studentNum;
    }

    /**
     * 设置学生学号
     *
     * @param studentNum 学生学号
     */
    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    /**
     * 获取用户电话
     *
     * @return phone - 用户电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置用户电话
     *
     * @param phone 用户电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取0: 普通用户;1: 管理员; 2: 超级管理员
     *
     * @return role - 0: 普通用户;1: 管理员; 2: 超级管理员
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置0: 普通用户;1: 管理员; 2: 超级管理员
     *
     * @param role 0: 普通用户;1: 管理员; 2: 超级管理员
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 获取0: 停用; 1: 启用; -1: 黑名单
     *
     * @return status - 0: 停用; 1: 启用; -1: 黑名单
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0: 停用; 1: 启用; -1: 黑名单
     *
     * @param status 0: 停用; 1: 启用; -1: 黑名单
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取默认0, 到3 自动转换黑名单
     *
     * @return bad_record - 默认0, 到3 自动转换黑名单
     */
    public Integer getBadRecord() {
        return badRecord;
    }

    /**
     * 设置默认0, 到3 自动转换黑名单
     *
     * @param badRecord 默认0, 到3 自动转换黑名单
     */
    public void setBadRecord(Integer badRecord) {
        this.badRecord = badRecord;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
