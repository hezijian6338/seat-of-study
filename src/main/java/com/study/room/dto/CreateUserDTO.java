package com.study.room.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Macbook on 2020/4/11.
 */
@ApiModel(value = "CreateUserDTO", description = "用户信息传输对象")
public class CreateUserDTO {
    /**
     * 用户名称
     */
    @ApiModelProperty(name = "name", value = "学生名称 (例子: 何子健)", example="何子健", required=true)
    private String name;


    /**
     * 学生学号
     */
    @ApiModelProperty(name = "studentNum", value = "学生学号 (例子: 140202011031)", example="140202011031", required=true)
    private String studentNum;

    /**
     * 用户电话
     */
    @ApiModelProperty(name = "phone", value = "学生电话 (例子: 13160666721)", example="13160666721", required=true)
    private String phone;

    /**
     * 0: 普通用户;1: 管理员; 2: 超级管理员
     */
    @ApiModelProperty(name = "role", value = "0: 普通用户;1: 管理员; 2: 超级管理员", example="0", required=true)
    private Integer role;

    /**
     * 0: 停用; 1: 启用; -1: 黑名单
     */
    @ApiModelProperty(value = "0: 停用; 1: 启用; -1: 黑名单", example="1", required=true)
    private Integer status;

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
}
