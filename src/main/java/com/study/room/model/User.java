package com.study.room.model;

import javax.persistence.*;
import java.sql.Timestamp;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 管理员/学生
     */
    private String password;

    /**
     * 学生学号
     */
    @Column(name = "student_num")
    private String studentNum;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 0: 普通用户;1: 管理员; 2: 超级管理员
     */
    private Integer role;

    /**
     * 0: 停用; 1: 启用; -1: 黑名单
     */
    private Integer status;

    /**
     * 默认0, 到3 自动转换黑名单
     */
    @Column(name = "bad_record")
    private Integer badRecord;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "updated_time")
    private Timestamp updatedTime;

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
     * 获取管理员/学生
     *
     * @return password - 管理员/学生
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置管理员/学生
     *
     * @param password 管理员/学生
     */
    public void setPassword(String password) {
        this.password = password;
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

    /**
     * @return created_time
     */
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime
     */
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return updated_time
     */
    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    /**
     * @param updatedTime
     */
    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }
}