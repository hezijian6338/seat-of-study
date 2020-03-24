package com.study.room.model;

import javax.persistence.*;
import java.sql.Timestamp;

public class Seat {

    interface STATUS {
        /**
         * 座位为空
         */
        Integer EMPTY = 0;

        /**
         * 座位被坐下
         */
        Integer FULL = 1;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 自习室名称
     */
    private String name;

    /**
     * 自习室编号
     */
    @Column(name = "room_number")
    private String roomNumber;

    /**
     * 座位位置和状态
     */
    private String seats;

    /**
     * 座位总数
     */
    @Column(name = "seats_count")
    private Integer seatsCount;

    /**
     * 剩余座位数量
     */
    @Column(name = "seats_available")
    private Integer seatsAvailable;

    /**
     * 已使用座位数量
     */
    @Column(name = "seats_unavailabe")
    private Integer seatsUnavailabe;

    /**
     * 0: 待用; 1: 启用; -1: 维护
     */
    private Integer status;

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
     * 获取自习室名称
     *
     * @return name - 自习室名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置自习室名称
     *
     * @param name 自习室名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取自习室编号
     *
     * @return room_number - 自习室编号
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * 设置自习室编号
     *
     * @param roomNumber 自习室编号
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * 获取座位位置和状态
     *
     * @return seats - 座位位置和状态
     */
    public String getSeats() {
        return seats;
    }

    /**
     * 设置座位位置和状态
     *
     * @param seats 座位位置和状态
     */
    public void setSeats(String seats) {
        this.seats = seats;
    }

    /**
     * 获取座位总数
     *
     * @return seats_count - 座位总数
     */
    public Integer getSeatsCount() {
        return seatsCount;
    }

    /**
     * 设置座位总数
     *
     * @param seatsCount 座位总数
     */
    public void setSeatsCount(Integer seatsCount) {
        this.seatsCount = seatsCount;
    }

    /**
     * 获取剩余座位数量
     *
     * @return seats_available - 剩余座位数量
     */
    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    /**
     * 设置剩余座位数量
     *
     * @param seatsAvailable 剩余座位数量
     */
    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    /**
     * 获取已使用座位数量
     *
     * @return seats_unavailabe - 已使用座位数量
     */
    public Integer getSeatsUnavailabe() {
        return seatsUnavailabe;
    }

    /**
     * 设置已使用座位数量
     *
     * @param seatsUnavailabe 已使用座位数量
     */
    public void setSeatsUnavailabe(Integer seatsUnavailabe) {
        this.seatsUnavailabe = seatsUnavailabe;
    }

    /**
     * 获取0: 待用; 1: 启用; -1: 维护
     *
     * @return status - 0: 待用; 1: 启用; -1: 维护
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0: 待用; 1: 启用; -1: 维护
     *
     * @param status 0: 待用; 1: 启用; -1: 维护
     */
    public void setStatus(Integer status) {
        this.status = status;
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